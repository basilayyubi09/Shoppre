package com.shoppreglobal.shoppre.LockerModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shoppreglobal.shoppre.OrderModuleResponses.Store;

public class VerifyLinkResponse {
    @SerializedName("store")
    @Expose
    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
