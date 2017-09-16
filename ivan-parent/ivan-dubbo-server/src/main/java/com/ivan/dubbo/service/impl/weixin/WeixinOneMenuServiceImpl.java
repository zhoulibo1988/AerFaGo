package com.ivan.dubbo.service.impl.weixin;

import java.util.List;
import java.util.Map;

import org.ivan.api.weixin.WeixinOneMenuService;
import org.ivan.entity.WeixinOneMenu;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.weixin.WeixinOneMenuMapper;
/**
 * 自定义菜单实现类
 * @author 周立波
 *
 */
@Service
public class WeixinOneMenuServiceImpl implements WeixinOneMenuService {
	// 注入当前dao对象
	@Autowired
	private WeixinOneMenuMapper weixinOneMenuMapper;

	public WeixinOneMenu selectSingle(Map<String, Object> map) {
		return weixinOneMenuMapper.selectSingle(map);
	}

	public WeixinOneMenu selectSingle(WeixinOneMenu weixinOneMenu) {
		return weixinOneMenuMapper.selectSingle(weixinOneMenu);
	}

	public void updateByEntity(WeixinOneMenu weixinOneMenu) {
		weixinOneMenuMapper.updateByEntity(weixinOneMenu);
	}

	public PageObject<WeixinOneMenu> Pagequery(Map<String, Object> map) {
		if (!map.containsKey("curPage") && !map.containsKey("pageData")) {
			map.put("curPage", 1);
			map.put("pageData", 10);
		}
		int totalData = weixinOneMenuMapper.getCount(map);
		PageHelper pageHelper = new PageHelper(totalData, map);
		List<WeixinOneMenu> list = weixinOneMenuMapper.pageQueryByObject(pageHelper.getMap());
		PageObject<WeixinOneMenu> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
	}

	public List<WeixinOneMenu> getList(Map<String, Object> map) {
		return weixinOneMenuMapper.selectByObject(map);
	}

	public void insert(WeixinOneMenu weixinOneMenu) {
		weixinOneMenuMapper.insertByEntity(weixinOneMenu);
	}

	public List<WeixinOneMenu> getListBy2Menu(Map<String, Object> map) {
		return weixinOneMenuMapper.selectByObject2(map);
	}

	public void updateByEntity(Map<String, Object> map) {
		weixinOneMenuMapper.updateByEntity(map);
	}

	public void del(Map<String, Object> map) {
		weixinOneMenuMapper.deleteByEntity(map);
	}

	public void del(WeixinOneMenu weixinOneMenu) {
		weixinOneMenuMapper.deleteByEntity(weixinOneMenu);
	}
}
