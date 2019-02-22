package com.ivan.dubbo.service.impl.film;

import java.util.List;
import java.util.Map;

import org.ivan.api.file.FilmInfoService;
import org.ivan.entity.file.FilmInfo;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.film.FilmInfoMapper;

/**
 * @author cyl
 * @version 
 */
@Service
public class FilmInfoServiceImpl  implements FilmInfoService {
	// 注入当前dao对象
    @Autowired
    private FilmInfoMapper filmInfoMapper;

	@Override
	public void insert(FilmInfo t) {
		filmInfoMapper.insertByEntity(t);
	}

	@Override
	public FilmInfo selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilmInfo selectSingle(FilmInfo t) {
		return filmInfoMapper.selectSingle(t);
	}

	@Override
	public void updateByEntity(FilmInfo t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByEntity(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageObject<FilmInfo> Pagequery(Map<String, Object> map) {
		if(!map.containsKey("curPage")&&!map.containsKey("pageData")){
    		map.put("curPage", 1);
    		map.put("pageData", 10);
    	}
    	int totalData=	filmInfoMapper.getCount(map);
    	PageHelper pageHelper = new PageHelper(totalData, map);
		List<FilmInfo> list = filmInfoMapper.pageQueryByObject(pageHelper.getMap());
		PageObject<FilmInfo> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
	}

	@Override
	public List<FilmInfo> getList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void del(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void del(FilmInfo t) {
		// TODO Auto-generated method stub
		
	}

    
 
}
