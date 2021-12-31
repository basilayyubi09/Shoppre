package com.shoppreglobal.shoppre.UI.Shipment;

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
import com.shoppreglobal.shoppre.AccountResponse.CountryResponse;
import com.shoppreglobal.shoppre.AccountResponse.Item;
import com.shoppreglobal.shoppre.OrderModuleResponses.ShippingRateResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.SlabResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.LogisticClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.OrderFragment;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.WebViewFragment;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.PricingTableDialog;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingCalculatorResultFragment extends Fragment {
    ShippingRateResponse shippingRateResponse;
    TextView location, weight, unit, courierCharges, chooseCountry, viewPricingTable;
    MaterialButton placeOrderBtn;
    Spinner cmDropdown;
    List<Item> list, duplicateList;
    Integer countryId, categoryId;
    ArrayAdapter<Item> arrayAdapter;
    List<SlabResponse> slabResponse;
    LinearLayout edit, chooseCountryLayout, viewCountry , restricted;
    String weightFromBundle, kg, liquid, height, width, length , countryName="" , countryNameP;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipping_calculator_result, container, false);
        location = view.findViewById(R.id.location);
        weight = view.findViewById(R.id.weight);
        restricted = view.findViewById(R.id.restricted);
        unit = view.findViewById(R.id.unit);
        viewPricingTable = view.findViewById(R.id.viewPricingTable);
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

            /*
            *
            *  bundle.putString("weight" , weight);
                    bundle.putString("length" , length);
                    bundle.putString("width" , width);
                    bundle.putString("height" , height);*/
            weightFromBundle = bundle.getString("weight");
            length = bundle.getString("length");
            width = bundle.getString("width");
            height = bundle.getString("height");
            kg = bundle.getString("kg");
            liquid = bundle.getString("liquid");
            countryId = bundle.getInt("countryId");
            categoryId = bundle.getInt("categoryId");
            countryNameP = shippingRateResponse.getName();
            location.setText(shippingRateResponse.getName());
            chooseCountry.setText(shippingRateResponse.getName());
            unit.setText(kg);
            weight.setText(weightFromBundle);
            double rate = shippingRateResponse.getCustomerRate();
            rate = Double.parseDouble(new DecimalFormat("##.##").format(rate));

            courierCharges.setText("₹ " + String.valueOf(rate));
        }
        callSlabApi();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("weight", weightFromBundle);
                bundle1.putString("length", length);
                bundle1.putString("width", width);
                bundle1.putString("height", height);
                bundle1.putString("liquid", liquid);
                ShippingCalculator shippingCalculator = new ShippingCalculator();
                shippingCalculator.setArguments(bundle1);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, shippingCalculator, null)
                        .addToBackStack(null).commit();
            }
        });
        viewPricingTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PricingTableDialog pricingTableDialog = new PricingTableDialog();
                pricingTableDialog.showDialog(getActivity(), slabResponse);
            }
        });
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new OrderFragment(), null)
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

            }
        });
        chooseCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogue();
            }
        });
        restricted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://ship.shoppre.com/prohibited-items-what-you-cannot-ship-internationally-from-india");
                WebViewFragment cash = new WebViewFragment();
                cash.setArguments(bundle);
                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, cash, null)
                        .addToBackStack(null).commit();
            }
        });
        viewCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                if (countryName.equals("")){
                    url = "https://www.shoppre.com/shipping-from-india-to-"+countryNameP;
                }
                else {
                    url = "https://www.shoppre.com/shipping-from-india-to-"+countryName;
                }
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                WebViewFragment cash = new WebViewFragment();
                cash.setArguments(bundle);
                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, cash, null)
                        .addToBackStack(null).commit();
            }
        });
        return view;
    }

    private void callSlabApi() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<List<SlabResponse>> call = LogisticClient
                .getInstance3()
                .getAppApi().getSLab(String.valueOf(countryId)
                        , "nondoc", liquid, String.valueOf(categoryId));
        call.enqueue(new Callback<List<SlabResponse>>() {
            @Override
            public void onResponse(Call<List<SlabResponse>> call, Response<List<SlabResponse>> response) {
                if (response.code() == 200) {
                    slabResponse = response.body();


                    LoadingDialog.cancelLoading();

                } else {
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
                countryName = adapterView.getItemAtPosition(i).toString();
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