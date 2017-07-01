package ${basepackage}.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 数据库公共类Dao类接口
 * @author cyl
 * @version 
 * @param <E>
 */
public interface BaseDao<T, E>
{
	/**
	 * 设置对应的MapperClass
	 * @param mapperClass 要设置的MapperClass
	 * @throws DataAccessException DataAccessException
	 */
	void setMapperClass(Class<E> mapperClass) throws DataAccessException;
	
	/**
	 * 获取对应的Mapper
	 * @return Mapper对象
	 * @throws DataAccessException DataAccessException
	 */
	E getMapper() throws DataAccessException;

	/**
	 * 获取总数
	 * @return int
	 */
	int getCount(T t);
	int getCount(Map<String,Object> map);
	
	/**
	 * 插入数据
	 * @param t
	 */
	void insertByEntity(T t);
	
	/**
	 * 删除数据
	 * @param t
	 */
	void deleteByEntity(T t);
	
	/**
	 * 更新数据
	 * @param examuser
	 */
	void updateByEntity(T t);
	
 
	/**
	 * 查询单条记录
	 * @param t id
	 * @return
	 */
	T selectSingle(T t);
	
	
	/**
	 * 多条选择
	 * @param examuser
	 * @return List<T>
	 */
	List<T> selectByObject(T t);
	
	/**
	 * 分页查询
	 * @param map
	 * @return List<ExamUser>
	 */
	List<T> pageQueryByObject(HashMap<String,Object> map);
	
	
}
