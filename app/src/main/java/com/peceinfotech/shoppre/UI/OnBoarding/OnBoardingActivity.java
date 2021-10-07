package com.peceinfotech.shoppre.UI.OnBoarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.peceinfotech.shoppre.R;

public class OnBoardingActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.onboarding_container)!=null){
            if (savedInstanceState!=null){
                if (savedInstanceState!=null){
                    return;
                }
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FirstOnBoarding firstOnBoarding = new FirstOnBoarding();
            fragmentTransaction.add(R.id.onboarding_container,firstOnBoarding,null);
            fragmentTransaction.commit();
        }
    }
}