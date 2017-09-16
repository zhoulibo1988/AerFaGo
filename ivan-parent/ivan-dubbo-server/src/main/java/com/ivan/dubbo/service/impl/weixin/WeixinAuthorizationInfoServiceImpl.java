package com.ivan.dubbo.service.impl.weixin;

import java.util.List;
import java.util.Map;

import org.ivan.api.weixin.WeixinAuthorizationInfoService;
import org.ivan.entity.WeixinAuthorizationInfo;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.weixin.WeixinAuthorizationInfoMapper;


/**
 * @author cyl
 * @version 
 */
@Service
public class WeixinAuthorizationInfoServiceImpl implements WeixinAuthorizationInfoService {
	// 注入当前dao对象
    @Autowired
    private WeixinAuthorizationInfoMapper weixinAuthorizationInfoMapper;

	public WeixinAuthorizationInfo selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return weixinAuthorizationInfoMapper.selectSingle(map);
	}

	public WeixinAuthorizationInfo selectSingle(
			WeixinAuthorizationInfo WeixinAuthorizationInfo) {
		// TODO Auto-generated method stub
		return weixinAuthorizationInfoMapper.selectSingle(WeixinAuthorizationInfo);
	}

	public void updateByEntity(WeixinAuthorizationInfo WeixinAuthorizationInfo) {
		// TODO Auto-generated method stub
		weixinAuthorizationInfoMapper.updateByEntity(WeixinAuthorizationInfo);
	}

	public void insert(WeixinAuthorizationInfo WeixinAuthorizationInfo) {
		weixinAuthorizationInfoMapper.insertByEntity(WeixinAuthorizationInfo);
	}

	public List<WeixinAuthorizationInfo> getListByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return weixinAuthorizationInfoMapper.selectByObject(map);
	}

	public PageObject<WeixinAuthorizationInfo> Pagequery(Map<String, Object> map) {
		if(!map.containsKey("curPage")&&!map.containsKey("pageData")){
    		map.put("curPage", 1);
    		map.put("pageData", 10);
    	}
    	int totalData=	weixinAuthorizationInfoMapper.getCount(map);
    	PageHelper pageHelper = new PageHelper(totalData, map);
		List<WeixinAuthorizationInfo> list = weixinAuthorizationInfoMapper.pageQueryByObject(pageHelper.getMap());
		PageObject<WeixinAuthorizationInfo> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
	}

	public void updateByEntity(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	public List<WeixinAuthorizationInfo> getList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	public void del(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	public void del(WeixinAuthorizationInfo t) {
		// TODO Auto-generated method stub
		
	}
    
 
}
