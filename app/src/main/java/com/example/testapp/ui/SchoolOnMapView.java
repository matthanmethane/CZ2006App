package com.example.testapp.ui;

import android.os.Bundle;

import com.example.testapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

public class SchoolOnMapView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String KEY_SCHOOL_NAME;
    private Double schoolLatitude;
    private Double schoolLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_on_map_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        KEY_SCHOOL_NAME = getIntent().getStringExtra("school_name");
        schoolLongitude = getIntent().getDoubleExtra("longitude", -34); // TODO: set a proper default value
        schoolLatitude = getIntent().getDoubleExtra("latitude", 151); // TODO: set a proper default value

        System.out.println("School to map: " + KEY_SCHOOL_NAME);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker for the school and move the camera
        LatLng schoolLocation = new LatLng(schoolLatitude, schoolLongitude);
        mMap.addMarker(new MarkerOptions().position(schoolLocation).title(KEY_SCHOOL_NAME));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(schoolLocation));
    }
}
