package sine.framework.generator.newGen;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import sine.framework.generator.typemapping.DatabaseTypeUtils;
import sine.framework.generator.util.GLogger;
import sine.framework.generator.util.PropertiesHelper;
import sine.framework.generator.util.PropertyPlaceholderHelper;
import sine.framework.generator.util.PropertyPlaceholderHelper.PropertyPlaceholderConfigurerResolver;



/**
 * 生成器配置类
 * 用于装载generator.properties,generator.xml文件
 * @author buyuer
 * @email ayyocean@163.com
 */
public class GeneratorProperties {
	static PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}", ":", false);
	//配置文件
	static final String PROPERTIES_FILE_NAMES[] = new String[]{"generator.properties","generator.xml"};
	static PropertiesHelper props = new PropertiesHelper(new Properties(),false);
	private GeneratorProperties(){}
	static {
		reload();
	}
	
	public static void reload() {
		try {
			GLogger.println("Start Load GeneratorPropeties from classpath:"+Arrays.toString(PROPERTIES_FILE_NAMES));
			Properties p = new Properties();
			String[] loadedFiles = loadAllPropertiesFromClassLoader(p,PROPERTIES_FILE_NAMES);
			GLogger.println("GeneratorPropeties Load Success,files: "+Arrays.toString(loadedFiles));
			
			//添加databaseType属性 获取数据库类型
			setSepicalProperties(p, loadedFiles);
			
			setProperties(p);
		}catch(IOException e) {
			throw new RuntimeException("Load "+PROPERTIES_FILE_NAMES+" error",e);
		}
	}
	
	/**
	 * 正对多个配置文件  进行加载信息
	 * @param properties
	 * @param resourceNames
	 * @return
	 * @throws IOException
	 */
	public static String[] loadAllPropertiesFromClassLoader(Properties properties, String... resourceNames) throws IOException {
		List<Object> successLoadProperties = new ArrayList<Object>();
		for (String resourceName : resourceNames) {
			Enumeration<URL> urls = GeneratorProperties.class.getClassLoader().getResources(resourceName);
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				System.err.println("URL " + url);
				successLoadProperties.add(url.getFile());
				InputStream input = null;
				try {
					URLConnection con = url.openConnection();
					con.setUseCaches(false);
					input = con.getInputStream();
					if (resourceName.endsWith(".xml")) {
						properties.loadFromXML(input);
					} else {
						properties.load(input);
					}
				} finally {
					if (input != null) {
						input.close();
					}
				}
			}
		}
		return (String[]) successLoadProperties.toArray(new String[0]);
	}

	/**
	 * 获取数据库类型 并且对于 basedir 进行 补充
	 * @param p
	 * @param loadedFiles
	 */
	private static void setSepicalProperties(Properties p, String[] loadedFiles) {
		p.put("databaseType", getDatabaseType(p,"databaseType"));
		if(loadedFiles != null && loadedFiles.length > 0) {
			String basedir = p.getProperty("basedir");
			if(basedir != null && basedir.startsWith(".")) {
				p.setProperty("basedir", new File(new File(loadedFiles[0]).getParent(),basedir).getAbsolutePath());
			}
		}
	}
	
	private static String getDatabaseType(Properties p,String key) {
		String databaseType = DatabaseTypeUtils.getDatabaseTypeByJdbcDriver(p.getProperty("jdbc.driver"));
		return p.getProperty(key,databaseType == null ? "" : databaseType);
	}
	
	public static void setProperties(Properties inputProps) {
		props = new PropertiesHelper(resolveProperties(inputProps),false);
        for(Iterator it = props.entrySet().iterator();it.hasNext();) {
            Map.Entry entry = (Map.Entry)it.next();
            GLogger.println("[Property] "+entry.getKey()+"="+entry.getValue());
        }
        GLogger.println("");
        
        GLogger.println("[Auto Replace] [.] => [/] on generator.properties, key=source_key+'_dir', For example: pkg=com.company ==> pkg_dir=com/company  \n");
        Properties dirProperties = autoReplacePropertiesValue2DirValue(props.getProperties());
        props.getProperties().putAll(dirProperties);
	}
	
	private static Properties resolveProperties(Properties props) {
		Properties result = new Properties();
		for(Object s : props.keySet()) {
			String sourceKey = s.toString();
			String key  = resolveProperty(sourceKey,props);
			String value = resolveProperty(props.getProperty(sourceKey),props);
			result.setProperty(key, value);
		}
		return result;
	}
	
	private static String resolveProperty(String v,Properties props) {
		PropertyPlaceholderConfigurerResolver propertyPlaceholderConfigurerResolver = new PropertyPlaceholderConfigurerResolver(props);
		return helper.replacePlaceholders(v, propertyPlaceholderConfigurerResolver);
	}
	
	// 自动替换所有value从 com.company 替换为 com/company,并设置key = key+"_dir"后缀
	private static Properties autoReplacePropertiesValue2DirValue(Properties props) {
        Properties autoReplaceProperties = new Properties();
        for(Object key : getProperties().keySet()) {
            String dir_key = key.toString()+"_dir";
            String value = props.getProperty(key.toString());
            String dir_value = value.toString().replace('.', '/');
            autoReplaceProperties.put(dir_key, dir_value);           
        }
        return autoReplaceProperties;
    }
	
	public static Properties getProperties() {
		return getHelper().getProperties();
	}
	
	private static PropertiesHelper getHelper() {
		return props;
	}
	
	public static String getProperty(String key, String defaultValue) {
		return getHelper().getProperty(key, defaultValue);
	}
	
	public static String getProperty(String key) {
		return getHelper().getProperty(key);
	}
	
	public static String getRequiredProperty(String key) {
		return getHelper().getRequiredProperty(key);
	}
	
	public static int getRequiredInt(String key) {
		return getHelper().getRequiredInt(key);
	}
	
	public static boolean getRequiredBoolean(String key) {
		return getHelper().getRequiredBoolean(key);
	}
	
	public static String getNullIfBlank(String key) {
		return getHelper().getNullIfBlank(key);
	}
	
	public static void setProperty(String key,String value) {
		value = resolveProperty(value,getProperties());
		key = resolveProperty(key,getProperties());
	    GLogger.println("[setProperty()] "+key+"="+value);
		getHelper().setProperty(key, value);
		String dir_value = value.toString().replace('.', '/');
		getHelper().getProperties().put(key+"_dir", dir_value);
	}
}
