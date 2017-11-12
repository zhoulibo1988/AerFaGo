package com.ivan.web.controller.swagger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * 测试swagger接口文档
 * @author 周立波
 *
 */
@Api(value = "测试API", description = "测试API")
@RestController
@RequestMapping("/test")
public class TestController {
		/**
		 * 测试方法
		 * @param name
		 * @param password
		 * @return
		 */
		@ApiOperation(value="测试方法",httpMethod="POST")
		@ResponseBody
	 	@RequestMapping(value = "/hello")
	    public String hello(@ApiParam(required=true,value="用户名字",name="name")@RequestParam(value="name")String name,
	    					@ApiParam(required=true,value="用户密码",name="password")@RequestParam(value="password")String password) {
	        return "hello "+name;
	    }
}
