package com.example.sportshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportshop.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class OrderActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtAddress, edtCity;
    private Button btnOK;
    private TextView tvSub, tvShip, tvTotal;
    private int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        btnOK = findViewById(R.id.btn_OK_OS);
        edtName = findViewById(R.id.tv_Name_OS);
        edtPhone = findViewById(R.id.tv_Phone_OS);
        edtAddress = findViewById(R.id.tv_Address_OS);
        edtCity = findViewById(R.id.tv_City_OS);
        tvSub = findViewById(R.id.tv_Sub_OS);
        tvShip = findViewById(R.id.tv_Ship_OS);
        tvTotal = findViewById(R.id.tv_Total_OS);

        //Price
        int shipping = 5;
        totalPrice = getIntent().getIntExtra("totalPrice", 0);

        String spaceEmpty = "                                          ";
        String spaceEmpty1 = "                                         ";
        String spaceEmpty2 = "                             ";

        tvSub.setText("Subtotal: " + spaceEmpty + totalPrice + "$");
        tvShip.setText("Shipping: " + spaceEmpty1 + "5" + "$");
        tvTotal.setText("TotalPrice:" + spaceEmpty2 + (totalPrice + shipping) + "$");

        //Confirm
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });

    }

    private void Check() {

        if (TextUtils.isEmpty(edtName.getText().toString())) {
            Toast.makeText(this, "Please provide your Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edtPhone.getText().toString())) {
            Toast.makeText(this, "Please provide your Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edtAddress.getText().toString())) {
            Toast.makeText(this, "Please provide your Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edtCity.getText().toString())) {
            Toast.makeText(this, "Please provide your Name", Toast.LENGTH_SHORT).show();
        } else {
            confirmOrder();
        }
    }

    private void confirmOrder() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalPrice", totalPrice);
        ordersMap.put("name", edtName.getText().toString());
        ordersMap.put("phone", edtPhone.getText().toString());
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("address", edtAddress.getText().toString());
        ordersMap.put("city", edtCity.getText().toString());

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseDatabase.getInstance().getReference().child("CartList")
                        .child("CartView")
                        .child(Prevalent.currentOnlineUser.getPhone())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(OrderActivity.this, "Your order has been placed successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(OrderActivity.this, Home.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

}