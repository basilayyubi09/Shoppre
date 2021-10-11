package com.peceinfotech.shoppre.UI.OnBoarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.SignupLogin.SignUpActivity;

public class FifthOnBoarding extends Fragment {

    private Button startShopping;
    ImageView backBtn4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fifth_on_boarding, container, false);

        startShopping = view.findViewById(R.id.startShoppingBtn);
        backBtn4 = view.findViewById(R.id.back_arrow4);

        
        startShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Start Shopping Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        backBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnBoardingActivity.fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.entry_left_to_right, R.anim.exit_left_to_right, R.anim.entry_right_to_left, R.anim.exit_right_to_left)
                        .replace(R.id.onboarding_container,new FourthOnBoarding(),null)
                        .commit();
            }
        });

        return view;
    }
}