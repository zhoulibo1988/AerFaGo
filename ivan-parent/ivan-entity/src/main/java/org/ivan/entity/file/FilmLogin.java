package org.ivan.entity.file;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.film_login               
--------------------------------------------------------
id                   Integer(10)        NOTNULL             //
user_id              Integer(10)                            //
login_time           Date(19)                               //
creation_time        Date(19)                               //
*/
public class FilmLogin implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Integer id;
	private	Integer userId;
	private	Date loginTime;
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
	* user_id  Integer(10)  //    
	*/
	public Integer getUserId(){
		return userId;
	}
	
	/**
	* user_id  Integer(10)  //    
	*/
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	
	/**
	* login_time  Date(19)  //    
	*/
	public Date getLoginTime(){
		return loginTime;
	}
	
	/**
	* login_time  Date(19)  //    
	*/
	public void setLoginTime(Date loginTime){
		this.loginTime = loginTime;
	}
	
	/**
	* creation_time  Date(19)  //    
	*/
	public Date getCreationTime(){
		return creationTime;
	}
	
	/**
	* creation_time  Date(19)  //    
	*/
	public void setCreationTime(Date creationTime){
		this.creationTime = creationTime;
	}
	
}