package com.ivan.dubbo.service.impl.film;

import java.util.List;
import java.util.Map;

import org.ivan.api.file.FilmCountryService;
import org.ivan.entity.file.FilmCountry;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.film.FilmCountryMapper;

/**
 * @author cyl
 * @version 
 */
@Service
public class FilmCountryServiceImpl implements FilmCountryService {
	// 注入当前dao对象
    @Autowired
    private FilmCountryMapper filmCountryMapper;

	@Override
	public void insert(FilmCountry t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FilmCountry selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilmCountry selectSingle(FilmCountry t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateByEntity(FilmCountry t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByEntity(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageObject<FilmCountry> Pagequery(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FilmCountry> getList(Map<String, Object> map) {
		return filmCountryMapper.selectByObject(null);
	}

	@Override
	public void del(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void del(FilmCountry t) {
		// TODO Auto-generated method stub
		
	}

    
    
 
}
