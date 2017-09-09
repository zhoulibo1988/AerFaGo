package org.ivan.api.weixin;

import java.util.List;
import java.util.Map;

import org.ivan.entity.WeixinAuthorizationInfo;
import org.ivan.entity.utils.PageObject;


/**
 * @author cyl
 * @version 
 */
public interface WeixinAuthorizationInfoService {
	/**
	 * 插入对象
	 * @param WeixinAuthorizationInfo
	 */
	public void insert(WeixinAuthorizationInfo WeixinAuthorizationInfo);
	/**
	 * 查询对象
	 * @param map
	 * @return
	 */
	public WeixinAuthorizationInfo selectSingle(Map<String,Object> map);
	/**
	 * 查询对象
	 * @param WeixinAuthorizationInfo
	 * @return
	 */
	public WeixinAuthorizationInfo selectSingle(WeixinAuthorizationInfo WeixinAuthorizationInfo);
	/**
	 * 修改对象
	 * @param WeixinAuthorizationInfo
	 */
	public void updateByEntity(WeixinAuthorizationInfo WeixinAuthorizationInfo);
	/**
	 * 获取List集合
	 * @param map
	 * @return
	 */
	public List<WeixinAuthorizationInfo> getListByMap(Map<String,Object> map);
	 /**
     * 分页获取数据列表
     * @param map
     * @return
     */
    PageObject<WeixinAuthorizationInfo> Pagequery(Map<String,Object> map);
}
