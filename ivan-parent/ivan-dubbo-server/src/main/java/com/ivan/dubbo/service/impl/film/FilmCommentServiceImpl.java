package com.ivan.dubbo.service.impl.film;

import java.util.List;
import java.util.Map;

import org.ivan.api.file.FilmCommentService;
import org.ivan.entity.file.FilmComment;
import org.ivan.entity.file.FilmInfo;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.film.FilmCommentMapper;

/**
 * @author cyl
 * @version 
 */
@Service
public class FilmCommentServiceImpl  implements FilmCommentService {
	// 注入当前dao对象
    @Autowired
    private FilmCommentMapper filmCommentMapper;

	@Override
	public void insert(FilmComment t) {
		filmCommentMapper.insertByEntity(t);
		
	}

	@Override
	public FilmComment selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilmComment selectSingle(FilmComment t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateByEntity(FilmComment t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByEntity(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageObject<FilmComment> Pagequery(Map<String, Object> map) {
		if(!map.containsKey("curPage")&&!map.containsKey("pageData")){
    		map.put("curPage", 1);
    		map.put("pageData",5);
    	}
    	int totalData=	filmCommentMapper.getCount(map);
    	PageHelper pageHelper = new PageHelper(totalData, map);
		List<FilmComment> list = filmCommentMapper.pageQueryByObject(pageHelper.getMap());
		PageObject<FilmComment> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
	}

	@Override
	public List<FilmComment> getList(Map<String, Object> map) {
		FilmComment filmComment=new FilmComment();
		filmComment.setFilmId(Integer.valueOf(map.get("filmId").toString()));
		return filmCommentMapper.selectByObject(filmComment);
	}

	@Override
	public void del(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void del(FilmComment t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FilmComment> getListMap(Map<String, Object> map) {
		return filmCommentMapper.getListMap(map);
	}

    
 
}
