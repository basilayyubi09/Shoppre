package com.peceinfotech.shoppre.UI.Orders.OrderFragments.TabLayoutFragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peceinfotech.shoppre.Adapters.CartGroupAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.CartModelResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.ProductItem;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;


public class EmptyCart extends Fragment {

    RecyclerView cartRecycler;
    CardView itemCartCard, productCartCard;
    List<CartModelResponse> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empty_cart, container, false);

        cartRecycler = view.findViewById(R.id.cartRecycler);
        itemCartCard = view.findViewById(R.id.itemInCartCard);
        productCartCard = view.findViewById(R.id.productCard);

        String web = "Amazon";
        List<ProductItem>  p = new ArrayList<>();

        p.add(new ProductItem(1, 02, 300, "Shoes Premium Quality"));
        p.add(new ProductItem(2, 07, 900, "T-Shirt Premium Quality"));
        p.add(new ProductItem(3, 07, 1100, "Adidas Yeezee 600"));


        String web1 = "Myntra";
        List<ProductItem>  p1 = new ArrayList<>();

        p1.add(new ProductItem(1, 02, 300, "Shoes"));
        p1.add(new ProductItem(2, 02, 300, "Shoes"));
        p1.add(new ProductItem(3, 02, 300, "Shoes"));
        p1.add(new ProductItem(4, 02, 300, "Shoes"));
        p1.add(new ProductItem(5, 02, 300, "Shoes"));
        p1.add(new ProductItem(6, 02, 300, "Shoes"));



        String web2 = "Nyka";
        List<ProductItem>  p2 = new ArrayList<>();

        p2.add(new ProductItem(1, 02, 300, "Shoes"));
        p2.add(new ProductItem(2, 02, 300, "Shoes"));
        p2.add(new ProductItem(3, 02, 300, "Shoes"));

        String web3 = "Flipkart";
        List<ProductItem>  p3 = new ArrayList<>();

        p3.add(new ProductItem(1, 02, 300, "Shoes"));
        p3.add(new ProductItem(2, 02, 300, "Shoes"));


        list.add(new CartModelResponse(web, p));
        list.add(new CartModelResponse(web1, p1));
        list.add(new CartModelResponse(web2, p2));
        list.add(new CartModelResponse(web3, p3));

        CartGroupAdapter cartGroupAdapter = new CartGroupAdapter(list, getContext());
        cartRecycler.setAdapter(cartGroupAdapter);



        itemCartCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productCartCard.setVisibility(View.VISIBLE);

            }
        });


        return view;
    }
}