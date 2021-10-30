package com.peceinfotech.shoppre.OrderModuleResponses;

public class OrderResponse {

    String webSiteName;
    String orderNo;
    String orderDate;
    int orderImage;

    public OrderResponse() {
    }

    public OrderResponse(String webSiteName, String orderNo, String orderDate, int orderImage) {
        this.webSiteName = webSiteName;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.orderImage = orderImage;
    }

    public String getWebSiteName() {
        return webSiteName;
    }

    public void setWebSiteName(String webSiteName) {
        this.webSiteName = webSiteName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderImage() {
        return orderImage;
    }

    public void setOrderImage(int orderImage) {
        this.orderImage = orderImage;
    }
}
