package com.ivan.web.controller.it;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.ivan.api.it.ItDataService;
import org.ivan.entity.it.ItData;
import org.ivan.entity.utils.MapObjectUtil;
import org.ivan.entity.utils.PageObject;
import org.ivan.entity.utils.ParameterEunm;
import org.ivan.entity.utils.ReMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
/**
 * 
 * @author 周立波
 *
 */
@Api(value = "IT管理资料类", description = "IT管理资料类")
@RestController
@RequestMapping("/ItData")
public class ItDataController {
	private Logger logger = LoggerFactory.getLogger(ItDataController.class);
	@Reference
	private ItDataService itDataService;
	@ApiOperation("添加IT管理资料")
	@ResponseBody
	@RequestMapping("/addItData")
	public Map<String,Object> addItData(@RequestParam Map<String, Object> map){
		logger.info("进入添加IT管理资料");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isNotNull = MapObjectUtil.checkObject(new String[] { "itName", "itType", "itUrl"}, map);
		if(isNotNull){
			ItData itData=new ItData();
			itData.setCreateTime(new Date());
			itData.setItDel(2);
			itData.setItName(map.get("itName").toString());
			if(null!=map.get("itPassword")){
				itData.setItPassword(map.get("itPassword").toString());
			}
			itData.setItState(2);
			itData.setItType(Integer.valueOf(map.get("itType").toString()));
			itData.setItUrl(map.get("itUrl").toString());
			itDataService.insert(itData);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE,null);
		}else{
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_PARAMS_CODE, null);
		}
		return resultMap;
	}
	
	@ApiOperation("上架/下架")
	@ResponseBody
	@RequestMapping("/updateItData")
	public  Map<String,Object> updateItData(@RequestParam Map<String, Object> map){
		logger.info("进入上架/下架IT管理资料");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isNotNull = MapObjectUtil.checkObject(new String[] { "id", "itState"}, map);
		if(isNotNull){
			ItData itData=new ItData();
			itData.setId(Integer.valueOf(map.get("id").toString()));
			itData.setItState(Integer.valueOf(map.get("itState").toString()));
			itDataService.updateByEntity(itData);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE,null);
		}else{
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_PARAMS_CODE, null);
		}
		return resultMap;
	}
	
	@ApiOperation("分页获取IT管理资料")
	@RequestMapping("/getItDataByPage")
	public ModelAndView getItDataByPage(@RequestParam Map<String, Object> map) {
		logger.info("分页获取IT管理资料");
		ModelAndView view = new ModelAndView("it/it-list");
		map.put("itDel", 2);
		PageObject<ItData> pageList = itDataService.Pagequery(map);
		view.addObject("list", pageList);
		return view;
	}
	@ApiOperation("去IT资料新增页面")
	@RequestMapping("/gotoAddItData")
	public ModelAndView gotoAddItData(@RequestParam Map<String, Object> map){
		ModelAndView view = new ModelAndView("it/it-add");
		return view;
	}
	
	@ApiOperation("删除")
	@ResponseBody
	@RequestMapping("/delItData")
	public  Map<String,Object> delItData(@RequestParam Map<String, Object> map){
		logger.info("删除IT管理资料");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isNotNull = MapObjectUtil.checkObject(new String[] { "id"}, map);
		if(isNotNull){
			ItData itData=new ItData();
			itData.setId(Integer.valueOf(map.get("id").toString()));
			itData.setItDel(1);
			itDataService.updateByEntity(itData);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE,null);
		}else{
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_PARAMS_CODE, null);
		}
		return resultMap;
	}
	
	@ApiOperation("分页获取IT管理资料_对外展示")
	@RequestMapping("/getItDataByPageW")
	public ModelAndView getItDataByPageW(@RequestParam Map<String, Object> map) {
		logger.info("分页获取IT管理资料");
		ModelAndView view = new ModelAndView("itw/house_list");
		map.put("itDel", 2);
		map.put("itState", 1);
		map.put("type", 2);
		PageObject<ItData> pageList = itDataService.Pagequery(map);
		view.addObject("list", pageList);
		view.addObject("type",2);
		return view;
	}
	
	@ApiOperation("php")
	@RequestMapping("/php")
	public ModelAndView php(@RequestParam Map<String, Object> map) {
		logger.info("php");
		ModelAndView view = new ModelAndView("itw/house_list");
		map.put("itDel", 2);
		map.put("itState", 1);
		map.put("itType", 5);
		map.put("type", 1);
		PageObject<ItData> pageList = itDataService.Pagequery(map);
		view.addObject("list", pageList);
		view.addObject("type",1);
		return view;
	}
}
