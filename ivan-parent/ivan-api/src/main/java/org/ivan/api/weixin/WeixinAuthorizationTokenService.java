package org.ivan.api.weixin;

import java.util.Map;

import org.ivan.entity.WeixinAuthorizationToken;
import org.ivan.entity.utils.PageObject;

/**
 * @author cyl
 * @version 
 */
public interface WeixinAuthorizationTokenService{
public void insert(WeixinAuthorizationToken weixinAuthorizationToken);
	
	public WeixinAuthorizationToken selectSingle(Map<String,Object> map);
	
	public WeixinAuthorizationToken selectSingle(WeixinAuthorizationToken weixinAuthorizationToken);
	
	
	public void updateByEntity(WeixinAuthorizationToken weixinAuthorizationToken);
	
	
	 /**
     * 分页获取
     * @param map
     * @return
     */
    PageObject<WeixinAuthorizationToken> Pagequery(Map<String,Object> map);
}
