package com.ivan.dubbo.service.impl.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ivan.api.SysUcenterAdminService;
import org.ivan.entity.admin.SysUcenterAdmin;
import org.ivan.entity.utils.PageObject;
import org.ivan.entity.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.SysUcenterAdminMapper;
@Service
public class SysUcenterAdminServiceImpl implements SysUcenterAdminService{
	 // 注入当前dao对象
    @Autowired
    private SysUcenterAdminMapper sysUcenterAdminMapper;
    /*
     * (非 Javadoc) <p>Title: getAdmin</p> <p>Description: </p>
     * 
     * @param userName
     * 
     * @return
     * 
     * @see cn.iyizhan.treamwork.service.manager.SysUcenterAdminService#getAdmin(java.lang.String)
     */

    public SysUcenterAdmin getAdminByUserName(String userName) {
        SysUcenterAdmin admin = new SysUcenterAdmin();
        admin.setAdmStatus(0);
        admin.setAdmName(userName);
        return sysUcenterAdminMapper.selectSingle(admin);
    }
    
    /* (非 Javadoc)
     * <p>Title: getCount</p>
     * <p>Description: </p>
     * @param map
     * @return
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterAdminService#getCount(java.util.Map)
    */

    public Integer getCount(Map<String, Object> map) {
        return sysUcenterAdminMapper.getCount(map);
    }

    /* (非 Javadoc)
     * <p>Title: startAdmin</p>
     * <p>Description: </p>
     * @param id
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterAdminService#startAdmin(java.lang.Long)
    */

    public void startAdmin(Long id) {
        SysUcenterAdmin admin = new SysUcenterAdmin();
        admin.setId(id);
        admin.setAdmStatus(0);
        admin.setUpdateTime(new Date());
        sysUcenterAdminMapper.updateByEntity(admin);
    }

    /* (非 Javadoc)
     * <p>Title: stopAdmin</p>
     * <p>Description: </p>
     * @param id
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterAdminService#stopAdmin(java.lang.Long)
    */

    public void stopAdmin(Long id) {
        SysUcenterAdmin admin = new SysUcenterAdmin();
        admin.setId(id);
        admin.setAdmStatus(1);
        admin.setUpdateTime(new Date());
        sysUcenterAdminMapper.updateByEntity(admin);
    }

    /* (非 Javadoc)
     * <p>Title: delAdmin</p>
     * <p>Description: </p>
     * @param id
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterAdminService#delAdmin(java.lang.Long)
    */

    public void delAdmin(Long id) {
        SysUcenterAdmin admin = new SysUcenterAdmin();
        admin.setId(id);
        sysUcenterAdminMapper.deleteByEntity(admin);
    }
    
    /* (非 Javadoc)
     * <p>Title: add</p>
     * <p>Description: </p>
     * @param map
     * @return
     * @throws Exception
     * @see cn.iyizhan.teamwork.base.service.impl.BaseServiceImpl#add(java.util.Map)
    */

    public void add(Map<String, Object> map) throws Exception {
        SysUcenterAdmin admin = M2O(map);
        admin.setAdmStatus(0);
        admin.setCreateTime(new Date());
        sysUcenterAdminMapper.insertByEntity(admin);
    }
    /* (非 Javadoc)
     * <p>Title: findById</p>
     * <p>Description: </p>
     * @param id
     * @return
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterAdminService#findById(java.lang.Long)
    */

    public SysUcenterAdmin findById(Long id) {
        SysUcenterAdmin admin = new SysUcenterAdmin();
        admin.setId(id);
        return sysUcenterAdminMapper.selectSingle(admin);
    }
    
    /* (非 Javadoc)
     * <p>Title: addAdmin</p>
     * <p>Description: </p>
     * @param map
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterAdminService#addAdmin(java.util.Map)
    */

    public void addAdmin(Map<String, Object> map) {
        SysUcenterAdmin admin = M2O(map);
        String pass = admin.getAdmPassword();
        pass = PasswordEncoder.getPassword(pass);
        admin.setAdmPassword(pass);
        admin.setAdmType("NORMAL");
        admin.setAdmStatus(0);
        admin.setCreateTime(new Date());
        sysUcenterAdminMapper.insertByEntity(admin);
    }
    
    /* (非 Javadoc)
     * <p>Title: update</p>
     * <p>Description: </p>
     * @param map
     * @throws Exception
     * @see cn.iyizhan.teamwork.base.service.impl.BaseServiceImpl#update(java.util.Map)
    */

    public void update(Map<String, Object> map) throws Exception {
        SysUcenterAdmin admin = M2O(map);
        String pass = admin.getAdmPassword();
        if (StringUtils.isNoneBlank(pass)) {
            pass = PasswordEncoder.getPassword(pass);
            admin.setAdmPassword(pass);
        }
        admin.setUpdateTime(new Date());
        sysUcenterAdminMapper.updateByEntity(admin);
    }
    
    /* (非 Javadoc)
     * <p>Title: Pagequery</p>
     * <p>Description: </p>
     * @param map
     * @return
     * @see cn.iyizhan.teamwork.base.service.impl.BaseServiceImpl#Pagequery(java.util.Map)
    */

//    public PageObject<SysUcenterAdmin> Pagequery(Map<String, Object> map) {
//        map.put("admType", "NORMAL");
//        sysUcenterAdminMapper.pageQueryByObject(map);
//        return super.Pagequery(map);
//    }
    
    /* (非 Javadoc)
     * <p>Title: loginAdmin</p>
     * <p>Description: </p>
     * @param userName
     * @param password
     * @return
     * @see cn.iyizhan.teamwork.manager.service.SysUcenterAdminService#loginAdmin(java.lang.String, java.lang.String)
    */

    public SysUcenterAdmin loginAdmin(String userName, String password) {
        SysUcenterAdmin admin = new SysUcenterAdmin();
        admin.setAdmName(userName);
        admin.setAdmPassword(password);
        admin.setAdmStatus(0);
        return admin = sysUcenterAdminMapper.selectSingle(admin);
    }

	@Override
	public List<SysUcenterAdmin> query(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(SysUcenterAdmin t) throws Throwable {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(SysUcenterAdmin t) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SysUcenterAdmin detail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysUcenterAdmin M2O(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <R> List<R> handleGameInfo(List<R> list, boolean flag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(SysUcenterAdmin t) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageObject<SysUcenterAdmin> Pagequery(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
}
