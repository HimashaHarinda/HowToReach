package com.example.sahan.howtoreach;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleTripActivity extends AppCompatActivity {

    private String trip_key = null;

    private TextView tName;
    private TextView tStartDate;
    private TextView tEndDate;
    private TextView tDescription;
    private TextView tPostedTime;
    private TextView tdesttt;

    private String tripAddedUser = null;

    private CardView planbtncardview;
    private CardView editRemoveTripcardview;
    private CardView postedUserCard;
    private LinearLayout destinationMap;

    private Button addnewplanBTN;
    private Button deleteTrip;
    private Button editTrip;
    private TextView tDestination;

    private RecyclerView plan_list;

    private DatabaseReference howtoreachUsers;
    private DatabaseReference howtoreachTrips;
    private DatabaseReference howtoreachPlans;
    private FirebaseAuth auth;

    private AlertDialog.Builder builder;

    private Double tripLat;
    private Double tripLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_trip);
        trip_key = getIntent().getExtras().getString("trip_id");

        final String tpkettriodelete = trip_key;
        auth = FirebaseAuth.getInstance();

        howtoreachTrips = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("trips");
        howtoreachUsers = FirebaseDatabase.getInstance().getReference().child("users");
        howtoreachPlans = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("trips").child(trip_key).child("plans");



        howtoreachUsers.keepSynced(true);
        howtoreachPlans.keepSynced(true);
        howtoreachPlans.keepSynced(true);

        builder = new AlertDialog.Builder(this);

        tName = (TextView) findViewById(R.id.singleTripName);
        tStartDate = (TextView) findViewById(R.id.singleTripStartTimr);
        tEndDate = (TextView) findViewById(R.id.singleTripEndTime);
        tDestination = (TextView) findViewById(R.id.singleTripDestination);
        tDescription = (TextView) findViewById(R.id.singleTripDescription);
        tPostedTime = (TextView) findViewById(R.id.singleTripPostedTime);
        tdesttt = (TextView) findViewById(R.id.tripdest);

        planbtncardview = (CardView)findViewById(R.id.addplanBTNcardview);
        editRemoveTripcardview = (CardView)findViewById(R.id.editRemoveTripCardView);
        destinationMap = (LinearLayout) findViewById(R.id.tripDestinationCardView);

        plan_list = (RecyclerView) findViewById(R.id.plan_listT);
        plan_list.setHasFixedSize(true);
        plan_list.setLayoutManager(new LinearLayoutManager(this));


        addnewplanBTN = (Button) findViewById(R.id.addnewplanBTN);
        deleteTrip = (Button) findViewById(R.id.deleteTripBTN);
        editTrip = (Button) findViewById(R.id.editTripBTN);


        FirebaseRecyclerAdapter <Plan, PlanViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Plan, PlanViewHolder>(
                Plan.class,
                R.layout.plan_row,
                PlanViewHolder.class,
                howtoreachPlans

        ) {
            @Override
            protected void populateViewHolder(PlanViewHolder viewHolder, Plan model, int position) {

                final String plan_key = getRef(position).getKey();

                viewHolder.setPlanName(model.getPlanName());
                viewHolder.setPlanDesc(model.getPlanDesc());

                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SingleTripActivity.this, PlanDetailsActivity.class);
                        intent.putExtra("plan_id", plan_key);
                        intent.putExtra("trip_id",trip_key);
                        startActivity(intent);
                    }
                });


            }
        };
        plan_list.setAdapter(firebaseRecyclerAdapter);



        howtoreachTrips.child(trip_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {

                final String trip_name = (String) dataSnapshot1.child("tripName").getValue();
                final String trip_startDate = (String) dataSnapshot1.child("startDate").getValue();
                final String trip_endDate = (String) dataSnapshot1.child("endDate").getValue();
                final String trip_dest = (String) dataSnapshot1.child("destination").getValue();
                final String trip_description = (String) dataSnapshot1.child("description").getValue();
                final String trip_postedTime = (String) dataSnapshot1.child("addedDate").getValue();
                tripLat = (Double) dataSnapshot1.child("markerLat").getValue();
                tripLong = (Double) dataSnapshot1.child("markerLong").getValue();
                tripAddedUser = (String) dataSnapshot1.child("postedUserId").getValue();
                        tName.setText(trip_name);
                        tStartDate.setText(trip_startDate);
                        tEndDate.setText(trip_endDate);
                        tdesttt.setText("To "+trip_dest);
                        tDestination.setText("Get Directions to "+trip_dest);
                        tDescription.setText(trip_description);
                        tPostedTime.setText(trip_postedTime);

                        if (auth.getCurrentUser().getUid().equals(tripAddedUser))
                        {
                            planbtncardview.setVisibility(View.VISIBLE);
                            editRemoveTripcardview.setVisibility(View.VISIBLE);
                        }
                        else {
                            planbtncardview.setVisibility(View.GONE);
                        }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        destinationMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTripActivity.this, MapLoadActivity.class);
                intent.putExtra("trip_lat", tripLat);
                intent.putExtra("trip_long",tripLong);
                startActivity(intent);
            }
        });

        editTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTripActivity.this, editTripActivity.class);
                intent.putExtra("trip_id", trip_key);
                startActivity(intent);
            }
        });

        addnewplanBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTripActivity.this, AddPlanActivity.class);
                intent.putExtra("trip_id", trip_key);
                startActivity(intent);
            }
        });

        deleteTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete this trip?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if(trip_key != null) {
                            howtoreachTrips.child(trip_key).removeValue();

                            Intent intent2 = new Intent(SingleTripActivity.this, MainActivity.class);
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

    @Override
    protected void onStart() {
        super.onStart();


    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder{

        View mview;
        public PlanViewHolder(View itemView) {
            super(itemView);
            mview = itemView;

        }

        public void setPlanName(String planName){
            TextView pName = (TextView)mview.findViewById(R.id.plan_nameS);
            pName.setText(planName);
        }

        public void setPlanDesc(String planDes){
            TextView pHotel = (TextView)mview.findViewById(R.id.plan_DescS);
            pHotel.setText(planDes);
        }
    }
}
