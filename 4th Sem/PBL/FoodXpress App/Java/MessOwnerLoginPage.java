package com.example.foodxpress;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MessOwnerLoginPage extends AppCompatActivity {

    AppCompatButton btnMessOwnerLogin,goToMessOwnerRegisterPage;
    EditText etMessOwnerEmail,etMessOwnerPassword;
    FirebaseAuth mAuth;
    String strEmail,strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mess_owner_login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnMessOwnerLogin = findViewById(R.id.btnMessOwnerLogin);
        etMessOwnerEmail = findViewById(R.id.etMessOwnerEmail);
        etMessOwnerPassword = findViewById(R.id.etMessOwnerPassword);
        //goToMessOwnerRegisterPage = findViewById(R.id.goToMessOwnerRegister);

        strEmail = etMessOwnerEmail.getText().toString().trim();
        strPassword = etMessOwnerPassword.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();

        btnMessOwnerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MessOwnerLoginPage.this,MessOwnerDashBoard.class));
                //loginMessOwner(etMessOwnerEmail.getText().toString().trim(),etMessOwnerPassword.getText().toString().trim());
                validatePage();
                //directLogin();
            }
        });

//        goToMessOwnerRegisterPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MessOwnerLoginPage.this,MessOwnerRegisterPage.class));
//            }
//        });

    }

    private void validatePage() {
        if (TextUtils.isEmpty(etMessOwnerEmail.getText().toString())){
            etMessOwnerEmail.setError("Please enter username !");
            etMessOwnerEmail.requestFocus();
        } else if (TextUtils.isEmpty(etMessOwnerPassword.getText().toString())) {
            etMessOwnerPassword.setError("Please enter password !");
            etMessOwnerPassword.requestFocus();
        } else {
            directLogin();
            //loginMessOwner(etMessOwnerEmail.getText().toString().trim(),etMessOwnerPassword.getText().toString().trim());
        }
    }

    private void directLogin() {
        if(etMessOwnerEmail.getText().toString().trim().equals("Mess1")&&etMessOwnerPassword.getText().toString().trim().equals("demo1")) {
            Toast.makeText(getApplicationContext(), "Welcome,Aditya Mess", Toast.LENGTH_LONG).show();
            startActivity(new Intent(MessOwnerLoginPage.this, MessOwnerDashBoard.class));
            emptyAll();
        }else if(etMessOwnerEmail.getText().toString().trim().equals("Mess2")&&etMessOwnerPassword.getText().toString().trim().equals("demo2")){
            Toast.makeText(getApplicationContext(), "Welcome,Swad Mess", Toast.LENGTH_LONG).show();
            startActivity(new Intent(MessOwnerLoginPage.this, Mess2OwnerDashBoard.class));
            emptyAll();
        }else if(etMessOwnerEmail.getText().toString().trim().equals("Mess3")&&etMessOwnerPassword.getText().toString().trim().equals("demo3")){
            Toast.makeText(getApplicationContext(), "Welcome,Shital Mess", Toast.LENGTH_LONG).show();
            startActivity(new Intent(MessOwnerLoginPage.this, Mess3OwnerDashBoard.class));
            emptyAll();
        }else{
            Toast.makeText(this, "Wrong Credentials !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void emptyAll() {
        etMessOwnerEmail.setText("");
        etMessOwnerPassword.setText("");
    }

    private void loginMessOwner(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()){
                         Toast.makeText(MessOwnerLoginPage.this, "Success", Toast.LENGTH_SHORT).show();
                         goToMessOwnerDashBoard();
                     }else {
                         Toast.makeText(MessOwnerLoginPage.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MessOwnerLoginPage.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToMessOwnerDashBoard() {
        startActivity(new Intent(MessOwnerLoginPage.this,MessOwnerDashBoard.class));
    }
}