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

    public void storeFirstName(String firstname) {
        editor.putString("firstName", firstname);
        editor.apply();
    }

    public String getFirstName() {
        return userSharedPref.getString("firstName", "");
    }

    public void storeLastName(String lastname) {
        editor.putString("lastName", lastname);
        editor.apply();
    }

    public String getLastName() {
        return userSharedPref.getString("lastName", "");
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
