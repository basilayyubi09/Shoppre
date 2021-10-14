package com.peceinfotech.shoppre.Models;

public class WalletTransactionDummyModel {
    int image ;
    String date , mainText , messageText , price;

    public WalletTransactionDummyModel(int image, String date, String mainText, String messageText, String price) {
        this.image = image;
        this.date = date;
        this.mainText = mainText;
        this.messageText = messageText;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
