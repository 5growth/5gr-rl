package com.rl.DbConnectionPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbProperties {
    private static volatile DbProperties _instance = null;
    private Properties prop = new Properties();


    private DbProperties() {
        InputStream inputStream = null;
        try {
            String absolutePathJar = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            File jarFile = new File(absolutePathJar);
            String path = jarFile.getParent();
            File propFile = new File(path + "/application.properties");
            if (propFile.exists() == true) {
                inputStream = new FileInputStream(propFile);
            } else {
                inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            }
            prop.load(inputStream);
        } catch (IOException ex) {
            System.out.println("DbServiceDatasource properties file reading Failed : " + ex.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("DbServiceDatasource properties file closing Failed : " + e.getMessage());
                }
            }
        }


    }

    public static synchronized DbProperties getInstance() {
        if (_instance == null)
            synchronized (DbProperties.class) {
                if (_instance == null)
                    _instance = new DbProperties();
            }
        return _instance;
    }

    public String getProperty(String PropertyName){
        return prop.getProperty(PropertyName);
    }
}