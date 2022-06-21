package com.caoliang.entity;


import com.caoliang.anno.ColumnAnno;
import com.caoliang.anno.Table;

import java.util.Date;

@Table(tableName = "order_info",orderBy = "order_date", order = "desc")
public class OrderInfo {

    @ColumnAnno(columnName = "order_id",isPK = "true")
    private String orderId;

    @ColumnAnno(columnName = "client_name")
    private String clientName;

    @ColumnAnno(columnName = "product_name")
    private String productName;

    @ColumnAnno(columnName = "order_date")
    private Date orderDate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
