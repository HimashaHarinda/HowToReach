package com.example.sahan.howtoreach;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RestaurantPlanActivity extends AppCompatActivity {


    private String trip_key = null;
    private String plan_key = null;

    private EditText restname;
    private EditText restVenue;
    private EditText restDate;
    private Button restsaveBTN;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private DatabaseReference howtoreachRestaurant;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_plan);
        getSupportActionBar().setTitle("Restaurant Plan Details");

        trip_key = getIntent().getExtras().getString("trip_id");
        plan_key = getIntent().getExtras().getString("plan_id");

        auth = FirebaseAuth.getInstance();
        howtoreachRestaurant = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("trips").child(trip_key).child("plans").child(plan_key).child("restaurant");

        restname = (EditText)findViewById(R.id.restaurantName);
        restVenue = (EditText)findViewById(R.id.restaurantVenue);
        restDate = (EditText)findViewById(R.id.restaurantDate);
        restsaveBTN = (Button) findViewById(R.id.restaurantDetailsBTN);

        howtoreachRestaurant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                restname.setText((String)dataSnapshot.child("restName").getValue());
                restVenue.setText((String)dataSnapshot.child("venue").getValue());
                restDate.setText((String)dataSnapshot.child("restDate").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

        restDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fromDatePickerDialog.show();
                return false;
            }
        });


        restsaveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rName = restname.getText().toString();
                String rvenue = restVenue.getText().toString();
                String rdate = restDate.getText().toString();

                Restaurant newRes = new Restaurant();
                newRes.setRestName(rName);
                newRes.setVenue(rvenue);
                newRes.setRestDate(rdate);

                howtoreachRestaurant.setValue(newRes);

                Toast.makeText(RestaurantPlanActivity.this,"Restaurant Details saved successfuly!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        final Calendar newDate = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                newDate.set(year, monthOfYear, dayOfMonth);
                restDate.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
