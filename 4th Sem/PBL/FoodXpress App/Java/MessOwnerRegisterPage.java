package com.example.foodxpress;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MessOwnerRegisterPage extends AppCompatActivity {

    EditText etRegEmailMO,etRegPasswordMO;
    Button btnMessOwnerRegister;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mess_owner_register_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    
        etRegEmailMO = findViewById(R.id.etMessOwnerEmailReg);
        etRegPasswordMO = findViewById(R.id.etMessOwnerPasswordReg);
        btnMessOwnerRegister = findViewById(R.id.btnMessOwnerRegister);
        
        mAuth = FirebaseAuth.getInstance();
        
        btnMessOwnerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerMessOwner(etRegEmailMO.getText().toString().trim(),etRegPasswordMO.getText().toString().trim());
            }
        });
        
    
    }

    private void registerMessOwner(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MessOwnerRegisterPage.this, "Registered", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MessOwnerRegisterPage.this, "Error in registration mess owner", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}