package com.ivan.web.controller.vide;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ivan.api.vide.TbMediaService;
import org.ivan.entity.utils.MapObjectUtil;
import org.ivan.entity.utils.PageObject;
import org.ivan.entity.utils.ParameterEunm;
import org.ivan.entity.utils.ReMessage;
import org.ivan.entity.vide.TbMedia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author zhoulibo
 *	视频播放器
 */
@Api(value = "视频播放器", description = "视频播放器类")
@Controller
@RequestMapping("/vide")
public class VideController {
	private Logger logger = LoggerFactory.getLogger(VideController.class);
	@Reference
	private TbMediaService tbMediaService;
	
	@ApiOperation("获取视频列表")
	@RequestMapping("/videList")
	public ModelAndView getVideList(@RequestParam Map<String,Object> map) {
		logger.info("获取视频分页列表");
		ModelAndView me=new ModelAndView("vide/vide-list");
		PageObject<TbMedia> pageList=tbMediaService.Pagequery(map);
		me.addObject("list", pageList);
		return me;
	}
	@ApiOperation("添加视频信息")
	@ResponseBody
	@RequestMapping("/addVide")
	public Map<String,Object> addVide(@RequestParam Map<String,Object> map){
		logger.info("添加视频信息");
		Map<String,Object> returnMap=new HashMap<String, Object>();
		boolean isNotNull = MapObjectUtil.checkObject(new String[] { "title", "src"}, map);
		if(isNotNull) {
			TbMedia t=new TbMedia();
			t.setDescript(map.get("descript").toString());
			t.setPicture(map.get("picture").toString());
			t.setUptime(new Date());
			t.setSrc(map.get("src").toString());
			t.setTitle(map.get("title").toString());
			t.setVip(Integer.valueOf(map.get("vip").toString()));
			tbMediaService.insert(t);
			returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE,null);
		}
		return returnMap;
	}
	
	@ApiOperation("去添加视频信息页面")
	@RequestMapping("/goAddVideHtml")
	public ModelAndView goAddVideHtml(@RequestParam Map<String,Object> map){
		logger.info("获取视频分页列表");
		ModelAndView me=new ModelAndView("vide/vide-add");
		return me;
	}
	
	@ApiOperation("体验用户观看首页视频列表")
	@RequestMapping("/getVideIndex")
	public ModelAndView getVideIndex(@RequestParam Map<String,Object> map) {
		logger.info("用户观看首页视频列表");
		map.put("vip",2);
		ModelAndView me=new ModelAndView("vide/vide-index");
		PageObject<TbMedia> pageList=tbMediaService.Pagequery(map);
		me.addObject("list", pageList);
		return me;
	}
	@ApiOperation("删除")
	@RequestMapping("/delVide")
	@ResponseBody
	public Map<String,Object> delVide(@RequestParam Map<String,Object> map){
		Map<String,Object> returnMap=new HashMap<String, Object>();
		TbMedia tbMedia=new TbMedia();
		tbMedia.setId(Integer.valueOf(map.get("id").toString()));
		tbMediaService.del(tbMedia);
		returnMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE,null);
		return returnMap;
	}
	
	@ApiOperation("VIP用户观看首页视频列表")
	@RequestMapping("/getVipVideIndex")
	public ModelAndView getVipVideIndex(@RequestParam Map<String,Object> map) {
		logger.info("用户观看首页视频列表");
		map.put("vip",1);
		ModelAndView me=new ModelAndView("vide/vide-vip-index");
		PageObject<TbMedia> pageList=tbMediaService.Pagequery(map);
		me.addObject("list", pageList);
		return me;
	}
}
