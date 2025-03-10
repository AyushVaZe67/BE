package com.example.foodxpress;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText etResetPassword;
    Button btnResetPassword;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etResetPassword = findViewById(R.id.etResetPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        progressBar = findViewById(R.id.forgetPasswordProgressBar);

        mAuth = FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etResetPassword.getText().toString().trim())){
                    resetPassword();
                }else{
                    etResetPassword.setError("Please Enter Email !");
                }
            }
        });

    }

    private void resetPassword() {
        progressBar.setVisibility(View.VISIBLE);
        btnResetPassword.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(etResetPassword.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgotPassword.this, "Reset Password link sent to your email", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ForgotPassword.this,StudentLoginPage.class));
                finish();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPassword.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}