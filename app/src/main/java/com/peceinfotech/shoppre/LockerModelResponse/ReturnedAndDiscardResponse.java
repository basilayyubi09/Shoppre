package com.peceinfotech.shoppre.LockerModelResponse;

public class ReturnedAndDiscardResponse {

    int returnAndDiscardImage;
    String returnAndDiscardWebName, returnAndDiscardPackageId;

    public ReturnedAndDiscardResponse(int returnAndDiscardImage, String returnAndDiscardWebName, String returnAndDiscardPackageId) {
        this.returnAndDiscardImage = returnAndDiscardImage;
        this.returnAndDiscardWebName = returnAndDiscardWebName;
        this.returnAndDiscardPackageId = returnAndDiscardPackageId;
    }

    public int getReturnAndDiscardImage() {
        return returnAndDiscardImage;
    }

    public void setReturnAndDiscardImage(int returnAndDiscardImage) {
        this.returnAndDiscardImage = returnAndDiscardImage;
    }

    public String getReturnAndDiscardWebName() {
        return returnAndDiscardWebName;
    }

    public void setReturnAndDiscardWebName(String returnAndDiscardWebName) {
        this.returnAndDiscardWebName = returnAndDiscardWebName;
    }

    public String getReturnAndDiscardPackageId() {
        return returnAndDiscardPackageId;
    }

    public void setReturnAndDiscardPackageId(String returnAndDiscardPackageId) {
        this.returnAndDiscardPackageId = returnAndDiscardPackageId;
    }
}
