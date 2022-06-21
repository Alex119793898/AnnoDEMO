package com.caoliang;

import com.caoliang.entity.OrderInfo;
import com.caoliang.util.SQLProvider;

public class ReflectTest {
    public static void main(String[] args) throws Exception {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId("10001");
        orderInfo.setClientName("客户1");

        SQLProvider sqlProvider = new SQLProvider();

        //String sql = sqlProvider.genDeleteSql(orderInfo);
        //String sql = sqlProvider.genUpdateSql(orderInfo);
        String sql = sqlProvider.genQuerySql(orderInfo);


        System.out.println(sql);
    }
}
