package com.example.foodxpress;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PlaceOrderPage2 extends AppCompatActivity implements PaymentResultListener {

    Button btnPlaceOrder2,btnUserTimePlaceOrder2,btnMakePayment2;
    FirebaseDatabase db2;
    DatabaseReference reference2,reference3;
    EditText userNamePlaceOrder2,userPhonePlaceOrder2;
    String strTime;
    String timeString1,timeString;
    ListView menuListView2;
    ArrayList<String> list2;
    ArrayAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_order_page2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Checkout.preload(getApplicationContext());

        db2 = FirebaseDatabase.getInstance();
        reference2 = db2.getReference("Orders2");

        menuListView2 = findViewById(R.id.menuListView2);
        list2 = new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(this,R.layout.list_item,list2);
        menuListView2.setAdapter(adapter2);
        reference3 = FirebaseDatabase.getInstance().getReference().child("UpdateList2");
        retrieveData();

        userNamePlaceOrder2 = findViewById(R.id.userNamePlaceOrder2);
        userPhonePlaceOrder2 = findViewById(R.id.userPhoneNumberPlaceOrder2);
        btnUserTimePlaceOrder2 = findViewById(R.id.btnUserTimePlaceOrder2);

        btnMakePayment2 = findViewById(R.id.btnMakePayment2);
        btnMakePayment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valilatePage();
                //sendDataToDB();
            }
        });

        btnUserTimePlaceOrder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });

    }

    private void retrieveData() {
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Information info = snapshot1.getValue(Information.class);
                    String txt = info.getItem1() + "\n" + info.getItem2() + "\n" + info.getItem3();
                    list2.add(txt);
                }
                adapter2.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void valilatePage() {
        if (TextUtils.isEmpty(userNamePlaceOrder2.getText().toString().trim())){
            userNamePlaceOrder2.setError("Please enter name !");
            userNamePlaceOrder2.requestFocus();
        }else if(TextUtils.isEmpty(userPhonePlaceOrder2.getText().toString().trim())){
            userPhonePlaceOrder2.setError("Please enter mobile number !");
            userPhonePlaceOrder2.requestFocus();
        } else if (TextUtils.isEmpty(timeString1)) {
            btnUserTimePlaceOrder2.setError("Please enter time !");
            btnUserTimePlaceOrder2.requestFocus();
        } else if (userPhonePlaceOrder2.getText().toString().trim().length() != 10) {
            userPhonePlaceOrder2.setError("Please enter correct phone number");
            userPhonePlaceOrder2.requestFocus();
        } else {
            sendDataToDB();
        }
    }

    private void sendDataToDB() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("Name",userNamePlaceOrder2.getText().toString());
        map.put("PhoneNumber",userPhonePlaceOrder2.getText().toString());
        map.put("Time",timeString1);

        reference2.child(userPhonePlaceOrder2.getText().toString()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(PlaceOrderPage2.this, "Redirecting to Payment", Toast.LENGTH_SHORT).show();
                    paymentNow();
                }else{
                    Toast.makeText(PlaceOrderPage2.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PlaceOrderPage2.this, "Error : " + e, Toast.LENGTH_SHORT).show();
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

            options.put("name", "Swad Mess");
            options.put("description", "Pre-Order Payment");
            //options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "6000");//pass amount in currency subunits
            options.put("prefill.email", "swadmess@gmail.com");
            options.put("prefill.contact","9888776655");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
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
                    Toast.makeText(PlaceOrderPage2.this, "You can't place order now !!!", Toast.LENGTH_SHORT).show();
                }
            }
        },Hour,Minute,false);

        timePickerDialog.show();
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PlaceOrderPage2.this,StudentDashBoard.class));
        emptyAll();
        finish();
    }

    private void emptyAll() {
        userNamePlaceOrder2.setText("");
        userPhonePlaceOrder2.setText("");
        timeString1 = "";
    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}