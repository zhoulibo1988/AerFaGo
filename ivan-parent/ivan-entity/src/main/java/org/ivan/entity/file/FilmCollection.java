package org.ivan.entity.file;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.film_collection          
--------------------------------------------------------
id                   Integer(10)        NOTNULL             //
user_id              Integer(10)                            //
film_id              Integer(10)                            //
creation_time        Date(19)                               //
*/
public class FilmCollection implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Integer id;
	private	Integer userId;
	private	Integer filmId;
	private	Date creationTime;
	
	private String filmName;
	private	String filmIntroduce;
	private String filmImg;
	
	public String getFilmName() {
		return filmName;
	}

	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}

	public String getFilmIntroduce() {
		return filmIntroduce;
	}

	public void setFilmIntroduce(String filmIntroduce) {
		this.filmIntroduce = filmIntroduce;
	}

	public String getFilmImg() {
		return filmImg;
	}

	public void setFilmImg(String filmImg) {
		this.filmImg = filmImg;
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
	* film_id  Integer(10)  //    
	*/
	public Integer getFilmId(){
		return filmId;
	}
	
	/**
	* film_id  Integer(10)  //    
	*/
	public void setFilmId(Integer filmId){
		this.filmId = filmId;
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