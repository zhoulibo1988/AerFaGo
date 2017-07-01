package ${basepackage}.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import ${basepackage}.dao.${table.className}Dao;
import ${basepackage}.entity.${table.className};
import ${basepackage}.mapper.${table.className}Mapper;

/**
 * @author cyl
 * @version 
 */
@Repository
public class ${table.className}DaoImpl extends BaseDaoImpl<${table.className}, ${table.className}Mapper>
		implements ${table.className}Dao {
	Logger logger = Logger.getLogger(this.getClass());

	public ${table.className}DaoImpl() {
		setMapperClass(${table.className}Mapper.class);
	}
}
