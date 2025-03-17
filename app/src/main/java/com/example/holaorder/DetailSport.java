package com.example.holaorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.holaorder.Model.Food;
import com.example.holaorder.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        sportId = getIntent().getStringExtra("FoodId");

        addToCartBtn = findViewById(R.id.addToCart_Dt);
        btnDecrease = findViewById(R.id.btn_decrease);
        btnIncrease = findViewById(R.id.btn_increase);
        edtQuantity = findViewById(R.id.edt_quantity);
        fImg = findViewById(R.id.img_fDetail);
        fName = findViewById(R.id.txtFoodName);
        fPrice = findViewById(R.id.price);
        fDes = findViewById(R.id.txtDescription);
        fRate = findViewById(R.id.ratingBar_Detail);

        getFoodDetails(sportId);

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
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("FoodId", sportId);
        cartMap.put("name", "");
        cartMap.put("price", "");
        cartMap.put("rate", 0);
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", edtQuantity.getText().toString());
        cartMap.put("discount", "");
        cartMap.put("Image", "");

        DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference().child("Foods").child(sportId);
        foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Food food = dataSnapshot.getValue(Food.class);
                    cartMap.put("name", food.getName());
                    cartMap.put("price", food.getPrice());
                    cartMap.put("rate", food.getRate());
                    cartMap.put("Image", food.getImage());

                    cartListRef.child("CartView")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .child("Foods")
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

    private void getFoodDetails(String foodId) {
        DatabaseReference foodDetailsRef = FirebaseDatabase.getInstance().getReference().child("Foods");
        foodDetailsRef.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Food food = snapshot.getValue(Food.class);
                    fName.setText(food.getName());
                    fDes.setText(food.getDescription());
                    fPrice.setText(food.getPrice() + "$");
                    Picasso.get().load(food.getImage()).into(fImg);
                    fRate.setRating(Float.parseFloat(food.getRate()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
