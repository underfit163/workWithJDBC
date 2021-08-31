/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labweb;

import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConnectBD {

    private static Properties prop;

    public Connection getConnection() throws SQLException, FileNotFoundException, IOException {
        if (prop == null) {
            prop = new Properties();
            FileInputStream in = new FileInputStream("C:/Users/163ty/Desktop/LabWeb/src/labweb/database.properties");
            prop.load(in);
            in.close();
        }

        String drivers = prop.getProperty("jdbc.drivers");
        if (drivers != null) {
            System.setProperty("jdbc.drivers", drivers);
        }
        String url = prop.getProperty("jdbc.url");
        String username = prop.getProperty("jdbc.username");
        String password = prop.getProperty("jdbc.password");
        Connection conn = DriverManager.getConnection(url, username, password);
        System.out.println(" successfully connected");
        return conn;
    }
}
