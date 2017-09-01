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

public class editPlanActivity extends AppCompatActivity {

    private String plan_key = null;
    private String trip_key = null;

    private TextView planName;
    private TextView hotelName;
    private TextView carNo;

    private Button editPBTN;

    private DatabaseReference howtoreachplans;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);

        plan_key = getIntent().getExtras().getString("plan_id");
        trip_key = getIntent().getExtras().getString("trip_id");

        builder = new AlertDialog.Builder(this);

        planName = (TextView) findViewById(R.id.editplanname);
        hotelName = (TextView) findViewById(R.id.editplanhotelname);
        carNo = (TextView) findViewById(R.id.editplancarno);

        editPBTN = (Button)findViewById(R.id.editplanBTN);

        howtoreachplans = FirebaseDatabase.getInstance().getReference().child("trips").child(trip_key).child("plans").child(plan_key);

        howtoreachplans.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                planName.setText((String) dataSnapshot.child("planName").getValue());
                hotelName.setText((String)dataSnapshot.child("hotelName").getValue());
                carNo.setText((String)dataSnapshot.child("carNo").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editPBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Confirm");
                builder.setMessage("Save changes?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        final String pName = planName.getText().toString();
                        final String hName = hotelName.getText().toString();
                        final String cNo = carNo.getText().toString();

                        howtoreachplans.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child("planName").setValue(pName);
                                dataSnapshot.getRef().child("hotelName").setValue(hName);
                                dataSnapshot.getRef().child("carNo").setValue(cNo);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        Toast.makeText(editPlanActivity.this,"Changes saves!",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(editPlanActivity.this, SinglePlanActivity.class);
                        intent.putExtra("plan_id", plan_key);
                        intent.putExtra("trip_id",trip_key);
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
