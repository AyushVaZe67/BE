package com.example.foodxpress;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentLoginPage extends AppCompatActivity {

    TextView goToRegistrationPage,goToMessOwnerLoginPage,goToForgotPassword;
    EditText email,password;
    Button btnLoginUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        goToRegistrationPage = findViewById(R.id.goToRegistrationPage);
        goToMessOwnerLoginPage = findViewById(R.id.goToMessOwnerLogin);
        goToForgotPassword = findViewById(R.id.goToForgotPassword);
        btnLoginUser = findViewById(R.id.btnLoginUser);
        email = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        mAuth = FirebaseAuth.getInstance();

        goToForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentLoginPage.this,ForgotPassword.class));
            }
        });



        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setError("Please Enter Email");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Please Enter Password");
                    password.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("Please Enter Valid Email");
                    email.requestFocus();
                } else if (password.getText().toString().length() < 6) {
                    password.setError("Please Enter Correct Password");
                    password.requestFocus();
                } else {
                    startActivity(new Intent(StudentLoginPage.this, StudentDashBoard.class));
                    //loginUser(email.getText().toString(),password.getText().toString());
                    //loginUser1(email.getText().toString(),password.getText().toString());
                }
            }
        });

        goToMessOwnerLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentLoginPage.this,MessOwnerLoginPage.class));
            }
        });

        goToRegistrationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLoginPage.this,StudentRegistrationPage.class);
                startActivity(intent);
            }
        });

    }

    private void loginUser1(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Toast.makeText(StudentLoginPage.this, "Logged In successful", Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            Toast.makeText(StudentLoginPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser account){

        if(account != null){
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,StudentDashBoard.class));

        }else {
            Toast.makeText(this,"You didn't signed in",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    private void loginUser(String email1, String password1) {
        mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (mAuth.getCurrentUser().isEmailVerified()){
                        //progressBar.setVisibility(View.VISIBLE);
                        emptyAll();
                        Toast.makeText(StudentLoginPage.this, "LOGGED IN SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(StudentLoginPage.this,StudentDashBoard.class));
                    }else{
                        Toast.makeText(StudentLoginPage.this, "Email Not Verified", Toast.LENGTH_SHORT).show();
                    }
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    if (user.isEmailVerified()){
//
//                    }else{
//                        user.sendEmailVerification();
//                        Toast.makeText(LoginPage.this, "Check your Email to verify our account", Toast.LENGTH_SHORT).show();
//                    }
                }else{
                    Toast.makeText(StudentLoginPage.this, "ACCOUNT NOT FOUND WITH THIS EMAIL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void emptyAll() {
        email.setText("");
        password.setText("");
    }
}