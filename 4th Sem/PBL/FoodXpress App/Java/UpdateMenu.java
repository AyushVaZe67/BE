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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateMenu extends AppCompatActivity {

    EditText updateFoodItem1,updateFoodItem2,updateFoodItem3;
    Button btnUpdateMenu;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("UpdateList");
        
        btnUpdateMenu = findViewById(R.id.btnUpdateMenu);
        updateFoodItem1 = findViewById(R.id.updateFoodItem1);
        updateFoodItem2 = findViewById(R.id.updateFoodItem2);
        updateFoodItem3 = findViewById(R.id.updateFoodItem3);

        btnUpdateMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateToDB();
            }
        });
    }

    private void updateToDB() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("item1",updateFoodItem1.getText().toString());
        map.put("item2",updateFoodItem2.getText().toString());
        map.put("item3",updateFoodItem3.getText().toString());

        reference.child("AdityaMess").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(UpdateMenu.this, "Menu Updated Successfully", Toast.LENGTH_SHORT).show();
                    emptyAll();
                }else{
                    Toast.makeText(UpdateMenu.this, "Error in updating menu", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
    }

    private void emptyAll() {
        updateFoodItem1.setText("");
        updateFoodItem2.setText("");
        updateFoodItem3.setText("");
    }
}