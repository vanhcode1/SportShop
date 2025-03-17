package com.example.holaorder.Sellers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.holaorder.R;

public class SellerCategory extends AppCompatActivity {
    private ImageView drink, rice, cuon, fast;
    private ImageView noodle, soup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_category);

        drink = (ImageView) findViewById(R.id.iv_drink);
        rice = (ImageView) findViewById(R.id.iv_rice);
        cuon = (ImageView) findViewById(R.id.iv_cuon);
        fast = (ImageView) findViewById(R.id.iv_fast);

        noodle = (ImageView) findViewById(R.id.iv_noodle);
        soup = (ImageView) findViewById(R.id.iv_soup);


        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewFood.class);
                intent.putExtra("category", "Drink");
                startActivity(intent);
            }
        });

        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewFood.class);
                intent.putExtra("category", "Rice");
                startActivity(intent);
            }
        });
        cuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewFood.class);
                intent.putExtra("category", "Cuốn");
                startActivity(intent);
            }
        });
        fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewFood.class);
                intent.putExtra("category", "Fast");
                startActivity(intent);
            }
        });
        noodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewFood.class);
                intent.putExtra("category", "Noodle");
                startActivity(intent);
            }
        });
        soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewFood.class);
                intent.putExtra("category", "Soup");
                startActivity(intent);
            }
        });
    }
}