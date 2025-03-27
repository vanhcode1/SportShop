package com.example.sportshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.sportshop.Model.Product;
import com.example.sportshop.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rey.material.widget.ImageButton;
import com.squareup.picasso.Picasso;

public class SearchActivity extends AppCompatActivity {

    private ImageButton searchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;

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

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(reference.orderByChild("Name").startAt(SearchInput).endAt(SearchInput + "\uf8ff"), Product.class)
                .build();

        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product product) {
                        String productId = getRef(position).getKey();
                        holder.tvProductName.setText(product.getName());
                        Picasso.get().load(product.getImage()).into(holder.imgProduct);
                        holder.tvPrice.setText(product.getPrice());
                        holder.rate.setRating(Float.parseFloat(product.getRate()));



                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SearchActivity.this, DetailSport.class);
                                intent.putExtra("ProductId", productId);
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product, parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                } ;
        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}