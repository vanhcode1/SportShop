package com.example.sportshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportshop.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    private EditText etName, etPhone, etPassword;
    private Button btnRegister, btnLoginNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        etName = findViewById(R.id.edt_Name);
        etPhone = findViewById(R.id.edt_phone);
        etPassword = findViewById(R.id.edt_Password);
        btnRegister = findViewById(R.id.btn_signUp);
        btnLoginNow = findViewById(R.id.btn_loginNow);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference data_User = database.getReference("User");


        btnLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginNow = new Intent(SignUp.this, SignIn.class);
                startActivity(loginNow);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                final String name = etName.getText().toString();
                final String phone = etPhone.getText().toString();
                final String password = etPassword.getText().toString();

                data_User.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(phone).exists()) {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Phone Number already registered",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            User user = new User(name, password);
                            data_User.child(phone).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                mDialog.dismiss();
                                                Toast.makeText(SignUp.this, "Sign up successfully!",
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SignUp.this, SignIn.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                mDialog.dismiss();
                                                Toast.makeText(SignUp.this, "Sign up failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        mDialog.dismiss();
                        Toast.makeText(SignUp.this, "Error: " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

