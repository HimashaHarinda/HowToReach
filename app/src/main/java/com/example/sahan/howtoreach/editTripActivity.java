package com.example.sahan.howtoreach;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editTripActivity extends AppCompatActivity {

    private String trip_key = null;

    private TextView eName;
    private TextView eStartDate;
    private TextView eEndDate;
    private TextView eDestination;
    private TextView eDescription;

    private Button editTripBTN;

    private AlertDialog.Builder builder;

    private DatabaseReference howtoreachTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        trip_key = getIntent().getExtras().getString("trip_id");

        builder = new AlertDialog.Builder(this);

        howtoreachTrips = FirebaseDatabase.getInstance().getReference().child("trips").child(trip_key);

        eName = (TextView)findViewById(R.id.editTripName);
        eStartDate = (TextView)findViewById(R.id.editTripStartDate);
        eEndDate = (TextView)findViewById(R.id.editTripEndDate);
        eDescription = (TextView)findViewById(R.id.editTripDescription);
        eDestination = (TextView)findViewById(R.id.editTripDestination);

        editTripBTN = (Button)findViewById(R.id.editTripBTN);

        howtoreachTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eName.setText((String)dataSnapshot.child("tripName").getValue());
                eStartDate.setText((String)dataSnapshot.child("startDate").getValue());
                eEndDate.setText((String)dataSnapshot.child("endDate").getValue());
                eDescription.setText((String)dataSnapshot.child("description").getValue());
                eDestination.setText((String)dataSnapshot.child("destination").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        editTripBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Confirm");
                builder.setMessage("Save changes?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        final String editedName = eName.getText().toString();
                        final String editedStartTime = eStartDate.getText().toString();
                        final String editedEndDate = eEndDate.getText().toString();
                        final String editedDesc = eDescription.getText().toString();
                        final String editedDest = eDestination.getText().toString();

                        howtoreachTrips.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child("tripName").setValue(editedName);
                                dataSnapshot.getRef().child("startDate").setValue(editedStartTime);
                                dataSnapshot.getRef().child("endDate").setValue(editedEndDate);
                                dataSnapshot.getRef().child("description").setValue(editedDesc);
                                dataSnapshot.getRef().child("destination").setValue(editedDest);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        Toast.makeText(editTripActivity.this,"Changes saves!",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(editTripActivity.this, SingleTripActivity.class);
                        intent.putExtra("trip_id", trip_key);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();


                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }
}
