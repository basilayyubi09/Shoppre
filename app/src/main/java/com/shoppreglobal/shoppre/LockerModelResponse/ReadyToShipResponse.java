package com.shoppreglobal.shoppre.LockerModelResponse;

public class ReadyToShipResponse {

    int readyToShipImg;

    String readyToShipWebSiteName;
    String packageId;

    public ReadyToShipResponse(int readyToShipImg, String readyToShipWebSiteName, String packageId) {
        this.readyToShipImg = readyToShipImg;
        this.readyToShipWebSiteName = readyToShipWebSiteName;
        this.packageId = packageId;
    }

    public int getReadyToShipImg() {
        return readyToShipImg;
    }

    public void setReadyToShipImg(int readyToShipImg) {
        this.readyToShipImg = readyToShipImg;
    }

    public String getReadyToShipWebSiteName() {
        return readyToShipWebSiteName;
    }

    public void setReadyToShipWebSiteName(String readyToShipWebSiteName) {
        this.readyToShipWebSiteName = readyToShipWebSiteName;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
}
