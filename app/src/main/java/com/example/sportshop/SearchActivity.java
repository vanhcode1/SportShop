package com.example.sportshop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holaorder.Model.Food;
import com.example.holaorder.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rey.material.widget.ImageButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ImageButton searchBtn;
    Context context;
    private EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;
    private List<Food> foodList;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inputText = findViewById(R.id.searchEditText);
        searchBtn = findViewById(R.id.searchButton);
        searchList = (RecyclerView) findViewById(R.id.searchList);
        GridLayoutManager layoutManagerGrid = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        searchList.setLayoutManager(layoutManagerGrid);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchInput = inputText.getText().toString();
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Foods");

        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(reference.orderByChild("Name").startAt(SearchInput).endAt(SearchInput + "\uf8ff"), Food.class)
                .build();

        FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter =
                new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food food) {
                        String foodId = getRef(position).getKey();
                        holder.tvFoodName.setText(food.getName());
                        Picasso.get().load(food.getImage()).into(holder.imgFood);
                        holder.tvPrice.setText(food.getPrice());
                        holder.rate.setRating(Float.parseFloat(food.getRate()));
                        Food clickItem = food;
                        Log.d("Food", food.toString());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SearchActivity.this, DetailSport.class);
                                intent.putExtra("FoodId", foodId);
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent,false);
                        FoodViewHolder holder = new FoodViewHolder(view);
                        return holder;
                    }
                } ;
        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}