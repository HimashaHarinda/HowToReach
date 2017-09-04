package com.example.sahan.howtoreach;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.sahan.howtoreach.R.drawable.input_outline;

public class AddTripActivity extends AppCompatActivity{

    private EditText tripDestination;
    private EditText tripStartDate;
    private EditText tripEndDate;
    private EditText tripName;
    private EditText tripDescription;
    private Button tripAddBTN;

    private FirebaseAuth auth;
    private DatabaseReference howtoreach;

    private Double tripLong;
    private Double tripLat;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        getSupportActionBar().setTitle("Add Trip");

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

        String placeId = null;

        auth = FirebaseAuth.getInstance();
        howtoreach = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("trips");

        tripDestination = (EditText)findViewById(R.id.addTripDestination);

        tripStartDate = (EditText)findViewById(R.id.addTripStartDate);
        tripStartDate.setInputType(InputType.TYPE_NULL);

        tripEndDate = (EditText)findViewById(R.id.addTripEndDate);
        tripEndDate.setInputType(InputType.TYPE_NULL);

        tripName = (EditText)findViewById(R.id.addTripName);
        tripDescription = (EditText)findViewById(R.id.addTripDescription);
        tripAddBTN = (Button) findViewById(R.id.addTripBTN);

        tripStartDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fromDatePickerDialog.show();
                return false;
            }
        });

        tripEndDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                toDatePickerDialog.show();
                return false;
            }
        });

        PlaceAutocompleteFragment places= (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY)
                .setCountry("LK")
                .build();

        places.setFilter(typeFilter);

        places.setHint("Destination");
        ((View)findViewById(R.id.place_autocomplete_search_button)).setVisibility(View.GONE);
        ((EditText)findViewById(R.id.place_autocomplete_search_input)).setBackgroundResource(R.drawable.input_outline);
        ((EditText)findViewById(R.id.place_autocomplete_search_input)).setTextSize(16);
        ((EditText)findViewById(R.id.place_autocomplete_search_input)).setPadding(34,34,34,34);

        places.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                tripLat = place.getLatLng().latitude;
                tripLong = place.getLatLng().longitude;
                tripDestination.setText(place.getName());


            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();

            }
        });


        tripAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String destination = tripDestination.getText().toString();
                final String startDate = tripStartDate.getText().toString();
                final String endDate = tripEndDate.getText().toString();
                final String name = tripName.getText().toString();
                final String description = tripDescription.getText().toString();

                if (!TextUtils.isEmpty(destination) || !TextUtils.isEmpty(startDate) || !TextUtils.isEmpty(endDate) || !TextUtils.isEmpty(name) || !TextUtils.isEmpty(description))
                {
                    final String uid = auth.getCurrentUser().getUid();
                    final String tripid = howtoreach.push().getKey();

                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
                    final String date = sdf.format(new Date());

                    DatabaseReference howtoreachUsers = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
                    howtoreachUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String usernamePosted = (String) dataSnapshot.child("name").getValue();

                            Trip newTrip = new Trip();
                            newTrip.setDestination(destination);
                            newTrip.setStartDate(startDate);
                            newTrip.setEndDate(endDate);
                            newTrip.setTripName(name);
                            newTrip.setDescription(description);
                            newTrip.setPostedUserId(uid);
                            newTrip.setAddedDate(date);
                            newTrip.setPostedUserName(usernamePosted);
                            newTrip.setPlan(null);
                            newTrip.setMarkerLong(tripLong);
                            newTrip.setMarkerLat(tripLat);

                            howtoreach.child(tripid).setValue(newTrip);
                            Toast.makeText(AddTripActivity.this,"Trip added successfuly!",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(AddTripActivity.this,MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                }
                else
                {
                    Toast.makeText(AddTripActivity.this,"You cannot have one or more empty fields!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        final Calendar newDate = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                newDate.set(year, monthOfYear, dayOfMonth);
                tripStartDate.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate2 = Calendar.getInstance();
                newDate2.set(year, monthOfYear, dayOfMonth);

                if (newDate2.after(newDate) || newDate2.equals(newDate))
                {
                    tripEndDate.setText(dateFormatter.format(newDate2.getTime()));
                }
                else
                {
                    Toast.makeText(AddTripActivity.this,"Trip End date must be after start date!",Toast.LENGTH_SHORT).show();
                }


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
