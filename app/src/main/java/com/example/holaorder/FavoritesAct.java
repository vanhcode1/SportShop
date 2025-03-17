package com.example.holaorder;

import androidx.annotation.NonNull;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.holaorder.Model.Food;
import com.example.holaorder.Prevalent.Prevalent;
import com.example.holaorder.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FavoritesAct extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = (RecyclerView) findViewById(R.id.rv_favorite);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        loadFavorite();

    }

    private void loadFavorite() {

        final  DatabaseReference favoriteRef = FirebaseDatabase.getInstance().getReference().child("FavoriteList");

        FirebaseRecyclerOptions<Food> options =
                new FirebaseRecyclerOptions.Builder<Food>()
                        .setQuery(favoriteRef.child("FavoriteView")
                                .child(Prevalent.currentOnlineUser.getPhone())
                                .child("Foods"), Food.class)
                        .build();

        FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter
                = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
                FoodViewHolder holder = new FoodViewHolder(view);
                return holder;

            }

            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                String foodId = getRef(position).getKey();
                holder.tvFoodName.setText(model.getName());
                holder.tvPrice.setText(model.getPrice());
                holder.rate.setRating(Float.parseFloat(model.getRate()));
                Picasso.get().load(model.getImage()).into(holder.imgFood);
                Log.d("Food", holder.imgFood.toString());
                Food clickItem = model;
                Log.d("Food", model.toString());


                //Remove & Edit Product
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "View",
                                        "Remove"
                                };
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(FavoritesAct.this);
                        builder.setTitle("Food Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (i == 0) {
                                    Intent intent = new Intent(FavoritesAct.this, DetailSport.class);
                                    intent.putExtra("FoodId", foodId);
                                    startActivity(intent);
                                }
                                if (i == 1) {
                                    favoriteRef.child("FavoriteView")
                                            .child(Prevalent.currentOnlineUser.getPhone())
                                            .child("Foods")
                                            .child(foodId)
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(FavoritesAct.this, "Item Removed Successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }
            @Override
            public void onDataChanged() {
                super.onDataChanged();
                if (getItemCount() == 0) {
                    Intent intent = new Intent(FavoritesAct.this, ListProduct.class);
                    startActivity(intent);
                    Toast.makeText(FavoritesAct.this, "Your favorite is empty, please place an order", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}


