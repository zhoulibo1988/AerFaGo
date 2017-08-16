package com.ivan.dubbo.dao.system;

import org.ivan.entity.admin.SysUcenterFiles;

import com.ivan.dubbo.dao.BaseMapper;

/**
 * @author cyl
 * @version 
 */
public interface SysUcenterFilesMapper extends BaseMapper<SysUcenterFiles>{

	SysUcenterFiles selectMD5(String md5);
}