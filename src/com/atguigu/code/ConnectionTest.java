package com.atguigu.code;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {

    @Test
    public void m1() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hpcrm?useSSL=false", "root", "root");

        System.out.println("connection = " + connection);

    }


    @Test
    public void m2() throws IOException, ClassNotFoundException, SQLException {
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
        String driverClass = properties.getProperty("driverClass");
        String userName = properties.getProperty("userName");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, userName, password);
        System.out.println("connection = " + connection);


    }

}
