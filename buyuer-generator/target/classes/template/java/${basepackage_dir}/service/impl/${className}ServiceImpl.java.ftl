package ${basepackage}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${basepackage}.entity.${table.className};
import ${basepackage}.mapper.${table.className}Mapper;
import ${basepackage}.service.${table.className}Service;
import com.core.teamwork.base.service.impl.BaseServiceImpl;

/**
 * @author cyl
 * @version 
 */
@Service("${table.className?uncap_first}Service")
public class ${table.className}ServiceImpl extends BaseServiceImpl<${table.className}, ${table.className}Mapper> implements ${table.className}Service {
	// 注入当前dao对象
    @Autowired
    private ${table.className}Mapper ${table.classNameFirstLower}Mapper;

    public ${table.className}ServiceImpl() {
        setMapperClass(${table.className}Mapper.class, ${table.className}.class);
    }
    
 
}
