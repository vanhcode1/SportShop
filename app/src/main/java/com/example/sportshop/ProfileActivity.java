package com.example.sportshop;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.sportshop.Common.Common;
import com.example.sportshop.Model.User;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    ImageView imgUser;
    EditText edtEmail;
    EditText edtPhone;
    EditText edtName;
    Button btnEdit;
    TextView tv_Order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

            imgUser = findViewById(R.id.imgUser);
            edtEmail = findViewById(R.id.edtEmail);
            edtPhone = findViewById(R.id.edtPhone);
            edtName = findViewById(R.id.edtName);
            btnEdit = findViewById(R.id.btnEdit);
            tv_Order = findViewById(R.id.tvMyCart);
            User user = Common.currentUser;
            edtEmail.setText(user.getEmail());
            edtPhone.setText(user.getPhone());
            edtName.setText(user.getName());
            Picasso.get().load(user.getImage()).into(imgUser);


        tv_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onClickEdit(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("User");

        String oldPhone = Common.currentUser.getPhone();
        String newPhone = edtPhone.getText().toString();
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();

        // Tạo nút mới với số điện thoại mới
        Map<String, Object> newUserData = new HashMap<>();
        newUserData.put("Name", name);
        newUserData.put("Email", email);
        newUserData.put("Password", Common.currentUser.getPassword());
        newUserData.put("Image", Common.currentUser.getImage());
        userRef.child(newPhone).setValue(newUserData);

        // Sao chép dữ liệu từ nút cũ sang nút mới
        userRef.child(oldPhone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    userRef.child(newPhone).setValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Xóa nút cũ
        userRef.child(oldPhone).removeValue();

        // Cập nhật thông tin người dùng hiện tại
        Common.currentUser.setPhone(newPhone);
        Common.currentUser.setName(name);
        Common.currentUser.setEmail(email);
        Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
    }

}