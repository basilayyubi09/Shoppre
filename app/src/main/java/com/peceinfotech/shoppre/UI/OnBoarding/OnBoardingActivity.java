package com.peceinfotech.shoppre.UI.OnBoarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

public class OnBoardingActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LinearLayout on;
    SharedPrefManager sharedPrefManager ;
    String token;


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

//    private void callMeApi() {
//        String bearer = sharedPrefManager.getBearerToken();
//        Toast.makeText(getApplicationContext(), "Bearer " + bearer, Toast.LENGTH_SHORT).show();
//        Call<MeResponse> call =
//                RetrofitClient3
//                        .getInstance3()
//                        .getAppApi()
//                        .getUser("Bearer " + bearer);
//        call.enqueue(new Callback<MeResponse>() {
//            @Override
//            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {
//                LoadingDialog.cancelLoading();
//                if (response.code() == 200) {
//                    Toast.makeText(getApplicationContext(), response.body().getFirstName(), Toast.LENGTH_SHORT).show();
////                    sharedPrefManager.storeFullName(response.body().getFirstName()+" "+response.body().getLastName());
////                    sharedPrefManager.storeId(response.body().getId());
////                    sharedPrefManager.storeSalutation(response.body().getSalutation());
////                    sharedPrefManager.storeFirstName(response.body().getFirstName());
////                    sharedPrefManager.storeLastName(response.body().getLastName());
////                    sharedPrefManager.storeEmail(response.body().getEmail());
////                    sharedPrefManager.setLogin();
////                    sharedPrefManager.storeVirtualAddressCode(response.body().getVirtualAddressCode());
////                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.onboarding_container), "Code has expired",Snackbar.LENGTH_LONG);
////                    snackbar.show();
//                } else if (response.code() == 400) {
//
//                    Toast.makeText(OnBoardingActivity.this, response.body().getErrorDescription(), Toast.LENGTH_SHORT).show();
////                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.onboarding_container), response.body().getErrorDescription(),Snackbar.LENGTH_LONG);
////                    snackbar.show();
//                } else {
//
//                    Toast.makeText(OnBoardingActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
////                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.onboarding_container), "Something Went Wrong",Snackbar.LENGTH_LONG);
////                    snackbar.show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MeResponse> call, Throwable t) {
//                LoadingDialog.cancelLoading();
//                Snackbar snackbar = Snackbar.make(OnBoardingActivity.this.findViewById(R.id.onboarding_container), t.toString(), Snackbar.LENGTH_LONG);
//                snackbar.show();
//            }
//        });
//    }

}