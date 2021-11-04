package com.peceinfotech.shoppre.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    SharedPreferences userSharedPref;
    SharedPreferences.Editor editor;
    Context context;


    private static final String IS_LOGIN = "isLoggedIn";


    public SharedPrefManager(Context context) {
        this.context = context;

        userSharedPref = context.getSharedPreferences("userSharedPref", Context.MODE_PRIVATE);
        editor = userSharedPref.edit();
    }

    public void storeFullName(String fullName) {
        editor.putString("fullName", fullName);
        editor.apply();
    }

    public String getFullName() {
        return userSharedPref.getString("fullName", "");
    }

    public void setLogin() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public void storeId(int id) {
        editor.putInt("id", id);
        editor.apply();
    }

    public int getId() {
        return userSharedPref.getInt("id", 0);
    }

    public void storeFirstName(String firstname) {
        editor.putString("firstName", firstname);
        editor.apply();
    }


    public void storeBearerToken(String bearer) {
        editor.putString("bearer", bearer);
        editor.apply();
    }

    public String getBearerToken() {
        return userSharedPref.getString("bearer", " ");
    }


    public String getFirstName() {
        return userSharedPref.getString("firstName", " ");
    }

    public void storeLastName(String lastname) {
        editor.putString("lastName", lastname);
        editor.apply();
    }



    public String getLastName() {
        return userSharedPref.getString("lastName", " ");
    }

    public void storeSalutation(String salutation) {
        editor.putString("salutation", salutation);
        editor.apply();
    }

    public String getSalutation() {
        return userSharedPref.getString("salutation", "");
    }

    public void storeEmail(String email) {
        editor.putString("email", email);
        editor.apply();
    }

    public String getEmail() {
        return userSharedPref.getString("email", "");
    }

    public void storeCreateDate(String date) {
        editor.putString("date", date);
        editor.apply();
    }

    public String getCreateDate() {
        return userSharedPref.getString("date", "");
    }

    public void storeVirtualAddressCode(String virtual_address_code) {
        editor.putString("virtual_address_code", virtual_address_code);
        editor.apply();
    }

    public String getVirtualAddressCode() {
        return userSharedPref.getString("virtual_address_code", "");
    }

    public void storeGrantType(String grantType) {
        editor.putString("grantType", grantType);
        editor.apply();
    }

    public String getGrantType() {
        return userSharedPref.getString("grantType", "");
    }

    public void storePhone(String phone) {
        editor.putString("phone", phone);
        editor.apply();
    }

    public String getPhone() {
        return userSharedPref.getString("phone", "");
    }

    public void storeRefreshToken(String token) {
        editor.putString("token", token);
        editor.apply();
    }

    public String getRefreshToken() {
        return userSharedPref.getString("token", "");
    }

    public void storeIsMigrated(String is) {
        editor.putString("isMigrated", is);
    }

    public String getIsMigrated() {
        return userSharedPref.getString("isMigrated", "");
    }

    public void storeIsMember(int is) {
        editor.putInt("isMember", is);
    }

    public int getIsMember() {
        return userSharedPref.getInt("isMember", 0);
    }

    public void storeGroupId(int id) {
        editor.putInt("groupId", id);
    }

    public int getGroupId() {
        return userSharedPref.getInt("groupId", 0);
    }

    public boolean checkLogin() {
        if (userSharedPref.getBoolean(IS_LOGIN, false)) {
            return true;
        } else return false;
    }

    public void logOut() {
        editor.clear();
        editor.commit();
    }
}
