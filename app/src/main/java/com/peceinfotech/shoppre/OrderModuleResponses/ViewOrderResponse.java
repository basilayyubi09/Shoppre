package com.peceinfotech.shoppre.OrderModuleResponses;

public class ViewOrderResponse {

    int productImage;
    String productName;
    String productColor;
    String productQuantity;
    String productRate;

    public ViewOrderResponse(int productImage, String productName, String productColor, String productQuantity, String productRate) {
        this.productImage = productImage;
        this.productName = productName;
        this.productColor = productColor;
        this.productQuantity = productQuantity;
        this.productRate = productRate;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductRate() {
        return productRate;
    }

    public void setProductRate(String productRate) {
        this.productRate = productRate;
    }
}
