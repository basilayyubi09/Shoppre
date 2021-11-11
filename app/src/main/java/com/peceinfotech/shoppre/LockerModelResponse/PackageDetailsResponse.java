package com.peceinfotech.shoppre.LockerModelResponse;

public class PackageDetailsResponse {

    int packageItemImage;
    String packageItemName;
    String packageItemId;
    String packageItemQuantity;
    String packageItemPrice;

    public PackageDetailsResponse(int packageItemImage, String packageItemName, String packageItemId, String packageItemQuantity, String packageItemPrice) {
        this.packageItemImage = packageItemImage;
        this.packageItemName = packageItemName;
        this.packageItemId = packageItemId;
        this.packageItemQuantity = packageItemQuantity;
        this.packageItemPrice = packageItemPrice;
    }

    public int getPackageItemImage() {
        return packageItemImage;
    }

    public void setPackageItemImage(int packageItemImage) {
        this.packageItemImage = packageItemImage;
    }

    public String getPackageItemName() {
        return packageItemName;
    }

    public void setPackageItemName(String packageItemName) {
        this.packageItemName = packageItemName;
    }

    public String getPackageItemId() {
        return packageItemId;
    }

    public void setPackageItemId(String packageItemId) {
        this.packageItemId = packageItemId;
    }

    public String getPackageItemQuantity() {
        return packageItemQuantity;
    }

    public void setPackageItemQuantity(String packageItemQuantity) {
        this.packageItemQuantity = packageItemQuantity;
    }

    public String getPackageItemPrice() {
        return packageItemPrice;
    }

    public void setPackageItemPrice(String packageItemPrice) {
        this.packageItemPrice = packageItemPrice;
    }
}
