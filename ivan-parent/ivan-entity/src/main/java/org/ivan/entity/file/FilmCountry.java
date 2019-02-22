package org.ivan.entity.file;

import java.io.Serializable;

/**
TABLE:.film_country             
--------------------------------------------------------
id                   Integer(10)        NOTNULL             //
country_name         String(255)                            //
*/
public class FilmCountry implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Integer id;
	private	String countryName;

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
	* country_name  String(255)  //    
	*/
	public String getCountryName(){
		return countryName;
	}
	
	/**
	* country_name  String(255)  //    
	*/
	public void setCountryName(String countryName){
		this.countryName = countryName;
	}
	
}