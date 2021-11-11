package com.peceinfotech.shoppre.Adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.OrderModuleResponses.Order;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.ViewOrderPersonalShop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.viewHolder> {

    List<Order> list;
    Context context;

    public OrdersAdapter(List<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_order_layout , parent ,  false);
        return new viewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Order order = list.get(position);

        holder.orderWebsiteName.setText(order.getStore().getName());
        holder.orderNo.setText("Order No: "+"#"+order.getOrderCode());

        String date1 = order.getCreatedAt();
        String[] split = date1.split("T");
        String date = split[0];

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(date, dtf);
        String month_name = dtf2.format(ld);

        holder.orderDate.setText(month_name);
        holder.orderImage.setImageResource(R.drawable.ic_personal_shopper);
        
        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putString("orderCode" , order.getOrderCode());
                bundle.putString("id" ,String.valueOf(order.getId()));

                ViewOrderPersonalShop viewOrderPersonalShop = new ViewOrderPersonalShop();
                viewOrderPersonalShop.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, viewOrderPersonalShop, null)
                        .addToBackStack(null).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView orderImage;
        TextView orderWebsiteName, orderNo, orderDate;
        LinearLayout viewMore;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            orderImage = itemView.findViewById(R.id.singleOrderImage);
            orderWebsiteName = itemView.findViewById(R.id.orderWebsiteName);
            orderNo = itemView.findViewById(R.id.orderNo);
            orderDate = itemView.findViewById(R.id.orderDate);
            viewMore = itemView.findViewById(R.id.viewMore);


        }
    }
}
