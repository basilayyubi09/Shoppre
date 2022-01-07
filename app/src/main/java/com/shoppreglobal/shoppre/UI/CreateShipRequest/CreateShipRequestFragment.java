package com.shoppreglobal.shoppre.UI.CreateShipRequest;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.CreateShipAdapters.DummyCreateAdapter;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageModel;
import com.shoppreglobal.shoppre.LockerModelResponse.RedirectShipmentResponse;
import com.shoppreglobal.shoppre.LockerModelResponse.ShipmentMeta;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.Utils.AlertDialogBox;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateShipRequestFragment extends Fragment {

    CheckBox checkBox;
    RecyclerView recyclerView;
    DummyCreateAdapter adapter;
    List<PackageModel> list;
    List<PackageModel> tempList;
    CardView back;
    SharedPrefManager sharedPrefManager;
    TextView selectedNumber, totalNumber, totalAmount;
    List<Integer> list1;
    MaterialButton choosePackageProceedBtn;
    MaterialCardView totalValue, containDamage;
    Integer total = 0;
    boolean isContainInvoice = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_ship_request, container, false);
        sharedPrefManager = new SharedPrefManager(getActivity());
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        tempList = new ArrayList<>();
        checkBox = view.findViewById(R.id.check);
        back = view.findViewById(R.id.back);
        recyclerView = view.findViewById(R.id.recycle);
        selectedNumber = view.findViewById(R.id.selectedNumber);
        totalNumber = view.findViewById(R.id.totalNumber);
        choosePackageProceedBtn = view.findViewById(R.id.choosePackageProceedBtn);
        totalValue = view.findViewById(R.id.totalValue);
        containDamage = view.findViewById(R.id.containDamage);
        totalAmount = view.findViewById(R.id.totalAmount);

        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.lockerMenu).setChecked(true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            list = (List<PackageModel>) bundle.getSerializable("list");
        }


        if (isContainInvoice) {
            containDamage.setVisibility(View.VISIBLE);
        }
        totalAmount.setText("₹ " + String.valueOf(total));


        selectedNumber.setText(String.valueOf(list1.size()));
        adapter = new DummyCreateAdapter(list, getActivity(), new DummyCreateAdapter.MyInterface() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void getCheckBox(Integer id, CheckBox check1, Integer priceAmount1, Object invoice, PackageModel model) {
                if (check1.isChecked()) {

                    if (list1.contains(id)) {

                    } else {
                        list1.add(id);
                        tempList.add(model);
                        selectedNumber.setText(String.valueOf(list1.size()));
                        total = total + priceAmount1;
                        totalAmount.setText("₹ " + String.valueOf(total));
                        if (list1.size() < 1) {
                            choosePackageProceedBtn.setEnabled(false);
                            choosePackageProceedBtn.setBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.grey)));
                        } else {
                            choosePackageProceedBtn.setEnabled(true);

                            choosePackageProceedBtn.setBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.selected_btn_color)));
                        }
                        if (total > 50000) {
                            totalValue.setVisibility(View.VISIBLE);
                        } else {
                            totalValue.setVisibility(View.GONE);
                        }
                        if (invoice == null) {
                            isContainInvoice = true;
                        }

                        if (isContainInvoice) {
                            containDamage.setVisibility(View.VISIBLE);
                        }
                        if (list.size() == list1.size()) {
                            checkBox.setChecked(true);
                            total = 0;
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getInvoice() == null) {
                                    isContainInvoice = true;
                                }
                                total = total + list.get(i).getPriceAmount();
                                choosePackageProceedBtn.setEnabled(true);
                                choosePackageProceedBtn.setBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.selected_btn_color)));
                            }
                            totalAmount.setText("₹ " + String.valueOf(total));
                            if (total > 50000) {
                                totalValue.setVisibility(View.VISIBLE);
                            } else {
                                totalValue.setVisibility(View.GONE);
                            }
                            if (isContainInvoice) {
                                containDamage.setVisibility(View.VISIBLE);
                            }
                        }


                    }


                } else {

                    if (list1.contains(id)) {
                        list1.remove(id);
                        tempList.remove(model);
                        selectedNumber.setText(String.valueOf(list1.size()));
                        checkBox.setChecked(false);
                        if (list1.size() < 1) {
                            choosePackageProceedBtn.setEnabled(false);
                            choosePackageProceedBtn.setBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.grey)));
                        } else {
                            choosePackageProceedBtn.setEnabled(true);
                            choosePackageProceedBtn.setBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.selected_btn_color)));
                        }

                        if (total > 0) {
                            total = total - priceAmount1;
                            totalAmount.setText("₹ " + String.valueOf(total));

                        }
                        if (total > 50000) {
                            totalValue.setVisibility(View.VISIBLE);
                        } else {
                            totalValue.setVisibility(View.GONE);
                        }


                        if (invoice == null) {
                            isContainInvoice = true;
                        } else {
                            isContainInvoice = false;
                        }

                        if (isContainInvoice) {
                            containDamage.setVisibility(View.VISIBLE);

                        } else {
                            containDamage.setVisibility(View.GONE);
                        }
                        if (list1.isEmpty()) {
                            total = 0;
                            totalAmount.setText("₹ " + String.valueOf(total));
                            containDamage.setVisibility(View.GONE);
                            totalValue.setVisibility(View.GONE);
                            choosePackageProceedBtn.setEnabled(false);
                            choosePackageProceedBtn.setBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.grey)));

                        }

                    }

                }

            }
        });
        recyclerView.setAdapter(adapter);
        totalNumber.setText("/" + String.valueOf(list.size()));
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    View view1 = recyclerView.getChildAt(i);
                    CheckBox checkBox1 = view1.findViewById(R.id.check);
                    checkBox1.setChecked(true);

                    if (checkBox.isChecked()) {

                        checkBox1.setChecked(true);

                    } else {
                        checkBox1.setChecked(false);
                    }
                }


                totalAmount.setText("₹ " + String.valueOf(total));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        choosePackageProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int inReviewCount = 0, actionRequiredCount = 0;
                boolean isBoth = false, isWrong = false, isRestricted = false;
                for (int i = 0; i < tempList.size(); i++) {
                    if (tempList.get(i).getStateName().equals("In Review")) {
                        inReviewCount++;
                    } else if (tempList.get(i).getStateName().equals("Action Required")) {
                        actionRequiredCount++;
                    }
                    if (tempList.get(i).getIsWrongItem() != null && tempList.get(i).getIsRestrictedItem() != null) {

                        isBoth = true;

                    } else if (tempList.get(i).getIsWrongItem() != null) {
                        isWrong = true;
                    } else if (tempList.get(i).getIsRestrictedItem() != null) {
                        isRestricted = true;
                    }
                }

                if (!isBoth || !isRestricted || !isWrong || inReviewCount == 0 || actionRequiredCount == 0) {
                    StringBuilder allIds = new StringBuilder();
                    for (int i = 0; i < tempList.size(); i++) {
                        if (i + 1 == tempList.size()) {
                            allIds.append(tempList.get(i).getId());
                        } else {
                            allIds.append(tempList.get(i).getId() + ",");
                        }
                    }

                    callRedirectShipment(allIds);
                } else {
                    AlertDialogBox alertDialogBox = new AlertDialogBox();
                    alertDialogBox.showAlertDialog(getContext(), tempList, inReviewCount, actionRequiredCount
                            , isBoth, isRestricted, isWrong);
                }

            }
        });

        return view;
    }

    private void callRedirectShipment(StringBuilder allIds) {

        LoadingDialog.showLoadingDialog(getActivity(), "");

        Call<RedirectShipmentResponse> call = RetrofitClient3.getInstance3()
                .getAppApi()
                .redirect("Bearer " + sharedPrefManager.getBearerToken(), allIds.toString());

        call.enqueue(new Callback<RedirectShipmentResponse>() {
            @Override
            public void onResponse(Call<RedirectShipmentResponse> call, Response<RedirectShipmentResponse> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    ShipmentMeta shipmentMeta = response.body().getShipmentMeta();
                    Bundle bundle = new Bundle();
                    bundle.putString("ids", allIds.toString());
                    bundle.putString("liquid", response.body().getIsLiquid());
                    bundle.putSerializable("meta", (Serializable) shipmentMeta);
                    CreateShipmentDeliveryAddress address = new CreateShipmentDeliveryAddress();
                    address.setArguments(bundle);

                    total = 0;
                    checkBox.setChecked(false);
                    totalAmount.setText("₹ " + String.valueOf(total));
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, address, null)
                            .addToBackStack(null).commit();

                } else if (response.code() == 401) {
                    callRefreshTokenApi(allIds);
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RedirectShipmentResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callRefreshTokenApi(StringBuilder allIds) {
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
                    callRedirectShipment(allIds);
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

    }
}