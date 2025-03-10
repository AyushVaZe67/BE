package com.example.foodxpress;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PlaceOrderPage3 extends AppCompatActivity {

    Button btnPlaceOrder3,btnUserTimePlaceOrder3,btnMakePayment3;
    FirebaseDatabase db3;
    DatabaseReference reference3,reference4;
    EditText userNamePlaceOrder3,userPhonePlaceOrder3;
    String strTime;
    String timeString1,timeString;
    ListView menuListView3;
    ArrayList<String> list3;
    ArrayAdapter adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_order_page3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Checkout.preload(getApplicationContext());

        menuListView3 = findViewById(R.id.menuListView3);
        list3 = new ArrayList<>();
        adapter3 = new ArrayAdapter<String>(this,R.layout.list_item,list3);
        menuListView3.setAdapter(adapter3);
        reference4 = FirebaseDatabase.getInstance().getReference().child("UpdateList3");


        db3 = FirebaseDatabase.getInstance();
        reference3 = db3.getReference("Orders3");
        retrieveData();

        userNamePlaceOrder3 = findViewById(R.id.userNamePlaceOrder3);
        userPhonePlaceOrder3 = findViewById(R.id.userPhoneNumberPlaceOrder3);
        btnUserTimePlaceOrder3 = findViewById(R.id.btnUserTimePlaceOrder3);

        btnUserTimePlaceOrder3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });

        btnMakePayment3 = findViewById(R.id.btnMakePayment3);
        btnMakePayment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valilatePage();
                //sendDataToDB();
            }
        });

    }

    private void retrieveData() {
        reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Information info = snapshot1.getValue(Information.class);
                    String txt = info.getItem1() + "\n" + info.getItem2() + "\n" + info.getItem3();
                    list3.add(txt);
                }
                adapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void handleTimeButton() {
        Calendar calendar = Calendar.getInstance();
        int Hour = calendar.get(Calendar.HOUR);
        int Minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                if ((hour >= 19 && hour <= 22) || (hour >= 12 && hour <= 14) ){
                    timeString = hour + ":" + minute;
                    timeString1 = "Selected Time : " + hour + ":" + minute;
                    Toast.makeText(getApplicationContext(), timeString1, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PlaceOrderPage3.this, "You can't place order now !!!", Toast.LENGTH_SHORT).show();
                }
            }
        },Hour,Minute,false);

        timePickerDialog.show();
    }

    private void valilatePage() {
        if (TextUtils.isEmpty(userNamePlaceOrder3.getText().toString().trim())){
            userNamePlaceOrder3.setError("Please enter name !");
            userNamePlaceOrder3.requestFocus();
        }else if(TextUtils.isEmpty(userPhonePlaceOrder3.getText().toString().trim())){
            userPhonePlaceOrder3.setError("Please enter mobile number !");
            userPhonePlaceOrder3.requestFocus();
        } else if (TextUtils.isEmpty(timeString1)) {
            btnUserTimePlaceOrder3.setError("Please enter time !");
            btnUserTimePlaceOrder3.requestFocus();
        } else if (userPhonePlaceOrder3.getText().toString().trim().length() != 10) {
            userPhonePlaceOrder3.setError("Please enter correct phone number");
            userPhonePlaceOrder3.requestFocus();
        } else {
            sendDataToDB();
        }
    }

    private void sendDataToDB() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("Name",userNamePlaceOrder3.getText().toString());
        map.put("PhoneNumber",userPhonePlaceOrder3.getText().toString());
        map.put("Time",timeString1);

        reference3.child(userPhonePlaceOrder3.getText().toString()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(PlaceOrderPage3.this, "Redirecting to Payment", Toast.LENGTH_SHORT).show();
                    paymentNow();
                }else{
                    Toast.makeText(PlaceOrderPage3.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PlaceOrderPage3.this, "Error : " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void paymentNow() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_FRXsWTMSu6on5g");
        checkout.setImage(R.drawable.padloack);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Shital Mess");
            options.put("description", "Pre-Order Payment");
            //options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "6000");//pass amount in currency subunits
            options.put("prefill.email", "shitalmess@gmail.com");
            options.put("prefill.contact","9788776655");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }
}