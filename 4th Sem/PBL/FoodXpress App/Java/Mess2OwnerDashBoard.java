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

public class Mess2OwnerDashBoard extends AppCompatActivity {

    CardView goToShowAllOrders2,goToUpdateMenu2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mess2_owner_dash_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        goToShowAllOrders2 = findViewById(R.id.goToShowAllOrders2);
        goToUpdateMenu2 = findViewById(R.id.goToUpdateMenu2);
        goToShowAllOrders2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mess2OwnerDashBoard.this,ShowAllOrders2.class));
            }
        });

        goToUpdateMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mess2OwnerDashBoard.this,UpdateMenu2.class));
            }
        });
    }
}