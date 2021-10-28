package com.peceinfotech.shoppre.AccountResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferralHistoryResponse {
    @SerializedName("user")
    @Expose
    private UserReferral user;
    @SerializedName("referralHistory")
    @Expose
    private List<ReferralHistory> referralHistory = null;

    public UserReferral getUser() {
        return user;
    }

    public void setUser(UserReferral user) {
        this.user = user;
    }

    public List<ReferralHistory> getReferralHistory() {
        return referralHistory;
    }

    public void setReferralHistory(List<ReferralHistory> referralHistory) {
        this.referralHistory = referralHistory;
    }
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_description")
    @Expose
    private String errorDescription;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
