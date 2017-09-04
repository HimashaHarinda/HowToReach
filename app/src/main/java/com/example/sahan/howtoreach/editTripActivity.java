package com.example.sahan.howtoreach;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class editTripActivity extends AppCompatActivity {

    private String trip_key = null;

    private TextView eName;
    private TextView eStartDate;
    private TextView eEndDate;
    private TextView eDestination;
    private TextView eDescription;

    private Double tripLong;
    private Double tripLat;

    private Button editTripBTN;

    private AlertDialog.Builder builder;

    private DatabaseReference howtoreachTrips;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    private PlaceAutocompleteFragment places;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

        trip_key = getIntent().getExtras().getString("trip_id");

        builder = new AlertDialog.Builder(this);

        auth = FirebaseAuth.getInstance();
        howtoreachTrips = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("trips").child(trip_key);



        eName = (TextView)findViewById(R.id.editTripName);
        eStartDate = (TextView)findViewById(R.id.editTripStartDate);
        eEndDate = (TextView)findViewById(R.id.editTripEndDate);
        eDescription = (TextView)findViewById(R.id.editTripDescription);
        eDestination = (TextView)findViewById(R.id.editTripDestination);

        editTripBTN = (Button)findViewById(R.id.editTripBTN);

        places = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        howtoreachTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) throws NullPointerException{


                eName.setText((String)dataSnapshot.child("tripName").getValue());
                eStartDate.setText((String)dataSnapshot.child("startDate").getValue());
                eEndDate.setText((String)dataSnapshot.child("endDate").getValue());
                eDescription.setText((String)dataSnapshot.child("description").getValue());
                eDestination.setText((String)dataSnapshot.child("destination").getValue());
                places.setText(eDestination.getText());
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        eStartDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fromDatePickerDialog.show();
                return false;
            }
        });

        eEndDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                toDatePickerDialog.show();
                return false;
            }
        });





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
                eDestination.setText(place.getName());


            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();

            }
        });


        editTripBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Confirm");
                builder.setMessage("Save changes?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        final String editedName = eName.getText().toString();
                        final String editedStartTime = eStartDate.getText().toString();
                        final String editedEndDate = eEndDate.getText().toString();
                        final String editedDesc = eDescription.getText().toString();
                        final String editedDest = eDestination.getText().toString();

                        howtoreachTrips.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child("tripName").setValue(editedName);
                                dataSnapshot.getRef().child("startDate").setValue(editedStartTime);
                                dataSnapshot.getRef().child("endDate").setValue(editedEndDate);
                                dataSnapshot.getRef().child("description").setValue(editedDesc);
                                dataSnapshot.getRef().child("destination").setValue(editedDest);
                                dataSnapshot.getRef().child("markerLat").setValue(tripLat);
                                dataSnapshot.getRef().child("markerLong").setValue(tripLong);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        Toast.makeText(editTripActivity.this,"Changes saves!",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(editTripActivity.this, SingleTripActivity.class);
                        intent.putExtra("trip_id", trip_key);
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

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        final Calendar newDate = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                newDate.set(year, monthOfYear, dayOfMonth);
                eStartDate.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate2 = Calendar.getInstance();
                newDate2.set(year, monthOfYear, dayOfMonth);

                if (newDate2.after(newDate) || newDate2.equals(newDate))
                {
                    eEndDate.setText(dateFormatter.format(newDate2.getTime()));
                }
                else
                {
                    Toast.makeText(editTripActivity.this,"Trip End date must be after start date!",Toast.LENGTH_SHORT).show();
                }


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

}
