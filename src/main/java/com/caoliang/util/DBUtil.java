package com.caoliang.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static Connection connection;

    public static Connection getConnection(){

        if(null == connection){
            synchronized (DBUtil.class){
                if(null == connection){
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/rebuild_mybatis?useSSL=false&useUnicode=true&characterEncoding=UTF-8",
                                "root",
                                "12345678"
                        );
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return connection;
    }
}
