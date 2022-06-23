package com.caoliang;

import com.caoliang.entity.OrderInfo;
import com.caoliang.service.OrderInfoService;
import com.caoliang.service.impl.OrderInfoServiceImpl;

import java.util.List;

public class ServiceTest {
    public static void main(String[] args) throws Exception {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId("10001");
        orderInfo.setClientName("客户3");

        OrderInfoService orderInfoService = new OrderInfoServiceImpl();
        //int i = orderInfoService.updateData(orderInfo);
        //System.out.println("result: " + i);

        List<OrderInfo> list = orderInfoService.queryData(orderInfo);

        System.out.println(list);

    }
}
