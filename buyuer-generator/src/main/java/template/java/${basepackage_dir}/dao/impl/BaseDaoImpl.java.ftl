package ${basepackage}.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import ${basepackage}.dao.BaseDao;
import ${basepackage}.mapper.BaseMapper;

/**
 * 数据库公共类Dao类接口实现类
 * @author cyl
 * @version 
 */
public class BaseDaoImpl<T, E> extends SqlSessionDaoSupport implements BaseDao<T, E>
{
	private Class<E> mapperClass;
	
	@Autowired
	private SqlSessionFactory sessionFactory;
	
	/**
	 * 设置对应的MapperClass
	 */
	public void setMapperClass(Class<E> mapperClass)
	{
		this.mapperClass = mapperClass;
	}
	
	/**
	 * 得到对应的Mapper对象
	 */
	public E getMapper()
	{
		return sessionFactory.getConfiguration().getMapper(mapperClass, getSqlSession());
	}
	
	@SuppressWarnings("unchecked")
    public BaseMapper<T> getBaseMapper()
	{
		return (BaseMapper<T>)this.getMapper();
	}
	
	/**
	 * 获取sessionFactory
	 * @return sessionFactory
	 */
	public SqlSessionFactory getSessionFactory(){
		return this.sessionFactory;
	}

	/**
	 * 获取总数[实体对象参数]
	 */
	public int getCount(T t) {
		// TODO Auto-generated method stub
		return this.getBaseMapper().getCount(t);
	}
	
	/**
	 * 获取总数[map参数]
	 */
	public int getCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return this.getBaseMapper().getCount(map);
	}

	/**
	 * 新增
	 */
	public void insertByEntity(T t) {
		// TODO Auto-generated method stub
		 this.getBaseMapper().insertByEntity(t);
	}
	/**
	 * 删除
	 */
	public void deleteByEntity(T t) {
		// TODO Auto-generated method stub
		this.getBaseMapper().deleteByEntity(t);
	}
	/**
	 * 修改
	 */
	public void updateByEntity(T t) {
		// TODO Auto-generated method stub
		this.getBaseMapper().updateByEntity(t);
	}
	/**
	 * 单条记录查询
	 */
	public T selectSingle(T t) {
		// TODO Auto-generated method stub
		return this.getBaseMapper().selectSingle(t);
	}
	 
	
	/**
	 * 根据实体信息查询实体列表
	 */
	public List<T> selectByObject(T t) {
		// TODO Auto-generated method stub
		return this.getBaseMapper().selectByObject(t);
	}
	/**
	 * 分页查询
	 */
	public List<T> pageQueryByObject(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getBaseMapper().pageQueryByObject(map);
	}
}
