package org.ivan.entity.utils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;
public class MapObjectUtil {

    /**
     * 根据属性名获取属性值
     * */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取属性名数组
     * */
    private static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     * */
    @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
    private static List getFiledsInfo(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        List list = new ArrayList();
        Map infoMap = null;
        for (int i = 0; i < fields.length; i++) {
            infoMap = new HashMap();
            infoMap.put("type", fields[i].getType().toString());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }

    /**
     * 获取对象的所有属性值，返回一个对象数组
     * */
    @SuppressWarnings("static-access")
    public Object[] getFiledValues(Object o) {
        String[] fieldNames = this.getFiledName(o);
        Object[] value = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            value[i] = this.getFieldValueByName(fieldNames[i], o);
        }
        return value;
    }

    /**
     * 判断是否为空
     * 
     * @param files
     * @param o
     * @return
     */
    public static boolean checkObjectFile(String[] files, Object o) {
        if (o==null) {
            return false;
        }
        List<Map<String, Object>> list = getFiledsInfo(o);
        for (Map<String, Object> map : list) {
            String fileName = map.get("name").toString();
            if (Arrays.asList(files).contains(fileName)) {
                Object value = map.get("value");
                if (value == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否为空
     * 
     * @param files
     * @param o
     * @return
     */
    @Deprecated
    public static boolean checkObjectFile(String[] files, Map<String, Object> map) {
        if (map==null || map.isEmpty()) {
            return false;
        }
        for (String key : files) {
            if (StringUtils.isNoneBlank(key) && map.keySet().contains(key)) {
                Object value = map.get(key);
                if (value == null) {
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }
    
    /**
     * @Description: 检测对象是否为空
     * @param @param files
     * @param @param map
     * @param @return   
     * @return boolean  
     * @throws
     * @author pengzhihao
     * @date 2016-10-24下午8:11:57
     */
    public static boolean checkObjectFileIsEmpty(String[] files, Map<String, Object> map) {
        if (map==null || map.isEmpty()) {
            return false;
        }
        for (String key : files) {
            if (StringUtils.isNoneBlank(key) && map.keySet().contains(key)) {
                Object value = map.get(key);
                if (value == null || "".equals(value.toString().trim()) || "null".equals(value.toString().trim())) {
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }
	
		/**
     * 判断是否为空
     * 
     * @param files
     * @param o
     * @return
     */
    public static boolean checkObject(String[] files, Map<String, Object> map) {
        if (map==null || map.isEmpty()) {
            return false;
        }
        for (String key : files) {
            if (StringUtils.isNoneBlank(key) && map.keySet().contains(key)) {
                Object value = map.get(key);
                if (value == null || "".equals(value)) {
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }
    
    /**
     * 判断参数是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj){
    	if(obj==null||"".equals(obj.toString().trim())){
    		return true;
    	}else{
    		return false;
    	}
    }
	
	/**
     * 判断是否为空
     * 
     * @param files
     * @param o
     * @return
     */
    public static boolean checkObject(String[] files, Object o) {
        if (o==null) {
            return false;
        }
        List<Map<String, Object>> list = getFiledsInfo(o);
        for (Map<String, Object> map : list) {
            String fileName = map.get("name").toString();
            if (Arrays.asList(files).contains(fileName)) {
                Object value = map.get("value");
                if (value == null || "".equals(value)) {
                    return false;
                }
            }
        }
        return true;
    }
	


    /**
     * 
     * @author buyuer
     * @Title: getObjectByFileNames
     * @Description: 获取一个object对象中的字段
     * @param files
     *            字段数组
     * @param o
     *            对象
     * @return
     */
    public static Map<String, Object> getObjectByFileNames(String[] files, Object o) {
        if (o == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = getFiledsInfo(o);
        for (Map<String, Object> filemap : list) {
            String fileName = filemap.get("name").toString();
            if (Arrays.asList(files).contains(fileName)) {
                Object value = filemap.get("value");
                map.put(fileName, value);
            }
        }
        return map;
    }

    /**
     * 
     * @author buyuer
     * @Title: getObjectByFileNames
     * @Description: 获取一个object对象中的字段
     * @param files
     *            字段数组
     * @param o
     *            对象
     * @return
     */
    public static List<Map<String, Object>> getListByFileNames(String[] files, List o) {
        if (o == null&&o.isEmpty()) {
            return null;
        }
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (Object obj : o) {
            Map<String, Object> map = new HashMap<String, Object>();
            List<Map<String, Object>> list = getFiledsInfo(obj);
            for (Map<String, Object> filemap : list) {
                String fileName = filemap.get("name").toString();
                if (Arrays.asList(files).contains(fileName)) {
                    Object value = filemap.get("value");
                    map.put(fileName, value);
                }
            }
            listMap.add(map);
        }
        return listMap;
    }
    
    /**
     * @Description: Bean To Map 过滤掉为空属性
     * @param @param obj
     * @param @return   
     * @return Map<String,Object>  
     * @throws
     * @author pengzhihao
     * @date 2016-9-2下午2:37:13
     */
    public static Map<String, Object> beanToMap(Object obj) { 
        Map<String, Object> params = new HashMap<String, Object>(0); 
        try { 
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
            for (int i = 0; i < descriptors.length; i++) { 
                String name = descriptors[i].getName(); 
                if (!"class".equals(name)) { 
                	System.out.println();
                	if(propertyUtilsBean.getNestedProperty(obj, name) == null){
                		continue;
                	}
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                } 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return params; 
    }
}
