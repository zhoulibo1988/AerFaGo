package com.ivan.dubbo.service.impl.weixin;

import java.util.List;
import java.util.Map;

import org.ivan.api.weixin.WeixinBusinessInfoService;
import org.ivan.entity.WeixinBusinessInfo;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.weixin.WeixinBusinessInfoMapper;

/**
 * @author cyl
 * @version 
 */
@Service
public class WeixinBusinessInfoServiceImpl implements WeixinBusinessInfoService {
	// 注入当前dao对象
    @Autowired
    private WeixinBusinessInfoMapper weixinBusinessInfoMapper;

	public WeixinBusinessInfo selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return weixinBusinessInfoMapper.selectSingle(map);
	}

	public WeixinBusinessInfo selectSingle(WeixinBusinessInfo WeixinBusinessInfo) {
		// TODO Auto-generated method stub
		return weixinBusinessInfoMapper.selectSingle(WeixinBusinessInfo);
	}

	public void updateByEntity(WeixinBusinessInfo WeixinBusinessInfo) {
		// TODO Auto-generated method stub
		weixinBusinessInfoMapper.updateByEntity(WeixinBusinessInfo);
	}

	public void insert(WeixinBusinessInfo WeixinBusinessInfo) {
		// TODO Auto-generated method stub
		weixinBusinessInfoMapper.insertByEntity(WeixinBusinessInfo);
	}

	public void updateByEntity(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	public PageObject<WeixinBusinessInfo> Pagequery(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<WeixinBusinessInfo> getList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	public void del(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	public void del(WeixinBusinessInfo t) {
		// TODO Auto-generated method stub
		
	}

   
 
}
