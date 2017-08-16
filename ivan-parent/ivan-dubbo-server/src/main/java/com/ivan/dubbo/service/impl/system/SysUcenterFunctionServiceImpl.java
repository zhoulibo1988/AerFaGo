package com.ivan.dubbo.service.impl.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ivan.api.system.SysUcenterFunctionService;
import org.ivan.entity.admin.SysUcenterFunction;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.system.SysUcenterFunctionMapper;
/**
 * 菜单管理seviceImpl
 * @author 周立波
 *
 */
@Service
public class SysUcenterFunctionServiceImpl implements SysUcenterFunctionService {
    @Autowired
    private SysUcenterFunctionMapper sysUcenterFunctionMapper;
    /**
     * 获取List
     */
    public PageObject<SysUcenterFunction> listFunctionForAdmin(Map<String, Object> map) {
//        map.put("fid", -1);
        map.put("funStatus", 0);
        map.put("sortName", "fun_sort");
        map.put("sortOrder", "desc");
        if(!map.containsKey("curPage")&&!map.containsKey("pageData")){
    		map.put("curPage", 1);
    		map.put("pageData", 10);
    	}
    	int totalData=	sysUcenterFunctionMapper.getCount(map);
    	PageHelper pageHelper = new PageHelper(totalData, map);
		List<SysUcenterFunction> list = sysUcenterFunctionMapper.pageQueryByObject(pageHelper.getMap());
		PageObject<SysUcenterFunction> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
    }
    /**
     * 
     */
    public List<Map<String, Object>> listFunctionForNormal(Long userId, String appsCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", userId);
        map.put("appsCode", appsCode);
        return sysUcenterFunctionMapper.selectFunByUser(map);
    }
    /**
     * 
     */
    public SysUcenterFunction findById(Long userId, Long id) {
        SysUcenterFunction function = new SysUcenterFunction();
        function.setId(id);
        return sysUcenterFunctionMapper.selectSingle(function);
    }
    /**
     * 
     */
    public void startFunction(Long userId, Long id) {
        SysUcenterFunction function = new SysUcenterFunction();
        function.setId(id);
        function.setFunStatus(0);
        function.setUpdateTime(new Date());
        function.setUpdateUserBy(userId);
        sysUcenterFunctionMapper.updateByEntity(function);
        
        //开启子元素
        function = new SysUcenterFunction();
        function.setFid(id);
        function.setFunStatus(0);
        function.setUpdateTime(new Date());
        function.setUpdateUserBy(userId);
        sysUcenterFunctionMapper.updateFunction(function);
        
    }
    /**
     * 
     */
    public void stopFunction(Long userId, Long id) {
        SysUcenterFunction function = new SysUcenterFunction();
        function.setId(id);
        function.setFunStatus(1);
        function.setUpdateTime(new Date());
        function.setUpdateUserBy(userId);
        sysUcenterFunctionMapper.updateByEntity(function);   
        
        //锁定子元素
        function = new SysUcenterFunction();
        function.setFid(id);
        function.setFunStatus(1);
        function.setUpdateTime(new Date());
        function.setUpdateUserBy(userId);
        sysUcenterFunctionMapper.updateFunction(function);   
    }
    /**
     * 删除
     */
    public void delFunction(Long id) {
        
        SysUcenterFunction function = new SysUcenterFunction();
        //删除子元素
        function.setId(id);
        sysUcenterFunctionMapper.deleteByEntity(function);
        
        //删除子元素
        function = new SysUcenterFunction();
        function.setFid(id);
        sysUcenterFunctionMapper.deleteByEntity(function);
        
    }
    /**
     * 添加菜单
     */
    public void addMenu(Long userId, Map<String, Object> map) {
        SysUcenterFunction function = null;
        function.setCreateTime(new Date());
        function.setCreateUserBy(userId);
        sysUcenterFunctionMapper.insertByEntity(function);
    }
    /**
     * 修改菜单
     */
    public void updMenu(Long userId, Map<String, Object> map) {
        SysUcenterFunction function = null;
        function.setUpdateUserBy(userId);
        function.setUpdateTime(new Date());
        sysUcenterFunctionMapper.updateByEntity(function);
    }
    /**
     * 
     */
    public List<SysUcenterFunction> findMenuListByAppId(Integer appId) {
        SysUcenterFunction  function = new SysUcenterFunction();
        function.setAppId(appId);
        function.setFunStatus(0);
        return sysUcenterFunctionMapper.selectByObject(function);
    }
    /**
     * 
     */
    public List<Map<String, Object>> selectFunList() {
        return sysUcenterFunctionMapper.selectFunList();
    }
    
}
