package com.example.foodxpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MessOwnerDashBoard extends AppCompatActivity {

    CardView goToShowAllOrders,goToUpdateMenu;
    TextView goToDeleteRecordPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mess_owner_dash_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        goToShowAllOrders = findViewById(R.id.goToShowAllOrders);
        goToUpdateMenu = findViewById(R.id.goToUpdateMenu);
        goToDeleteRecordPage = findViewById(R.id.goToDeleteRecordPage);

        goToDeleteRecordPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessOwnerDashBoard.this,DeleteRecord.class));
            }
        });
        goToShowAllOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessOwnerDashBoard.this,ShowAllOrders.class));
            }
        });

        goToUpdateMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessOwnerDashBoard.this,UpdateMenu.class));
            }
        });

    }
}