package com.pay.manger.controller.admin;

import java.io.Serializable;

import com.pay.business.admin.entity.SysUcenterAdmin;


public class SessionVO implements Serializable{
	
	// 用户对象
	private SysUcenterAdmin user;

	public SysUcenterAdmin getUser() {
		return user;
	}

	public void setUser(SysUcenterAdmin user) {
		this.user = user;
	}
	
	
}
