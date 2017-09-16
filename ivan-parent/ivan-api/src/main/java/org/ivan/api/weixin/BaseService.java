package org.ivan.api.weixin;

import java.util.List;
import java.util.Map;

import org.ivan.entity.utils.PageObject;

public interface BaseService<T> {
	/**
	 * 添加
	 * @param weixinAuthorizationToken
	 */
	public void insert(T t);
	/**
	 * 根据map获取对象
	 * @param map
	 * @return
	 */
	public T selectSingle(Map<String,Object> map);
	/**
	 * 根据对象查询对象
	 * @param weixinAuthorizationToken
	 * @return
	 */
	public T selectSingle(T t);
	/**
	 * 根据对象去修改对象
	 * @param weixinAuthorizationToken
	 */
	public void updateByEntity(T t);
	/**
	 * 根据map去修改对象
	 * @param weixinAuthorizationToken
	 */
	public void updateByEntity(Map<String,Object> map);
	 /**
     * 分页获取数据列表
     * @param map
     * @return
     */
    PageObject<T> Pagequery(Map<String,Object> map);
    /**
     * 根据map获取List数据集合
     * @param map
     * @return
     */
    List<T> getList(Map<String,Object> map);
    /**
     * 根据map删除数据
     * @param map
     */
    public void del(Map<String,Object> map);
    /**
     * 根据对象删除数据
     * @param weixinOneMenu
     */
    public void del(T t);
}
