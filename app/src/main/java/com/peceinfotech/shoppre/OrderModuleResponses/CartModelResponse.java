package com.peceinfotech.shoppre.OrderModuleResponses;

import java.util.List;

public class CartModelResponse {

    String webSiteName;
    List<ProductItem> productItem;

    public CartModelResponse(String webSiteName, List<ProductItem> productItem) {
        this.webSiteName = webSiteName;
        this.productItem = productItem;
    }

    public String getWebSiteName() {
        return webSiteName;
    }

    public void setWebSiteName(String webSiteName) {
        this.webSiteName = webSiteName;
    }

    public List<ProductItem> getProductItem() {
        return productItem;
    }

    public void setProductItem(List<ProductItem> productItem) {
        this.productItem = productItem;
    }


}

