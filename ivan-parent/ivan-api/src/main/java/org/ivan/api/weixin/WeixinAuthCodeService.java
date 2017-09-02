package org.ivan.api.weixin;

import java.util.Map;

import org.ivan.entity.WeixinAuthCode;
import org.ivan.entity.admin.SysUcenterApps;
import org.ivan.entity.utils.PageObject;


/**
 * @author cyl
 * @version 
 */
public interface WeixinAuthCodeService{
	public void insert(WeixinAuthCode weixinAuthCode);
	
	public WeixinAuthCode selectSingle(Map<String,Object> map);
	
	public WeixinAuthCode selectSingle(WeixinAuthCode weixinAuthCode);
	
	public void updateByEntity(WeixinAuthCode weixinAuthCode);
	 /**
     * 分页获取
     * @param map
     * @return
     */
    PageObject<WeixinAuthCode> Pagequery(Map<String,Object> map);
}
