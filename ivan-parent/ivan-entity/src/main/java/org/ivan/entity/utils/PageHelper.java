package org.ivan.entity.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageHelper {
	private static final Logger logger = LoggerFactory.getLogger(PageHelper.class);
	
	private Map<String, Object> map;
	private int totalData = 0;
	private int curPage =1;
	private int pageData=1;

	public PageHelper(int totalData,Map<String, Object> map) {
		this.totalData = totalData;
		this.map = map;
	}

	public HashMap<String, Object> getMap() {
		 if (map.get("curPage") != null) {
            curPage = Integer.valueOf(map.get("curPage").toString());
            pageData = Integer.valueOf(map.get("pageData").toString());
            logger.debug("curPage:" + curPage + "  pageData:" + pageData);
            map.put("curPage", (curPage + 1) < 0 ? (0 * pageData) : ((curPage-1) * pageData));
            // 让pageData变成INT
//            map.put("pageData", (curPage + 1) < 0 ? (0 * pageData) : ((curPage) * pageData));
            map.put("pageData", pageData);
        } else {
            map.put("curPage", curPage);
            map.put("pageData", pageData);
        }
        return (HashMap<String, Object>) map;
	}
	
	public PageObject getPageObject(){
		PageObject pageObject = new PageObject(totalData,curPage,pageData);
		return pageObject;
	}
}
