package com.shoppreglobal.shoppre.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.AccountResponse.WalletTransaction;
import com.shoppreglobal.shoppre.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class WalletTransactionAdapter extends RecyclerView.Adapter<WalletTransactionAdapter.viewHolder> {
    Context context;
    List<WalletTransaction> list;

    public WalletTransactionAdapter(Context context, List<WalletTransaction> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallet_transaction_single_layout, parent, false);

        return new viewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


//        holder.image.setImageResource(list.get(position).getImage());
        holder.mainText.setText(list.get(position).getDescription());
//        if (list.get(position).getMessageText().equals("")){
//            holder.messageText.setVisibility(View.GONE);
//        }
//        else
//            holder.messageText.setText(list.get(position).getMessageText());
        if (list.get(position).getAmount().toString().startsWith("-")) {
            String value = list.get(position).getAmount().toString();
            value = value.substring(1);
            holder.price.setTextColor(Color.parseColor("#EF5261"));
            holder.price.setText("- ₹ " + value);
        } else {
            holder.price.setTextColor(Color.parseColor("#17B28F"));
            holder.price.setText("+" + " ₹ " + list.get(position).getAmount().toString());
        }


        String date1 = list.get(position).getCreatedAt();
        String[] split = date1.split("T");
        String date = split[0];

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(date, dtf);
        String month_name = dtf2.format(ld);
        holder.date.setText(month_name);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView date, mainText, messageText, price;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.price);
            mainText = itemView.findViewById(R.id.mainText);
            messageText = itemView.findViewById(R.id.messageText);
        }
    }
}
