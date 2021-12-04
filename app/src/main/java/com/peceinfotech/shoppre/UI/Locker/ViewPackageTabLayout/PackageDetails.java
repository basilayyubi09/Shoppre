package com.peceinfotech.shoppre.UI.Locker.ViewPackageTabLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.LockerAdapters.PackageDetailsAdapter;
import com.peceinfotech.shoppre.LockerModelResponse.PackageItem;
import com.peceinfotech.shoppre.LockerModelResponse.ReturnPackageResponse;
import com.peceinfotech.shoppre.LockerModelResponse.ViewPackageResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.Locker.LockerReadyToShip;
import com.peceinfotech.shoppre.UI.Locker.LockerViewPackage;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageDetails extends Fragment {

    RecyclerView packageDetailsRecycler;
    List<PackageItem> list;
    PackageDetailsAdapter packageDetailsAdapter;
    List<Integer> ids;
    MaterialButton floatingBtn;
    JsonArray jsonArray;

    String type;
    Integer packageId;
    LinearLayout emptyView;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_package_details, container, false);
        packageDetailsRecycler = view.findViewById(R.id.packageDetailsRecycler);
        floatingBtn = view.findViewById(R.id.floatingBtn);
        emptyView = view.findViewById(R.id.emptyView);
        sharedPrefManager = new SharedPrefManager(getActivity());

        ids = new ArrayList<>();

        jsonArray = new JsonArray();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
