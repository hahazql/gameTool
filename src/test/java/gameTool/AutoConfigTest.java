package gameTool;

import com.hahazql.util.auto.config.*;
import com.hahazql.util.exception.BaseException;
import com.hahazql.util.test.TestConfig;
import com.hahazql.util.test.TestConfig_2;
import org.dom4j.DocumentException;
import org.junit.Test;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zql on 2015/9/29.
 */
public class AutoConfigTest
{
    @Test
    public void testReadConfigDataXML()
    {
        try {
            List<AutoConfig> list = AutoConfigUtil.loaderFromExternJar("C:/JAR/TestLoader.xml", ClassLoader.getSystemClassLoader());
            System.out.println(list);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (BaseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveConfigData()
    {
        try {
            List<AutoConfig> list = AutoConfigUtil.loadConfigFromPackage("com.hahazql.util");
            AutoConfigUtil.createConfigDataXML(list,"TestLoader","C:/JAR");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadClass()
    {
        try {
            List<AutoConfig> list = AutoConfigUtil.loaderFromExternJar("C:\\JAR\\LoaderJarTest.xml","C:\\JAR\\LoaderJarTest.jar");
            for(AutoConfig autoConfig : list)
            {
                String print = String.format("ClassName: %s, ConfigName: %s,ConfigDirName: %s",autoConfig.getClazz().getName(),autoConfig.getConfigName(),autoConfig.getConfigDirName());
                System.out.println(print);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeExcel()
    {
        TestConfig config = new TestConfig();
        TestConfig_2 config_2 = new TestConfig_2();

        TestConfig_2[] list = new TestConfig_2[]{config_2,config_2};

        config.setList(list);

        List<TestConfig> testList = new ArrayList<TestConfig>();
        testList.add(config);
        testList.add(config);

        try {
            AutoConfigUtil.writeExcel(testList,TestConfig.class,"C:/JAR/test.xls");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
