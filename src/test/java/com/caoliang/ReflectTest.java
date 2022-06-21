package com.caoliang;

import com.caoliang.entity.OrderInfo;
import com.caoliang.util.DBUtil;
import com.caoliang.util.SQLProvider;

import java.sql.Connection;
import java.sql.Statement;

public class ReflectTest {
    public static void main(String[] args) throws Exception {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId("10001");
        orderInfo.setClientName("客户3");

        SQLProvider sqlProvider = new SQLProvider();

        //String sql = sqlProvider.genDeleteSql(orderInfo);
        String sql = sqlProvider.genUpdateSql(orderInfo);
        //String sql = sqlProvider.genQuerySql(orderInfo);

        Connection connection = DBUtil.getConnection();
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate(sql);

        System.out.println(sql);
        System.out.println(i);
    }
}
