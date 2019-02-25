package org.ivan.entity.file;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.film_comment             
--------------------------------------------------------
id                   Integer(10)        NOTNULL             //
user_id              Integer(10)                            //用户id
film_id              Integer(10)                            //电影ID
comment_txt          String(255)                            //评论内容
comment_time         Date(19)                               //评论时间
creation_time        Date(19)                               //创建时间
*/
public class FilmComment implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Integer id;
	private	Integer userId;
	private	Integer filmId;
	private	String commentTxt;
	private	Date commentTime;
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
	* user_id  Integer(10)  //用户id    
	*/
	public Integer getUserId(){
		return userId;
	}
	
	/**
	* user_id  Integer(10)  //用户id    
	*/
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	
	/**
	* film_id  Integer(10)  //电影ID    
	*/
	public Integer getFilmId(){
		return filmId;
	}
	
	/**
	* film_id  Integer(10)  //电影ID    
	*/
	public void setFilmId(Integer filmId){
		this.filmId = filmId;
	}
	
	/**
	* comment_txt  String(255)  //评论内容    
	*/
	public String getCommentTxt(){
		return commentTxt;
	}
	
	/**
	* comment_txt  String(255)  //评论内容    
	*/
	public void setCommentTxt(String commentTxt){
		this.commentTxt = commentTxt;
	}
	
	/**
	* comment_time  Date(19)  //评论时间    
	*/
	public Date getCommentTime(){
		return commentTime;
	}
	
	/**
	* comment_time  Date(19)  //评论时间    
	*/
	public void setCommentTime(Date commentTime){
		this.commentTime = commentTime;
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