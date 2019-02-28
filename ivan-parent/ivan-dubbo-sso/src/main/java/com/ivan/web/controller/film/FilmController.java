package com.ivan.web.controller.film;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ivan.api.file.FilmCommentService;
import org.ivan.api.file.FilmCountryService;
import org.ivan.api.file.FilmInfoService;
import org.ivan.api.file.FilmLabelService;
import org.ivan.api.file.FilmStarService;
import org.ivan.api.file.FilmUserService;
import org.ivan.core.redis.RedisDBUtil;
import org.ivan.entity.file.FilmComment;
import org.ivan.entity.file.FilmCountry;
import org.ivan.entity.file.FilmInfo;
import org.ivan.entity.file.FilmLabel;
import org.ivan.entity.file.FilmStar;
import org.ivan.entity.file.FilmUser;
import org.ivan.entity.utils.CookieUtils;
import org.ivan.entity.utils.PageObject;
import org.ivan.entity.utils.ParameterEunm;
import org.ivan.entity.utils.ReMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ivan.web.controller.admin.BaseManagerController;

import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: FilmController
 * @Description: 电影操作类
 * @author Mr.zhou
 * @date 2019年2月20日 下午7:20:01
 *
 */
@Controller
@RequestMapping("/film")
public class FilmController extends BaseManagerController<FilmUser>{
	@Reference
	private FilmInfoService filmInfoService;
	@Reference
	private FilmLabelService filmLabelService;
	@Reference
	private FilmCountryService filmCountryService;
	@Reference
	private FilmStarService filmStarService;
	@Reference
	private FilmCommentService filmCommentService;
	@Reference
	private FilmUserService filmUserService;
	private Logger logger = LoggerFactory.getLogger(FilmController.class);
	@ApiOperation("获取电影首页")
	@RequestMapping("/getFilm")
	public ModelAndView getFilm(@RequestParam Map<String, Object> map,HttpServletRequest request,HttpServletResponse response) {
		logger.info("获取电影首页");
		//
		ModelAndView view = new ModelAndView("film/film/index");
			//判断用户是否登录
	        //1.从cookie中取token
	       String token= CookieUtils.getCookieValue(request, "userToken");
	       //2.从redis获取用户信息
	       if(token!=null) {
	    	  String userInfo= RedisDBUtil.redisDao.getString(token);
	    	  if(userInfo!=null) {
	    		  FilmUser filmUser = JSON.parseObject(userInfo, FilmUser.class);
	    		  if(filmUser.getIsVip()==1) {
	    			  map.put("filmVip", 1);
	    		  }else {
	    			  map.put("filmVip", 2); 
	    		  }
	    		  view.addObject("filmUser",filmUser);
	    	  }else {
	    		  map.put("filmVip", 2);  
	    	  }
	       }else {
	    	   map.put("filmVip", 2);
	       }
		PageObject<FilmInfo> infoList=filmInfoService.Pagequery(map);
		if(infoList==null) {
			infoList=new PageObject<FilmInfo>();
			
		}
		//获取标签
		List<FilmLabel> lablelist=filmLabelService.getList(null);
		//xing ji
		List<FilmStar> starList=filmStarService.getList(null);
		view.addObject("list", infoList);
		view.addObject("lablelist", lablelist);
		view.addObject("starList", starList);
		return view;
	}
	@ApiOperation("获取电影详情页面")
	@RequestMapping("/getFilmInfo")
	public ModelAndView getFilmInfo(@RequestParam Map<String, Object> map,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView view = new ModelAndView("film/film/play");
		FilmInfo filmInfo=new FilmInfo();
		filmInfo.setId(Integer.valueOf(map.get("id").toString()));
		filmInfo.setFilmState(1);
		filmInfo=filmInfoService.selectSingle(filmInfo);
		if(filmInfo!=null) {
			//增加播放量
			int paynum=filmInfo.getPlayNumber();
			paynum=paynum+1;
			filmInfo.setPlayNumber(paynum);
			filmInfoService.updateByEntity(filmInfo);
			//获取标签
			int labelid=filmInfo.getLabelId();
			FilmLabel filmLabel=new FilmLabel();
			filmLabel.setId(labelid);
			filmLabel=filmLabelService.selectSingle(filmLabel);
			view.addObject("filmLabel", filmLabel);
			
			// guo jia id
			int countryid=filmInfo.getCountryId();
			FilmCountry filmCountry=new FilmCountry();
			filmCountry.setId(countryid);
			filmCountry=filmCountryService.selectSingle(filmCountry);
			view.addObject("filmCountry", filmCountry);
			
			//xing ji
			int starid=filmInfo.getStarId();
			FilmStar statr=new FilmStar();
			statr.setId(starid);
			statr=filmStarService.selectSingle(statr);
			view.addObject("filmstatr", statr);
			//此电影获取评论
			FilmComment filmComment=new FilmComment();
			filmComment.setFilmId(filmInfo.getId());
			Map<String,Object> commentMap=new HashMap<>();
			commentMap.put("filmId", filmInfo.getId());
			commentMap.put("pageData",5);
			commentMap.put("curPage",map.get("curPage"));
			PageObject<FilmComment> pagelist=filmCommentService.Pagequery(commentMap);
			if(pagelist!=null) {
				List<FilmComment> list=pagelist.getDataList();
				for (FilmComment filmComment2 : list) {
					FilmUser filmUser=new FilmUser();
					filmUser.setId(filmComment2.getUserId());
					filmUser=filmUserService.selectSingle(filmUser);
					if(filmUser!=null) {
						filmComment2.setUserName(filmUser.getUserEmil());
						filmComment2.setUserUrl(filmUser.getUserImage());
					}
				}
				view.addObject("pagelist", pagelist);
				view.addObject("filmCommentNum", pagelist.getTotalData());
			}
			
		}
		FilmUser filmUser=getAdmin(request, response);
		view.addObject("filmInfo", filmInfo);
		view.addObject("filmUser", filmUser);
		return view;
	}
	/**
	* @Title: getFilmList 
	* @Description: 获取管理页面的电影列表
	* @param @param pageData
	* @param @param curPage
	* @param @return    设定文件 
	* @return ModelAndView    返回类型 
	* @throws
	*/
	
