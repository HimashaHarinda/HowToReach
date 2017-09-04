package com.example.sahan.howtoreach;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class ActivitiesPlanActivity extends AppCompatActivity {

    private String trip_key = null;
    private String plan_key = null;

    private EditText activityname;
    private EditText activitydetails;
    private Button activityaddBTN;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private DatabaseReference howtoreachActivities;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_plan);
        getSupportActionBar().setTitle("Activity Plan Details");

        trip_key = getIntent().getExtras().getString("trip_id");
        plan_key = getIntent().getExtras().getString("plan_id");

        auth = FirebaseAuth.getInstance();
        howtoreachActivities = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("trips").child(trip_key).child("plans").child(plan_key).child("activities");

        activityname = (EditText)findViewById(R.id.activityName);
        activitydetails = (EditText)findViewById(R.id.activityDetails);
        activityaddBTN = (Button) findViewById(R.id.activityAddBTN);

        howtoreachActivities.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                activityname.setText((String)dataSnapshot.child("activityName").getValue());
                activitydetails.setText((String)dataSnapshot.child("activityDetails").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        activityaddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rName = activityname.getText().toString();
                String rdetails = activitydetails.getText().toString();

                Activits newAct = new Activits();
                newAct.setActivityDetails(rdetails);
                newAct.setActivityName(rName);

                howtoreachActivities.setValue(newAct);

                Toast.makeText(ActivitiesPlanActivity.this,"Ground Transport Details saved successfuly!",Toast.LENGTH_SHORT).show();
            }

        });
    }
}
