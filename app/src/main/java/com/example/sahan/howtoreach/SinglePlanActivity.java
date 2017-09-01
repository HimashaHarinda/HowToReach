package com.example.sahan.howtoreach;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class SinglePlanActivity extends AppCompatActivity {

    private String plan_key = null;
    private String trip_key = null;

    private TextView planName;
    private TextView hotelName;
    private TextView carNo;

    private Button editPlanBTN;
    private Button removePlanBTN;

    private CardView editremoveplancardview;

    private DatabaseReference howtoreachplans;
    private FirebaseAuth auth;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_plan);

        plan_key = getIntent().getExtras().getString("plan_id");
        trip_key = getIntent().getExtras().getString("trip_id");

        howtoreachplans = FirebaseDatabase.getInstance().getReference().child("trips").child(trip_key).child("plans").child(plan_key);
        auth = FirebaseAuth.getInstance();

        builder = new AlertDialog.Builder(this);

        planName = (TextView) findViewById(R.id.singlePlanName);
        hotelName = (TextView) findViewById(R.id.singlePlanHotel);
        carNo = (TextView) findViewById(R.id.singlePlanCar);

        editPlanBTN = (Button) findViewById(R.id.editPlanBTN);
        removePlanBTN = (Button) findViewById(R.id.deletePlanBTN);

        editremoveplancardview = (CardView) findViewById(R.id.editRemovePlanCardView);

        howtoreachplans.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pName = (String) dataSnapshot.child("planName").getValue();
                String pHotel = (String) dataSnapshot.child("hotelName").getValue();
                String pCar = (String) dataSnapshot.child("carNo").getValue();
                String pUser = (String) dataSnapshot.child("userId").getValue();

                planName.setText(pName);
                hotelName.setText(pHotel);
                carNo.setText(pCar);

                if (auth.getCurrentUser().getUid().equals(pUser))
                {
                    editremoveplancardview.setVisibility(View.VISIBLE);
                }
                else {
                    editremoveplancardview.setVisibility(View.GONE);
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        removePlanBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete this plan?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if(trip_key != null) {
                            howtoreachplans.removeValue();

                            Intent intent2 = new Intent(SinglePlanActivity.this, SingleTripActivity.class);
                            intent2.putExtra("trip_id",trip_key);
                            startActivity(intent2);
                            finish();
                        }

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


        editPlanBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SinglePlanActivity.this, editPlanActivity.class);
                intent.putExtra("plan_id", plan_key);
                intent.putExtra("trip_id",trip_key);
                startActivity(intent);

            }
        });
    }
}
