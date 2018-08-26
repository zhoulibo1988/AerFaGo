package com.ivan.dubbo.service.impl.vide;

import java.util.List;
import java.util.Map;

import org.ivan.api.vide.TbMediaService;
import org.ivan.entity.it.ItData;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.ivan.entity.vide.TbMedia;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.vide.TbMediaMapper;


/**
 * @author cyl
 * @version 
 */
@Service
public class TbMediaServiceImpl implements TbMediaService {
	// 注入当前dao对象
    @Autowired
    private TbMediaMapper tbMediaMapper;

	public void insert(TbMedia t) {
		tbMediaMapper.insertByEntity(t);
		
	}

	public TbMedia selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	public TbMedia selectSingle(TbMedia t) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateByEntity(TbMedia t) {
		// TODO Auto-generated method stub
		
	}

	public void updateByEntity(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	public PageObject<TbMedia> Pagequery(Map<String, Object> map) {
		if(!map.containsKey("curPage")&&!map.containsKey("pageData")){
    		map.put("curPage", 1);
    		map.put("pageData", 10);
    	}
    	int totalData=	tbMediaMapper.getCount(map);
    	PageHelper pageHelper = new PageHelper(totalData, map);
		List<TbMedia> list = tbMediaMapper.pageQueryByObject(pageHelper.getMap());
		PageObject<TbMedia> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
	}

	public List<TbMedia> getList(Map<String, Object> map) {
		return tbMediaMapper.selectByObject(map);
	}

	public void del(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	public void del(TbMedia t) {
		// TODO Auto-generated method stub
		
	}

 
}
