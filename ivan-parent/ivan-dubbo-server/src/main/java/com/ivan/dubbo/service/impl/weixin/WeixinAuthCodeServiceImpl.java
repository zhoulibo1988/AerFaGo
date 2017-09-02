package com.ivan.dubbo.service.impl.weixin;

import java.util.List;
import java.util.Map;

import org.ivan.api.weixin.WeixinAuthCodeService;
import org.ivan.entity.WeixinAuthCode;
import org.ivan.entity.admin.SysUcenterApps;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.weixin.WeixinAuthCodeMapper;

/**
 * @author cyl
 * @version 
 */
@Service
public class WeixinAuthCodeServiceImpl implements WeixinAuthCodeService {
	// 注入当前dao对象
    @Autowired
    private WeixinAuthCodeMapper weixinAuthCodeMapper;

	public void insert(WeixinAuthCode weixinAuthCode) {
		weixinAuthCodeMapper.insertByEntity(weixinAuthCode);
		
	}

	public WeixinAuthCode selectSingle(Map<String, Object> map) {
		return weixinAuthCodeMapper.selectSingle(map);
	}

	public WeixinAuthCode selectSingle(WeixinAuthCode weixinAuthCode) {
		// TODO Auto-generated method stub
		return weixinAuthCodeMapper.selectSingle(weixinAuthCode);
	}

	public void updateByEntity(WeixinAuthCode weixinAuthCode) {
		// TODO Auto-generated method stub
		weixinAuthCodeMapper.updateByEntity(weixinAuthCode);
	}
	/**
	 * 分页获取数据
	 */
	public PageObject<WeixinAuthCode> Pagequery(Map<String, Object> map) {
		if(!map.containsKey("curPage")&&!map.containsKey("pageData")){
    		map.put("curPage", 1);
    		map.put("pageData", 10);
    	}
    	int totalData=	weixinAuthCodeMapper.getCount(map);
    	PageHelper pageHelper = new PageHelper(totalData, map);
		List<WeixinAuthCode> list = weixinAuthCodeMapper.pageQueryByObject(pageHelper.getMap());
		PageObject<WeixinAuthCode> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
	}
}
