package com.ivan.dubbo.service.impl.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.system.SysUcenterRoleMapper;

import org.ivan.api.system.SysUcenterRoleService;
import org.ivan.entity.admin.SysUcenterRole;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
/**
 * @author buyuer
 * @version
 */
@Service
public class SysUcenterRoleServiceImpl implements SysUcenterRoleService {
    @Autowired
    private SysUcenterRoleMapper sysUcenterRoleMapper;

    public void addRole(Map<String, Object> map, Long userId) {
        SysUcenterRole role =new SysUcenterRole();
        role.setCreateTime(new Date());
        role.setCreateUserBy(userId);
        role.setAppId(Integer.valueOf(map.get("appId").toString()));
        role.setRolCode(map.get("rolCode").toString());
        role.setRolName(map.get("rolName").toString());
        sysUcenterRoleMapper.insertByEntity(role);
    }

    /* (非 Javadoc)
     * <p>Title: findById</p>
     * <p>Description: </p>
     * @param id
     * @return
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterRoleService#findById(java.lang.Long)
    */
    public SysUcenterRole findById(Long id) {
        SysUcenterRole role = new SysUcenterRole();
        role.setId(id);
        return sysUcenterRoleMapper.selectSingle(role);
    }

    /* (非 Javadoc)
     * <p>Title: updRole</p>
     * <p>Description: </p>
     * @param map
     * @param userId
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterRoleService#updRole(java.util.Map, java.lang.Long)
    */
    public void updRole(Map<String, Object> map, Long userId) {
        SysUcenterRole role =new SysUcenterRole();
        role.setUpdateTime(new Date());
        role.setUpdateUserBy(userId);
        sysUcenterRoleMapper.updateByEntity(role);
    }

    /* (非 Javadoc)
     * <p>Title: stopRole</p>
     * <p>Description: </p>
     * @param id
     * @param userId
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterRoleService#stopRole(java.lang.Long, java.lang.Long)
    */
    public void stopRole(Long id, Long userId) {
        SysUcenterRole role = new SysUcenterRole();
        role.setId(id);
        role.setRolStatus(1);
        role.setUpdateTime(new Date());
        role.setUpdateUserBy(userId);
        sysUcenterRoleMapper.updateByEntity(role);
    }

    /* (非 Javadoc)
     * <p>Title: startRole</p>
     * <p>Description: </p>
     * @param id
     * @param userId
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterRoleService#startRole(java.lang.Long, java.lang.Long)
    */

    public void startRole(Long id, Long userId) {
        SysUcenterRole role = new SysUcenterRole();
        role.setId(id);
        role.setRolStatus(0);
        role.setUpdateTime(new Date());
        role.setUpdateUserBy(userId);
        sysUcenterRoleMapper.updateByEntity(role);
    }

    /* (非 Javadoc)
     * <p>Title: delRole</p>
     * <p>Description: </p>
     * @param id
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterRoleService#delRole(java.lang.Long)
    */
    public void delRole(Long id) {
        SysUcenterRole role = new SysUcenterRole();
        role.setId(id);
        sysUcenterRoleMapper.deleteByEntity(role);
    }
    
    /* (非 Javadoc)
     * <p>Title: pageQueryByObject_Map</p>
     * <p>Description: </p>
     * @param map
     * @return
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterRoleService#pageQueryByObject_Map(java.util.Map)
    */
    @SuppressWarnings("unchecked")

    public PageObject<Map<String, Object>> pageQueryByObject_Map(Map<String, Object> map) {
    	if(!map.containsKey("curPage")&&!map.containsKey("pageData")){
    		map.put("curPage", 1);
    		map.put("pageData", 10);
    	}
        int totalData = sysUcenterRoleMapper.getCount_Map(map);
        PageHelper pageHelper = new PageHelper(totalData, map);
        List<Map<String, Object>> list = sysUcenterRoleMapper.pageQueryByObject_Map(pageHelper.getMap());
        PageObject<Map<String, Object>> pageObject = pageHelper.getPageObject();
        pageObject.setDataList(list);
        return pageObject;
    }
    
    /* (非 Javadoc)
     * <p>Title: getRoleAll</p>
     * <p>Description: </p>
     * @return
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterRoleService#getRoleAll()
    */

    public List<Map<String, Object>> getRoleAll() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("rolStatus", 0);
        return sysUcenterRoleMapper.queryByObject_Map(paramMap);
    }
    
}
