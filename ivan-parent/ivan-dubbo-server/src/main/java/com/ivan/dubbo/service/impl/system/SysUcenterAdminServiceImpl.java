package com.ivan.dubbo.service.impl.system;

import java.util.List;
import java.util.Map;

import org.ivan.api.system.SysUcenterAdminService;
import org.ivan.entity.admin.SysUcenterAdmin;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.system.SysUcenterAdminMapper;
@Service
public class SysUcenterAdminServiceImpl implements SysUcenterAdminService{
	 // 注入当前dao对象
    @Autowired
    private SysUcenterAdminMapper sysUcenterAdminMapper;
    public SysUcenterAdmin loginAdmin(String userName, String password) {
    	System.out.println("进来了");
        SysUcenterAdmin admin = new SysUcenterAdmin();
        admin.setAdmName(userName);
        admin.setAdmPassword(password);
        admin.setAdmStatus(0);
        admin = sysUcenterAdminMapper.selectSingle(admin);
        return admin;
    }
	@Override
	public SysUcenterAdmin getAdminByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer getCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void startAdmin(Long id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void stopAdmin(Long id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delAdmin(Long id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addAdmin(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public SysUcenterAdmin findById(Long id) {
		// TODO Auto-generated method stub
		return null;
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
	public void add(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void add(SysUcenterAdmin t) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(Map<String, Object> map) throws Exception {
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
	public PageObject<SysUcenterAdmin> Pagequery(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <R> List<R> handleGameInfo(List<R> list, boolean flag) {
		// TODO Auto-generated method stub
		return null;
	}
}
