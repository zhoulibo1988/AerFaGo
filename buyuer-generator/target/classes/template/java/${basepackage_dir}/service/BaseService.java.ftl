package ${basepackage}.base.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import ${basepackage}.base.util.PageObject;

/**
 * @author cyl
 * @version
 */
public interface BaseService<T, E> {

    /**
     * 设置对应的MapperClass
     * 
     * @param mapperClass 要设置的MapperClass
     * @throws DataAccessException DataAccessException
     */
    void setMapperClass(Class<E> mapperClass, Class<T> tClass) throws DataAccessException;

    List<T> query(Map<String, Object> map);

    void delete(Map<String, Object> map) throws Exception;

    void delete(T t) throws Throwable;

    T add(Map<String, Object> map) throws Exception;

    T add(T t) throws Exception;

    void update(Map<String, Object> map) throws Exception;

    void update(T t) throws Exception;

    T detail(Map<String, Object> map);

    T M2O(Map<String, Object> map);

    PageObject<T> Pagequery(Map<String, Object> map);

}
