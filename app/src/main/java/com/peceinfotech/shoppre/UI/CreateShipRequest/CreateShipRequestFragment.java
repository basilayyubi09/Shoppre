package com.peceinfotech.shoppre.UI.CreateShipRequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.peceinfotech.shoppre.Adapters.CreateShipAdapters.DummyCreateAdapter;
import com.peceinfotech.shoppre.LockerModelResponse.PackageModel;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;

public class CreateShipRequestFragment extends Fragment {

    CheckBox checkBox;
    RecyclerView recyclerView;
    DummyCreateAdapter adapter;
    List<PackageModel> list;
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

        list = new ArrayList<>();
        list1 = new ArrayList<>();
        checkBox = view.findViewById(R.id.check);
        recyclerView = view.findViewById(R.id.recycle);
        selectedNumber = view.findViewById(R.id.selectedNumber);
        totalNumber = view.findViewById(R.id.totalNumber);
        choosePackageProceedBtn = view.findViewById(R.id.choosePackageProceedBtn);
        totalValue = view.findViewById(R.id.totalValue);
        containDamage = view.findViewById(R.id.containDamage);
        totalAmount = view.findViewById(R.id.totalAmount);


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
            @Override
            public void getCheckBox(Integer id, CheckBox check1, Integer priceAmount1, Object invoice) {
                if (check1.isChecked()) {

                    if (list1.contains(id)) {

                    }
                    else {
                        list1.add(id);
                        selectedNumber.setText(String.valueOf(list1.size()));
                        total = total + priceAmount1;
                        totalAmount.setText("₹ " + String.valueOf(total));
                        if (total>50000){
                            totalValue.setVisibility(View.VISIBLE);
                        }
                        else {
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
                        selectedNumber.setText(String.valueOf(list1.size()));
                        checkBox.setChecked(false);

                        if (total > 0) {
                            total = total - priceAmount1;
                            totalAmount.setText("₹ " + String.valueOf(total));

                        }
                        if (total>50000){
                            totalValue.setVisibility(View.VISIBLE);
                        }
                        else {
                            totalValue.setVisibility(View.GONE);
                        }
                        if (invoice == null) {
                            isContainInvoice = true;
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


        choosePackageProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), list1.toString(), Toast.LENGTH_SHORT).show();
//                AlertDialogBox alertDialogBox = new AlertDialogBox();
//                alertDialogBox.showAlertDialog(getContext());
            }
        });

        return view;
    }
}