<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    
<mapper namespace="${basepackage}.mapper.${table.className}Mapper">

	<resultMap id="${table.className?lower_case}_result_map" type="${basepackage}.entity.${table.className}" >
		<#list table.columnsList as column>
		<#if column.isPK >
		<id column="${column.columnName}" property="${column.columnNameFirstLower}" />
		<#else>
		<result column="${column.columnName}" property="${column.columnNameFirstLower}"/>
		</#if> 
		</#list>
	</resultMap>
	
	<!-- 分页limit -->
	<sql id="base_page_query_end">
		<![CDATA[
			LIMIT ${'#'}{curPage},${'#'}{pageData}
		]]>
	</sql>
	
	<!-- 获取总数 -->
	<select id="getCount" resultType="java.lang.Integer">
		select count(*) from ${table.tableName}  
		<where>
      	<#list table.columnsList as column>
      		<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
			<if test="${column.columnNameFirstLower}Begin!=null and ${column.columnNameFirstLower}Begin!=''">
				and ${column.columnName} <![CDATA[ >= ]]> str_to_date(${'#'}{${column.columnNameFirstLower}Begin},  '%Y-%m-%d') 
			</if>
			<if test="${column.columnNameFirstLower}End!=null and ${column.columnNameFirstLower}End!=''">
				and ${column.columnName} <![CDATA[ <= ]]> str_to_date(${'#'}{${column.columnNameFirstLower}End},  '%Y-%m-%d') 
			</if>
			<#elseif column.isLongColumn>
			<if test="${column.columnNameFirstLower}!=null and ${column.columnNameFirstLower}!=''">
				and ${column.columnName} = ${'#'}{${column.columnNameFirstLower}}
			</if>
			<#elseif column.isDTColumn>
			<if test="${column.columnNameFirstLower}!=null and ${column.columnNameFirstLower}!=''">
				and ${column.columnName} = ${'#'}{${column.columnNameFirstLower}}
			</if>
			<#else>
			<if test="${column.columnNameFirstLower}!=null and ${column.columnNameFirstLower}!=''">
				and ${column.columnName} like CONCAT('%',${'#'}{${column.columnNameFirstLower}},'%')
			</if>
			</#if>
		</#list>
		</where>
	</select>
	
	<!-- 新增对象 如果需要在新增的时候返回id 就加入这个: useGeneratedKeys="true" keyProperty="id"   -->
	<insert id="insertByEntity" parameterType="${basepackage}.entity.${table.className}" useGeneratedKeys="true" keyProperty="id">
		insert into ${table.tableName}
		<trim prefix="(" suffix=")" suffixOverrides="," >
      		<#list table.columnsList as column>
      		<if test="${column.columnNameFirstLower}!=null ">
				${column.columnName},
			</if>
			</#list>
    	</trim>
		<trim prefix="values (" suffix=")" suffixOverrides="," >
      		<#list table.columnsList as column>
      		<if test="${column.columnNameFirstLower}!=null ">
      			${'#'}{${column.columnNameFirstLower}},
			</if>
			</#list>
    	</trim>
	</insert>
	
	<!-- 根据删除 -->
	<delete id="deleteByEntity" parameterType="${basepackage}.entity.${table.className}">
		delete from ${table.tableName}
		<where>
			<#list table.columnsList as column>
      		<if test="${column.columnNameFirstLower}!=null ">
				and ${column.columnName}=${'#'}{${column.columnNameFirstLower}}
			</if>
		</#list>
		</where>
	</delete>

	<!-- 根据对象修改 -->
	<update id="updateByEntity" parameterType="${basepackage}.entity.${table.className}">
		update ${table.tableName}
		<set>
		<#list table.columnsList as column>
		<#if !column.isPK >
			<if test="${column.columnNameFirstLower}!=null ">
				${column.columnName}=${'#'}{${column.columnNameFirstLower}},
			</if>
		</#if>
		</#list>
		</set>
		<where>
		<#list table.columnsList as column>
		<#if column.isPK >
			<if test="${column.columnNameFirstLower}!=null ">
				and ${column.columnName}=${'#'}{${column.columnNameFirstLower}}
			</if>
		</#if>
		</#list>
		</where>
	</update>

	<!-- 根据对象查询单个 -->
	<select id="selectSingle" resultType="${basepackage}.entity.${table.className}" resultMap="${table.className?lower_case}_result_map">
		<#assign cols><#list table.columnsList as column>${column.columnName},</#list></#assign>
		select ${cols[0..cols?length-2]} from ${table.tableName}
		<where>
		<#list table.columnsList as column>
			<if test="${column.columnNameFirstLower}!=null ">
				and ${column.columnName}=${'#'}{${column.columnNameFirstLower}}
			</if>
		</#list>
		</where>
	</select>
	
	<!-- 根据对象查询列表-->
	<select id="selectByObject" resultMap="${table.className?lower_case}_result_map" >
		<#assign cols><#list table.columnsList as column>${column.columnName},</#list></#assign>
		select ${cols[0..cols?length-2]} from ${table.tableName}
		<where>
		<#list table.columnsList as column>
			<if test="${column.columnNameFirstLower}!=null ">
				and ${column.columnName}=${'#'}{${column.columnNameFirstLower}}
			</if>
		</#list>
		</where>
	</select>
	
	<!-- 分页 -->
	<select id="pageQueryByObject" resultMap="${table.className?lower_case}_result_map" >
		
		SELECT ${cols[0..cols?length-2]} FROM ${table.tableName} 
		<where>
		<#list table.columnsList as column>
      		<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
			<if test="${column.columnNameFirstLower}Begin!=null and ${column.columnNameFirstLower}Begin!=''">
				and ${column.columnName} <![CDATA[ >= ]]> str_to_date(${'#'}{${column.columnNameFirstLower}Begin},  '%Y-%m-%d') 
			</if>
			<if test="${column.columnNameFirstLower}End!=null and ${column.columnNameFirstLower}End!=''">
				and ${column.columnName} <![CDATA[ <= ]]> str_to_date(${'#'}{${column.columnNameFirstLower}End},  '%Y-%m-%d') 
			</if>
			<#elseif column.isLongColumn>
			<if test="${column.columnNameFirstLower}!=null and ${column.columnNameFirstLower}!=''">
				and ${column.columnName} = ${'#'}{${column.columnNameFirstLower}}
			</if>
			<#elseif column.isDTColumn>
			<if test="${column.columnNameFirstLower}!=null and ${column.columnNameFirstLower}!=''">
				and ${column.columnName} = ${'#'}{${column.columnNameFirstLower}}
			</if>
			<#else>
			<if test="${column.columnNameFirstLower}!=null and ${column.columnNameFirstLower}!=''">
				and ${column.columnName} like  CONCAT('%',${'#'}{${column.columnNameFirstLower}},'%')
			</if>
			</#if>
		</#list>
		</where>
		<#noparse>
			<if test="sortName!=null and sortName!=''">
				ORDER BY ${sortName} ${sortOrder}
			</if>
		</#noparse>
		<include refid="base_page_query_end"/>
	</select>
	
</mapper>