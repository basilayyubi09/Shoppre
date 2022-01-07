package com.shoppreglobal.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;


public class MyCashWebViewFragment extends Fragment {


    SharedPrefManager sharedPrefManager;
    WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_cash_web_view, container, false);
        OrderActivity.bottomNavigationView.setVisibility(View.GONE);
        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("orders");

        return view;
    }

}