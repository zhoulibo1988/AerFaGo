package com.ivan.web.controller.film;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ivan.api.file.FilmCollectionService;
import org.ivan.api.file.FilmInfoService;
import org.ivan.entity.file.FilmCollection;
import org.ivan.entity.file.FilmInfo;
import org.ivan.entity.file.FilmUser;
import org.ivan.entity.utils.PageObject;
import org.ivan.entity.utils.ParameterEunm;
import org.ivan.entity.utils.ReMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ivan.web.controller.admin.BaseManagerController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * 
* @ClassName: FilmCollectionController 
* @Description: 用户收藏电影操作类 
* @author Mr.zhou
* @date 2019年2月28日 下午2:45:41 
*
 */
@Api("用户收藏电影操作类")
@Controller
@RequestMapping("/filmCollection")
public class FilmCollectionController extends BaseManagerController<FilmCollection>{
	
	@Reference
	private FilmCollectionService filmCollectionService;
	@Reference
	private FilmInfoService filmInfoService;
	@ApiOperation("添加收藏电影")
	@RequestMapping("/addFilmCollection")
	@ResponseBody
	public Map<String,Object> addFilmCollection(@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap=new HashMap<>();
		FilmUser user=getAdmin(request, response);
		if(user!=null) {
			FilmCollection collection=new FilmCollection();
			collection.setFilmId(Integer.valueOf(map.get("filmId").toString()));
			collection.setUserId(user.getId());
			FilmCollection collection1=filmCollectionService.selectSingle(collection);
			if(collection1!=null) {
				resultMap = ReMessage.resultBack(ParameterEunm.ERROR_403_CODE, null);
				return resultMap;
			}
			collection.setCreationTime(new Date());
			filmCollectionService.insert(collection);
			System.out.println("添加成功");
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE, null);
		}else {
			System.out.println("用户未登录");
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_LOGIN_CODE, null);
		}
		return resultMap;
	}
	@ApiOperation("收藏电影列表")
	@RequestMapping("/collectionHtml")
	public ModelAndView collectionHtml(@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView av=new ModelAndView("film/film/moviecol");
		map.put("pageData",5);
		FilmUser user=getAdmin(request, response);
		if(user!=null) {
			map.put("userId", user.getId());
			PageObject<FilmCollection> collectionPage=filmCollectionService.Pagequery(map);
			if(collectionPage!=null) {
				List<FilmCollection> collectionList=collectionPage.getDataList();
				for (FilmCollection filmCollection : collectionList) {
					//获取影片详情
					FilmInfo filmInfo=new FilmInfo();
					filmInfo.setId(filmCollection.getFilmId());
					filmInfo.setFilmState(1);
					filmInfo=filmInfoService.selectSingle(filmInfo);
					if(filmInfo!=null) {
						filmCollection.setFilmName(filmInfo.getFilmName());
						filmCollection.setFilmIntroduce(filmInfo.getFilmIntroduce());
					}
				}
			}
			av.addObject("pageList",collectionPage);
		}else {
			System.out.println("用户未登录");
		}
		av.addObject("filmUser",user);
		return av;
	}
}
