package com.ivan.dubbo.service.impl.system;



import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.ivan.api.system.SysUcenterAdminRoleService;
import org.ivan.entity.admin.SysUcenterAdminRole;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.system.SysUcenterAdminRoleMapper;
/**
 * @author buyuer
 * @version
 */
@Service
public class SysUcenterAdminRoleServiceImpl implements SysUcenterAdminRoleService {
    @Autowired
    private SysUcenterAdminRoleMapper sysUcenterAdminRoleMapper;
    /**
     * 
     */
    public void addAdminRole(Map<String, Object> map, Long userId) {
        String roleIdList = map.get("roleIdList").toString();
        Long adminId = Long.valueOf(map.get("adminId").toString());
        //先删除原有的权限
        delRoleByUser(adminId);
        SysUcenterAdminRole adminRole = new SysUcenterAdminRole();
        for (String role : roleIdList.split(",")) {
            String appId = role.split(";")[1];
            String roleId = role.split(";")[0];
            if (StringUtils.isBlank(appId) || StringUtils.isBlank(roleId)) {
                continue;
            }
            adminRole.setAppId(Integer.parseInt(appId));
            adminRole.setRolId(Long.valueOf(roleId));
            adminRole.setAdmId(adminId);
            adminRole.setCreateTime(new Date());
            adminRole.setCreateUserBy(userId);
            sysUcenterAdminRoleMapper.insertByEntity(adminRole);
        }
    }

    /**
     * @author buyuer
     * @Title: findRoleByUser
     * @Description: 查询当前用户的角色
     */
    public List<Long> findRoleByUser(Long userId){
        return sysUcenterAdminRoleMapper.findRoleByUser(userId);
    }
    /* (非 Javadoc)
     * <p>Title: delRoleByUser</p>
     * <p>Description: </p>
     * @param userId
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterAdminRoleService#delRoleByUser(java.lang.Long)
    */
    public void delRoleByUser(Long userId) {
        SysUcenterAdminRole adminRole = new SysUcenterAdminRole();
        adminRole.setAdmId(userId);
        sysUcenterAdminRoleMapper.deleteByEntity(adminRole);
    }
}
