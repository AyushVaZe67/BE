package com.example.foodxpress;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteRecord extends AppCompatActivity {

    Button btnDeleteRecord;
    EditText deleteRecord;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_record);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        deleteRecord = findViewById(R.id.deleteRecord);
        btnDeleteRecord = findViewById(R.id.btnDeleteRecord);
        reference = FirebaseDatabase.getInstance().getReference().child("Orders");

        btnDeleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecord();
            }
        });

    }

    private void deleteRecord() {
        reference.child(deleteRecord.getText().toString()).removeValue();
        Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
        deleteRecord.setText("");
    }
}