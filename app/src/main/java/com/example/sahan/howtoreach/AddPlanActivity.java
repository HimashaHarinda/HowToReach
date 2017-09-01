package com.example.sahan.howtoreach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPlanActivity extends AppCompatActivity {

    private String trip_key = null;

    private EditText planName;
    private EditText carno;
    private EditText hotelName;
    private Button addPlanBTN;

    private FirebaseAuth auth;
    private DatabaseReference howtoreach;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        auth = FirebaseAuth.getInstance();
        trip_key = getIntent().getExtras().getString("trip_id");
        howtoreach = FirebaseDatabase.getInstance().getReference().child("trips").child(trip_key).child("plans");



        planName = (EditText)findViewById(R.id.addplanname);
        carno = (EditText)findViewById(R.id.addplancarno);
        hotelName = (EditText)findViewById(R.id.addplanhotelname);
        addPlanBTN = (Button) findViewById(R.id.addplanBTN);

        addPlanBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String planid = howtoreach.push().getKey();

                String pName = planName.getText().toString();
                String pcarno = carno.getText().toString();
                String photelName = hotelName.getText().toString();

                Plan newPlan = new Plan();
                newPlan.setPlanName(pName);
                newPlan.setHotelName(photelName);
                newPlan.setCarNo(pcarno);
                newPlan.setTripId(trip_key);
                newPlan.setUserId(auth.getCurrentUser().getUid());


                howtoreach.child(planid).setValue(newPlan);
                Toast.makeText(AddPlanActivity.this,"Plan added successfuly!",Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(AddPlanActivity.this, SingleTripActivity.class);
                intent.putExtra("trip_id", trip_key);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
    }
}
