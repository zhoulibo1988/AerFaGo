package com.ivan.dubbo.service.impl.film;

import java.util.List;
import java.util.Map;

import org.ivan.api.file.FilmLabelService;
import org.ivan.entity.file.FilmLabel;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.film.FilmCountryMapper;
import com.ivan.dubbo.dao.film.FilmLabelMapper;

/**
 * @author cyl
 * @version 
 */
@Service
public class FilmLabelServiceImpl  implements FilmLabelService {
	// 注入当前dao对象
    @Autowired
    private FilmLabelMapper filmLabelMapper;

	@Override
	public void insert(FilmLabel t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FilmLabel selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilmLabel selectSingle(FilmLabel t) {
		// TODO Auto-generated method stub
		return filmLabelMapper.selectSingle(t);
	}

	@Override
	public void updateByEntity(FilmLabel t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByEntity(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageObject<FilmLabel> Pagequery(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FilmLabel> getList(Map<String, Object> map) {
		return filmLabelMapper.selectByObject(null);
	}

	@Override
	public void del(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void del(FilmLabel t) {
		// TODO Auto-generated method stub
		
	}

    
    
 
}
