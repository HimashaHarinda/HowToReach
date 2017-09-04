package com.example.sahan.howtoreach;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlanDetailsActivity extends AppCompatActivity {

    private String trip_key = null;
    private String plan_key = null;

    private Button carRental;
    private Button railway;
    private Button gTransport;

    private Button restaurant;
    private Button deletePlan;
    private Button activit;
    private DatabaseReference howtoreachPlans;
    private FirebaseAuth auth;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);
        builder = new AlertDialog.Builder(this);
        getSupportActionBar().setTitle("Plans");

        trip_key = getIntent().getExtras().getString("trip_id");
        plan_key = getIntent().getExtras().getString("plan_id");

        auth = FirebaseAuth.getInstance();

        howtoreachPlans = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("trips").child(trip_key).child("plans");

        carRental = (Button) findViewById(R.id.carRentalBTN);
        railway = (Button) findViewById(R.id.RailwayBTN);
        gTransport = (Button)findViewById(R.id.GroundTransportBTN);

        restaurant = (Button) findViewById(R.id.restBTN);
        deletePlan = (Button) findViewById(R.id.planDeleteBTN);
        activit = (Button)findViewById(R.id.ActivityBTN);

        carRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDetailsActivity.this, CarRentalPlanActivity.class);
                intent.putExtra("trip_id", trip_key);
                intent.putExtra("plan_id", plan_key);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        railway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDetailsActivity.this, RailwayPlanActivity.class);
                intent.putExtra("trip_id", trip_key);
                intent.putExtra("plan_id", plan_key);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        gTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDetailsActivity.this, GroundTransportPlanActivity.class);
                intent.putExtra("trip_id", trip_key);
                intent.putExtra("plan_id", plan_key);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDetailsActivity.this, RestaurantPlanActivity.class);
                intent.putExtra("trip_id", trip_key);
                intent.putExtra("plan_id", plan_key);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        activit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanDetailsActivity.this, ActivitiesPlanActivity.class);
                intent.putExtra("trip_id", trip_key);
                intent.putExtra("plan_id", plan_key);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        deletePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete this plan?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if(plan_key != null) {
                            howtoreachPlans.child(plan_key).removeValue();

                            Intent intent2 = new Intent(PlanDetailsActivity.this, SingleTripActivity.class);
                            intent2.putExtra("trip_id", trip_key);
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



    }
}
