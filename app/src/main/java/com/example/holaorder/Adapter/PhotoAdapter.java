package com.example.holaorder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.holaorder.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends PagerAdapter {
    private Context context;
    private List<Integer>  listImage;
    public PhotoAdapter(Context context){
        this.context=context;
        listImage=new ArrayList<>();
        listImage.add(R.drawable.food_combo);
        listImage.add(R.drawable.combo_food_t);
        listImage.add(R.drawable.combo_food_tg);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo,container,false);
        ImageView imageView=view.findViewById(R.id.image);
        imageView.setBackgroundResource(listImage.get(position));
        return view;
    }

    @Override
    public int getCount() {
        return listImage.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
