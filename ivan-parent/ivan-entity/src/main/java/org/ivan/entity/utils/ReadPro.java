package org.ivan.entity.utils;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang3.StringUtils;

public class ReadPro {
//	static String path = ReadPro.class.getClassLoader()
//	.getResource("config.properties").getPath();
static InputStream is=ReadPro.class.getClassLoader().getResourceAsStream("systemConfig.properties");

static PropertiesConfiguration propConfig;

static Configuration multiConfig;

private ReadPro() {
}

private static void reloadPropFile() {
if (null == propConfig) {
	try {
		propConfig = new PropertiesConfiguration();
		propConfig.load(is);
		FileChangedReloadingStrategy change = new FileChangedReloadingStrategy();
		change.setRefreshDelay(60 * 1000);// 检测文件是否改变的时长
		propConfig.setReloadingStrategy(change);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}

@SuppressWarnings("unchecked")
public static <T> T getValue(String key, T defaultValue) {
reloadPropFile();
T value = null;
if(StringUtils.isNotBlank(key)){
	String keyValue = propConfig.getString(key);
	if(StringUtils.isNotBlank(keyValue)){
		if(null != defaultValue){
			if (defaultValue instanceof List) {
				value =  (T) propConfig.getList(key, (List<?>) defaultValue);
			} else if (defaultValue instanceof String[]) {
				value =  (T) propConfig.getStringArray(key);
			} else if(defaultValue instanceof Integer){
				value = (T) propConfig.getInteger(key, (Integer) defaultValue);
			}else  if(defaultValue instanceof String){
				value = (T) propConfig.getString(key, (String) defaultValue);
			}else  if(defaultValue instanceof Long){
				value = (T) propConfig.getLong(key, (Long) defaultValue);
			}else  if(defaultValue instanceof Float){
				value = (T) propConfig.getFloat(key, (Float) defaultValue);
			}else  if(defaultValue instanceof Boolean){
				value = (T) propConfig.getBoolean(key, (Boolean) defaultValue);
			}
		}else{
			value = (T)keyValue;
		}
			
	}else{
		value = defaultValue;
	}
}

return value;
}

public static String getValue(String key) {
reloadPropFile();
String s = propConfig.getString(key);
return StringUtils.isNotBlank(s)?s:null;
}

public static void main(String[] args) {
for (int i = 0; i < 100; i++) {
	if(ReadPro.getValue("connect_timeout") instanceof String){
		System.out.println("cacacaca");
	}else{
		System.out.println("aaaaaaaaaa");
	}
	try {
		Thread.sleep(1000);
	} catch (Exception e) {
		// TODO: handle exception
	}
}
}
}
