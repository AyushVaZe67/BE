package com.example.foodxpress;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class PlaceOrderPage extends AppCompatActivity implements PaymentResultListener {
    
    Button btnPlaceOrder,btnUserTimePlaceOrder,btnMakePayment;
    FirebaseDatabase db;
    DatabaseReference reference,reference1;
    EditText userNamePlaceOrder,userPhonePlaceOrder;
    String strTime;
    String timeString1,timeString;
    ListView menuListView;
    ArrayList<String> list;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_order_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Checkout.preload(getApplicationContext());

        menuListView = findViewById(R.id.menuListView);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,list);
        menuListView.setAdapter(adapter);
        reference1 = FirebaseDatabase.getInstance().getReference().child("UpdateList");

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Orders");
        retrieveData();

        userNamePlaceOrder = findViewById(R.id.userNamePlaceOrder);
        userPhonePlaceOrder = findViewById(R.id.userPhoneNumberPlaceOrder);
        btnUserTimePlaceOrder = findViewById(R.id.btnUserTimePlaceOrder);
        btnUserTimePlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });

        //btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
//        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //sendDataToDB();
//            }
//        });

        btnMakePayment = findViewById(R.id.btnMakePayment);
        btnMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valilatePage();
                //sendDataToDB();
            }
        });
    }

    private void retrieveData() {
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Information info = snapshot1.getValue(Information.class);
                    String txt = info.getItem1() + "\n" + info.getItem2() + "\n" + info.getItem3();
                    list.add(txt);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void valilatePage() {
        if (TextUtils.isEmpty(userNamePlaceOrder.getText().toString().trim())){
            userNamePlaceOrder.setError("Please enter name !");
            userNamePlaceOrder.requestFocus();
        }else if(TextUtils.isEmpty(userPhonePlaceOrder.getText().toString().trim())){
            userPhonePlaceOrder.setError("Please enter mobile number !");
            userPhonePlaceOrder.requestFocus();
        } else if (TextUtils.isEmpty(timeString1)) {
            btnUserTimePlaceOrder.setError("Please enter time !");
            btnUserTimePlaceOrder.requestFocus();
        } else if (userPhonePlaceOrder.getText().toString().trim().length() != 10) {
            userPhonePlaceOrder.setError("Please enter correct phone number");
            userPhonePlaceOrder.requestFocus();
        } else {
            //requestSmsPermission();
            sendDataToDB();
        }
    }

    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(PlaceOrderPage.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            sendDataToDB();
        }else{
            ActivityCompat.requestPermissions(PlaceOrderPage.this, new String[]{Manifest.permission.SEND_SMS},100);
        }
    }

    private void sendDataToDB() {
        
        HashMap<String,Object> map = new HashMap<>();
        map.put("Name",userNamePlaceOrder.getText().toString().trim());
        map.put("PhoneNumber",userPhonePlaceOrder.getText().toString().trim());
        map.put("Time",timeString1);

        reference.child(userPhonePlaceOrder.getText().toString()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(PlaceOrderPage.this, "Redirecting to Payment", Toast.LENGTH_SHORT).show();
                    paymentNow();
                }else{
                    Toast.makeText(PlaceOrderPage.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PlaceOrderPage.this, "Error : " + e, Toast.LENGTH_SHORT).show();
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

            options.put("name", "Aditya Mess");
            options.put("description", "Aditya Mess Pre-Order Payment");
            //options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "6000");//pass amount in currency subunits
            options.put("prefill.email", "adityamess@gmail.com");
            options.put("prefill.contact","9988776655");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }


    }

    private void openTimePicker(){

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.Theme_FoodXpress, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                strTime = String.valueOf(hour + " : " + minute);
                //Toast.makeText(PlaceOrderPage.this, strTime, Toast.LENGTH_SHORT).show();
            }
        }, 0, 30, false);

        timePickerDialog.show();
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
                    Toast.makeText(PlaceOrderPage.this, "You can't place order now !!!", Toast.LENGTH_SHORT).show();
                }
            }
        },Hour,Minute,false);

        timePickerDialog.show();
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PlaceOrderPage.this,StudentDashBoard.class));
        emptyAll();
        //sendSms();
        finish();
    }

    private void sendSms() {
        String num = userPhonePlaceOrder.getText().toString();
        String mess = userNamePlaceOrder.getText().toString();

        if (userPhonePlaceOrder.getText().toString().isEmpty() && userNamePlaceOrder.getText().toString().isEmpty()){
            SmsManager.getDefault().sendTextMessage(num, null, "mess", null,null);
            Toast.makeText(this, "SMS Sent", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "ENTER Number and Message", Toast.LENGTH_SHORT).show();
        }
    }

    private void emptyAll() {
        userNamePlaceOrder.setText("");
        userPhonePlaceOrder.setText("");
        timeString1 = "";
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Error in Payment : " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            sendSms();
        }else{
            Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
        }
    }

}