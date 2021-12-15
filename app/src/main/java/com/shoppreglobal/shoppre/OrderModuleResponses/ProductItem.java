package com.shoppreglobal.shoppre.OrderModuleResponses;

public class ProductItem {

    int productSerialNo, productCount, productPrice;
    String productName;

    public ProductItem(int productSerialNo, int productCount, int productPrice, String productName) {
        this.productSerialNo = productSerialNo;
        this.productCount = productCount;
        this.productPrice = productPrice;
        this.productName = productName;
    }

    public int getProductSerialNo() {
        return productSerialNo;
    }

    public void setProductSerialNo(int productSerialNo) {
        this.productSerialNo = productSerialNo;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
