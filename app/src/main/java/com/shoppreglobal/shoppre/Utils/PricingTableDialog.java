package com.shoppreglobal.shoppre.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.Adapters.ShipmentAdapters.PriceTableAdapter;
import com.shoppreglobal.shoppre.OrderModuleResponses.SlabResponse;
import com.shoppreglobal.shoppre.R;

import java.util.List;

public class PricingTableDialog {


    RecyclerView pricingTableRecycler;

    public void showDialog(Context context, List<SlabResponse> list) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.pricing_table_pop_up);
        TextView currency = dialog.findViewById(R.id.currency);

        pricingTableRecycler = dialog.findViewById(R.id.pricingTableRecycler);

        String string = list.get(0).getCountryCurrencyRates();
        String[] parts = string.split("-");


        currency.setText("(in " + parts[0] + ")");

        PriceTableAdapter priceTableAdapter = new PriceTableAdapter(list, context);
        pricingTableRecycler.setAdapter(priceTableAdapter);


        dialog.show();

    }
}
