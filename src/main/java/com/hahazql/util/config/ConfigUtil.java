package com.hahazql.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.hahazql.util.constants.CommonErrorLogInfo;
import com.hahazql.util.helper.ErrorsUtil;
import com.hahazql.util.script.IScriptEngine;
import com.hahazql.util.script.impl.JSScriptManagerImpl;


public class ConfigUtil 
{
	
	
   public static <T>void createConfig(Collection<T> configs,String filePath) throws IOException {
        createConfig(configs,filePath,true);
    }

	/**
	 * 创建配置文件
	 * @param configs    配置集合
	 * @param filePath   文件路径
	 * @param isCreate   若文件不存在是否创建
	 * @param <T>
	 * @throws IOException
	 */
	public static <T>void createConfig(Collection<T> configs,String filePath,boolean isCreate) throws IOException {
	    File file = new File(filePath);
	    if(!file.exists()&&isCreate)
	        file.createNewFile();
	    FileWriter writer = new FileWriter(file);
	    List<T> list = new ArrayList<T>();
	    list.addAll(configs);
	    Yaml yaml = new Yaml();
	    yaml.dump(list,writer);
	    writer.flush();
	    writer.close();
	}
	
	
	/**
	 * 读入配置文件
	 * @param filePath  文件路径
	 * @param <T>  配置的类型
	 * @return
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> loadConfig(String filePath) throws FileNotFoundException {
	
	    List<T> list = new ArrayList<T>();
	    File file = new File(filePath);
	    if(!file.exists())
	        return list;
	    InputStream input = new FileInputStream(file);
	    Yaml yaml = new Yaml();
	    for (Object data : yaml.loadAll(input)) {
	        List<T> t = (List<T>) data;
	        list.addAll(t);
	    }
	    return list;
	}
	
	/**
	 * 根据指定的配置类型<tt>configClass</tt>从<tt>configURL</tt>中加载配置
	 * 
	 * @param <T>
	 * @param configClass
	 *            配置的类型
	 * @param configStream
	 *            配置文件的URL,文件内容是一个以JavaScript编写的配置脚本
	 * @return 从configURL加载的配置对象
	 * @exception RuntimeException
	 *                从configClass构造对象失败时抛出此异常
	 * @exception IllegalArgumentException
	 *                配置验证失败时抛出此异常
	 * @exception IllegalStateException
	 *                从configUrl中加载内容失败时抛出此异常
	 */
	public static <T extends Config> T buildConfig(Class<T> configClass,
			InputStream configStream) {
		if (configClass == null) {
			throw new IllegalArgumentException(ErrorsUtil.error(
					CommonErrorLogInfo.ARG_NOT_NULL_EXCEPT, "", "configClass"));
		}
		if (configStream == null) {
			throw new IllegalArgumentException(ErrorsUtil.error(
					CommonErrorLogInfo.ARG_NOT_NULL_EXCEPT, "", "configURL"));
		}

		T _config = null;
		try {
			_config = configClass.newInstance();
		} catch (InstantiationException e1) {
			throw new RuntimeException(e1);
		} catch (IllegalAccessException e1) {
			throw new RuntimeException(e1);
		}
		IScriptEngine _jsEngine = new JSScriptManagerImpl("UTF-8");
		Map<String, Object> _bindings = new HashMap<String, Object>();
		_bindings.put("com/hahazql/util/config", _config);
		Reader _r = null;
		String _scriptContent = null;
		try {
			_r = new InputStreamReader(configStream, "UTF-8");
			_scriptContent = IOUtils.toString(_r);
		} catch (IOException e) {
			throw new IllegalStateException("Can't load com.hahazql.util.config from url ["
					+ configStream + "]");
		} finally {
			IOUtils.closeQuietly(_r);
		}
		_jsEngine.runScript(_bindings, _scriptContent);
		_config.validate();
		return _config;
	}

	/**
	 * 获得配置文件的真实路径
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getConfigPath(String fileName) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		return classLoader.getResource(fileName).getPath();
	}

	public static URL getConfigURL(String fileName) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		return classLoader.getResource(fileName);
	}
	    
	    
}
