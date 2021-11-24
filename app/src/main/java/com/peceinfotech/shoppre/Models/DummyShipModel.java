package com.peceinfotech.shoppre.Models;

public class DummyShipModel {
    String id, webSiteName , quantity , packageId ;

    public DummyShipModel(String id , String webSiteName, String quantity, String packageId) {
        this.id = id;
        this.webSiteName = webSiteName;
        this.quantity = quantity;
        this.packageId = packageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
