package org.ivan.entity.wx;

import java.io.Serializable;

/**
TABLE:.weixin_one_menu          
--------------------------------------------------------
id                   Integer(10)        NOTNULL             //
fid                  Integer(10)                            //父级ID
authorizer_appid     String(255)                            //授权方appid
type                 String(10)                             //按钮类型
name                 String(100)                            //按钮名字
tykey                String(20)                             //按钮key值
url                  String(255)                            //跳转URL
media_id             String(100)                            //菜单id
appid                String(255)                            //小程序的appid
pagepath             String(100)                            //小程序的页面路径
menu_type            Integer(10)                            //菜单级别类型
*/
public class WeixinOneMenu implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Integer id;
	private	Integer fid;
	private	String authorizerAppid;
	private	String type;
	private	String name;
	private	String tykey;
	private	String url;
	private	String mediaId;
	private	String appid;
	private	String pagepath;
	private	Integer menuType;

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
	* fid  Integer(10)  //父级ID    
	*/
	public Integer getFid(){
		return fid;
	}
	
	/**
	* fid  Integer(10)  //父级ID    
	*/
	public void setFid(Integer fid){
		this.fid = fid;
	}
	
	/**
	* authorizer_appid  String(255)  //授权方appid    
	*/
	public String getAuthorizerAppid(){
		return authorizerAppid;
	}
	
	/**
	* authorizer_appid  String(255)  //授权方appid    
	*/
	public void setAuthorizerAppid(String authorizerAppid){
		this.authorizerAppid = authorizerAppid;
	}
	
	/**
	* type  String(10)  //按钮类型    
	*/
	public String getType(){
		return type;
	}
	
	/**
	* type  String(10)  //按钮类型    
	*/
	public void setType(String type){
		this.type = type;
	}
	
	/**
	* name  String(100)  //按钮名字    
	*/
	public String getName(){
		return name;
	}
	
	/**
	* name  String(100)  //按钮名字    
	*/
	public void setName(String name){
		this.name = name;
	}
	
	/**
	* tykey  String(20)  //按钮key值    
	*/
	public String getTykey(){
		return tykey;
	}
	
	/**
	* tykey  String(20)  //按钮key值    
	*/
	public void setTykey(String tykey){
		this.tykey = tykey;
	}
	
	/**
	* url  String(255)  //跳转URL    
	*/
	public String getUrl(){
		return url;
	}
	
	/**
	* url  String(255)  //跳转URL    
	*/
	public void setUrl(String url){
		this.url = url;
	}
	
	/**
	* media_id  String(100)  //菜单id    
	*/
	public String getMediaId(){
		return mediaId;
	}
	
	/**
	* media_id  String(100)  //菜单id    
	*/
	public void setMediaId(String mediaId){
		this.mediaId = mediaId;
	}
	
	/**
	* appid  String(255)  //小程序的appid    
	*/
	public String getAppid(){
		return appid;
	}
	
	/**
	* appid  String(255)  //小程序的appid    
	*/
	public void setAppid(String appid){
		this.appid = appid;
	}
	
	/**
	* pagepath  String(100)  //小程序的页面路径    
	*/
	public String getPagepath(){
		return pagepath;
	}
	
	/**
	* pagepath  String(100)  //小程序的页面路径    
	*/
	public void setPagepath(String pagepath){
		this.pagepath = pagepath;
	}
	
	/**
	* menu_type  Integer(10)  //菜单级别类型    
	*/
	public Integer getMenuType(){
		return menuType;
	}
	
	/**
	* menu_type  Integer(10)  //菜单级别类型    
	*/
	public void setMenuType(Integer menuType){
		this.menuType = menuType;
	}
	
}