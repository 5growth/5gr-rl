/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.DbConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author efabuba
 */
public class DbAppDDatasource {
    private static DbAppDDatasource instance;
    private Connection connection;

    private DbAppDDatasource() throws SQLException {

        DbProperties dbPropertires = DbProperties.getInstance();

        String url = dbPropertires.getProperty("DbAppDUrl");
        String username = dbPropertires.getProperty("DbAppDUsername");
        String password = dbPropertires.getProperty("DbAppDPassword");


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("DbAppDDatasource Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DbAppDDatasource getInstance() throws SQLException {
        if (instance == null) {
            instance = new DbAppDDatasource();
        } else if (instance.getConnection().isClosed()) {
            instance = new DbAppDDatasource();
        }

        return instance;
    }

}
