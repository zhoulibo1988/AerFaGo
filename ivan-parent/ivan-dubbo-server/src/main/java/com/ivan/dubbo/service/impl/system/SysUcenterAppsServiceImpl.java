package com.ivan.dubbo.service.impl.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ivan.api.system.SysUcenterAppsService;
import org.ivan.entity.admin.SysUcenterAdmin;
import org.ivan.entity.admin.SysUcenterApps;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.system.SysUcenterAppsMapper;
@Service
public class SysUcenterAppsServiceImpl implements SysUcenterAppsService{

    // 注入当前dao对象
    @Autowired
    private SysUcenterAppsMapper sysUcenterAppsMapper;
   /**
    * 添加
    * @param map
    * @throws Exception
    */
    public void add(Map<String, Object> map) throws Exception {
        SysUcenterApps sysUcenterApps = new SysUcenterApps();
        sysUcenterApps.setCreateTime(new Date());
        sysUcenterAppsMapper.insertByEntity(sysUcenterApps);
    }
    /**
     * 获取总数
     */
    public Integer getCount(Map<String, Object> map) {
        return sysUcenterAppsMapper.getCount(map);
    }

    /**
     * 根据ID查询
     */
    public SysUcenterApps findById(Integer id) {
        SysUcenterApps apps = new SysUcenterApps();
        apps.setId(id);
        return sysUcenterAppsMapper.selectSingle(apps);
    }

    /**
     * 获取全部
     */
    public List<SysUcenterApps> findAppsAll() {
        SysUcenterApps apps = new SysUcenterApps();
        return sysUcenterAppsMapper.selectByObject(apps);
    }

    /**
     * app_code
     */
    public SysUcenterApps findSysApp(String appCode) {
        return findByCode(appCode);
    }
    /**
     * 获取code获取app
     */
    public SysUcenterApps findByCode(String code) {
        SysUcenterApps apps = new SysUcenterApps();
        apps.setAppKey(code);
        return sysUcenterAppsMapper.selectSingle(apps);
    }
    /**
     * 分页获取
     */
	public PageObject<SysUcenterApps> Pagequery(Map<String, Object> map) {
		if(!map.containsKey("curPage")&&!map.containsKey("pageData")){
    		map.put("curPage", 1);
    		map.put("pageData", 10);
    	}
    	int totalData=	sysUcenterAppsMapper.getCount(map);
    	PageHelper pageHelper = new PageHelper(totalData, map);
		List<SysUcenterApps> list = sysUcenterAppsMapper.pageQueryByObject(pageHelper.getMap());
		PageObject<SysUcenterApps> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
	}
	/**
	 * 修改
	 */
	public void update(Map<String, Object> map) {
		sysUcenterAppsMapper.updateByEntity(map);
	}
	/**
	 * 删除
	 */
	public void delete(Map<String, Object> map) {
		sysUcenterAppsMapper.deleteByEntity(map);
	}
	/**
	 * 查询
	 */
	public List<SysUcenterApps> query(Map<String, Object> map) {
		return sysUcenterAppsMapper.selectByObject(map);
	}
}
