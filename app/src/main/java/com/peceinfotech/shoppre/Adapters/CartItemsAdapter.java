package com.peceinfotech.shoppre.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.DeleteOrderResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.Order;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.viewHolder> {

    Order productItemList;
    Context context;
    SharedPrefManager sharedPrefManager;

    public CartItemsAdapter(Order productItemList, Context context) {
        this.productItemList = productItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        sharedPrefManager = new SharedPrefManager(context.getApplicationContext());

        holder.productSerialNo.setText(String.valueOf(position + 1));
        holder.productName.setText(productItemList.getOrderItems().get(position).getName());

        holder.productQuantity.setText("(" + String.valueOf(productItemList.getOrderItems().get(position).getQuantity()) + ")");
        holder.productPrice.setText("₹ " + String.valueOf(productItemList.getOrderItems().get(position).getPriceAmount()));

        holder.productDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialog.showLoadingDialog(context, "");
                callDeleteApi(position, productItemList.getId(), productItemList.getOrderItems().get(position).getId());

            }
        });

    }

    private void callDeleteApi(int position, Integer id, Integer itemId) {

//        Toast.makeText(context.getApplicationContext(), String.valueOf(itemId ), Toast.LENGTH_SHORT).show();
        Call<List<DeleteOrderResponse>> call = RetrofitClient3
                .getInstance3()
                .getAppApi().deleteOrder("Bearer " + sharedPrefManager.getBearerToken(),
                        id, itemId);
        call.enqueue(new Callback<List<DeleteOrderResponse>>() {
            @Override
            public void onResponse(Call<List<DeleteOrderResponse>> call, Response<List<DeleteOrderResponse>> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    productItemList.getOrderItems().remove(position);
                    notifyItemRemoved(position);

                    notifyDataSetChanged();
                    notifyItemRangeChanged(position, productItemList.getOrderItems().size());
                    Toast.makeText(context.getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    callRefreshTokenApi(position, id, itemId);
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(context.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DeleteOrderResponse>> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(context.getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productItemList.getOrderItems().size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView productSerialNo, productName, productQuantity, productPrice;
        ImageView productEditButton, productDeleteBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            productSerialNo = itemView.findViewById(R.id.productSerialNo);
            productName = itemView.findViewById(R.id.productName);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            productPrice = itemView.findViewById(R.id.productPrice);
            productEditButton = itemView.findViewById(R.id.productEditButton);
            productDeleteBtn = itemView.findViewById(R.id.productDeleteBtn);

        }
    }

    private void callRefreshTokenApi(int position, Integer id, Integer itemId) {
        Call<RefreshTokenResponse> call = RetrofitClient
                .getInstance().getApi()
                .getRefreshToken(sharedPrefManager.getRefreshToken());
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    callDeleteApi(position, id, itemId);
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(context.getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(context.getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}