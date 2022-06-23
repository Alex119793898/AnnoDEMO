package com.caoliang;

import com.caoliang.entity.OrderInfo;
import com.caoliang.service.OrderInfoService;
import com.caoliang.service.impl.OrderInfoServiceImpl;

import java.util.List;

public class ServiceTest {
    public static void main(String[] args) {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId("10001");
        orderInfo.setClientName("客户1");

        OrderInfoService orderInfoService = new OrderInfoServiceImpl();
        List<OrderInfo> list = orderInfoService.queryData(orderInfo);

    }
}
