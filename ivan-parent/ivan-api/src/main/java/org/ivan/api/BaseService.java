package org.ivan.api;

import java.util.List;
import java.util.Map;

import org.ivan.entity.utils.PageObject;
/**
 * 父级service
 * @author 周立波
 *
 * @param <T>
 */
public interface BaseService<T> {
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
	
	public <R> List<R> handleGameInfo(List<R> list, boolean flag);
	
	Integer getCount(Map<String, Object> map);
}
