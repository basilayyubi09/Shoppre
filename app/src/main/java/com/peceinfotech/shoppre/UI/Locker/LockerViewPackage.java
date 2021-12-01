package com.peceinfotech.shoppre.UI.Locker;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.LockerAdapters.ViewPackageViewPager;
import com.peceinfotech.shoppre.LockerModelResponse.PackageItem;
import com.peceinfotech.shoppre.LockerModelResponse.ViewPackageResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.Locker.ViewPackageTabLayout.PackageDetails;
import com.peceinfotech.shoppre.UI.Locker.ViewPackageTabLayout.PackageUpdates;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LockerViewPackage extends Fragment {

    TabLayout viewPackageTablayout;
    ViewPager viewPackageViewPager;
    SharedPrefManager sharedPrefManager;
    TextView comment, websiteName, packageId, weight, date, amount, trackingNumber;
    int id;
    Bundle bundle1 = new Bundle();
    PackageDetails packageDetails = new PackageDetails();
    List<PackageItem> list;
    PackageUpdates packageUpdates = new PackageUpdates();
    ViewPackageViewPager viewPackageViewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_locker_view_package, container, false);
        viewPackageTablayout = view.findViewById(R.id.viewPackageTablayout);
        viewPackageViewPager = view.findViewById(R.id.viewPackageViewPager);
        comment = view.findViewById(R.id.comment);
        packageId = view.findViewById(R.id.packageId);
        weight = view.findViewById(R.id.weight);
        date = view.findViewById(R.id.date);
        trackingNumber = view.findViewById(R.id.trackingNumber);
        amount = view.findViewById(R.id.amount);
        websiteName = view.findViewById(R.id.websiteName);
        sharedPrefManager = new SharedPrefManager(getActivity());


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");

        }

        if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
        {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.main), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            LoadingDialog.showLoadingDialog(getActivity(), getString(R.string.Loading));
            callViewPackage();

        }


        return view;
    }

    private void callViewPackage() {
        Call<ViewPackageResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().viewPackage("Bearer " + sharedPrefManager.getBearerToken()
                        , id);
        call.enqueue(new Callback<ViewPackageResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ViewPackageResponse> call, Response<ViewPackageResponse> response) {
                if (response.code() == 200) {

                    list = response.body().getPackageItems();
                    bundle1.putInt("id" , response.body().getId());
                    bundle1.putSerializable("list", (Serializable) list);
                    packageDetails.setArguments(bundle1);
                    Bundle bundle = new Bundle();

                    bundle.putInt("id", response.body().getId());

                    packageUpdates.setArguments(bundle);
                    viewPackageViewPagerAdapter = new ViewPackageViewPager(getChildFragmentManager());
                    viewPackageViewPagerAdapter.addFragment(packageDetails, "Package Details " + "(" + String.valueOf(list.size()) + ")");
                    viewPackageViewPagerAdapter.addFragment(packageUpdates, "Locker Updates");

                    viewPackageViewPager.setAdapter(viewPackageViewPagerAdapter);
                    viewPackageTablayout.setupWithViewPager(viewPackageViewPager);

                    setTextToFields(response.body());
                    LoadingDialog.cancelLoading();

                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViewPackageResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTextToFields(ViewPackageResponse list) {

        String s = list.getCreatedAt();
        String[] split = s.split("T");
        String date1 = split[0];

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(date1, dtf);
        String month_name = dtf2.format(ld);

        date.setText(month_name);
        trackingNumber.setText(list.getInvoiceCode());
        websiteName.setText(list.getStore().getName());
        packageId.setText("#" + String.valueOf(list.getId()));
        weight.setText(String.valueOf(list.getWeight()));
        amount.setText("₹ "+String.valueOf(list.getPriceAmount()));

        if (list.getStateNameAndColor().getStateName().equals("In Review")) {

            comment.setText(list.getStateNameAndColor().getStateName());
            comment.setTextColor(getActivity().getColor(R.color.in_review_blue_color));
            comment.setBackground(getActivity().getDrawable(R.drawable.price_changed_background));
        } else if (list.getStateNameAndColor().getStateName().equals("Action Required")) {


            comment.setText(list.getStateNameAndColor().getStateName());
            comment.setTextColor(getActivity().getColor(R.color.action_required_yellow_color));
            comment.setBackground(getActivity().getDrawable(R.drawable.order_placed_background));
        } else if (list.getStateNameAndColor().getStateName().equals("Ready To Send")) {

            comment.setText(list.getStateNameAndColor().getStateName());
            comment.setTextColor(getActivity().getColor(R.color.ready_to_ship_green_color));
            comment.setBackground(getActivity().getDrawable(R.drawable.ready_to_ship_background));
        }
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
                } else {
                    callViewPackage();
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