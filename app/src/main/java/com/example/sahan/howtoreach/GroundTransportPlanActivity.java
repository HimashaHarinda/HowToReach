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

public class GroundTransportPlanActivity extends AppCompatActivity {
    private String trip_key = null;
    private String plan_key = null;

    private EditText routeNo;
    private EditText routeFrom;
    private EditText routeTo;
    private Button routeDetailsAddBTN;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private DatabaseReference howtoreachGroundTransport;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_transport_plan);
        getSupportActionBar().setTitle("Ground Transport Plan Details");

        trip_key = getIntent().getExtras().getString("trip_id");
        plan_key = getIntent().getExtras().getString("plan_id");

        auth = FirebaseAuth.getInstance();
        howtoreachGroundTransport = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("trips").child(trip_key).child("plans").child(plan_key).child("groundTransport");

        routeNo = (EditText)findViewById(R.id.groundRouteNo);
        routeFrom = (EditText)findViewById(R.id.groundStartLocation);
        routeTo = (EditText)findViewById(R.id.groundDestination);
        routeDetailsAddBTN = (Button) findViewById(R.id.railwayAddBTN);

        howtoreachGroundTransport.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                routeNo.setText((String)dataSnapshot.child("routeNo").getValue());
                routeFrom.setText((String)dataSnapshot.child("routeFrom").getValue());
                routeTo.setText((String)dataSnapshot.child("routeTo").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        routeDetailsAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rNo = routeNo.getText().toString();
                String rFrom = routeFrom.getText().toString();
                String rTo = routeTo.getText().toString();

                GroundTransport newGT = new GroundTransport();
                newGT.setRouteNo(rNo);
                newGT.setRouteFrom(rFrom);
                newGT.setRouteTo(rTo);

                howtoreachGroundTransport.setValue(newGT);

                Toast.makeText(GroundTransportPlanActivity.this,"Ground Transport Details saved successfuly!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
