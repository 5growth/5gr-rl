/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.DbConnectionPool;

/**
 * @author ezimbgi
 */
//import java.sql.Connection;
//import org.apache.tomcat.jdbc.pool.DataSource;
//import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbDomainDatasource {

    private static DbDomainDatasource instance;
    private Connection connection;

    private DbDomainDatasource() throws SQLException {

        DbProperties dbPropertires = DbProperties.getInstance();
        String url = dbPropertires.getProperty("DbDomainUrl");
        String username = dbPropertires.getProperty("DbDomainUsername");
        String password = dbPropertires.getProperty("DbDomainPassword");


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("DbADomainDatasource Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DbDomainDatasource getInstance() throws SQLException {
        if (instance == null) {
            instance = new DbDomainDatasource();
        } else if (instance.getConnection().isClosed()) {
            instance = new DbDomainDatasource();
        }

        return instance;
    }
}

//public class DbDomainDatasource {
//     private static DataSource datasource = null;
// 
//    DbDomainDatasource() {
//    }
// 
//    public static Connection getConnection() {
//        try {
//            if (datasource == null) {
//                DbDomainDatasource.initDatasource();
//            }
//            return datasource.getConnection();
//        } catch (Exception e) {
//            
//           System.out.println("Exception in DbDomainDatasource" + e.getMessage() + "");
//            return null;
//        }
//    }
// 
//    public static void initDatasource() {
// 
//        PoolProperties p = new PoolProperties();
// 
//        p.setUrl("jdbc:mysql://localhost:3306/mtpdomdb?useSSL=false");
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
//        p.setMinEvictableIdleTimeMillis(5000); //30000
//        p.setTimeBetweenEvictionRunsMillis(1000);
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
