package com.example.sahan.howtoreach;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleProfile extends AppCompatActivity {

    private String trip_key = null;

    private TextView postedUserNameText;
    private TextView postedUserEmailText;

    private EditText reviewPos;
    private Button submitReBTN;

    private String postedUserName;
    private String posUsr;
    private String postedUID;

    private RecyclerView reviews_list;

    private DatabaseReference howtoreachUsers;
    private DatabaseReference howtoreachTrips;
    private DatabaseReference howtoreachReviews;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_profile);

        trip_key = getIntent().getExtras().getString("trip_id");
        reviews_list.setHasFixedSize(true);
        reviews_list.setLayoutManager(new LinearLayoutManager(this));


        postedUserNameText = (TextView)findViewById(R.id.profileName);
        postedUserEmailText = (TextView) findViewById(R.id.profileEmail);

        howtoreachTrips = FirebaseDatabase.getInstance().getReference().child("trips").child(trip_key);
        howtoreachUsers = FirebaseDatabase.getInstance().getReference().child("users");
        auth = FirebaseAuth.getInstance();


        howtoreachTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postedUID = (String) dataSnapshot.child("postedUserId").getValue();
                postedUserName = (String) dataSnapshot.child("postedUserName").getValue();

                howtoreachReviews = FirebaseDatabase.getInstance().getReference().child("users").child(postedUID).child("review");

                howtoreachUsers.child(postedUID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        postedUserNameText.setText((CharSequence) dataSnapshot2.child("name").getValue());
                        postedUserEmailText.setText((CharSequence) dataSnapshot2.child("email").getValue());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









    }



}
