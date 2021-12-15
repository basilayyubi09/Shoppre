package com.shoppreglobal.shoppre.AccountResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferralHistoryResponse {
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("referralHistory")
    @Expose
    private List<ReferralHistory> referralHistory = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ReferralHistory> getReferralHistory() {
        return referralHistory;
    }

    public void setReferralHistory(List<ReferralHistory> referralHistory) {
        this.referralHistory = referralHistory;
    }
}
