package com.example.holaorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.holaorder.Model.Cart;
import com.example.holaorder.Prevalent.Prevalent;
import com.example.holaorder.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button OrderBtn ;
    private TextView txtTotalAmount;
    DatabaseReference table_cart;
    private int totalPrice = 0;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
//        table_cart = database.getReference("CartList")
//                .child("CartView")
//                .child(Prevalent.currentOnlineUser.getPhone())
//                .child("Foods");

        recyclerView = (RecyclerView) findViewById(R.id.rv_cart);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);


        loadCart();

        OrderBtn = (Button) findViewById(R.id.btn_order_C);
//        txtTotalAmount = (TextView) findViewById(R.id.tv_total_C);


    }

    private void loadCart() {

        final  DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("CartView")
                                .child(Prevalent.currentOnlineUser.getPhone())
                                .child("Foods"), Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;

            }

            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                String foodId = getRef(position).getKey();
                Cart clickItem = model;
                Log.d("My App", model.toString());

                Picasso.get().load(model.getImage()).into(holder.imgFood);
                holder.tvFoodName.setText(model.getName());
                holder.tvPrice.setText("Price: " + model.getPrice() + "$");
                holder.tvQuantity.setText("Quantity: " + model.getQuantity());


                //TotalPrice
                int totalPriceOfPFood = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                totalPrice = totalPrice + totalPriceOfPFood;

                OrderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                        intent.putExtra("totalPriceOfPFood",totalPriceOfPFood);
                        intent.putExtra("totalPrice", totalPrice);
                        startActivity(intent);
                    }
                });

                //Remove & Edit Product
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (i == 0) {
                                    Intent intent = new Intent(CartActivity.this, DetailSport.class);
                                    intent.putExtra("FoodId", foodId);
                                    startActivity(intent);
                                }
                                if (i == 1) {
                                    cartListRef.child("CartView")
                                            .child(Prevalent.currentOnlineUser.getPhone())
                                            .child("Foods")
                                            .child(foodId)
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(CartActivity.this, "Item Removed Successfully", Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(CartActivity.this, ListProduct.class);
                    startActivity(intent);
                    Toast.makeText(CartActivity.this, "Your cart is empty, please place an order", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
