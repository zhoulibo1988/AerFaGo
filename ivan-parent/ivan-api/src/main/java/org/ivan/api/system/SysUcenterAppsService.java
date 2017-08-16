package org.ivan.api.system;

import java.util.List;
import java.util.Map;

import org.ivan.entity.admin.SysUcenterApps;
import org.ivan.entity.utils.PageObject;

/**
 * app管理
 * @author 周立波
 *
 */
public interface SysUcenterAppsService {

    
    /**
     * 
     * @author buyuer
     * @Title: getCount
     * @Description: 获取总数
     */
    Integer getCount(Map<String, Object> map);
    
    /**
     * 
     * @author buyuer
     * @Title: findById
     * @Description: 根据ID查询项目
     * @param id 项目ID
     */
    SysUcenterApps findById(Integer id);
    
    /**
     * 
     * @author buyuer
     * @Title: findAppsAll
     * @Description:查询所有的app
     */
    List<SysUcenterApps> findAppsAll();
    
    /**
     * 
     * @author buyuer
     * @Title: findByCode
     * @Description: 根据应用code 查询应用信息
     * @param appCode 应用code
     */
    SysUcenterApps findSysApp(String appCode);
    
    /**
     * 
     * @author buyuer
     * @Title: findByCode
     * @Description: 根据应用code 查询应用信息
     * @param code 应用code
     */
    SysUcenterApps findByCode(String code);
    /**
     * 分页获取
     * @param map
     * @return
     */
    PageObject<SysUcenterApps> Pagequery(Map<String,Object> map);
    /**
     * 添加
     * @param map
     */
    void add(Map<String,Object> map) throws Exception;
    /**
     * 修改
     * @param map
     */
    void update(Map<String,Object> map);
    /**
     * 删除
     * @param map
     */
    void delete(Map<String,Object> map);
    
    List<SysUcenterApps> query(Map<String,Object> map);
}
