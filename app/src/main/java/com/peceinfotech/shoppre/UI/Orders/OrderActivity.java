package com.peceinfotech.shoppre.UI.Orders;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.card.MaterialCardView;
import com.peceinfotech.shoppre.AccountResponse.MeResponse;
import com.peceinfotech.shoppre.AuthenticationModel.SignInGoogleResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.ViewProfile;
import com.peceinfotech.shoppre.UI.Locker.LockerReadyToShip;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.OrderFragment;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentList;
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
        lockerImage = findViewById(R.id.lockerImage);
        orderImage = findViewById(R.id.ordersImage);
        accountImage = findViewById(R.id.accountImage);
        shipmentImage = findViewById(R.id.shipmentImage);


        if (savedInstanceState != null) return;
        fragmentTransaction = fragmentManager.beginTransaction();
        OrderFragment orderFragment = new OrderFragment();
        fragmentTransaction.add(R.id.orderFrameLayout, orderFragment, null);
        fragmentTransaction.commit();

        //Call me api
        LoadingDialog.showLoadingDialog(OrderActivity.this , "Loading...");
        callApi();

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                OrderFragment orderFragment = new OrderFragment();
                fragmentTransaction.add(R.id.orderFrameLayout, orderFragment, null);
                fragmentTransaction.commit();


                orderImage.setImageResource(R.drawable.ic_orders___selected);
                lockerImage.setImageResource(R.drawable.ic_locker);
                shipmentImage.setImageResource(R.drawable.ic_shipments);
                accountImage.setImageResource(R.drawable.ic_account);

            }
        });

        locker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                LockerReadyToShip lockerReadyToShip = new LockerReadyToShip();
                fragmentTransaction.replace(R.id.orderFrameLayout, lockerReadyToShip, null);
                fragmentTransaction.addToBackStack(null).commit();


                orderImage.setImageResource(R.drawable.ic_orders);
                lockerImage.setImageResource(R.drawable.ic_locker___selected);
                shipmentImage.setImageResource(R.drawable.ic_shipments);
                accountImage.setImageResource(R.drawable.ic_account);


            }
        });

        shipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedInstanceState != null) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                ShipmentList shipmentList = new ShipmentList();
                fragmentTransaction.replace(R.id.orderFrameLayout, shipmentList, null);
                fragmentTransaction.addToBackStack(null).commit();

                orderImage.setImageResource(R.drawable.ic_orders);
                lockerImage.setImageResource(R.drawable.ic_locker);
                shipmentImage.setImageResource(R.drawable.ic_shipments___selected);
                accountImage.setImageResource(R.drawable.ic_account);

            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedInstanceState != null) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                ViewProfile viewProfile = new ViewProfile();
                fragmentTransaction.replace(R.id.orderFrameLayout, viewProfile, null);
                fragmentTransaction.addToBackStack(null).commit();

                orderImage.setImageResource(R.drawable.ic_orders);
                lockerImage.setImageResource(R.drawable.ic_locker);
                shipmentImage.setImageResource(R.drawable.ic_shipments);
                accountImage.setImageResource(R.drawable.ic_account___selected);

            }
        });

