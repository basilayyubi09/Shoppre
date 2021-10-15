package com.peceinfotech.shoppre.Models;

public class RefferalDummyModel {
    int image;
    String date , mainText;

    public RefferalDummyModel(int image, String date, String mainText) {
        this.image = image;
        this.date = date;
        this.mainText = mainText;
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
}
