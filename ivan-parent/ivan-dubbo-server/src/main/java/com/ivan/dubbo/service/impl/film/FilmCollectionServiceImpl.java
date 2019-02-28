package com.ivan.dubbo.service.impl.film;

import java.util.List;
import java.util.Map;

import org.ivan.api.file.FilmCollectionService;
import org.ivan.entity.file.FilmCollection;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.film.FilmCollectionMapper;

/**
 * @author cyl
 * @version 
 */
@Service
public class FilmCollectionServiceImpl  implements FilmCollectionService {
	// 注入当前dao对象
    @Autowired
    private FilmCollectionMapper filmCollectionMapper;

	@Override
	public void insert(FilmCollection t) {
		filmCollectionMapper.insertByEntity(t);
		
	}

	@Override
	public FilmCollection selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilmCollection selectSingle(FilmCollection t) {
		// TODO Auto-generated method stub
		return filmCollectionMapper.selectSingle(t);
	}

	@Override
	public void updateByEntity(FilmCollection t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByEntity(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageObject<FilmCollection> Pagequery(Map<String, Object> map) {
		if(!map.containsKey("curPage")&&!map.containsKey("pageData")){
    		map.put("curPage", 1);
    		map.put("pageData",5);
    	}
    	int totalData=	filmCollectionMapper.getCount(map);
    	PageHelper pageHelper = new PageHelper(totalData, map);
		List<FilmCollection> list = filmCollectionMapper.pageQueryByObject(pageHelper.getMap());
		PageObject<FilmCollection> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
	}

	@Override
	public List<FilmCollection> getList(Map<String, Object> map) {
		return null;
	}
	
	@Override
	public void del(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void del(FilmCollection t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FilmCollection> getList(FilmCollection t) {
		return filmCollectionMapper.selectByObject(t);
	}

 
}
