package com.shoppreglobal.shoppre.UI.Shipment;

import static com.shoppreglobal.shoppre.R.drawable.no_btn_bg;
import static com.shoppreglobal.shoppre.R.drawable.no_btn_bg1;
import static com.shoppreglobal.shoppre.R.drawable.yes_btn_bg;
import static com.shoppreglobal.shoppre.R.drawable.yes_btn_bg1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.shoppreglobal.shoppre.AccountResponse.CountryResponse;
import com.shoppreglobal.shoppre.AccountResponse.Item;
import com.shoppreglobal.shoppre.OrderModuleResponses.ProductCategoryResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.ShippingRateResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.LogisticClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShippingCalculator extends Fragment {


    TextView yesButton, noButton, chooseCountry;
    LinearLayout yesNoButtonLayout;
    ArrayAdapter arrayAdapterKg, arrayAdapterCm, arrayAdapterChooseCategory, arrayAdapterChooseCountry;
    List<Item> list, duplicateList;
    List<ProductCategoryResponse> listCategory;
    ArrayAdapter<Item> arrayAdapter;
    ArrayAdapter<ProductCategoryResponse> categoryAdapter;
    LinearLayout chooseCountryLayout;
    Spinner kgDropdown, cmDropdown, chooseCategoryDropdown;
    MaterialButton getEstimateBtn;
    TextView countryError, categoryError;
    String country, category, liquid, weight, length, height, width;
    int flag = 1;
    Integer Id, countryId;
    String cm, kg;
    FrameLayout main;
    ShippingRateResponse shippingRateResponse;
    LinearLayout kgLinearLayout, cmLinearLayout, chooseCategoryLinearLayout;
    String[] kgDropdownItem = {"Kilogram (KG)", "Pounds (lb)"};
    String[] cmDropdownItem = {"Centimeter (CM)", "Inches (in)"};
    String[] chooseCategoryItem = {"Choose Category", "Clothing", "Footwear, Accessories & Jewelry", "Electronics, Mobiles, Computers & Accessories", "Home, Kitchen & Furniture", "Food & Groceries", "Medicines", "Daily Essentials & Pooja Items", "Bike / Car Accessories", "Books & Stationaries", "Sports & Fitness", "Music Instruments", "Beauty Products", "Industrial Specific", "Pet Supplies", "Others"};
    final ArrayList<String> chooseCategoryArrayList = new ArrayList<>(Arrays.asList(chooseCategoryItem));

    TextView packageMinus, packagePlus, packageTextView, lenghtPlus, lenghtMinus, lengthTextView, heightPlus, heightMinus, heightTextView, widthPlus, widthMinus, widthTextView;
    float widthCount = (float) 0.5, heightCount = (float) 0.5, lengthCount = (float) 0.5, weightCount = (float) 0.5;

    @Override
    public void onResume() {
        super.onResume();

        arrayAdapterKg = new ArrayAdapter(getContext(), R.layout.kg_dropdown_text, kgDropdownItem);
        kgDropdown.setAdapter(arrayAdapterKg);

        arrayAdapterCm = new ArrayAdapter(getContext(), R.layout.cm_drop_down, cmDropdownItem);
        cmDropdown.setAdapter(arrayAdapterCm);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipping_calculator, container, false);
        OrderActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.shipmentMenu).setChecked(true);
        yesButton = view.findViewById(R.id.yesButton);
        noButton = view.findViewById(R.id.noButton);
        getEstimateBtn = view.findViewById(R.id.getEstimateBtn);
        yesNoButtonLayout = view.findViewById(R.id.yesNoButtonLayout);
        kgDropdown = view.findViewById(R.id.kgDropdown);
        countryError = view.findViewById(R.id.countryError);
        categoryError = view.findViewById(R.id.categoryError);
        kgLinearLayout = view.findViewById(R.id.kgLinearLayout);
        cmDropdown = view.findViewById(R.id.cmDropdown);
        main = view.findViewById(R.id.main);
        cmLinearLayout = view.findViewById(R.id.cmLinearLayout);
        chooseCategoryDropdown = view.findViewById(R.id.chooseCategoryDropdown);
        chooseCategoryLinearLayout = view.findViewById(R.id.chooseCategoryLinearLayout);
        chooseCountry = view.findViewById(R.id.chooseCountry);
        chooseCountryLayout = view.findViewById(R.id.chooseCountryLayout);


        packageMinus = view.findViewById(R.id.packageMinus);
        packagePlus = view.findViewById(R.id.packagePlus);
        packageTextView = view.findViewById(R.id.packageTextView);
        lenghtPlus = view.findViewById(R.id.lenghtPlus);
        lenghtMinus = view.findViewById(R.id.lenghtMinus);
        lengthTextView = view.findViewById(R.id.lengthTextView);
        heightPlus = view.findViewById(R.id.heightPlus);
        heightMinus = view.findViewById(R.id.heightMinus);
        heightTextView = view.findViewById(R.id.heightTextView);
        widthPlus = view.findViewById(R.id.widthPlus);
        widthMinus = view.findViewById(R.id.widthMinus);
        widthTextView = view.findViewById(R.id.widthTextView);

        setCountryList("");

        callCategoryAdapter();
        packagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightCount = (float) (weightCount + 0.5);
                packageTextView.setText(String.valueOf(weightCount));
            }
        });

        setupUI(main);

        Bundle bundle = this.getArguments();
        if (bundle != null) {


            packageTextView.setText(bundle.getString("weight"));
            lengthTextView.setText(bundle.getString("length"));
            heightTextView.setText(bundle.getString("height"));
            widthTextView.setText(bundle.getString("width"));
            if (bundle.getString("liquid").equals("true")) {
                yesButton.setTextColor(getResources().getColor(R.color.white));
                noButton.setTextColor(getResources().getColor(R.color.black));
                yesButton.setBackground(getResources().getDrawable(yes_btn_bg));
                noButton.setBackground(getResources().getDrawable(no_btn_bg1));
            } else if (bundle.getString("liquid").equals("false")) {
                yesButton.setTextColor(getResources().getColor(R.color.black));
                yesButton.setBackground(getResources().getDrawable(yes_btn_bg1));
                noButton.setTextColor(getResources().getColor(R.color.white));
                noButton.setBackground(getResources().getDrawable(no_btn_bg));

            }

        }
        packageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weightCount == 0.5) {
                    packageTextView.setText("0.5");
                } else {
                    weightCount -= 0.5;
                    packageTextView.setText("" + weightCount);
                }

            }
        });


        lenghtMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lengthCount == 0.5) {
                    lengthTextView.setText("0.5");
                } else {
                    lengthCount -= 0.5;
                    lengthTextView.setText("" + lengthCount);
                }

            }
        });

        lenghtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lengthCount = (float) (lengthCount + 0.5);
                lengthTextView.setText(String.valueOf(lengthCount));
            }
        });


        heightMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (heightCount == 0.5) {
                    lengthTextView.setText("0.5");
                } else {
                    heightCount -= 0.5;
                    heightTextView.setText("" + heightCount);
                }
            }
        });


        heightPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heightCount = (float) (heightCount + 0.5);
                heightTextView.setText((String.valueOf(heightCount)));

            }
        });

        widthMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (widthCount == 0.5) {
                    lengthTextView.setText("0.5");
                } else {
                    widthCount -= 0.5;
                    widthTextView.setText("" + weightCount);
                }
            }
        });

        widthPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                widthCount = (float) (widthCount + 0.5);
                widthTextView.setText("" + widthCount);
            }
        });


        chooseCountryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseCountry.performClick();
            }
        });


        chooseCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogue();
            }
        });


        chooseCategoryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseCategoryDropdown.performClick();
            }
        });

        cmLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmDropdown.performClick();
            }
        });

        kgLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kgDropdown.performClick();
            }
        });


        noButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "ResourceType"})
            @Override
            public void onClick(View v) {

                flag = 2;
                noButton.setTextColor(getResources().getColor(R.color.white));
                yesButton.setTextColor(getResources().getColor(R.color.black));
                noButton.setBackground(getResources().getDrawable(no_btn_bg));
                yesButton.setBackground(getResources().getDrawable(yes_btn_bg1));


            }
        });


        yesButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "ResourceType"})
            @Override
            public void onClick(View v) {

                flag = 1;
                yesButton.setTextColor(getResources().getColor(R.color.white));
                noButton.setTextColor(getResources().getColor(R.color.black));
                yesButton.setBackground(getResources().getDrawable(yes_btn_bg));
                noButton.setBackground(getResources().getDrawable(no_btn_bg1));


            }
        });

        getEstimateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
                {

                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    getTextFromFields();
                    if (!validateCountry() || !validateCategory()) {
                        return;
                    } else {

                        callShippingRateApi();

                    }
                }


            }
        });


        chooseCategoryDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    Id = listCategory.get(position).getId();
                    if (listCategory.get(position).getName().equals("Liquid")) {
                        yesButton.setTextColor(getResources().getColor(R.color.white));
                        noButton.setTextColor(getResources().getColor(R.color.black));
                        yesButton.setBackground(getResources().getDrawable(yes_btn_bg));
                        noButton.setBackground(getResources().getDrawable(no_btn_bg1));
                        noButton.setClickable(false);
                    } else {
                        noButton.setClickable(true);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    private void callShippingRateApi() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<ShippingRateResponse> call = LogisticClient
                .getInstance3()
                .getAppApi().getShippingRate(String.valueOf(countryId)
                        , "nondoc", weight,
                        length, width, height,
                        cm, kg, liquid
                        , String.valueOf(Id));
        call.enqueue(new Callback<ShippingRateResponse>() {
            @Override
            public void onResponse(Call<ShippingRateResponse> call, Response<ShippingRateResponse> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    shippingRateResponse = response.body();
                    Bundle bundle = new Bundle();

                    bundle.putString("weight", weight);
                    bundle.putString("length", length);
                    bundle.putString("width", width);
                    bundle.putString("height", height);
                    bundle.putString("kg", kg);
                    bundle.putInt("countryId", countryId);
                    bundle.putInt("categoryId", Id);
                    bundle.putString("liquid", liquid);

                    bundle.putSerializable("rate", shippingRateResponse);
                    ShippingCalculatorResultFragment shippingCalculatorResultFragment = new ShippingCalculatorResultFragment();
                    shippingCalculatorResultFragment.setArguments(bundle);

                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, shippingCalculatorResultFragment, null)
                            .addToBackStack(null).commit();

                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShippingRateResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callCategoryAdapter() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<List<ProductCategoryResponse>> call = LogisticClient
                .getInstance3()
                .getAppApi().getCategory();
        call.enqueue(new Callback<List<ProductCategoryResponse>>() {
            @Override
            public void onResponse(Call<List<ProductCategoryResponse>> call, Response<List<ProductCategoryResponse>> response) {
                if (response.code() == 200) {
                    listCategory = response.body();
                    listCategory.add(0, new ProductCategoryResponse(0, "Choose Category"));


                    categoryAdapter = new ArrayAdapter<ProductCategoryResponse>(
                            getActivity(), R.layout.choose_category_dropdown_text, listCategory
                    ) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                return false;
                            } else {
                                return true;
                            }

                        }

                        @Override
                        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);

                            TextView chooseCategoryTextView = (TextView) view;

                            if (position == 0) {
                                chooseCategoryTextView.setVisibility(View.GONE);
                                chooseCategoryTextView.setTextColor(Color.GRAY);
                            } else {
                                chooseCategoryTextView.setTextColor(Color.BLACK);
                            }

                            return view;
                        }
                    };
                    chooseCategoryDropdown.setAdapter(categoryAdapter);
                    LoadingDialog.cancelLoading();

                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductCategoryResponse>> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateCountry() {
        if (country.equals("Choose Country")) {
            countryError.setVisibility(View.VISIBLE);
            return false;
        } else {
            countryError.setVisibility(View.GONE);
            return true;
        }

    }

    private boolean validateCategory() {
        if (category.equals("Choose Category")) {
            categoryError.setVisibility(View.VISIBLE);
            return false;
        } else {
            categoryError.setVisibility(View.GONE);
            return true;
        }

    }

    private void getTextFromFields() {

        weight = packageTextView.getText().toString();
        length = lengthTextView.getText().toString();
        height = heightTextView.getText().toString();
        width = widthTextView.getText().toString();
        country = chooseCountry.getText().toString();
        category = chooseCategoryDropdown.getSelectedItem().toString();
        kg = kgDropdown.getSelectedItem().toString();
        cm = cmDropdown.getSelectedItem().toString();
        if (flag == 1) {
            liquid = "true";
        } else {
            liquid = "false";
        }


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
                    countryId = ((Item) item).getId();
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

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}