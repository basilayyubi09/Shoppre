package com.peceinfotech.shoppre.UI.Shipment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.peceinfotech.shoppre.AccountResponse.CountryResponse;
import com.peceinfotech.shoppre.AccountResponse.Item;
import com.peceinfotech.shoppre.OrderModuleResponses.ShippingRateResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.SlabResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.LogisticClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.SelfShopper;
import com.peceinfotech.shoppre.Utils.LoadingDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingCalculatorResultFragment extends Fragment {
    ShippingRateResponse shippingRateResponse;
    TextView location, weight, unit, courierCharges, chooseCountry;
    MaterialButton placeOrderBtn;
    Spinner cmDropdown;
    List<Item> list, duplicateList;
    Integer countryId, categoryId;
    ArrayAdapter<Item> arrayAdapter;
    LinearLayout edit, chooseCountryLayout , viewCountry;
    String weightFromBundle, kg, liquid, height, width, length;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipping_calculator_result, container, false);
        location = view.findViewById(R.id.location);
        weight = view.findViewById(R.id.weight);
        unit = view.findViewById(R.id.unit);
        chooseCountryLayout = view.findViewById(R.id.chooseCountryLayout);
        edit = view.findViewById(R.id.edit);
        chooseCountry = view.findViewById(R.id.chooseCountry);
        cmDropdown = view.findViewById(R.id.cmDropdown);
        viewCountry = view.findViewById(R.id.viewCountry);
        courierCharges = view.findViewById(R.id.courierCharges);
        placeOrderBtn = view.findViewById(R.id.placeOrderBtn);
        setCountryList("");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            shippingRateResponse = (ShippingRateResponse) bundle.getSerializable("rate");
            weightFromBundle = bundle.getString("weight");
            kg = bundle.getString("kg");
            liquid = bundle.getString("liquid");
            countryId = bundle.getInt("countryId");
            categoryId = bundle.getInt("categoryId");
            location.setText(shippingRateResponse.getName());
            unit.setText(kg);
            weight.setText(weightFromBundle);

            courierCharges.setText("₹ " + shippingRateResponse.getCustomerRate().toString());
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShippingCalculator(), null)
                        .addToBackStack(null).commit();
            }
        });
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new SelfShopper(), null)
                        .addToBackStack(null).commit();
            }
        });
        chooseCountryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseCountry.performClick();
            }
        });

        viewCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSlabApi();
            }
        });
        chooseCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogue();
            }
        });
        return view;
    }

    private void callSlabApi() {
        LoadingDialog.showLoadingDialog(getActivity() , "");
        Call<List<SlabResponse>> call = LogisticClient
                .getInstance3()
                .getAppApi().getSLab(String.valueOf(countryId)
                        , "nondoc",liquid,String.valueOf(categoryId));
        call.enqueue(new Callback<List<SlabResponse>>() {
            @Override
            public void onResponse(Call<List<SlabResponse>> call, Response<List<SlabResponse>> response) {
                if (response.code()==200){
                    List<SlabResponse> slabResponse = response.body();

                    Toast.makeText(getActivity(), String.valueOf(slabResponse.get(0).getCustomerRate()), Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();

                }
                else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<List<SlabResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                LoadingDialog.cancelLoading();
            }
        });
    }

    private void showDialogue() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View row = getLayoutInflater().inflate(R.layout.country_dialog, null);

        ListView listView = row.findViewById(R.id.dialogListView);
        TextInputLayout inputLayout = row.findViewById(R.id.dialogSearch);


        AlertDialog dialog = builder.create();
        arrayAdapter = new ArrayAdapter<Item>(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
        listView.setTextFilterEnabled(true);
        dialog.setView(row);

        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                if (item instanceof Item) {
                    Item item1 = (Item) item;
                    String str = ((Item) item).getCountryCode().toString().split("[\\(\\)]")[1];

                }
                chooseCountry.setText(adapterView.getItemAtPosition(i).toString());
                chooseCountry.setTextColor(R.color.black);
                inputLayout.getEditText().setText("");
                dialog.dismiss();
            }
        });
        dialog.show();


        inputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.equals(null) || charSequence.length() == 0) {
                    arrayAdapter = new ArrayAdapter<Item>(getContext(), android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                arrayAdapter = new ArrayAdapter<Item>(getContext(), android.R.layout.simple_list_item_1, list);
                listView.setAdapter(arrayAdapter);
                setCountryList(editable.toString());
                arrayAdapter.getFilter().filter(editable);
                arrayAdapter.notifyDataSetChanged();

            }
        });


    }

    private void setCountryList(String typedString) {
        Call<CountryResponse> call = RetrofitClient3.getInstance3().getAppApi()
                .getCountry("Country", typedString);
        call.enqueue(new Callback<CountryResponse>() {

            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.code() == 200) {
                    list = response.body().getItems();
                    duplicateList = response.body().getItems();

                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}