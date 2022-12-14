package com.shoppreglobal.shoppre.UI.Orders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.shoppreglobal.shoppre.AccountResponse.MeResponse;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments.ViewProfile;
import com.shoppreglobal.shoppre.UI.Locker.LockerReadyToShip;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.OrderFragment;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.ThankYouFragment;
import com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment.ShipmentLanding;
import com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment.ShipmentListingFragment;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    boolean isFirstTime = false;
    FrameLayout frameLayout;
    public static FragmentManager fragmentManager;
    SharedPrefManager sharedPrefManager;
    public static BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);

        sharedPrefManager = new SharedPrefManager(OrderActivity.this);
        fragmentManager = getSupportFragmentManager();


        frameLayout = findViewById(R.id.orderFrameLayout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);


        Intent intent = getIntent();
        String action = intent.getAction();
        if (action != null && action.equals(Intent.ACTION_VIEW)) {
            Uri uri = intent.getData();
            String scheme = uri.getScheme();
            if (scheme.equals("paymentorders")) {
                String status = uri.getQueryParameter("status");
                String orderId = uri.getQueryParameter("orderId");
                if (status.equals("success")) {
                    Log.d("kaha ruka", orderId);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("id", orderId);
                    bundle1.putString("type", "order");
                    ThankYouFragment thankYouFragment = new ThankYouFragment();
                    thankYouFragment.setArguments(bundle1);
                    OrderActivity.fragmentManager.beginTransaction()
                            .replace(R.id.orderFrameLayout, thankYouFragment)
                            .commit();
                } else {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("type", "order");
                    OrderFragment orderFragment = new OrderFragment();
                    orderFragment.setArguments(bundle1);
                    OrderActivity.fragmentManager.beginTransaction()
                            .replace(R.id.orderFrameLayout, orderFragment)
                            .commit();
                }
            } else if (scheme.equals("paymentshipments")) {
                String status = uri.getQueryParameter("status");
                Integer id = Integer.valueOf(uri.getQueryParameter("shipmentId"));
                Log.d("kaha ruka", String.valueOf(id));
                if (status.equals("success")) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("type", "shipment");
                    ThankYouFragment thankYouFragment = new ThankYouFragment();
                    thankYouFragment.setArguments(bundle1);
                    OrderActivity.fragmentManager.beginTransaction()
                            .replace(R.id.orderFrameLayout, thankYouFragment)
                            .commit();
                } else {
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("id", id);
                    bundle1.putBoolean("toast", true);
//                    bundle1.putInt("size", 0);

                    ShipmentLanding shipmentLanding = new ShipmentLanding();
                    shipmentLanding.setArguments(bundle1);
                    OrderActivity.fragmentManager.beginTransaction()
                            .replace(R.id.orderFrameLayout, shipmentLanding)
                            .commit();
                }
            }
        } else {
            internetCheck();
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.orderMenu:
                        fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new OrderFragment(), null)
                                .addToBackStack(null).commit();
                        break;

                    case R.id.lockerMenu:
                        fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new LockerReadyToShip(), null)
                                .addToBackStack(null).commit();
                        break;

                    case R.id.shipmentMenu:
                        fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShipmentListingFragment(), null)
                                .addToBackStack(null).commit();
                        break;

                    case R.id.accountMenu:
                        fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ViewProfile(), null)
                                .addToBackStack(null).commit();
                        break;


                }

                return true;
            }
        });

    }


    private void callMeApi() {

        Call<MeResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi()
                .getUser("Bearer " + sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {

                if (response.code() == 200) {
                    sharedPrefManager.setLogin();
                    sharedPrefManager.storeFirstName(response.body().getFirstName());
                    sharedPrefManager.storeLastName(response.body().getLastName());
                    sharedPrefManager.storeFullName(response.body().getName());
                    sharedPrefManager.storeEmail(response.body().getEmail());
                    sharedPrefManager.storeId(response.body().getId());
                    sharedPrefManager.storeSalutation(response.body().getSalutation());
                    sharedPrefManager.storeVirtualAddressCode(response.body().getVirtualAddressCode());
                    sharedPrefManager.storeIsMember(response.body().getIsMember());
                    sharedPrefManager.storeIsMigrated(response.body().getIsCourierMigrated());
                    sharedPrefManager.storeGroupId(response.body().getGroupId());
                    sharedPrefManager.storePhone(response.body().getPhone());
                    sharedPrefManager.storeCreateDate(response.body().getCreatedAt());
                    isFirstTime = true;
                    fragmentManager.beginTransaction().add(R.id.orderFrameLayout, new OrderFragment(), null)
                            .commit();


                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callRefreshTokenApi() {
        Call<RefreshTokenResponse> call = RetrofitClient
                .getInstance().getApi()
                .getRefreshToken(sharedPrefManager.getRefreshToken());
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.code() == 200) {
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    callMeApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
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

    private void internetCheck() {

        if (!CheckNetwork.isInternetAvailable(getApplicationContext())) //if connection not available
        {

            Snackbar snackbar = Snackbar.make(findViewById(R.id.orderFrameLayout), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            if (isFirstTime || getIntent() == null) {
                fragmentManager.beginTransaction().add(R.id.orderFrameLayout, new OrderFragment(), null)
                        .commit();
            } else {
                LoadingDialog.showLoadingDialog(OrderActivity.this, "");
                callMeApi();
            }

        }

    }


}