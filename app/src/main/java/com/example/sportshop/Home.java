package com.example.sportshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.sportshop.Common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    DatabaseReference table_category;
    DatabaseReference table_product;
    private ConstraintLayout btnAllProduct;

    EditText searchHome;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_category = database.getReference("Category");
        table_product = database.getReference("Products");

        ((TextView) findViewById(R.id.textHello)).setText("Hello, " + Common.currentUser.getName());

        btnAllProduct = (ConstraintLayout) findViewById(R.id.allProduct);

        btnAllProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent product = new Intent(Home.this, ListProduct.class);
                startActivity(product);
            }
        });

        searchHome = findViewById(R.id.searchHome);
        searchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                Intent intent = new Intent(Home.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        DatabaseReference productsRef = database.getReference("Products");

        productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = (int) dataSnapshot.getChildrenCount();
                TextView textView = findViewById(R.id.number_Product);
                textView.setText(count + " Products");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}