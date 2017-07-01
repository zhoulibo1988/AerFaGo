<#assign ignores = []>
package ${basepackage}.entity;

import java.io.Serializable;
import java.util.Date;

/**
${table}
*/
public class ${table.className} implements Serializable{

	private static final long serialVersionUID = 1L;
	
<#list table.columnsList as column>
	<#if ignores?seq_contains(column.columnName)==false>
	private	${column.javaType} ${column.columnNameFirstLower};
	</#if>
</#list>

<#list table.columnsList as column>
	<#if ignores?seq_contains(column.columnName)==false>
	/**
	* <#list (column?word_list) as word>${word}  </#list>  
	*/
	public ${column.javaType} get${column.columnNameFirstLower?cap_first}(){
		return ${column.columnNameFirstLower};
	}
	
	/**
	* <#list (column?word_list) as word>${word}  </#list>  
	*/
	public void set${column.columnNameFirstLower?cap_first}(${column.javaType} ${column.columnNameFirstLower}){
		this.${column.columnNameFirstLower} = ${column.columnNameFirstLower};
	}
	
	</#if>
</#list>
}