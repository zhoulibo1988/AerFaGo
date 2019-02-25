package com.ivan.dubbo.service.impl.film;

import java.util.List;
import java.util.Map;

import org.ivan.api.file.FilmUserService;
import org.ivan.entity.file.FilmUser;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.film.FilmUserMapper;

/**
 * @author cyl
 * @version 
 */
@Service
public class FilmUserServiceImpl  implements FilmUserService {
	// 注入当前dao对象
    @Autowired
    private FilmUserMapper filmUserMapper;

	@Override
	public void insert(FilmUser t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FilmUser selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilmUser selectSingle(FilmUser t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateByEntity(FilmUser t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByEntity(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageObject<FilmUser> Pagequery(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FilmUser> getList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void del(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void del(FilmUser t) {
		// TODO Auto-generated method stub
		
	}

 
}
