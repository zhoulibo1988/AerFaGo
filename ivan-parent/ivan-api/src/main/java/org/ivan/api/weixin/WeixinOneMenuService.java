package org.ivan.api.weixin;

import java.util.List;
import java.util.Map;

import org.ivan.entity.wx.WeixinOneMenu;


/**
 * 自定义菜单
 * @author 周立波
 *
 */
public interface WeixinOneMenuService extends BaseService<WeixinOneMenu>{
    /**
     * 根据map获取二级菜单数据集合
     * @param map
     * @return
     */
    List<WeixinOneMenu> getListBy2Menu(Map<String,Object> map);
    
}
