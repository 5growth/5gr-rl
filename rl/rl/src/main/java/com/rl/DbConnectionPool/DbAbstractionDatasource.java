/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.DbConnectionPool;

///**
// *
// * @author ezimbgi
// */
//import java.sql.Connection;
//import org.apache.tomcat.jdbc.pool.DataSource;
//import org.apache.tomcat.jdbc.pool.PoolProperties;
//
//
//
//public class DbAbstractionDatasource {
//     private static DataSource datasource = null;
// 
//    DbAbstractionDatasource() {
//    }
// 
//    public static Connection getConnection() {
//        try {
//            if (datasource == null) {
//                DbAbstractionDatasource.initDatasource();
//            }
//            return datasource.getConnection();
//        } catch (Exception e) {
//            System.out.println("Exception in DbAbstractionDatasource" + e.getMessage() + "");
//            return null;
//        }
//    }
// 
//    public static void initDatasource() {
// 
//        PoolProperties p = new PoolProperties();
// 
//        p.setUrl("jdbc:mysql://localhost:3306/mtpabstrdb?useSSL=false");
//        p.setDriverClassName("com.mysql.jdbc.Driver");
//        p.setUsername("root");
//        p.setPassword("pisalab90");
// 
//        p.setJmxEnabled(true);
//        p.setTestWhileIdle(false);
//        p.setTestOnBorrow(true);
//        p.setValidationQuery("SELECT 1");
//        p.setTestOnReturn(false);
//        p.setValidationInterval(30000);
//        p.setTimeBetweenEvictionRunsMillis(30000);
// 
//        p.setMaxActive(200); //75
//        p.setMaxIdle(200);//75
//        p.setInitialSize(100);//10
//        p.setMaxWait(30000);
//        p.setRemoveAbandonedTimeout(60);//60
//        p.setMinEvictableIdleTimeMillis(1000); //30000
//        p.setTimeBetweenEvictionRunsMillis(500);
//        p.setMinIdle(10);
// 
//        p.setLogAbandoned(false);        
//        p.setRemoveAbandoned(true);
// 
//        p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
//                + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
// 
//        datasource = new DataSource();
//        datasource.setPoolProperties(p);
// 
//    }
// 
//    public static void closeDatasource() {
//        datasource.close();
//    } 
//}


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DbAbstractionDatasource {

    private static DbAbstractionDatasource instance;
    private Connection connection;

    private DbAbstractionDatasource() throws SQLException {

        DbProperties dbPropertires = DbProperties.getInstance();

        String url = dbPropertires.getProperty("DbAbstractionUrl");
        String username = dbPropertires.getProperty("DbAbstractionUsername");
        String password = dbPropertires.getProperty("DbAbstractionPassword");


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("DbAbstractionDatasource Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DbAbstractionDatasource getInstance() throws SQLException {
        if (instance == null) {
            instance = new DbAbstractionDatasource();
        } else if (instance.getConnection().isClosed()) {
            instance = new DbAbstractionDatasource();
        }

        return instance;
    }
}