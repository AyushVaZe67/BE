package com.example.foodxpress;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentDashBoard extends AppCompatActivity {

    TextView goToProfilePage,goToAboutFoodXpress,goToStudentMenuPage;
    CardView adityaMess,swadMess,shitalMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_dash_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //goToProfilePage = findViewById(R.id.goToProfilePage);
        goToAboutFoodXpress = findViewById(R.id.goToAboutFoodXpress);
        adityaMess = findViewById(R.id.adityaMess);
        //goToStudentMenuPage = findViewById(R.id.goToStudentMenuPage);
        swadMess = findViewById(R.id.swadMess);
        shitalMess = findViewById(R.id.shitalMess);

        shitalMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashBoard.this,PlaceOrderPage3.class));
            }
        });

        swadMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashBoard.this,PlaceOrderPage2.class));
            }
        });

//        goToStudentMenuPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(StudentDashBoard.this,StudentMenuPage.class));
//            }
//        });

        goToAboutFoodXpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashBoard.this,AboutFoodXpress.class));
            }
        });
//        goToProfilePage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(StudentDashBoard.this,StudentProfilePage.class));
//            }
//        });
        adityaMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashBoard.this,PlaceOrderPage.class));
            }
        });
    }

    public void accessUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
    }

}