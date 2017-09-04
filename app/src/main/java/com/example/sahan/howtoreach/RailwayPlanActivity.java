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

public class RailwayPlanActivity extends AppCompatActivity {

    private String trip_key = null;
    private String plan_key = null;

    private EditText fromStation;
    private EditText toStation;
    private EditText railwaydate;
    private Button addrailwaybtn;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private DatabaseReference howtoreachRailway;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway_plan);
        getSupportActionBar().setTitle("Railway Plan Details");

        trip_key = getIntent().getExtras().getString("trip_id");
        plan_key = getIntent().getExtras().getString("plan_id");

        auth = FirebaseAuth.getInstance();
        howtoreachRailway = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("trips").child(trip_key).child("plans").child(plan_key).child("railway");

        fromStation = (EditText)findViewById(R.id.railwayStartingStation);
        toStation = (EditText)findViewById(R.id.railwayStoppingStation);
        railwaydate = (EditText)findViewById(R.id.railwayTakingDate);
        addrailwaybtn = (Button) findViewById(R.id.railwayAddBTN);

        howtoreachRailway.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fromStation.setText((String)dataSnapshot.child("fromStation").getValue());
                toStation.setText((String)dataSnapshot.child("toStation").getValue());
                railwaydate.setText((String)dataSnapshot.child("railwayDate").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

        railwaydate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fromDatePickerDialog.show();
                return false;
            }
        });

        addrailwaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fStation = fromStation.getText().toString();
                String tStation = toStation.getText().toString();
                String rDate = railwaydate.getText().toString();

                Railway newRailway = new Railway();
                newRailway.setFromStation(fStation);
                newRailway.setToStation(tStation);
                newRailway.setRailwayDate(rDate);

                howtoreachRailway.setValue(newRailway);

                Toast.makeText(RailwayPlanActivity.this,"Railway Details saved successfuly!",Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        final Calendar newDate = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                newDate.set(year, monthOfYear, dayOfMonth);
                railwaydate.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
