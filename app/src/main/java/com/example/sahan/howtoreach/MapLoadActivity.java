package com.example.sahan.howtoreach;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapLoadActivity extends AppCompatActivity implements OnMapReadyCallback{

    private Double trip_lat = null;
    private Double trip_long = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_load);

        trip_lat = getIntent().getExtras().getDouble("trip_lat");
        trip_long = getIntent().getExtras().getDouble("trip_long");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng marker = new LatLng(trip_lat, trip_long);
        googleMap.addMarker(new MarkerOptions().position(marker));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker));

    }
}
