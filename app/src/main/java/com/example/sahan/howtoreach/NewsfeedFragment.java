package com.example.sahan.howtoreach;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewsfeedFragment extends Fragment {
    private RecyclerView tripList;
    private DatabaseReference howtoreach;
    private FirebaseAuth auth;

    public static NewsfeedFragment newInstance() {
        NewsfeedFragment fragment = new NewsfeedFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Trip,TripviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Trip, TripviewHolder>(
                Trip.class,
                R.layout.trip_row,
                TripviewHolder.class,
                howtoreach

        ) {
            @Override
            protected void populateViewHolder(TripviewHolder viewHolder, Trip model, int position) {

                final String trip_key = getRef(position).getKey();

                viewHolder.setName(model.getTripName());
                viewHolder.setDestination(model.getDestination());
                viewHolder.setPostedDate(model.getAddedDate());
                viewHolder.setTripFromDate(model.getStartDate());
                viewHolder.setTripToDate(model.getEndDate());

                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SingleTripActivity.class);
                        intent.putExtra("trip_id", trip_key);
                        startActivity(intent);
                    }
                });
            }
        };

        tripList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class TripviewHolder extends RecyclerView.ViewHolder
    {
        View mview;
        public TripviewHolder(View itemView) {
            super(itemView);

            mview = itemView;
        }

        public void setName(String tripName){
            TextView tName = (TextView) mview.findViewById(R.id.trip_nameC);
            tName.setText(tripName);
        }

        public void setDestination(String tripDestination){
            TextView tDestination = (TextView) mview.findViewById(R.id.trip_destinationC);
            tDestination.setText("To "+tripDestination);
        }

        public void setPostedDate(String tripPostedDate){
            TextView tPostedDate = (TextView) mview.findViewById(R.id.job_posted_dateC);
            tPostedDate.setText("Added on "+tripPostedDate);
        }

        public void setTripFromDate(String tripFromDate)
        {
            TextView tFromDate = (TextView) mview.findViewById(R.id.fromDateT);
            tFromDate.setText(tripFromDate);
        }

        public void setTripToDate(String tripToDate)
        {
            TextView tToDate = (TextView) mview.findViewById(R.id.toDateT);
            tToDate.setText(tripToDate);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_newsfeed, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        tripList = (RecyclerView)view.findViewById(R.id.trip_list);
        tripList.setHasFixedSize(true);
        tripList.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        auth = FirebaseAuth.getInstance();

        howtoreach = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("trips");
        howtoreach.keepSynced(true);

    }

}
