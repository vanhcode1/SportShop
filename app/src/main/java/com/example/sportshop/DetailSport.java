package com.example.sportshop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportshop.Model.Product;
import com.example.sportshop.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class DetailSport extends AppCompatActivity {
    private ImageView fImg;
    private Button btnDecrease, btnIncrease, addToCartBtn;
    private EditText edtQuantity;
    private TextView fName, fPrice, fDes;
    private RatingBar fRate;
    private String sportId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sport);

        sportId = getIntent().getStringExtra("ProductId");

        addToCartBtn = findViewById(R.id.addToCart_Dt);
        btnDecrease = findViewById(R.id.btn_decrease);
        btnIncrease = findViewById(R.id.btn_increase);
        edtQuantity = findViewById(R.id.edt_quantity);
        fImg = findViewById(R.id.img_fDetail);
        fName = findViewById(R.id.txtProductName);
        fPrice = findViewById(R.id.price);
        fDes = findViewById(R.id.txtDescription);
        fRate = findViewById(R.id.ratingBar_Detail);

        getProductDetails(sportId);

        btnDecrease.setOnClickListener(v -> {
            int quantity = Integer.parseInt(edtQuantity.getText().toString());
            if (quantity > 1) {
                quantity--;
                edtQuantity.setText(String.valueOf(quantity));
            }
        });

        btnIncrease.setOnClickListener(v -> {
            int quantity = Integer.parseInt(edtQuantity.getText().toString());
            quantity++;
            edtQuantity.setText(String.valueOf(quantity));
        });

        addToCartBtn.setOnClickListener(v -> addToCartList());
    }

    private void addToCartList() {

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("ProductId", sportId);
        cartMap.put("name", "");
        cartMap.put("price", "");
        cartMap.put("quantity", edtQuantity.getText().toString());
        cartMap.put("Image", "");

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products").child(sportId);
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    cartMap.put("name", product.getName());
                    cartMap.put("price", product.getPrice());
                    cartMap.put("Image", product.getImage());

                    cartListRef.child("CartView")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .child("Products")
                            .child(sportId)
                            .updateChildren(cartMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(DetailSport.this, "Add to cart successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DetailSport.this, CartActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getProductDetails(String productId) {
        DatabaseReference productDetailsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productDetailsRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Product product = snapshot.getValue(Product.class);
                    fName.setText(product.getName());
                    fDes.setText(product.getDescription());
                    fPrice.setText(product.getPrice() + "$");
                    Picasso.get().load(product.getImage()).into(fImg);
                    fRate.setRating(Float.parseFloat(product.getRate()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
