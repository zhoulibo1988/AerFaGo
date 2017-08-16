package com.ivan.dubbo.service.impl.system;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.ivan.api.system.SysUcenterPermissionService;
import org.ivan.entity.admin.SysUcenterPermission;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.system.SysUcenterPermissionMapper;
/**
 * @author buyuer
 * @version
 */

@Service
public class SysUcenterPermissionServiceImpl implements SysUcenterPermissionService {
    @Autowired
    private SysUcenterPermissionMapper sysUcenterPermissionMapper;
   
    public void addPermissToRole(Map<String, Object> map, Long userId) {
        SysUcenterPermission permission = null;
        String roles = map.get("roles").toString();
        Long rolId = Long.valueOf(map.get("rolId").toString());
        String prm_remark = map.get("prm_remark") != null ? map.get("prm_remark").toString() : null;
        Set<String> roSet = new HashSet<String>(Arrays.asList(roles.split(",")));
        //先删除原有的数据
        permission= new SysUcenterPermission();
        permission.setRolId(rolId);
        sysUcenterPermissionMapper.deleteByEntity(permission);
        for (String fun : roSet) {
            if (StringUtils.isNoneBlank(fun)) {
                permission= new SysUcenterPermission();
                permission.setFunId(Long.valueOf(fun));
                permission.setPrmRemark(prm_remark);
                permission.setRolId(rolId);
                permission.setCreateTime(new Date());
                permission.setCreateUserBy(userId);
                sysUcenterPermissionMapper.insertByEntity(permission);
            }
        }
        
    }
    
    /* (非 Javadoc)
     * <p>Title: selectFunByRole</p>
     * <p>Description: </p>
     * @return
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterPermissionService#selectFunByRole()
    */
    public List<Long> selectFunByRole(Long id) {
        
        return sysUcenterPermissionMapper.selectFunByRole(id);
    }

}
