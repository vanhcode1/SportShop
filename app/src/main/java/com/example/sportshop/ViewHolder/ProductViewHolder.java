package com.example.sportshop.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sportshop.Interface.ItemClickListener;
import com.example.sportshop.R;
import org.jetbrains.annotations.NotNull;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvProductName;
    public TextView tvPrice;
    public ImageView imgProduct;
    public RatingBar rate;
    public Button btn;
    private ItemClickListener itemClickListener;

    public ProductViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        tvProductName = itemView.findViewById(R.id.title);
        tvPrice = itemView.findViewById(R.id.fee);
        imgProduct = itemView.findViewById(R.id.pic);
        rate = itemView.findViewById(R.id.ratingBar1);
        btn= itemView.findViewById(R.id.addBtn);
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
