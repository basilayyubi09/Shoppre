package com.peceinfotech.shoppre.UI.CreateShipRequest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.peceinfotech.shoppre.Adapters.CreateShipAdapters.DummyCreateAdapter;
import com.peceinfotech.shoppre.Models.DummyShipModel;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;

public class CreateShipRequestFragment extends Fragment {

    CheckBox checkBox;
    RecyclerView recyclerView;
    DummyCreateAdapter adapter;
    List<DummyShipModel> list = new ArrayList<>();
    TextView selectedNumber , totalNumber;
    List<String> list1 = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_create_ship_request, container, false);

        checkBox = view.findViewById(R.id.check);
        recyclerView = view.findViewById(R.id.recycle);
        selectedNumber = view.findViewById(R.id.selectedNumber);
        totalNumber = view.findViewById(R.id.totalNumber);
        selectedNumber.setText(String.valueOf(list1.size()));
        list.add(new DummyShipModel("1","Myntra","2","8473"));
        list.add(new DummyShipModel("2","Myntra","2","8473"));
        list.add(new DummyShipModel("3","Myntra","2","8473"));
        list.add(new DummyShipModel("4","Myntra","2","8473"));
        list.add(new DummyShipModel("5","Myntra","2","8473"));
        list.add(new DummyShipModel("6","Myntra","2","8473"));

        adapter = new DummyCreateAdapter(list, getActivity(), new DummyCreateAdapter.MyInterface() {
            @Override
            public void getCheckBox(String id, CheckBox check1) {
//                Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
                if (check1.isChecked()){

                    if (list1.contains(id)){

                    }
                    else {
                        list1.add(id);
                        selectedNumber.setText(String.valueOf(list1.size()));
                        if (list.size()== list1.size()){
                            checkBox.setChecked(true);
                        }

                    }

                }
                else {

                    if (list1.contains(id)){
                        list1.remove(id);
                        selectedNumber.setText(String.valueOf(list1.size()));
                        if (list.size()== list1.size()){
                            checkBox.setChecked(true);
                        }
                        else {
                            checkBox.setChecked(false);
                        }
                    }
                    else {

                    }
                }

            }
        });
        recyclerView.setAdapter(adapter);
        totalNumber.setText("/"+String.valueOf(list.size()));
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0 ; i<list.size() ; i++) {
                    View view1 = recyclerView.getChildAt(i);
                    CheckBox checkBox1 = view1.findViewById(R.id.check);
                    checkBox1.setChecked(true);

                    if (checkBox.isChecked()) {

                            checkBox1.setChecked(true);
                        }
                     else {
                        checkBox1.setChecked(false);
                    }
                }
            }
        });



        return view;
    }
}