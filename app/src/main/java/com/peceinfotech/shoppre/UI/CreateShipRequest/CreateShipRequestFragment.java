package com.peceinfotech.shoppre.UI.CreateShipRequest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

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
    int checkCount = 0;
    TextView selectedNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_create_ship_request, container, false);

        checkBox = view.findViewById(R.id.check);
        recyclerView = view.findViewById(R.id.recycle);
        selectedNumber = view.findViewById(R.id.selectedNumber);

        list.add(new DummyShipModel("Myntra","2","8473"));
        list.add(new DummyShipModel("Myntra","2","8473"));
        list.add(new DummyShipModel("Myntra","2","8473"));
        list.add(new DummyShipModel("Myntra","2","8473"));

        adapter = new DummyCreateAdapter(list , getActivity());
        recyclerView.setAdapter(adapter);

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

//        for (int i=0 ; i<list.size() ; i++) {
//             recyclerView.getChildAt(i);
//            CheckBox checkBox2 = getView().findViewById(R.id.check);
//            checkBox2.setChecked(true);
//
//            if (checkBox.isChecked()) {
//                checkCount = checkCount+i;
//                checkBox2.setChecked(true);
//                selectedNumber.setText(String.valueOf(checkCount));
//            }
//            else {
//                if (checkCount!=0){
//                    checkBox2.setChecked(false);
//                    checkCount = checkCount-i;
//                    selectedNumber.setText(String.valueOf(checkCount));
//                }
//
//            }
//        }

        return view;
    }
}