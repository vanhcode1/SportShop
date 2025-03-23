package com.example.holaorder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnSignUp, btnSignIn, btnLoginNow;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = ((Button) findViewById(R.id.btnSignin));
        btnSignUp = ((Button) findViewById(R.id.btnSignup));
        btnLoginNow = ((Button) findViewById(R.id.btn_loginNow));
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = new Intent(MainActivity.this, SignIn.class);
                startActivity(signin);

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, SignUp.class);
                startActivity(signup);
            }
        });
    }


}