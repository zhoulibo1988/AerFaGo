package com.ivan.web.controller.film;

import java.util.Map;

import org.ivan.entity.it.ItData;
import org.ivan.entity.utils.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ivan.web.controller.it.ItDataController;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName: FilmController
 * @Description: 电影首页
 * @author Mr.zhou
 * @date 2019年2月20日 下午7:20:01
 *
 */
@Controller
@RequestMapping("/film")
public class FilmController {
	private Logger logger = LoggerFactory.getLogger(ItDataController.class);
	@ApiOperation("获取电影首页")
	@RequestMapping("/getFilm")
	public ModelAndView getFilm(@RequestParam Map<String, Object> map) {
		logger.info("获取电影首页");
		ModelAndView view = new ModelAndView("film/film/index");
		return view;
	}
}
