package com.peceinfotech.shoppre.ShipmentModelResponse;

public class UploadInvoiceResponse {
    String websiteName;
    String packageId;

    public UploadInvoiceResponse(String websiteName, String packageId) {
        this.websiteName = websiteName;
        this.packageId = packageId;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
}
