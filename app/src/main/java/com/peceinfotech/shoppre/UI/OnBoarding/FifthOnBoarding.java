package com.peceinfotech.shoppre.UI.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;

public class FifthOnBoarding extends Fragment {

    private Button startShopping;
    ImageView backBtn4;
    TextView lockerNo;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fifth_on_boarding, container, false);

        startShopping = view.findViewById(R.id.startShoppingBtn);
        backBtn4 = view.findViewById(R.id.back_arrow4);
        lockerNo = view.findViewById(R.id.lockerNo);





        startShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , OrderActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        backBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }


}