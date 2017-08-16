package com.ivan.dubbo.service.impl.system;

import java.util.List;
import java.util.Map;

import org.ivan.api.system.SysConfigDictionaryService;
import org.ivan.entity.admin.SysConfigDictionary;
import org.ivan.entity.utils.PageHelper;
import org.ivan.entity.utils.PageObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.ivan.dubbo.dao.system.SysConfigDictionaryMapper;

/**
 * @author buyuer
 * @version
 */
@Service
public class SysConfigDictionaryServiceImpl implements SysConfigDictionaryService {
    // 注入当前dao对象
    @Autowired
    private SysConfigDictionaryMapper sysConfigDictionaryMapper;

    
    public SysConfigDictionary getDic(int type, String value) {
        SysConfigDictionary dictionary = new SysConfigDictionary();
        dictionary.setDictPvalue(type);
        dictionary.setDictValue(value);
        dictionary.setCurrType(0);
        return sysConfigDictionaryMapper.selectSingle(dictionary);
    }

    
    public SysConfigDictionary getDic(String typeCode, String value) {
        SysConfigDictionary dictionary = new SysConfigDictionary();
        dictionary.setDictValue(typeCode);
        dictionary.setCurrType(0);
        dictionary = sysConfigDictionaryMapper.selectSingle(dictionary);
        if (dictionary != null) {
            return getDic(dictionary.getId(), value);
        } else {
            return null;
        }
    }
    /*
     * (非 Javadoc) <p>Title: getDicList</p> <p>Description: </p>
     * 
     * @param type
     * 
     * @return
     * 
     * @see cn.iyizhan.teamwork.sysconfig.service.SysConfigDictionaryService#getDicList(int)
     */
    public List<SysConfigDictionary> getDicList(int type) {
        SysConfigDictionary dictionary = new SysConfigDictionary();
        if (type != -1) {
            dictionary.setDictPvalue(type);
        }
        dictionary.setCurrType(0);
        return sysConfigDictionaryMapper.selectByObject(dictionary);
    }

    /*
     * (非 Javadoc) <p>Title: getDicList</p> <p>Description: </p>
     * 
     * @param typeCode
     * 
     * @return
     * 
     * @see cn.iyizhan.teamwork.sysconfig.service.SysConfigDictionaryService#getDicList(java.lang.String)
     */
    public List<SysConfigDictionary> getDicList(String typeCode) {
        SysConfigDictionary dictionary = new SysConfigDictionary();
        dictionary.setDictValue(typeCode);
        dictionary.setCurrType(0);
        dictionary = sysConfigDictionaryMapper.selectSingle(dictionary);
        SysConfigDictionary configDictionary = new SysConfigDictionary();
        configDictionary.setCurrType(0);
        configDictionary.setDictPvalue(dictionary.getId());
        return sysConfigDictionaryMapper.selectByObject(configDictionary);
    }

    /*
     * (非 Javadoc) <p>Title: findIsExits</p> <p>Description: </p>
     * 
     * @param dicName
     * 
     * @param dicPvalue
     * 
     * @return
     * 
     * @see cn.iyizhan.teamwork.sysconfig.service.SysConfigDictionaryService#findIsExits(java.lang.String, java.lang.Integer)
     */
    public boolean findIsExits(String dicName, Integer dicPvalue) {
        SysConfigDictionary dictionary = new SysConfigDictionary();
        dictionary.setDictPvalue(dicPvalue);
        dictionary.setDictName(dicName);
        dictionary = sysConfigDictionaryMapper.selectSingle(dictionary);
        return dictionary != null ? true : false;
    }

    /*
     * (非 Javadoc) <p>Title: findById</p> <p>Description: </p>
     * 
     * @param id
     * 
     * @return
     * 
     * @see cn.iyizhan.teamwork.sysconfig.service.SysConfigDictionaryService#findById(java.lang.Integer)
     */
    public SysConfigDictionary findById(Integer id) {
        SysConfigDictionary dictionary = new SysConfigDictionary();
        dictionary.setId(id);
        dictionary.setCurrType(0);
        return sysConfigDictionaryMapper.selectSingle(dictionary);
    }


	public PageObject<SysConfigDictionary> Pagequery(Map<String, Object> map) {
		if(!map.containsKey("curPage")&&!map.containsKey("pageData")){
    		map.put("curPage", 1);
    		map.put("pageData", 10);
    	}
    	int totalData=	sysConfigDictionaryMapper.getCount(map);
    	PageHelper pageHelper = new PageHelper(totalData, map);
		List<SysConfigDictionary> list = sysConfigDictionaryMapper.pageQueryByObject(pageHelper.getMap());
		PageObject<SysConfigDictionary> pageObject = pageHelper.getPageObject();
		pageObject.setDataList(list);
		return pageObject;
	}
	public void add(SysConfigDictionary t) {
		sysConfigDictionaryMapper.insertByEntity(t);
	}
	public void update(Map<String, Object> map) {
		sysConfigDictionaryMapper.updateByEntity(map);
		
	}
	public void update(SysConfigDictionary t) {
		sysConfigDictionaryMapper.updateByEntity(t);
		
	}
	public void delete(SysConfigDictionary t) {
		sysConfigDictionaryMapper.deleteByEntity(t);
	}

}
