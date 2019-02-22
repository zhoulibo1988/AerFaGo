package org.ivan.entity.file;

import java.io.Serializable;

/**
TABLE:.film_star                
--------------------------------------------------------
id                   Integer(10)        NOTNULL             //
star_number          Integer(10)                            //星级数
*/
public class FilmStar implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Integer id;
	private	Integer starNumber;

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
	* star_number  Integer(10)  //星级数    
	*/
	public Integer getStarNumber(){
		return starNumber;
	}
	
	/**
	* star_number  Integer(10)  //星级数    
	*/
	public void setStarNumber(Integer starNumber){
		this.starNumber = starNumber;
	}
	
}