package com.example.sahan.howtoreach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.Date;

public class AddTripActivity extends AppCompatActivity {

    private EditText tripDestination;
    private EditText tripStartDate;
    private EditText tripEndDate;
    private EditText tripName;
    private EditText tripDescription;
    private Button tripAddBTN;

    private FirebaseAuth auth;
    private DatabaseReference howtoreach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        getSupportActionBar().setTitle("Add Trip");

        auth = FirebaseAuth.getInstance();
        howtoreach = FirebaseDatabase.getInstance().getReference().child("trips");

        tripDestination = (EditText)findViewById(R.id.addTripDestination);
        tripStartDate = (EditText)findViewById(R.id.addTripStartDate);
        tripEndDate = (EditText)findViewById(R.id.addTripEndDate);
        tripName = (EditText)findViewById(R.id.addTripName);
        tripDescription = (EditText)findViewById(R.id.addTripDescription);
        tripAddBTN = (Button) findViewById(R.id.addTripBTN);



        tripAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String destination = tripDestination.getText().toString();
                final String startDate = tripStartDate.getText().toString();
                final String endDate = tripEndDate.getText().toString();
                final String name = tripName.getText().toString();
                final String description = tripDescription.getText().toString();

                if (!TextUtils.isEmpty(destination) || !TextUtils.isEmpty(startDate) || !TextUtils.isEmpty(endDate) || !TextUtils.isEmpty(name) || !TextUtils.isEmpty(description))
                {
                    final String uid = auth.getCurrentUser().getUid();
                    final String tripid = howtoreach.push().getKey();

                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
                    final String date = sdf.format(new Date());

                    DatabaseReference howtoreachUsers = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
                    howtoreachUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String usernamePosted = (String) dataSnapshot.child("name").getValue();

                            Trip newTrip = new Trip();
                            newTrip.setDestination(destination);
                            newTrip.setStartDate(startDate);
                            newTrip.setEndDate(endDate);
                            newTrip.setTripName(name);
                            newTrip.setDescription(description);
                            newTrip.setPostedUserId(uid);
                            newTrip.setAddedDate(date);
                            newTrip.setPostedUserName(usernamePosted);
                            newTrip.setPlan(null);

                            howtoreach.child(tripid).setValue(newTrip);
                            Toast.makeText(AddTripActivity.this,"Trip added successfuly!",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(AddTripActivity.this,MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                }
                else
                {
                    Toast.makeText(AddTripActivity.this,"You cannot have one or more empty fields!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
