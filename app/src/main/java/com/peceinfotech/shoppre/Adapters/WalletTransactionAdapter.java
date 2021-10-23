package com.peceinfotech.shoppre.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.AccountResponse.WalletTransactionResponse;
import com.peceinfotech.shoppre.Models.WalletTransactionDummyModel;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class WalletTransactionAdapter extends RecyclerView.Adapter<WalletTransactionAdapter.viewHolder> {
    Context context;
    List<WalletTransactionResponse> list;

    public WalletTransactionAdapter(Context context, List<WalletTransactionResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallet_transaction_single_layout , parent , false);

        return new viewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.date.setText(list.get(position).getWalletTransactions().get(position).getCreatedAt());
//        holder.image.setImageResource(list.get(position).getImage());
        holder.mainText.setText(list.get(position).getWalletTransactions().get(position).getDescription());
//        if (list.get(position).getMessageText().equals("")){
//            holder.messageText.setVisibility(View.GONE);
//        }
//        else
//            holder.messageText.setText(list.get(position).getMessageText());
        if (list.get(position).getWalletTransactions().get(position).getAmount().toString().startsWith("+")){
            holder.price.setTextColor(Color.parseColor("#17B28F"));
        }
        else holder.price.setTextColor(Color.parseColor("#EF5261"));

        holder.price.setText(list.get(position).getWalletTransactions().get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView date , mainText , messageText , price;
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
