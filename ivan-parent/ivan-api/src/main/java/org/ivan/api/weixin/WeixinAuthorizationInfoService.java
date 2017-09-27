package org.ivan.api.weixin;

import java.util.List;
import java.util.Map;

import org.ivan.entity.wx.WeixinAuthorizationInfo;


/**
 * @author cyl
 * @version 
 */
public interface WeixinAuthorizationInfoService extends BaseService<WeixinAuthorizationInfo>{
	/**
	 * 获取List集合
	 * @param map
	 * @return
	 */
	public List<WeixinAuthorizationInfo> getListByMap(Map<String,Object> map);
	
}
