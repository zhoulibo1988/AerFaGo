package org.ivan.api.system;

import java.util.List;
import java.util.Map;
/**
 * @author sine
 * @version
 */
public interface SysUcenterAdminRoleService{

    
    /**
     * @author buyuer
     * @Title: addAdminRole
     * @Description: 增加角色
     */
    void addAdminRole(Map<String, Object> map, Long userId);
    
    /**
     * @author buyuer
     * @Title: findRoleByUser
     * @Description: 查询当前用户的角色
     */
    List<Long> findRoleByUser(Long userId);
    
    void delRoleByUser(Long userId);
    
}