//        order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//
//            }
//        });
//
//        locker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//            }
//        });
//
//        shipment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                orderImage.setImageResource(R.drawable.ic_orders);
//                lockerImage.setImageResource(R.drawable.ic_locker);
//                shipmentImage.setImageResource(R.drawable.ic_locker___selected);
//                accountImage.setImageResource(R.drawable.ic_locker);
//
//
//            }
//        });
//
//        account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                ;
//
//            }
//        });

    }

    private void callApi() {

        String email = sharedPrefManager.getEmail();
        String grant_type = sharedPrefManager.getGrantType();

        if (grant_type.equals("facebook")) {
            callFacebookApi(email);
        } else {
            callGoogleApi(email);
        }
    }

    private void callFacebookApi(String email) {
        Call<SignInGoogleResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .signInFacebook(email, "facebook");
        call.enqueue(new Callback<SignInGoogleResponse>() {
            @Override
            public void onResponse(Call<SignInGoogleResponse> call, Response<SignInGoogleResponse> response) {
                if (response.code() == 200) {
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    String bearerToken = response.body().getAccessToken();
                    callMeApi(bearerToken);
                } else if (response.code() == 400) {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignInGoogleResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMeApi(String bearerToken) {

        Call<MeResponse> call = RetrofitClient3
               .getInstance3()
                .getAppApi()
                .getUser("Bearer "+bearerToken);
        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {

                MeResponse meResponse = response.body();
                if (response.code() == 200){
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeFirstName(response.body().getFirstName());
                    sharedPrefManager.storeLastName(response.body().getLastName());
                    sharedPrefManager.storeFullName(response.body().getName());
                    sharedPrefManager.storeEmail(response.body().getEmail());
                    sharedPrefManager.storeId(response.body().getId());
                    sharedPrefManager.storeSalutation(response.body().getSalutation());
                    sharedPrefManager.storeVirtualAddressCode(response.body().getVirtualAddressCode());

                    Toast.makeText(getApplicationContext(), response.body().getSalutation()
                            +"\n"+response.body().getFirstName()+"\n" +
                            response.body().getLastName()+"\n"+
                            response.body().getName()+"\n"+
                            response.body().getEmail()+"\n"+
                            response.body().getVirtualAddressCode(), Toast.LENGTH_LONG).show();
                }
                else  if(response.code() == 401){
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getApplicationContext(), "not registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callGoogleApi(String email) {

        Call<SignInGoogleResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .signInGoogle(email, "google");
        call.enqueue(new Callback<SignInGoogleResponse>() {
            @Override
            public void onResponse(Call<SignInGoogleResponse> call, Response<SignInGoogleResponse> response) {
                if (response.code() == 200) {
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    String bearerToken = response.body().getAccessToken();
                    callMeApi(bearerToken);
                } else if (response.code() == 400) {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignInGoogleResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


//        order.setOnClickListener(this);
//        locker.setOnClickListener(this);
//        shipment.setOnClickListener(this);
//        account.setOnClickListener(this);
//
//
//
//
//
//    }
//    public void onClick(View view){
//
//        int id = view.getId();
//        switch (id){
//
//            case R.id.orderCard:
//                pressedCardOrder();
//                break;
//
//            case R.id.lockerCard:
//                pressedLockerCard();
//                break;
//
//            case R.id.shipmentCard:
//                pressedCardShipment();
//                break;
//
//            case R.id.accountCard:
//                pressedAcountCard();
//                break;
//        }
//
//    }

//    private void pressedAcountCard() {
//
//        order.
//        locker.setCheckedIconResource(R.drawable.ic_locker);
//        shipment.setCheckedIconResource(R.drawable.ic_shipments);
//        account.setCheckedIconResource(R.drawable.ic_account___selected);
//
//    }
//
//    private void pressedCardShipment() {
//
//        order.setCheckedIconResource(R.drawable.ic_orders);
//        locker.setCheckedIconResource(R.drawable.ic_locker);
//        shipment.setCheckedIconResource(R.drawable.ic_shipments___selected);
//        account.setCheckedIconResource(R.drawable.ic_account);
//
//    }
//
//    private void pressedLockerCard() {
//
//        order.setCheckedIconResource(R.drawable.ic_orders);
//        locker.setCheckedIconResource(R.drawable.ic_locker___selected);
//        shipment.setCheckedIconResource(R.drawable.ic_shipments);
//        account.setCheckedIconResource(R.drawable.ic_account);
//
//    }
//
//    private void pressedCardOrder() {
//
//        order.setCheckedIconResource(R.drawable.ic_orders___selected);
//        locker.setCheckedIconResource(R.drawable.ic_locker);
//        shipment.setCheckedIconResource(R.drawable.ic_shipments);
//        account.setCheckedIconResource(R.drawable.ic_account);
//    }
}