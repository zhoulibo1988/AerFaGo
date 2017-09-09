package com.ivan.dubbo.service.impl.weixin;

import java.util.List;
import java.util.Map;

import org.ivan.api.weixin.WeixinAuthorizationTokenService;
import org.ivan.entity.WeixinAuthCode;
import org.ivan.entity.WeixinAuthorizationToken;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.weixin.WeixinAuthorizationTokenMapper;
/**
 * @author cyl
 * @version 
 */
@Service
public class WeixinAuthorizationTokenServiceImpl implements WeixinAuthorizationTokenService {
	@Autowired
	private WeixinAuthorizationTokenMapper weixinAuthorizationTokenMapper;
	
	public void insert(WeixinAuthorizationToken weixinAuthorizationToken) {
		// TODO Auto-generated method stub
		weixinAuthorizationTokenMapper.insertByEntity(weixinAuthorizationToken);
		
	}

	public WeixinAuthorizationToken selectSingle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return weixinAuthorizationTokenMapper.selectSingle(map);
	}

	public WeixinAuthorizationToken selectSingle(
			WeixinAuthorizationToken weixinAuthorizationToken) {
		// TODO Auto-generated method stub
		return weixinAuthorizationTokenMapper.selectSingle(weixinAuthorizationToken);
	}

	public void updateByEntity(WeixinAuthorizationToken weixinAuthorizationToken) {
		// TODO Auto-generated method stub
		weixinAuthorizationTokenMapper.updateByEntity(weixinAuthorizationToken);
	}
	/**
	 * 分页获取数据
	 */
	public PageObject<WeixinAuthorizationToken> Pagequery(Map<String, Object> map) {
		if(!map.containsKey("curPage")&&!map.containsKey("pageData")){
    		map.put("curPage", 1);
    		map.put("pageData", 10);
    	}
    	int totalData=	weixinAuthorizationTokenMapper.getCount(map);
    	PageHelper pageHelper = new PageHelper(totalData, map);
		List<WeixinAuthorizationToken> list = weixinAuthorizationTokenMapper.pageQueryByObject(pageHelper.getMap());
		PageObject<WeixinAuthorizationToken> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
	}
	/**
	 * 获取数据List集合
	 */
	public List<WeixinAuthorizationToken> getList(Map<String, Object> map) {
		return weixinAuthorizationTokenMapper.selectByObject(map);
	}
 
}
