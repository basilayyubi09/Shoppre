package com.peceinfotech.shoppre.UI.AccountAndWallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.ViewProfile;
import com.peceinfotech.shoppre.UI.OnBoarding.FirstOnBoarding;

public class AccountWalletActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_wallet);
        fragmentManager =  getSupportFragmentManager();

        if (savedInstanceState!=null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ViewProfile viewProfileFragment = new ViewProfile();
        fragmentTransaction.add(R.id.frameLayout , viewProfileFragment , null);
        fragmentTransaction.commit();


    }
}