package com.ivan.dubbo.service.impl.it;

import java.util.List;
import java.util.Map;

import org.ivan.api.it.ItDataService;
import org.ivan.entity.admin.SysUcenterAdmin;
import org.ivan.entity.it.ItData;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.it.ItDataMapper;


/**
 * @author cyl
 * @version 
 */
@Service
public class ItDataServiceImpl implements ItDataService {
	// 注入当前dao对象
    @Autowired
    private ItDataMapper itDataMapper;

	@Override
	public void insert(ItData t) {
		itDataMapper.insertByEntity(t);
	}

	@Override
	public ItData selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItData selectSingle(ItData t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateByEntity(ItData t) {
		itDataMapper.updateByEntity(t);
		
	}

	@Override
	public void updateByEntity(Map<String, Object> map) {
		
		
	}

	@Override
	public PageObject<ItData> Pagequery(Map<String, Object> map) {
		if(!map.containsKey("curPage")&&!map.containsKey("pageData")){
    		map.put("curPage", 1);
    		map.put("pageData", 10);
    	}
    	int totalData=	itDataMapper.getCount(map);
    	PageHelper pageHelper = new PageHelper(totalData, map);
		List<ItData> list = itDataMapper.pageQueryByObject(pageHelper.getMap());
		PageObject<ItData> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
	}

	@Override
	public List<ItData> getList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void del(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void del(ItData t) {
		// TODO Auto-generated method stub
		
	}

 
}
