package com.peceinfotech.shoppre.UI.AccountAndWallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.AddAddress;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.AddressBook;

public class AccountWalletActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_wallet);


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddressBook addressBook = new AddressBook();
        fragmentTransaction.add(R.id.accountWalletContainer, addressBook, null);
        fragmentTransaction.commit();


        }

    }
