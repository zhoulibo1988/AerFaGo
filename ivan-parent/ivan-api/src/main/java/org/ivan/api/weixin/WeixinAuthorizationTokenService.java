package org.ivan.api.weixin;

import java.util.List;
import java.util.Map;

import org.ivan.entity.WeixinAuthorizationToken;
import org.ivan.entity.utils.PageObject;

/**
 * @author cyl
 * @version 
 */
public interface WeixinAuthorizationTokenService{
public void insert(WeixinAuthorizationToken weixinAuthorizationToken);
	/**
	 * 根据map获取对象
	 * @param map
	 * @return
	 */
	public WeixinAuthorizationToken selectSingle(Map<String,Object> map);
	/**
	 * 根据对象查询对象
	 * @param weixinAuthorizationToken
	 * @return
	 */
	public WeixinAuthorizationToken selectSingle(WeixinAuthorizationToken weixinAuthorizationToken);
	/**
	 * 根据对象去修改对象
	 * @param weixinAuthorizationToken
	 */
	public void updateByEntity(WeixinAuthorizationToken weixinAuthorizationToken);
	 /**
     * 分页获取数据列表
     * @param map
     * @return
     */
    PageObject<WeixinAuthorizationToken> Pagequery(Map<String,Object> map);
    /**
     * 根据map获取List数据集合
     * @param map
     * @return
     */
    List<WeixinAuthorizationToken> getList(Map<String,Object> map);
}
