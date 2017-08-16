package org.ivan.api.system;

import java.util.Map;

import org.ivan.entity.admin.SysUcenterAdmin;
import org.ivan.entity.utils.PageObject;
/**
 * 系统管理-管理员Service
 * @author 周立波
 *
 */
public interface SysUcenterAdminService{
	/**
     * 
     * @author buyuer
     * @Title: getAdmin
     * @Description: 通过用户名称查询管理员
     * @param userName 用户名
     * @return
     */
    SysUcenterAdmin getAdminByUserName(String userName);
    
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
     * @Title: startAdmin
     * @Description: 启用管理员
     * @param id 管理员id
     */
    void startAdmin(Long id);
    
    /**
     * 
     * @author buyuer
     * @Title: stopAdmin
     * @Description: 禁用管理员
     * @param id 管理员id
     */
    void stopAdmin(Long id);
    
    /**
     * 
     * @author buyuer
     * @Title: delAdmin
     * @Description: 删除管理员
     * @param id 管理员id
     */
    void delAdmin(Long id);
    
    /**
     * 
     * @author buyuer
     * @Title: addAdmin
     * @Description: 增加管理员
     */
    void addAdmin(Map<String, Object> map);
    /**
     * 
     * @author buyuer
     * @Title: findById
     * @Description: 根据ID查询
     * @param id 用户id
     */
    SysUcenterAdmin findById(Long id);
    
    /**
     * @author buyuer
     * @Title: loginAdmin
     * @Description: 登录方法
     * @param userName 用户名
     * @param password 密码
     */
    SysUcenterAdmin loginAdmin(String userName, String password);
    /**
     * 修改
     * @param map
     * @throws Exception
     */
    void update(Map<String, Object> map) throws Exception;
    /**
     * 分页查询
     * @param map
     * @return
     */
    PageObject<SysUcenterAdmin> Pagequery(Map<String, Object> map);
}
