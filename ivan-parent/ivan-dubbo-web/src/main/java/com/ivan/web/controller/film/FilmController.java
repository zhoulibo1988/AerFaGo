package com.ivan.web.controller.film;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ivan.api.file.FilmCountryService;
import org.ivan.api.file.FilmInfoService;
import org.ivan.api.file.FilmLabelService;
import org.ivan.api.file.FilmStarService;
import org.ivan.entity.file.FilmCountry;
import org.ivan.entity.file.FilmInfo;
import org.ivan.entity.file.FilmLabel;
import org.ivan.entity.file.FilmStar;
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
import com.ivan.web.controller.it.ItDataController;

import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: FilmController
 * @Description: 电影首页
 * @author Mr.zhou
 * @date 2019年2月20日 下午7:20:01
 *
 */
@Controller
@RequestMapping("/film")
public class FilmController {
	@Reference
	private FilmInfoService filmInfoService;
	@Reference
	private FilmLabelService filmLabelService;
	@Reference
	private FilmCountryService filmCountryService;
	@Reference
	private FilmStarService filmStarService;
	private Logger logger = LoggerFactory.getLogger(ItDataController.class);
	@ApiOperation("获取电影首页")
	@RequestMapping("/getFilm")
	public ModelAndView getFilm(@RequestParam Map<String, Object> map) {
		logger.info("获取电影首页");
		PageObject<FilmInfo> infoList=filmInfoService.Pagequery(map);
		if(infoList==null) {
			infoList=new PageObject<FilmInfo>();
			
		}
		ModelAndView view = new ModelAndView("film/film/index");
		view.addObject("list", infoList);
		return view;
	}
	@ApiOperation("获取电影详情页面")
	@RequestMapping("/getFilmInfo")
	public ModelAndView getFilmInfo(@RequestParam Map<String, Object> map) {
		ModelAndView view = new ModelAndView("film/film/play");
		FilmInfo filmInfo=new FilmInfo();
		filmInfo.setId(Integer.valueOf(map.get("id").toString()));
		filmInfo.setFilmState(1);
		filmInfo=filmInfoService.selectSingle(filmInfo);
		view.addObject("filmInfo", filmInfo);
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
