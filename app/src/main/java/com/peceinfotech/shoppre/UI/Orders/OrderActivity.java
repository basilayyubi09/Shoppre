package com.peceinfotech.shoppre.UI.Orders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.peceinfotech.shoppre.AccountResponse.MeResponse;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.ViewProfile;
import com.peceinfotech.shoppre.UI.CreateShipRequest.CreateShipRequestFragment;
import com.peceinfotech.shoppre.UI.Locker.LockerReadyToShip;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.OrderFragment;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment.ShipmentLanding;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment.ShipmentListingFragment;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayDeque;
import java.util.Deque;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    MaterialCardView locker, shipment, account, order;
    ImageView lockerImage, orderImage, accountImage, shipmentImage;
    FrameLayout frameLayout;
    public static FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public static String SELECTED_TAB = "order";
    SharedPrefManager sharedPrefManager;

    BottomNavigationView bottomNavigationView;
    Deque<Integer> integerDeque = new ArrayDeque<>(4);


    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        sharedPrefManager = new SharedPrefManager(OrderActivity.this);
        fragmentManager = getSupportFragmentManager();


        frameLayout = findViewById(R.id.orderFrameLayout);
        locker = findViewById(R.id.lockerCard);
        shipment = findViewById(R.id.shipmentCard);
        account = findViewById(R.id.accountCard);
        order = findViewById(R.id.orderCard);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);


        internetCheck();


        fragmentManager.beginTransaction().add(R.id.orderFrameLayout, new OrderFragment(), null)
                .commit();

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
                        fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShipmentLanding(), null)
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


//        integerDeque.push(R.id.orderMenu);
//
//
//        loadFragment(new OrderFragment());

//        bottomNavigationView.setSelectedItemId(R.id.orderMenu);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                int id = item.getItemId();
//
//                if (integerDeque.contains(id)){
//                    if (id==R.id.orderMenu){
//
//                        if (integerDeque.size() != 1 ){
//
//                            if (flag){
//                                integerDeque.addFirst(R.id.orderMenu);
//
//                                flag = false;
//
//
//                            }
//                        }
//                    }
//
//                    integerDeque.remove(id);
//                }
//                integerDeque.push(id);
//
//
//                loadFragment(getFragment(item.getItemId()));
//
//                return true;
//            }
//        });


    }

    //    private Fragment getFragment(int itemId) {
//        switch (itemId){
//
//            case R.id.orderMenu:
//                bottomNavigationView.getMenu().getItem(0).setChecked(true);
//                return new OrderFragment();
//
//            case R.id.lockerMenu:
//                bottomNavigationView.getMenu().getItem(1).setChecked(true);
//                return new LockerReadyToShip();
//
//
//
//            case R.id.shipmentMenu:
//                bottomNavigationView.getMenu().getItem(2).setChecked(true);
//                return new ShipmentList();
//
//            case R.id.accountMenu:
//                bottomNavigationView.getMenu().getItem(3).setChecked(true);
//                return new ViewProfile();
//
//        }
//
//        bottomNavigationView.getMenu().getItem(1).setChecked(true);
//
//        return new OrderFragment();
//    }
//
//    private void loadFragment(Fragment fragment) {
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.orderFrameLayout, fragment, fragment.getClass().getSimpleName())
//                .commit();
//
//    }

//    @Override
//    public void onBackPressed() {
//
//        integerDeque.pop();
//
//        if (!integerDeque.isEmpty()){
//
//            loadFragment(getFragment(integerDeque.peek()));
//        }else{
//            finish();
//        }
//    }


    private void callMeApi() {

        Call<MeResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi()
                .getUser("Bearer " + sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {

                if (response.code() == 200) {

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
                    LoadingDialog.cancelLoading();

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

            LoadingDialog.showLoadingDialog(OrderActivity.this, "");
            callMeApi();
        }

    }


}