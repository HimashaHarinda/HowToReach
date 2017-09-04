package com.example.sahan.howtoreach;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {
    private String postedUserName;
    private String posUsr;
    private String postedUID;

    private TextView username;
    private TextView userEmail;

    private DatabaseReference howtoreachUsers;
    private FirebaseAuth auth;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        auth = FirebaseAuth.getInstance();
        howtoreachUsers = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
        username = (TextView)view.findViewById(R.id.profileName2);
        userEmail = (TextView)view.findViewById(R.id.profileEmail2);

        howtoreachUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                username.setText((String)dataSnapshot.child("name").getValue());
                userEmail.setText((String)dataSnapshot.child("email").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
