package com.ivan.dubbo.service.impl.film;

import java.util.List;
import java.util.Map;

import org.ivan.api.file.FilmStarService;
import org.ivan.entity.file.FilmStar;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.film.FilmStarMapper;

/**
 * @author cyl
 * @version 
 */
@Service
public class FilmStarServiceImpl  implements FilmStarService {
	// 注入当前dao对象
    @Autowired
    private FilmStarMapper filmStarMapper;

	@Override
	public void insert(FilmStar t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FilmStar selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilmStar selectSingle(FilmStar t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateByEntity(FilmStar t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByEntity(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageObject<FilmStar> Pagequery(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FilmStar> getList(Map<String, Object> map) {
		return filmStarMapper.selectByObject(null);
	}

	@Override
	public void del(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void del(FilmStar t) {
		// TODO Auto-generated method stub
		
	}

 
}
