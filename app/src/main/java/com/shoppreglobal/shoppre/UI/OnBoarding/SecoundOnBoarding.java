package com.shoppreglobal.shoppre.UI.OnBoarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.shoppreglobal.shoppre.R;

public class SecoundOnBoarding extends Fragment {

    private Button nextBtn2;
    TextView skip2;
    ImageView backBtn1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_secound_on_boarding, container, false);

        //Hooks
        nextBtn2 = view.findViewById(R.id.next_btn_2);
        skip2 = view.findViewById(R.id.skip2);
        backBtn1 = view.findViewById(R.id.back_arrow1);

        nextBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OnBoardingActivity.fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.entry_right_to_left, R.anim.exit_right_to_left, R.anim.entry_left_to_right, R.anim.exit_left_to_right)
                        .replace(R.id.onboarding_container, new ThirdOnBoarding(), null).addToBackStack(null).commit();


            }
        });

        skip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnBoardingActivity.fragmentManager.beginTransaction()
                        .replace(R.id.onboarding_container, new FourthOnBoarding(), null).addToBackStack(null).commit();
            }
        });

        backBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}