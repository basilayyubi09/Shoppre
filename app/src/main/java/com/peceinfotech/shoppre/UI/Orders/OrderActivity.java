package com.peceinfotech.shoppre.UI.Orders;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.peceinfotech.shoppre.AccountResponse.MeResponse;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.ViewProfile;
import com.peceinfotech.shoppre.UI.Locker.LockerReadyToShip;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.OrderFragment;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentList;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

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
//        lockerImage = findViewById(R.id.lockerImage);
//        orderImage = findViewById(R.id.ordersImage);
//        accountImage = findViewById(R.id.accountImage);
//        shipmentImage = findViewById(R.id.shipmentImage);


        if (savedInstanceState != null) return;
        fragmentTransaction = fragmentManager.beginTransaction();
        OrderFragment orderFragment = new OrderFragment();
        fragmentTransaction.add(R.id.orderFrameLayout, orderFragment, null);
        fragmentTransaction.commit();

        if (!CheckNetwork.isInternetAvailable(getApplicationContext())) //if connection not available
        {

            Snackbar snackbar = Snackbar.make(findViewById(R.id.orderFrameLayout), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            String token = sharedPrefManager.getBearerToken();
            LoadingDialog.showLoadingDialog(OrderActivity.this, "");
            callMeApi(token);
        }


//        order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (savedInstanceState != null) return;
//
//                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new OrderFragment(), null)
//                        .addToBackStack(null).commit();
//
//                    orderImage.setImageResource(R.drawable.ic_orders___selected);
//                    lockerImage.setImageResource(R.drawable.ic_locker);
//                    shipmentImage.setImageResource(R.drawable.ic_shipments);
//                    accountImage.setImageResource(R.drawable.ic_account);
//
//            }
//        });
//
//        locker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (savedInstanceState != null) return;
//                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new LockerReadyToShip(), null)
//                        .addToBackStack(null).commit();
//
//
//                orderImage.setImageResource(R.drawable.ic_orders);
//                lockerImage.setImageResource(R.drawable.ic_locker___selected);
//                shipmentImage.setImageResource(R.drawable.ic_shipments);
//                accountImage.setImageResource(R.drawable.ic_account);
//
//
//            }
//        });
//
//        shipment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (savedInstanceState != null) return;
//                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShipmentList(), null)
//                        .addToBackStack(null).commit();
//
//                orderImage.setImageResource(R.drawable.ic_orders);
//                lockerImage.setImageResource(R.drawable.ic_locker);
//                shipmentImage.setImageResource(R.drawable.ic_shipments___selected);
//                accountImage.setImageResource(R.drawable.ic_account);
//
//            }
//        });
//
//        account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (savedInstanceState != null) return;
//
//                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ViewProfile(), null)
//                        .addToBackStack(null).commit();
//
//                    orderImage.setImageResource(R.drawable.ic_orders);
//                    lockerImage.setImageResource(R.drawable.ic_locker);
//                    shipmentImage.setImageResource(R.drawable.ic_shipments);
//                    accountImage.setImageResource(R.drawable.ic_account___selected);
//
//            }
//        });

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.orderMenu:
                        fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new OrderFragment())
                                .addToBackStack(null).commit();
                        break;

                    case R.id.lockerMenu:
                        fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new LockerReadyToShip())
                                .addToBackStack(null).commit();
                        break;

                    case R.id.shipmentMenu:
                        fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShipmentList())
                                .addToBackStack(null).commit();
                        break;

                    case R.id.accountMenu:
                        fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ViewProfile())
                                .addToBackStack(null).commit();
                        break;

                }
            }
        });




    }




    private void callMeApi(String bearerToken) {

        Call<MeResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi()
                .getUser("Bearer " + bearerToken);
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

                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                }
                else {
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
                if (response.code()==200){
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                }
                else {
                    callMeApi(sharedPrefManager.getBearerToken());
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }



}