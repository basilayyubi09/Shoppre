package com.shoppreglobal.shoppre.UI.Locker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.LockerAdapters.ReadyToShipAdapter;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageListingResponse;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageModel;
import com.shoppreglobal.shoppre.LockerModelResponse.ReadyToSendResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.CreateShipRequest.CreateShipRequestFragment;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.OrderFragment;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LockerReadyToShip extends Fragment {

    SharedPrefManager sharedPrefManager;
    List<PackageModel> list = new ArrayList<>();
    ReadyToShipAdapter readyToShipAdapter;
    RecyclerView lockerReadyToShipRecycler;
    LinearLayout returnAndDiscardText;
    MaterialButton createShipRequestBtn, placeAnOrderBtn;
    LinearLayout emptyLockerDiscardText;
    CardView emptyLockerCard, lockerReadyToShipCard;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_locker_ready_to_ship, container, false);

        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.lockerMenu).setChecked(true);
        OrderActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        lockerReadyToShipRecycler = view.findViewById(R.id.lockerReadyToShipRecycler);
        returnAndDiscardText = view.findViewById(R.id.returnAndDiscardText);
        createShipRequestBtn = view.findViewById(R.id.createShipRequestBtn);
        placeAnOrderBtn = view.findViewById(R.id.placeAnOrderBtn);
        emptyLockerDiscardText = view.findViewById(R.id.emptyLockerDiscardText);
        emptyLockerCard = view.findViewById(R.id.emptyLockerCard);
        lockerReadyToShipCard = view.findViewById(R.id.lockerReadyToShipCard);

        sharedPrefManager = new SharedPrefManager(getActivity());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.getBoolean("showToast")) {
//                Toast.makeText(getActivity(), String.valueOf(bundle.getBoolean("showToast")), Toast.LENGTH_SHORT).show();
                String type = bundle.getString("type");

                LayoutInflater inflater1 = getLayoutInflater();
                View layout = inflater1.inflate(R.layout.yellow_toast,
                        (ViewGroup) view.findViewById(R.id.toast_layout_root));
                TextView toastText = (TextView) layout.findViewById(R.id.toastText);
                toastText.setText("We’re reviewing your " + type + " Request");
                Toast toast = new Toast(getContext());
                toast.setGravity(Gravity.TOP, 0, 200);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();

                bundle.clear();

            }
        }


        emptyLockerDiscardText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ReadyToShipReturnedAndDiscard(), null)
                        .addToBackStack(null).commit();
            }
        });

        createShipRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callReadyToSendApi();
            }
        });

        if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
        {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.main), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            LoadingDialog.showLoadingDialog(getActivity(), getString(R.string.Loading));
            callListingApi();
        }


        returnAndDiscardText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ReadyToShipReturnedAndDiscard(), null)
                        .addToBackStack(null).commit();

            }
        });

        placeAnOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new OrderFragment(), null)
                        .addToBackStack(null).commit();
            }
        });


        return view;
    }

    private void callReadyToSendApi() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<ReadyToSendResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().readyToSend("Bearer " + sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<ReadyToSendResponse>() {
            @Override
            public void onResponse(Call<ReadyToSendResponse> call, Response<ReadyToSendResponse> response) {
                if (response.code() == 201) {
                    LoadingDialog.cancelLoading();
                    Bundle bundle = new Bundle();
                    CreateShipRequestFragment createShipRequestFragment = new CreateShipRequestFragment();
                    bundle.putSerializable("list", (Serializable) response.body().getPackages());
                    createShipRequestFragment.setArguments(bundle);
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, createShipRequestFragment, null)
                            .addToBackStack(null).commit();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReadyToSendResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callListingApi() {
        Call<PackageListingResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().lockerListing("Bearer " + sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<PackageListingResponse>() {
            @Override
            public void onResponse(Call<PackageListingResponse> call, Response<PackageListingResponse> response) {
                if (response.code() == 201) {
                    list = response.body().getPackages();
                    readyToShipAdapter = new ReadyToShipAdapter(list, getContext());
                    lockerReadyToShipRecycler.setAdapter(readyToShipAdapter);

                    int count = readyToShipAdapter.getItemCount();
                    if (count == 0) {
                        emptyLockerCard.setVisibility(View.VISIBLE);
                        lockerReadyToShipCard.setVisibility(View.GONE);
                    } else {
                        emptyLockerCard.setVisibility(View.GONE);
                        lockerReadyToShipCard.setVisibility(View.VISIBLE);
                    }

                    LoadingDialog.cancelLoading();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PackageListingResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
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
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    callListingApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}