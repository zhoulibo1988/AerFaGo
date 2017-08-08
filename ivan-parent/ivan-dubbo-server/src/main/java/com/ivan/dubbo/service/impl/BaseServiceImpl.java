package com.ivan.dubbo.service.impl;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Method;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.ivan.api.BaseService;
import org.ivan.entity.utils.MapToEntity;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.BaseMapper;
@Service
public class BaseServiceImpl<T, E> extends SqlSessionDaoSupport implements BaseService<T> {
	private static final Logger LOGGER = Logger.getLogger(BaseServiceImpl.class);
	private T t;

	private Class<T> tClass;

	private Class<E> mapperClass;

	@Autowired
	private SqlSessionFactory sessionFactory;

	/**
	 * 设置对应的MapperClass
	 */
	public void setMapperClass(Class<E> mapperClass, Class<T> tClass) {
		this.mapperClass = mapperClass;
		this.tClass = tClass;
	}

	/**
	 * 得到对应的Mapper对象
	 */
	public E getMapper() {
		return sessionFactory.getConfiguration().getMapper(mapperClass, getSqlSession());
	}

	@SuppressWarnings("unchecked")
	public BaseMapper<T> getBaseMapper() {
		return (BaseMapper<T>) this.getMapper();
	}

	/**
	 * 获取sessionFactory
	 * 
	 * @return sessionFactory
	 */
	public SqlSessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public List<T> query(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getBaseMapper().selectByObject(M2O(map));
	}

	public void delete(Map<String, Object> map) throws Exception {
		T t = M2O(map);
		if (t != null) {
			try {
				this.getBaseMapper().deleteByEntity(M2O(map));
			} catch (Exception e) {
				LOGGER.error("Server request exception !", e);
				throw new Exception("Server request exception !", e);
			}
		} else {
			throw new Exception("Server request Parames error !");
		}
	}

	public void delete(T t) throws Exception {
		// TODO Auto-generated method stub
		if (t != null) {
			try {
				this.getBaseMapper().deleteByEntity(t);
			} catch (Exception e) {
				LOGGER.error("Server request exception !", e);
				throw new Exception("Server request exception !", e);
			}
		} else {
			throw new Exception("Server request Parames error !");
		}
	}

	public T add(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		try {
			t = M2O(map);
			if (t != null) {
				this.getBaseMapper().insertByEntity(t);
			}

		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Server request exception !", e);
			throw new Exception("Server request exception !", e);
		}
		return t;
	}

	public T add(T t) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.getBaseMapper().insertByEntity(t);
		} catch (Exception e) {
			LOGGER.error("Server request exception !", e);
			throw new Exception("Server request exception !", e);
		}
		return t;
	}

	public void update(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.getBaseMapper().updateByEntity(M2O(map));
		} catch (Exception e) {
			LOGGER.error("Server request exception !", e);
			throw new Exception("Server request exception !", e);
		}
	}

	public void update(T t) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.getBaseMapper().updateByEntity(t);
		} catch (Exception e) {
			LOGGER.error("Server request exception !", e);
			throw new Exception("Server request exception !", e);
		}
	}

	public T detail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getBaseMapper().selectSingle(M2O(map));
	}

	@SuppressWarnings("unchecked")
	public PageObject<T> Pagequery(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int totalData = this.getBaseMapper().getCount(map);
		PageHelper pageHelper = new PageHelper(totalData, map);
		List<T> list = this.getBaseMapper().pageQueryByObject(pageHelper.getMap());
		PageObject<T> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
	}

	public T M2O(Map<String, Object> map) {
		// TODO Auto-generated method stub
		MapToEntity<T> mapToEntity = new MapToEntity<T>(tClass, map);
		return (T) mapToEntity.getEntity();
	}

	public <R> List<R> handleGameInfo(List<R> list, boolean flag) {
		if (flag && list != null && list.size() > 0) {
			Method method = null;
			try {
				method = list.get(0).getClass().getMethod("setShowType", Integer.class);
				for (R r : list) {
					method.invoke(r, 1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public Integer getCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int totalData = this.getBaseMapper().getCount(map);
		return totalData;
	}
}
