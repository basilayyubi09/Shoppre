package com.peceinfotech.shoppre.UI.OnBoarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;

public class OnBoardingActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        fragmentManager = getSupportFragmentManager();

        sharedPreferences = getSharedPreferences("SharedPref" , MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("firstTime" , true);

        if (!isFirstTime){
            startActivity(new Intent(OnBoardingActivity.this , OrderActivity.class));
        }

        if(findViewById(R.id.onboarding_container)!=null){
            if (savedInstanceState!=null){
               return;
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FirstOnBoarding firstOnBoarding = new FirstOnBoarding();
            fragmentTransaction.add(R.id.onboarding_container,firstOnBoarding,null);
            fragmentTransaction.commit();
        }
    }
}