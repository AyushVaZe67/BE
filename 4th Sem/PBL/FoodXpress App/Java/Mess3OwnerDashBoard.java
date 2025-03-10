package com.example.foodxpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Mess3OwnerDashBoard extends AppCompatActivity {

    CardView goToShowAllOrders3,goToUpdateMenu3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mess3_owner_dash_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        goToShowAllOrders3 = findViewById(R.id.goToShowAllOrders3);
        goToUpdateMenu3 = findViewById(R.id.goToUpdateMenu3);
        goToShowAllOrders3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mess3OwnerDashBoard.this,ShowAllOrders3.class));
            }
        });

        goToUpdateMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mess3OwnerDashBoard.this,UpdateMenu3.class));
            }
        });
    }
}