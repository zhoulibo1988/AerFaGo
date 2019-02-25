package org.ivan.entity.file;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.film_user                
--------------------------------------------------------
id                   Integer(10)        NOTNULL             //
user_name            String(255)                            //昵称
user_emil            String(100)                            //邮箱
user_image           String(255)                            //用户图像
user_phone           String(11)                             //用户手机
user_password        String(255)                            //密码
login_time           Date(19)                               //最后登录时间
login_number         Integer(10)                            //登录次数
is_vip               Integer(10)                            //VIP 1是 2否
vip_time             Date(19)                               //VIP失效时间
user_synopsis        String(65535)                          //简介
is_delete            Integer(10)                            //是否删除1是 2否
creation_time        Date(19)                               //创建时间
*/
public class FilmUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Integer id;
	private	String userName;
	private	String userEmil;
	private	String userImage;
	private	String userPhone;
	private	String userPassword;
	private	Date loginTime;
	private	Integer loginNumber;
	private	Integer isVip;
	private	Date vipTime;
	private	String userSynopsis;
	private	Integer isDelete;
	private	Date creationTime;

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
	* user_name  String(255)  //昵称    
	*/
	public String getUserName(){
		return userName;
	}
	
	/**
	* user_name  String(255)  //昵称    
	*/
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	/**
	* user_emil  String(100)  //邮箱    
	*/
	public String getUserEmil(){
		return userEmil;
	}
	
	/**
	* user_emil  String(100)  //邮箱    
	*/
	public void setUserEmil(String userEmil){
		this.userEmil = userEmil;
	}
	
	/**
	* user_image  String(255)  //用户图像    
	*/
	public String getUserImage(){
		return userImage;
	}
	
	/**
	* user_image  String(255)  //用户图像    
	*/
	public void setUserImage(String userImage){
		this.userImage = userImage;
	}
	
	/**
	* user_phone  String(11)  //用户手机    
	*/
	public String getUserPhone(){
		return userPhone;
	}
	
	/**
	* user_phone  String(11)  //用户手机    
	*/
	public void setUserPhone(String userPhone){
		this.userPhone = userPhone;
	}
	
	/**
	* user_password  String(255)  //密码    
	*/
	public String getUserPassword(){
		return userPassword;
	}
	
	/**
	* user_password  String(255)  //密码    
	*/
	public void setUserPassword(String userPassword){
		this.userPassword = userPassword;
	}
	
	/**
	* login_time  Date(19)  //最后登录时间    
	*/
	public Date getLoginTime(){
		return loginTime;
	}
	
	/**
	* login_time  Date(19)  //最后登录时间    
	*/
	public void setLoginTime(Date loginTime){
		this.loginTime = loginTime;
	}
	
	/**
	* login_number  Integer(10)  //登录次数    
	*/
	public Integer getLoginNumber(){
		return loginNumber;
	}
	
	/**
	* login_number  Integer(10)  //登录次数    
	*/
	public void setLoginNumber(Integer loginNumber){
		this.loginNumber = loginNumber;
	}
	
	/**
	* is_vip  Integer(10)  //VIP  1是  2否    
	*/
	public Integer getIsVip(){
		return isVip;
	}
	
	/**
	* is_vip  Integer(10)  //VIP  1是  2否    
	*/
	public void setIsVip(Integer isVip){
		this.isVip = isVip;
	}
	
	/**
	* vip_time  Date(19)  //VIP失效时间    
	*/
	public Date getVipTime(){
		return vipTime;
	}
	
	/**
	* vip_time  Date(19)  //VIP失效时间    
	*/
	public void setVipTime(Date vipTime){
		this.vipTime = vipTime;
	}
	
	/**
	* user_synopsis  String(65535)  //简介    
	*/
	public String getUserSynopsis(){
		return userSynopsis;
	}
	
	/**
	* user_synopsis  String(65535)  //简介    
	*/
	public void setUserSynopsis(String userSynopsis){
		this.userSynopsis = userSynopsis;
	}
	
	/**
	* is_delete  Integer(10)  //是否删除1是  2否    
	*/
	public Integer getIsDelete(){
		return isDelete;
	}
	
	/**
	* is_delete  Integer(10)  //是否删除1是  2否    
	*/
	public void setIsDelete(Integer isDelete){
		this.isDelete = isDelete;
	}
	
	/**
	* creation_time  Date(19)  //创建时间    
	*/
	public Date getCreationTime(){
		return creationTime;
	}
	
	/**
	* creation_time  Date(19)  //创建时间    
	*/
	public void setCreationTime(Date creationTime){
		this.creationTime = creationTime;
	}
	
}