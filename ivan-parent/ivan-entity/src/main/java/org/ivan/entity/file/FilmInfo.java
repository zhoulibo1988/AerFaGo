package org.ivan.entity.file;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.film_info                
--------------------------------------------------------
id                   Integer(10)        NOTNULL             //
film_name            String(255)                            //电影名字
label_id             Integer(10)                            //标签ID
film_time            String(100)                            //电影时长
country_id           Integer(10)                            //国家ID
star_id              Integer(10)                            //星级ID
release_time         String(255)                            //上映时间
play_number          Integer(10)                            //播放量
comment_number       Integer(10)                            //评论数
film_introduce       String(65535)                          //影片介绍
film_state           Integer(10)                            //1:上架 2下架
play_url             String(255)                            //播放地址
film_vip             Integer(10)                            //是否是VIP影片：1是 2不是
creation_time        Date(19)                               //
update_time          Date(19)                               //
creation_userid      Integer(10)                            //
update_userid        Integer(10)                            //
*/
public class FilmInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Integer id;
	private	String filmName;
	private	Integer labelId;
	private	String filmTime;
	private	Integer countryId;
	private	Integer starId;
	private	String releaseTime;
	private	Integer playNumber;
	private	Integer commentNumber;
	private	String filmIntroduce;
	private	Integer filmState;
	private	String playUrl;
	private	Integer filmVip;
	private	Date creationTime;
	private	Date updateTime;
	private	Integer creationUserid;
	private	Integer updateUserid;

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
	* film_name  String(255)  //电影名字    
	*/
	public String getFilmName(){
		return filmName;
	}
	
	/**
	* film_name  String(255)  //电影名字    
	*/
	public void setFilmName(String filmName){
		this.filmName = filmName;
	}
	
	/**
	* label_id  Integer(10)  //标签ID    
	*/
	public Integer getLabelId(){
		return labelId;
	}
	
	/**
	* label_id  Integer(10)  //标签ID    
	*/
	public void setLabelId(Integer labelId){
		this.labelId = labelId;
	}
	
	/**
	* film_time  String(100)  //电影时长    
	*/
	public String getFilmTime(){
		return filmTime;
	}
	
	/**
	* film_time  String(100)  //电影时长    
	*/
	public void setFilmTime(String filmTime){
		this.filmTime = filmTime;
	}
	
	/**
	* country_id  Integer(10)  //国家ID    
	*/
	public Integer getCountryId(){
		return countryId;
	}
	
	/**
	* country_id  Integer(10)  //国家ID    
	*/
	public void setCountryId(Integer countryId){
		this.countryId = countryId;
	}
	
	/**
	* star_id  Integer(10)  //星级ID    
	*/
	public Integer getStarId(){
		return starId;
	}
	
	/**
	* star_id  Integer(10)  //星级ID    
	*/
	public void setStarId(Integer starId){
		this.starId = starId;
	}
	
	/**
	* release_time  String(255)  //上映时间    
	*/
	public String getReleaseTime(){
		return releaseTime;
	}
	
	/**
	* release_time  String(255)  //上映时间    
	*/
	public void setReleaseTime(String releaseTime){
		this.releaseTime = releaseTime;
	}
	
	/**
	* play_number  Integer(10)  //播放量    
	*/
	public Integer getPlayNumber(){
		return playNumber;
	}
	
	/**
	* play_number  Integer(10)  //播放量    
	*/
	public void setPlayNumber(Integer playNumber){
		this.playNumber = playNumber;
	}
	
	/**
	* comment_number  Integer(10)  //评论数    
	*/
	public Integer getCommentNumber(){
		return commentNumber;
	}
	
	/**
	* comment_number  Integer(10)  //评论数    
	*/
	public void setCommentNumber(Integer commentNumber){
		this.commentNumber = commentNumber;
	}
	
	/**
	* film_introduce  String(65535)  //影片介绍    
	*/
	public String getFilmIntroduce(){
		return filmIntroduce;
	}
	
	/**
	* film_introduce  String(65535)  //影片介绍    
	*/
	public void setFilmIntroduce(String filmIntroduce){
		this.filmIntroduce = filmIntroduce;
	}
	
	/**
	* film_state  Integer(10)  //1:上架  2下架    
	*/
	public Integer getFilmState(){
		return filmState;
	}
	
	/**
	* film_state  Integer(10)  //1:上架  2下架    
	*/
	public void setFilmState(Integer filmState){
		this.filmState = filmState;
	}
	
	/**
	* play_url  String(255)  //播放地址    
	*/
	public String getPlayUrl(){
		return playUrl;
	}
	
	/**
	* play_url  String(255)  //播放地址    
	*/
	public void setPlayUrl(String playUrl){
		this.playUrl = playUrl;
	}
	
	/**
	* film_vip  Integer(10)  //是否是VIP影片：1是  2不是    
	*/
	public Integer getFilmVip(){
		return filmVip;
	}
	
	/**
	* film_vip  Integer(10)  //是否是VIP影片：1是  2不是    
	*/
	public void setFilmVip(Integer filmVip){
		this.filmVip = filmVip;
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
	
	/**
	* update_time  Date(19)  //    
	*/
	public Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	* update_time  Date(19)  //    
	*/
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	/**
	* creation_userid  Integer(10)  //    
	*/
	public Integer getCreationUserid(){
		return creationUserid;
	}
	
	/**
	* creation_userid  Integer(10)  //    
	*/
	public void setCreationUserid(Integer creationUserid){
		this.creationUserid = creationUserid;
	}
	
	/**
	* update_userid  Integer(10)  //    
	*/
	public Integer getUpdateUserid(){
		return updateUserid;
	}
	
	/**
	* update_userid  Integer(10)  //    
	*/
	public void setUpdateUserid(Integer updateUserid){
		this.updateUserid = updateUserid;
	}
	
}