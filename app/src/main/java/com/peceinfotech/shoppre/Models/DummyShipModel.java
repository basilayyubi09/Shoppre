package com.peceinfotech.shoppre.Models;

public class DummyShipModel {
    String webSiteName , quantity , packageId ;

    public DummyShipModel(String webSiteName, String quantity, String packageId) {
        this.webSiteName = webSiteName;
        this.quantity = quantity;
        this.packageId = packageId;
    }

    public String getWebSiteName() {
        return webSiteName;
    }

    public void setWebSiteName(String webSiteName) {
        this.webSiteName = webSiteName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
}