//            list = (List<PackageItem>) bundle.getSerializable("list");
            packageId = bundle.getInt("id");

        }
        LoadingDialog.showLoadingDialog(getActivity(), "");
        callViewPackage();


        return view;
    }

    private void callViewPackage() {
        Call<ViewPackageResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().viewPackage("Bearer " + sharedPrefManager.getBearerToken()
                        , packageId);
        call.enqueue(new Callback<ViewPackageResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ViewPackageResponse> call, Response<ViewPackageResponse> response) {
                if (response.code() == 200) {

                    list = response.body().getPackageItems();
                    packageDetailsAdapter = new PackageDetailsAdapter(list, getContext(), packageId, new PackageDetailsAdapter.GetData() {
                        @Override
                        public void dotsVisiblity(String type1, Integer id, int position) {

                            type = type1;
                            for (int i = 0; i < list.size(); i++) {
                                View view1 = packageDetailsRecycler.getChildAt(i);

                                CheckBox checkBox = view1.findViewById(R.id.packageDetailCheckbox);
                                ImageView dots = view1.findViewById(R.id.three_dots);


                                dots.setVisibility(View.GONE);
                                checkBox.setVisibility(View.VISIBLE);
                                if (position == i) {
                                    if (checkBox.isChecked()) {
                                        ids.add(id);
                                        jsonArray.add(id);
                                        showButton();
                                    } else {
                                        checkBox.setChecked(true);
                                        ids.add(id);
                                        jsonArray.add(id);
                                        showButton();
                                    }

                                }


                            }

                            packageDetailsAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void getId(Integer ida, CheckBox packageDetailCheckbox) {
                            for (int i = 0; i < list.size(); i++) {

                                if (packageDetailCheckbox.isChecked()) {
                                    if (!ids.contains(ida)) {

                                        ids.add(ida);
                                        jsonArray.add(ida);
                                        packageDetailCheckbox.setChecked(true);


                                    }
                                } else {

                                    if (jsonArray.toString().contains(String.valueOf(ida))) {
                                        ids.remove(ida);
//                            jsonArray.remove(ida);
                                        while (jsonArray.size() > 0) {
                                            jsonArray.remove(0);
                                        }
                                        for (int j = 0; j < ids.size(); j++) {
                                            jsonArray.add(ids.get(j));
                                        }

                                        packageDetailCheckbox.setChecked(false);
                                    }
                                }

                            }
                            if (ids.size() > 0) {
                                showButton();

                            } else {
                                floatingBtn.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void singleProceed(String type1, Integer id) {
                            type = type1;
                            if (type.equals("return")) {
                                jsonArray.add(id);
                                callReturnPackageApi();
                            } else if (type.equals("exchange")) {
                                jsonArray.add(id);
                                callExchangePackageApi();
                            } else if (type.equals("discard")) {
                                jsonArray.add(id);
                                callDiscardPackageApi();
                            } else if (type.equals("singlePhoto")) {
                                jsonArray.add(id);
                                callAdditionalPhoto();
                            }else if (type.equals("split")) {
                                jsonArray.add(id);
                                callSplitPackageApi();
                            }
                        }

                        @Override
                        public void click(Integer quantity, Integer packageId, Integer id, int position,
                                          LinearLayout secondLayoutBg,
                                          LinearLayout layoutBg, EditText thirdPriceEditText, EditText editText1) {
                            LoadingDialog.showLoadingDialog(getActivity(), "");
//                callViewPackage(secondLayoutBg,layoutBg , s);
//                            editText1.setEnabled(true);
                            editText1.setSelection(editText1.getText().length());
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(editText1, InputMethodManager.SHOW_IMPLICIT);
                            String s = editText1.getText().toString();

                            callUpdatePriceApi(quantity, secondLayoutBg, layoutBg, s, id, packageId, editText1, thirdPriceEditText);


                        }

                        @Override
                        public void callApi() {
                            LoadingDialog.showLoadingDialog(getActivity() , "");
                            callViewPackage();
                        }
                    });
                    packageDetailsRecycler.setAdapter(packageDetailsAdapter);

                    int number = packageDetailsRecycler.getAdapter().getItemCount();
                    if (number == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                        packageDetailsRecycler.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        packageDetailsRecycler.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<ViewPackageResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void callAdditionalPhoto() {
        /*
        {
	"comments": "Advanced Photo Requested",
    "itemId": [664],
    "state_id": 53,
    "type": "advanced_photo"
         */
        LoadingDialog.showLoadingDialog(getActivity(), "");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("comments", "Advanced Photo Requested");
        jsonObject.add("itemId", jsonArray);
        jsonObject.addProperty("state_id", 53);
        jsonObject.addProperty("type", "advanced_photo");


        Call<ReturnPackageResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().additionalPhoto("Bearer " + sharedPrefManager.getBearerToken()
                        , packageId, jsonObject.toString());
        call.enqueue(new Callback<ReturnPackageResponse>() {
            @Override
            public void onResponse(Call<ReturnPackageResponse> call, Response<ReturnPackageResponse> response) {
                if (response.code() == 200) {
                    LoadingDialog.showLoadingDialog(getActivity(), "");
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("showToast", true);
                    bundle.putString("type", "Additional Photo");
                    LockerReadyToShip lockerReadyToShip = new LockerReadyToShip();
                    lockerReadyToShip.setArguments(bundle);
                    LoadingDialog.cancelLoading();
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, lockerReadyToShip, null)
                            .addToBackStack(null).commit();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnPackageResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callUpdatePriceApi(Integer quantity,
                                    LinearLayout secondLayoutBg,
                                    LinearLayout layoutBg,
                                    String s, Integer id,
                                    Integer packageId1, EditText editText, EditText thirdEditText) {


        if (s.contains("₹ ")) {
            String[] separated = s.split("₹ ");
            s = separated[1];
        }


//

//        separated[1]; // this will contain "400"
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("name", "product Name");
        jsonObject.addProperty("price_amount", s);
        jsonObject.addProperty("quantity", quantity);
        jsonObject.addProperty("total_amount", "10");

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);
        Call<ReturnPackageResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().updatePrice("Bearer " + sharedPrefManager.getBearerToken()
                        , packageId, jsonArray.toString());
        String finalS = s;
        call.enqueue(new Callback<ReturnPackageResponse>() {
            @Override
            public void onResponse(Call<ReturnPackageResponse> call, Response<ReturnPackageResponse> response) {
                if (response.code() == 200) {
                    secondLayoutBg.setVisibility(View.GONE);
                    layoutBg.setVisibility(View.VISIBLE);
                    packageDetailsAdapter.isEdit = false;
                    if (finalS.contains("₹ ")) {
                        thirdEditText.setText(finalS);
                    } else {
                        thirdEditText.setText("₹ " + finalS);
                    }

                    LoadingDialog.cancelLoading();
                } else if (response.code() == 401) {
                    LoadingDialog.cancelLoading();
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(),
                            response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnPackageResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(),
                        t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callExchangePackageApi() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
//        "message1": "",
//                "message2": "testing exchange",
//                "return_pickup": 1,
//                "return_shoppre": "",
//                "itemIds": [
//        638
//    ]
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message1", "");
        jsonObject.addProperty("message2", "testing exchange");
        jsonObject.addProperty("return_pickup", 1);
        jsonObject.addProperty("return_shoppre", "");
        jsonObject.add("itemIds", jsonArray);


        Call<ReturnPackageResponse> call = RetrofitClient3
                .getInstance3().getAppApi()
                .exchangePackage("Bearer " + sharedPrefManager.getBearerToken(), packageId, jsonObject.toString());
        call.enqueue(new Callback<ReturnPackageResponse>() {
            @Override
            public void onResponse(Call<ReturnPackageResponse> call, Response<ReturnPackageResponse> response) {
                if (response.code() == 201) {

                    while (jsonArray.size() > 0) {
                        jsonArray.remove(0);
                    }
                    while (ids.size() > 0) {
                        ids.remove(0);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("showToast", true);
                    bundle.putString("type", "Exchange");
                    LockerReadyToShip lockerReadyToShip = new LockerReadyToShip();
                    lockerReadyToShip.setArguments(bundle);
                    LoadingDialog.cancelLoading();
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, lockerReadyToShip, null)
                            .addToBackStack(null).commit();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnPackageResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callDiscardPackageApi() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
//        "message1": "",
//    "message2": "",
//    "return_pickup": 1,
//    "return_shoppre": "",
//    "itemIds": [
//        639
//    ]
//    ]
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message1", "");
        jsonObject.addProperty("message2", "");
        jsonObject.addProperty("return_pickup", 1);
        jsonObject.addProperty("return_shoppre", "");
        jsonObject.add("itemIds", jsonArray);

        Call<ReturnPackageResponse> call = RetrofitClient3
                .getInstance3().getAppApi()
                .discardPackage("Bearer " + sharedPrefManager.getBearerToken(), packageId, jsonObject.toString());

        call.enqueue(new Callback<ReturnPackageResponse>() {
            @Override
            public void onResponse(Call<ReturnPackageResponse> call, Response<ReturnPackageResponse> response) {
                if (response.code() == 201) {

                    while (jsonArray.size() > 0) {
                        jsonArray.remove(0);
                    }
                    while (ids.size() > 0) {
                        ids.remove(0);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("showToast", true);
                    bundle.putString("type", "Discard");
                    LockerReadyToShip lockerReadyToShip = new LockerReadyToShip();
                    lockerReadyToShip.setArguments(bundle);
                    LoadingDialog.cancelLoading();
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, lockerReadyToShip, null)
                            .addToBackStack(null).commit();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnPackageResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callSplitPackageApi() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
//      "message1": "",
//    "message2": "testing split package",
//    "return_pickup": 1,
//    "return_shoppre": "",
//    "itemIds": [
//        24,
//        25
//    ],
//    "agree": true
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message1", "");
        jsonObject.addProperty("message2", "testing split package");
        jsonObject.addProperty("return_pickup", 1);
        jsonObject.addProperty("return_shoppre", "");
        jsonObject.add("itemIds", jsonArray);
        jsonObject.addProperty("agree", true);

        Call<ReturnPackageResponse> call = RetrofitClient3
                .getInstance3().getAppApi()
                .splitPackage("Bearer " + sharedPrefManager.getBearerToken(), packageId, jsonObject.toString());
        call.enqueue(new Callback<ReturnPackageResponse>() {
            @Override
            public void onResponse(Call<ReturnPackageResponse> call, Response<ReturnPackageResponse> response) {
                if (response.code() == 201) {

                    while (jsonArray.size() > 0) {
                        jsonArray.remove(0);
                    }
                    while (ids.size() > 0) {
                        ids.remove(0);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("showToast", true);
                    bundle.putString("type", "Split Package");
                    LockerReadyToShip lockerReadyToShip = new LockerReadyToShip();
                    lockerReadyToShip.setArguments(bundle);
                    LoadingDialog.cancelLoading();
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, lockerReadyToShip, null)
                            .addToBackStack(null).commit();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnPackageResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callReturnPackageApi() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("itemIds", jsonArray);
        jsonObject.addProperty("message1", "return pickup will be done by seller");
        jsonObject.addProperty("message2", "");
        Call<ReturnPackageResponse> call = RetrofitClient3
                .getInstance3().getAppApi()
                .returnPackage("Bearer " + sharedPrefManager.getBearerToken(), packageId, jsonObject.toString());
        call.enqueue(new Callback<ReturnPackageResponse>() {
            @Override
            public void onResponse(Call<ReturnPackageResponse> call, Response<ReturnPackageResponse> response) {
                if (response.code() == 201) {

                    while (jsonArray.size() > 0) {
                        jsonArray.remove(0);
                    }
                    while (ids.size()>0){
                        ids.remove(0);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putBoolean("showToast", true);
                    bundle.putString("type", "return");
                    LockerReadyToShip lockerReadyToShip = new LockerReadyToShip();
                    lockerReadyToShip.setArguments(bundle);
                    LoadingDialog.cancelLoading();
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, lockerReadyToShip, null)
                            .addToBackStack(null).commit();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReturnPackageResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showButton() {
        floatingBtn.setVisibility(View.VISIBLE);
        if (type.equals("return")) {
            floatingBtn.setText("Return Item(s)");
        } else if (type.equals("exchange")) {
            floatingBtn.setText("Exchange Item(s)");
        } else if (type.equals("discard")) {
            floatingBtn.setText("Discard Item(s)");
        } else if (type.equals("split")) {
            floatingBtn.setText("Split Item(s)");
        } else if (type.equals("multiPhoto")) {
            floatingBtn.setText("Request Additional Photos");
        }
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (type.equals("return")) {
                    callReturnPackageApi();
                } else if (type.equals("exchange")) {
                    callExchangePackageApi();
                } else if (type.equals("discard")) {
                    callDiscardPackageApi();
                } else if (type.equals("split")) {
                    callSplitPackageApi();

                } else if (type.equals("multiPhoto")) {
                    callAdditionalPhoto();
                }
                while (jsonArray.size() > 0) {
                    jsonArray.remove(0);
                }
                for (int i = 0; i < ids.size(); i++) {

                    if (!jsonArray.toString().contains(String.valueOf(ids.get(i)))) {
                        jsonArray.add(ids.get(i));
                    }

                }
                Toast.makeText(getActivity(), jsonArray.toString(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "Something Went Wrong Please try again!", Toast.LENGTH_SHORT).show();
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