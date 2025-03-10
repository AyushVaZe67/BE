package com.example.foodxpress;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StudentRegistrationPage extends AppCompatActivity {

    //ImageView profilePic;
    EditText etRegEmail1,etRegPass1,etRegName1,etRegConfirmPass1,etRegMobileNo1;
    TextView backToLoginPage;
    Button btnRegisterUser;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_registration_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("User");
        etRegName1 = findViewById(R.id.etRegName1);
        etRegConfirmPass1 = findViewById(R.id.etRegConfirmPassword1);
        etRegMobileNo1 = findViewById(R.id.etRegMobile1);
        btnRegisterUser = findViewById(R.id.btnRegisterUser1);
        etRegEmail1 = findViewById(R.id.etRegEmail1);
        etRegPass1 = findViewById(R.id.etRegPassword1);
        //profilePic = findViewById(R.id.profilePic);
        //profilePic.setAlpha(127);
        backToLoginPage = findViewById(R.id.backToLoginPage);


        backToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentRegistrationPage.this,StudentLoginPage.class));
            }
        });

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etRegName1.getText().toString())){
                    etRegName1.setError("Please Enter Name ! ");
                    etRegName1.requestFocus();
                }else if (TextUtils.isEmpty(etRegEmail1.getText().toString())){
                    etRegEmail1.setError("Please Enter Email ! ");
                    etRegEmail1.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(etRegEmail1.getText().toString()).matches()) {
                    etRegEmail1.setError("Please Enter Valid Email");
                    etRegEmail1.requestFocus();
                }else if (TextUtils.isEmpty(etRegPass1.getText().toString())){
                    etRegPass1.setError("Please Enter Password ! ");
                    etRegPass1.requestFocus();
                }else if (TextUtils.isEmpty(etRegConfirmPass1.getText().toString())){
                    etRegConfirmPass1.setError("Please Enter Confirm Password ! ");
                    etRegConfirmPass1.requestFocus();
                }else if (!TextUtils.equals(etRegConfirmPass1.getText().toString(),etRegPass1.getText().toString())){
                    etRegConfirmPass1.setError("Please Enter Confirm Password ! ");
                    etRegConfirmPass1.requestFocus();
                }else if (TextUtils.isEmpty(etRegMobileNo1.getText().toString())){
                    etRegMobileNo1.setError("Please Enter Phone Number ! ");
                    etRegMobileNo1.requestFocus();
                }else if (etRegMobileNo1.getText().toString().length()!=10) {
                    etRegMobileNo1.setError("Please Enter Valid Phone Number ! ");
                    etRegMobileNo1.requestFocus();
                }else{
                    createUser(etRegEmail1.getText().toString(),etRegPass1.getText().toString());
                }
            }
        });

        /*btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createUser(etRegEmail1.getText().toString(),etRegPass1.getText().toString());
                ////createUser1(etRegEmail1.getText().toString(),etRegPass1.getText().toString());
                //send();
            }
        });
*/
    }

    private void send() {
        if (ContextCompat.checkSelfPermission(StudentRegistrationPage.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            String num = etRegMobileNo1.getText().toString();
            String mess = etRegName1.getText().toString();

            if (!num.isEmpty() && !mess.isEmpty()){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(num,null,mess,null,null);
                Toast.makeText(this, "SMS Sent", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "ENTER Number and Message", Toast.LENGTH_SHORT).show();
            }
        }else{
            //ActivityCompat.requestPermissions(StudentRegistrationPage.this, new String[]{Manifest.permission.SEND_SMS},100);
        }
    }

    private void createUser1(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Toast.makeText(StudentRegistrationPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser account){

        if(account != null){
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,StudentDashBoard.class));

        }else {
            Toast.makeText(this,"You Didnt signed in",Toast.LENGTH_LONG).show();
        }

    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    verifyEmail();

//                    Toast.makeText(RegisteraPage2.this, "Created", Toast.LENGTH_SHORT).show();
                    //sendUserToDatabase();
                    //sendUserToDatabase1();
                    //goToLoginPage();
                    //Toast.makeText(StudentRegistrationPage.this, "REGISTER ZALA", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendUserToDatabase1() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("Email",etRegEmail1.getText().toString());
        map.put("MobileNo",etRegMobileNo1.getText().toString());
        map.put("Name",etRegName1.getText().toString());
        reference.child(etRegMobileNo1.getText().toString()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(StudentRegistrationPage.this, "Registered", Toast.LENGTH_SHORT).show();
                    goToLoginPage();
                    emptyAll();
                }else{
                    Toast.makeText(StudentRegistrationPage.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void emptyAll() {
        etRegName1.setText("");
        etRegEmail1.setText("");
        etRegPass1.setText("");
        etRegConfirmPass1.setText("");
        etRegMobileNo1.setText("");

    }

    private void verifyEmail() {
        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(StudentRegistrationPage.this, "Check your Email", Toast.LENGTH_SHORT).show();
                    sendUserToDatabase1();
                }else{
                    Toast.makeText(StudentRegistrationPage.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

        private void sendUserToDatabase() {
            HashMap<String,Object> map = new HashMap<>();
            //map.put("Name",etRegName1.getText().toString());
            map.put("Email",etRegEmail1.getText().toString());
            map.put("Mobile No",etRegConfirmPass1.getText().toString());

            reference.push().child(etRegEmail1.getText().toString()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(StudentRegistrationPage.this, "ERROR SENDING DATA TO DATABASE", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(StudentRegistrationPage.this, "User Sent to Database", Toast.LENGTH_SHORT).show();
                        goToLoginPage();
                    }
                }
            });
    }

    private void goToLoginPage() {
        startActivity(new Intent(StudentRegistrationPage.this,StudentLoginPage.class));
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            currentUser.reload();
//        }
//    }




}