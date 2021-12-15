package com.shoppreglobal.shoppre.ShipmentModelResponse;

public class ShipmentDetailsResponse {

    int image;
    String webSiteName;
    String packageId;
    String date;
    float weight;

    public ShipmentDetailsResponse(int image, String webSiteName, String packageId, String date, float weight) {
        this.image = image;
        this.webSiteName = webSiteName;
        this.packageId = packageId;
        this.date = date;
        this.weight = weight;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getWebSiteName() {
        return webSiteName;
    }

    public void setWebSiteName(String webSiteName) {
        this.webSiteName = webSiteName;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
