package com.ivan.dubbo.service.impl.film;

import java.util.List;
import java.util.Map;

import org.ivan.api.file.FilmLoginService;
import org.ivan.entity.file.FilmLogin;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.film.FilmLoginMapper;

/**
 * @author cyl
 * @version 
 */
@Service
public class FilmLoginServiceImpl implements FilmLoginService {
	// 注入当前dao对象
    @Autowired
    private FilmLoginMapper filmLoginMapper;

	@Override
	public void insert(FilmLogin t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FilmLogin selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilmLogin selectSingle(FilmLogin t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateByEntity(FilmLogin t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByEntity(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageObject<FilmLogin> Pagequery(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FilmLogin> getList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void del(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void del(FilmLogin t) {
		// TODO Auto-generated method stub
		
	}

    
 
}
