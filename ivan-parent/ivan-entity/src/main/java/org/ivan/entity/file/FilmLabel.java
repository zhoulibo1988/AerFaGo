package org.ivan.entity.file;

import java.io.Serializable;

/**
TABLE:.film_label               
--------------------------------------------------------
id                   Integer(10)        NOTNULL             //
label_name           String(255)                            //标签名称
*/
public class FilmLabel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Integer id;
	private	String labelName;

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
	* label_name  String(255)  //标签名称    
	*/
	public String getLabelName(){
		return labelName;
	}
	
	/**
	* label_name  String(255)  //标签名称    
	*/
	public void setLabelName(String labelName){
		this.labelName = labelName;
	}
	
}