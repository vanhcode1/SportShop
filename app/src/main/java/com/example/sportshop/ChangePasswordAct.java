package com.example.sportshop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportshop.Common.Common;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordAct extends AppCompatActivity {
    EditText edtOld;
    EditText edtNew;
    EditText edtNewCF;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        edtOld = findViewById(R.id.edtOld);
        edtNew = findViewById(R.id.edtNew);
        edtNewCF = findViewById(R.id.edtNewCF);

    }

    public void onClickChange(View view) {
        String old = edtOld.getText().toString();
        if (!old.isEmpty() && !old.equals(Common.currentUser.getPassword())) {
            Toast.makeText(this, "Old password is not correct!", Toast.LENGTH_SHORT).show();
        }

        String newP = edtNew.getText().toString();
        String newPCF = edtNewCF.getText().toString();
        if (newP.isEmpty() || newPCF.isEmpty() || !newP.equals(newPCF)) {
            Toast.makeText(this, "Confirm password is not correct!", Toast.LENGTH_SHORT).show();
        }
        else{
            String currentUserId = Common.currentUser.getPhone();

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(currentUserId);
            userRef.child("password").setValue(newP)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Password changed successfully!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to change password: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}