package com.example.sahan.howtoreach;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CarRentalPlanActivity extends AppCompatActivity{

    private String trip_key = null;
    private String plan_key = null;

    private EditText carNo;
    private EditText carModel;
    private EditText carAgent;

    private EditText carStartDate;
    private EditText carEndDate;
    private Button saveCarRentalBTN;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private DatabaseReference howtoreachCarRental;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rental_plan);
        getSupportActionBar().setTitle("Car Rental Details");

        trip_key = getIntent().getExtras().getString("trip_id");
        plan_key = getIntent().getExtras().getString("plan_id");

        auth = FirebaseAuth.getInstance();
        howtoreachCarRental = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("trips").child(trip_key).child("plans").child(plan_key).child("carRental");

        carNo = (EditText)findViewById(R.id.carRentalCarNo);
        carModel = (EditText)findViewById(R.id.carRentalModel);
        carAgent = (EditText)findViewById(R.id.carRentalAgent);
        carStartDate = (EditText)findViewById(R.id.carRentalDateFrom);
        carEndDate = (EditText)findViewById(R.id.carRentalDateTo);
        saveCarRentalBTN = (Button) findViewById(R.id.addCarRentalBTN);

        howtoreachCarRental.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                carNo.setText((String)dataSnapshot.child("carNo").getValue());
                carModel.setText((String)dataSnapshot.child("carModel").getValue());
                carAgent.setText((String)dataSnapshot.child("carAgent").getValue());
                carStartDate.setText((String)dataSnapshot.child("carRentalStartDate").getValue());
                carEndDate.setText((String)dataSnapshot.child("carRentalEndDate").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

        carStartDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fromDatePickerDialog.show();
                return false;
            }
        });

        carEndDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                toDatePickerDialog.show();
                return false;
            }
        });

        saveCarRentalBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cNo = carNo.getText().toString();
                String cModel = carModel.getText().toString();
                String cAgent = carAgent.getText().toString();
                String cstartDate = carStartDate.getText().toString();
                String cendDate = carEndDate.getText().toString();

                String carRentslId = howtoreachCarRental.push().getKey();

                CarRental newCarRental = new CarRental();
                newCarRental.setCarNo(cNo);
                newCarRental.setCarModel(cModel);
                newCarRental.setCarAgent(cAgent);
                newCarRental.setCarRentalStartDate(cstartDate);
                newCarRental.setCarRentalEndDate(cendDate);

                howtoreachCarRental.setValue(newCarRental);

                Toast.makeText(CarRentalPlanActivity.this,"Car Rental Details saved successfuly!",Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        final Calendar newDate = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                newDate.set(year, monthOfYear, dayOfMonth);
                carStartDate.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate2 = Calendar.getInstance();
                newDate2.set(year, monthOfYear, dayOfMonth);

                if (newDate2.after(newDate) || newDate2.equals(newDate))
                {
                    carEndDate.setText(dateFormatter.format(newDate2.getTime()));
                }
                else
                {
                    Toast.makeText(CarRentalPlanActivity.this,"Car rental End date must be after start date!",Toast.LENGTH_SHORT).show();
                }


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

}
