package com.shoppreglobal.shoppre.UI.OnBoarding;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

public class OnBoardingActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;
    LinearLayout on;
    SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        sharedPrefManager = new SharedPrefManager(OnBoardingActivity.this);
        fragmentManager = getSupportFragmentManager();
        on = findViewById(R.id.onboarding_container);
        sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);


        if (!isFirstTime) {
            startActivity(new Intent(OnBoardingActivity.this, OrderActivity.class));
        } else {

            if (findViewById(R.id.onboarding_container) != null) {
                if (savedInstanceState != null) {
                    return;
                }
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FirstOnBoarding firstOnBoarding = new FirstOnBoarding();
                fragmentTransaction.add(R.id.onboarding_container, firstOnBoarding, null);
                fragmentTransaction.commit();
            }
        }

    }

    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure want to exit?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

    }


}