package com.example.sportshop.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportshop.Interface.ItemClickListener;
import com.example.sportshop.R;

import org.jetbrains.annotations.NotNull;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView imgProduct;
    public TextView tvProductName;
    public TextView tvPrice;
    public TextView tvQuantity;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        imgProduct = itemView.findViewById(R.id.img_C);
        tvProductName = itemView.findViewById(R.id.nameProduct_C);
        tvPrice = itemView.findViewById(R.id.price_C);
        tvQuantity = itemView.findViewById(R.id.quantity_C);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}