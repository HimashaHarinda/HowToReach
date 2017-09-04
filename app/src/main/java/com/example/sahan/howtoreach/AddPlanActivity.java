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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPlanActivity extends AppCompatActivity {

    private String trip_key = null;

    private EditText planName;
    private EditText planDesc;
    private Button addPlanBTN;

    private String planid;

    private FirebaseAuth auth;
    private DatabaseReference howtoreach;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        getSupportActionBar().setTitle("Add a plan");

        auth = FirebaseAuth.getInstance();
        trip_key = getIntent().getExtras().getString("trip_id");
        howtoreach = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("trips").child(trip_key).child("plans");



        planName = (EditText)findViewById(R.id.addPlanName);
        planDesc = (EditText)findViewById(R.id.addPlanDesc);
        addPlanBTN = (Button) findViewById(R.id.addPlanBTN);

        addPlanBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                planid = howtoreach.push().getKey();

                String pName = planName.getText().toString();
                String pDesc = planDesc.getText().toString();

                if (!TextUtils.isEmpty(pName) || !TextUtils.isEmpty(pDesc))
                {
                    Plan newPlan = new Plan();
                    newPlan.setPlanName(pName);
                    newPlan.setPlanDesc(pDesc);
                    newPlan.setTripId(trip_key);
                    newPlan.setUserId(auth.getCurrentUser().getUid());


                    howtoreach.child(planid).setValue(newPlan);


                    Intent intent = new Intent(AddPlanActivity.this, SingleTripActivity.class);
                    intent.putExtra("trip_id", trip_key);
                    intent.putExtra("plan_id", planid);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(AddPlanActivity.this,"Cannot have one or more empty fields!",Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
}
