package com.ivan.dubbo.service.impl.film;

import java.util.List;
import java.util.Map;

import org.ivan.api.file.FilmCommentService;
import org.ivan.entity.file.FilmComment;
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FilmComment> getList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void del(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void del(FilmComment t) {
		// TODO Auto-generated method stub
		
	}

    
 
}
