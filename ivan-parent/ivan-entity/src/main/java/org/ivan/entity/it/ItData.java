package org.ivan.entity.it;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.it_data                  
--------------------------------------------------------
id                   Integer(10)        NOTNULL             //
it_name              String(255)                            //资料名字
it_type              Integer(10)                            //类型1：文档 2：源码 3: 视频 4：工具
it_url               String(255)                            //分享的URL
it_password          String(255)                            //分享的密码
it_del               String(255)                            //是否删除 1：删除 2：未删除
it_state             String(255)                            //是否上架 1：上架 2：下架
create_time          Date(19)                               //创建时间
*/
public class ItData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Integer id;
	private	String itName;
	private	Integer itType;
	private	String itUrl;
	private	String itPassword;
	private	Integer itDel;
	private	Integer itState;
	private	Date createTime;

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
	* it_name  String(255)  //资料名字    
	*/
	public String getItName(){
		return itName;
	}
	
	/**
	* it_name  String(255)  //资料名字    
	*/
	public void setItName(String itName){
		this.itName = itName;
	}
	
	/**
	* it_type  Integer(10)  //类型1：文档  2：源码  3:  视频  4：工具    
	*/
	public Integer getItType(){
		return itType;
	}
	
	/**
	* it_type  Integer(10)  //类型1：文档  2：源码  3:  视频  4：工具    
	*/
	public void setItType(Integer itType){
		this.itType = itType;
	}
	
	/**
	* it_url  String(255)  //分享的URL    
	*/
	public String getItUrl(){
		return itUrl;
	}
	
	/**
	* it_url  String(255)  //分享的URL    
	*/
	public void setItUrl(String itUrl){
		this.itUrl = itUrl;
	}
	
	/**
	* it_password  String(255)  //分享的密码    
	*/
	public String getItPassword(){
		return itPassword;
	}
	
	/**
	* it_password  String(255)  //分享的密码    
	*/
	public void setItPassword(String itPassword){
		this.itPassword = itPassword;
	}
	
	/**
	* it_del  String(255)  //是否删除  1：删除  2：未删除    
	*/
	public Integer getItDel(){
		return itDel;
	}
	
	/**
	* it_del  String(255)  //是否删除  1：删除  2：未删除    
	*/
	public void setItDel(Integer itDel){
		this.itDel = itDel;
	}
	
	/**
	* it_state  String(255)  //是否上架  1：上架  2：下架    
	*/
	public Integer getItState(){
		return itState;
	}
	
	/**
	* it_state  String(255)  //是否上架  1：上架  2：下架    
	*/
	public void setItState(Integer itState){
		this.itState = itState;
	}
	
	/**
	* create_time  Date(19)  //创建时间    
	*/
	public Date getCreateTime(){
		return createTime;
	}
	
	/**
	* create_time  Date(19)  //创建时间    
	*/
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
}