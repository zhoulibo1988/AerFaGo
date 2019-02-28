package com.ivan.web.controller.film;
/**
* @ClassName: FilmCommentController 
* @Description: 用户评论操作类
* @author Mr.zhou
* @date 2019年2月28日 上午11:40:14 
*
*/

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ivan.api.file.FilmCommentService;
import org.ivan.api.file.FilmInfoService;
import org.ivan.api.file.FilmUserService;
import org.ivan.entity.file.FilmComment;
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

import io.swagger.annotations.ApiOperation;
/**
* @ClassName: FilmCommentController 
* @Description: 用户评论操作类
* @author Mr.zhou
* @date 2019年2月28日 下午2:31:20 
*
*/
@Controller
@RequestMapping("/filmComment")
public class FilmCommentController extends BaseManagerController<FilmComment>{
	@Reference
	private FilmCommentService filmCommentService;
	@Reference
	private FilmUserService filmUserService;
	@Reference
	private FilmInfoService FilmInfoService;
	@ApiOperation("添加用户评论")
	@RequestMapping("/addComment")
	@ResponseBody
	public Map<String,Object> addComment(@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap=new HashMap<>();
		FilmComment filmComment=new FilmComment();
		filmComment.setCommentTime(new Date());
		filmComment.setCommentTxt(map.get("commentTxt").toString());
		filmComment.setCreationTime(new Date());
		filmComment.setFilmId(Integer.valueOf(map.get("filmId").toString()));
		FilmUser user=getAdmin(request, response);
		if(user!=null) {
			filmComment.setUserId(user.getId());
			filmCommentService.insert(filmComment);
			FilmInfo FilmInfo=new FilmInfo();
			FilmInfo.setId(Integer.valueOf(map.get("filmId").toString()));
			FilmInfo=FilmInfoService.selectSingle(FilmInfo);
			int commentNumber=FilmInfo.getCommentNumber();
			commentNumber=commentNumber+1;
			FilmInfo.setCommentNumber(commentNumber);
			FilmInfoService.updateByEntity(FilmInfo);
			resultMap = ReMessage.resultBack(ParameterEunm.SUCCESSFUL_CODE,null);
		}else {
			System.out.println("用户未登录");
			resultMap = ReMessage.resultBack(ParameterEunm.ERROR_LOGIN_CODE, null);
		}
		return resultMap;
		
	}
	
	@ApiOperation("获取某用户评论")
	@RequestMapping("/getUserComment")
	public ModelAndView getUserComment(@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView view=new ModelAndView("film/film/comments");
		FilmUser user=getAdmin(request, response);
		map.put("pageData",5);
		map.put("curPage",map.get("curPage"));
		map.put("userId", user.getId());
		PageObject<FilmComment> filmCommentPage=filmCommentService.Pagequery(map);
		if(filmCommentPage!=null) {
			List<FilmComment> list=filmCommentPage.getDataList();
			for (FilmComment filmComment2 : list) {
				FilmUser filmUser=new FilmUser();
				filmUser.setId(filmComment2.getUserId());
				filmUser=filmUserService.selectSingle(filmUser);
				if(filmUser!=null) {
					filmComment2.setUserName(filmUser.getUserEmil());
					filmComment2.setUserUrl(filmUser.getUserImage());
				}
			}
			view.addObject("pagelist", filmCommentPage);
		}
		view.addObject("filmUser", user);
		return view;
	}
}