	@ApiOperation("获取管理页面的电影列表")
	@RequestMapping("/getFilmList")
	public ModelAndView getFilmList(@RequestParam Map<String,Object> map){
		PageObject<FilmInfo> infoList=filmInfoService.Pagequery(map);
		if(infoList==null) {
			infoList=new PageObject<FilmInfo>();
			
		}
		ModelAndView view = new ModelAndView("film/film-list");
		view.addObject("list", infoList);
		return view;
		
	}
	/**
	* @Title: getAddFilmHtml 
	* @Description: 跳转管理电影添加页面
	* @param @return    设定文件 
	* @return ModelAndView    返回类型 
	* @throws
	 */
	@ApiOperation("跳转管理电影添加页面")
	@RequestMapping("/getAddFilmHtml")
	public ModelAndView getAddFilmHtml(@RequestParam Map<String,Object> map){
		List<FilmLabel> list=filmLabelService.getList(map);
		List<FilmCountry> filmCountryList=filmCountryService.getList(map);
		List<FilmStar> filmStarList=filmStarService.getList(map);
		ModelAndView view = new ModelAndView("film/film-add");
		view.addObject("filmLabelList", list);
		view.addObject("filmCountryList", filmCountryList);
		view.addObject("filmStarList", filmStarList);
		return view;
	}
	
	@ApiOperation("添加电影数据提交")
	@ResponseBody
	@RequestMapping("addFilmInfo")
	public Map<String,Object> addFilmInfo(@RequestParam Map<String,Object> map){
		Map<String,Object> returnMap=new HashMap<>();
		FilmInfo filmInfo=new FilmInfo();
		filmInfo.setCountryId(Integer.valueOf(map.get("countryId").toString()));
		filmInfo.setCreationTime(new Date());
		filmInfo.setFilmIntroduce( map.get("filmIntroduce").toString());
		filmInfo.setFilmName( map.get("filmName").toString());
		filmInfo.setFilmState(1);
		filmInfo.setFilmTime( map.get("filmTime").toString());
		filmInfo.setFilmVip(2);
		filmInfo.setLabelId( Integer.valueOf(map.get("labelId").toString()));
		filmInfo.setPlayUrl( map.get("playUrl").toString());
		filmInfo.setReleaseTime( map.get("releaseTime").toString());
		filmInfo.setStarId( Integer.valueOf(map.get("starId").toString()));
		filmInfoService.insert(filmInfo);
		returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE,null);
		return returnMap;
	}
	
}
