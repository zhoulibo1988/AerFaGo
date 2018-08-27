package org.ivan.entity.vide;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.tb_media                 
--------------------------------------------------------
id                   Integer(10)        NOTNULL             //
title                String(255)                            //视频名字
src                  String(255)                            //视频存放地址
picture              String(255)                            //视频截图地址
descript             String(255)                            //视频描述
uptime               Date(19)                               //上传时间
*/
public class TbMedia implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Integer id;
	private	String title;
	private	String src;
	private	String picture;
	private	String descript;
	private	Date uptime;
	private Integer vip;
	
	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
		this.vip = vip;
	}

	/**
	* id  Integer(10)  NOTNULL  //    
	*/
	public Integer getId(){
		return id;
	}
	
	/**
	* id  Integer(10)  NOTNULL  //    
	*/
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	* title  String(255)  //视频名字    
	*/
	public String getTitle(){
		return title;
	}
	
	/**
	* title  String(255)  //视频名字    
	*/
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	* src  String(255)  //视频存放地址    
	*/
	public String getSrc(){
		return src;
	}
	
	/**
	* src  String(255)  //视频存放地址    
	*/
	public void setSrc(String src){
		this.src = src;
	}
	
	/**
	* picture  String(255)  //视频截图地址    
	*/
	public String getPicture(){
		return picture;
	}
	
	/**
	* picture  String(255)  //视频截图地址    
	*/
	public void setPicture(String picture){
		this.picture = picture;
	}
	
	/**
	* descript  String(255)  //视频描述    
	*/
	public String getDescript(){
		return descript;
	}
	
	/**
	* descript  String(255)  //视频描述    
	*/
	public void setDescript(String descript){
		this.descript = descript;
	}
	
	/**
	* uptime  Date(19)  //上传时间    
	*/
	public Date getUptime(){
		return uptime;
	}
	
	/**
	* uptime  Date(19)  //上传时间    
	*/
	public void setUptime(Date uptime){
		this.uptime = uptime;
	}
	
}