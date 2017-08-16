package com.ivan.dubbo.service.impl.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ivan.api.system.SysUcenterAdminService;
import org.ivan.entity.admin.SysUcenterAdmin;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.ivan.entity.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.system.SysUcenterAdminMapper;
@Service
public class SysUcenterAdminServiceImpl implements SysUcenterAdminService{
	 // 注入当前dao对象
    @Autowired
    private SysUcenterAdminMapper sysUcenterAdminMapper;
    public SysUcenterAdmin loginAdmin(String userName, String password) {
        SysUcenterAdmin admin = new SysUcenterAdmin();
        admin.setAdmName(userName);
        admin.setAdmPassword(password);
        admin.setAdmStatus(0);
        admin = sysUcenterAdminMapper.selectSingle(admin);
        return admin;
    }
    /**
     * 分页查询获取管理员列表
     * @param map
     * @return
     */
    public PageObject<SysUcenterAdmin> Pagequery(Map<String, Object> map) {
    	if(!map.containsKey("curPage")&&!map.containsKey("pageData")){
    		map.put("curPage", 1);
    		map.put("pageData", 10);
    	}
    	int totalData=	sysUcenterAdminMapper.getCount(map);
    	PageHelper pageHelper = new PageHelper(totalData, map);
		List<SysUcenterAdmin> list = sysUcenterAdminMapper.pageQueryByObject(pageHelper.getMap());
		PageObject<SysUcenterAdmin> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
	}
    /**
     * 根据ID获取管理员
     */
    public SysUcenterAdmin findById(Long id) {
    	 SysUcenterAdmin admin = new SysUcenterAdmin();
         admin.setId(id);
         return sysUcenterAdminMapper.selectSingle(admin);
	}
    /**
     * 启用管理员
     */
    public void startAdmin(Long id) {
    	SysUcenterAdmin admin = new SysUcenterAdmin();
        admin.setId(id);
        admin.setAdmStatus(0);
        admin.setUpdateTime(new Date());
        sysUcenterAdminMapper.updateByEntity(admin);
	}
    /**
     * 禁用管理员
     */
	public void stopAdmin(Long id) {
		SysUcenterAdmin admin = new SysUcenterAdmin();
        admin.setId(id);
        admin.setAdmStatus(1);
        admin.setUpdateTime(new Date());
        sysUcenterAdminMapper.updateByEntity(admin);
	}
	/**
	 * 删除管理员
	 */
	public void delAdmin(Long id) {
		SysUcenterAdmin admin = new SysUcenterAdmin();
        admin.setId(id);
        sysUcenterAdminMapper.deleteByEntity(admin);
		
	}
	/**
	 * 添加管理员
	 */
	public void addAdmin(Map<String, Object> map) {
		SysUcenterAdmin admin =new SysUcenterAdmin();
		admin.setAdmDisplayName(String.valueOf(map.get("admDisplayName")));
		admin.setAdmName( String.valueOf(map.get("admName")));
		admin.setAdmTel(String.valueOf(map.get("admTel")));
        String pass = admin.getAdmPassword();
        pass = PasswordEncoder.getPassword(pass);
        admin.setAdmPassword(pass);
        admin.setAdmType("NORMAL");
        admin.setAdmStatus(0);
        admin.setCreateTime(new Date());
        sysUcenterAdminMapper.insertByEntity(admin);
	}
	/**
	 * 修改管理信息
	 */
	public void update(Map<String, Object> map) throws Exception {
		sysUcenterAdminMapper.updateByEntity(map);
	}
	/**
	 * 提供管理员名字查询管理员
	 */
	public SysUcenterAdmin getAdminByUserName(String userName) {
		SysUcenterAdmin admin = new SysUcenterAdmin();
		admin.setAdmStatus(0);
		admin.setAdmName(userName);
		return sysUcenterAdminMapper.selectSingle(admin);
	}
	/**
	 * 查询总数
	 */
	public Integer getCount(Map<String, Object> map) {
		 return sysUcenterAdminMapper.getCount(map);
	}
}
