package com.shoppreglobal.shoppre.AccountResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalletTransactionResponse {
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("walletTransactions")
    @Expose
    private List<WalletTransaction> walletTransactions = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<WalletTransaction> getWalletTransactions() {
        return walletTransactions;
    }

    public void setWalletTransactions(List<WalletTransaction> walletTransactions) {
        this.walletTransactions = walletTransactions;
    }
}
