package com.example.holaorder.ViewHolder;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.holaorder.Interface.ItemClickListener;
import com.example.holaorder.R;
import org.jetbrains.annotations.NotNull;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvCategoryName;
    public ImageView imgCategory;

    private ItemClickListener itemClickListener;
    public CategoryViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        tvCategoryName = ((TextView)itemView.findViewById(R.id.categoryName) );
        imgCategory = ((ImageView) itemView.findViewById(R.id.categoryPic));
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
