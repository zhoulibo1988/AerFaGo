package org.ivan.api.system;

import java.util.List;
import java.util.Map;

import org.ivan.entity.admin.SysConfigDictionary;
import org.ivan.entity.utils.PageObject;

/**
 * @author sine
 * @version
 */
public interface SysConfigDictionaryService{

    /**
     * 
     * @author buyuer
     * @Title: getDic
     * @Description: 获取字典表数据
     * @param type
     * @param value
     * @return
     */
    public SysConfigDictionary getDic(int type, String value);

    /**
     * 
     * @author buyuer
     * @Title: getDic
     * @Description: 根据类型code 和  对应数据查询字典是否存在
     * @param typeCode 类型code
     * @param value 值
     */
    public SysConfigDictionary getDic(String typeCode, String value);
    /**
     * 
     * @author buyuer
     * @Title: getDicList
     * @Description:根据数据类型查询所有字典数据
     * @param type
     */
    public List<SysConfigDictionary> getDicList(int type);
    
    /**
     * 
     * @author buyuer
     * @Title: getDicList
     * @Description: 根据类型编码查询字典数据
     * @param typeCode 类型编码
     */
    public List<SysConfigDictionary> getDicList(String typeCode);
    /**
     * 
     * @author buyuer
     * @Title: findIsExits
     * @Description: 查询当前的信息是否存在
     */
    public boolean findIsExits(String dicName, Integer dicPvalue);
    
    /**
     * 
     * @author buyuer
     * @Title: findById
     * @Description: 根据id查询
     * @param id 字典表id
     */
    public SysConfigDictionary findById(Integer id);
    
    PageObject<SysConfigDictionary> Pagequery(Map<String, Object> map);
    
    void add(SysConfigDictionary t);
    
    void update(Map<String, Object> map);
    
    void update(SysConfigDictionary t);
    
    void delete(SysConfigDictionary t);
}
