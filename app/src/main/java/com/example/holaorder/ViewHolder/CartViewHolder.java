package com.example.holaorder.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaorder.Interface.ItemClickListener;
import com.example.holaorder.R;

import org.jetbrains.annotations.NotNull;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView imgFood;
    public TextView tvFoodName;
    public TextView tvPrice;
    public TextView tvQuantity;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        imgFood = itemView.findViewById(R.id.img_C);
        tvFoodName = itemView.findViewById(R.id.nameFood_C);
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