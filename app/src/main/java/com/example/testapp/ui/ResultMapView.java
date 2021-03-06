package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.R;
import com.example.testapp.db.entity.SchoolEntity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Map view of search results.
 */
public class ResultMapView extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private List<SchoolEntity> schools;
    /**
     * Return to the previous page when the back button in the header is selected.
     * @param item header menu button
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Gemerate the map UI display. On the map includes the location of all the schools in the search result. The user can access the detailed information of the
     * school by selecting the popup marker of a particular school.
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_map_view);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Map Result");

        Intent intent = getIntent();
        int schoolLevel = intent.getIntExtra("schoolLevel",-1);
        String nameSearch = intent.getStringExtra("nameSearch");
        String address = intent.getStringExtra("address");
        String[] courses = intent.getStringArrayExtra("courses");
        String[] ccas = intent.getStringArrayExtra("ccas");

        // Convert courses and ccas to array list
        List<String> selectedCourses = new ArrayList<>();
        List<String> selectedCcas = new ArrayList<>();
        if (courses.length != 0) {
            selectedCourses = Arrays.asList(courses);
        }
        if (ccas.length != 0) {
            selectedCcas = Arrays.asList(ccas);
        }

        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();
        schools = dataRepository.findSchools(nameSearch,selectedCcas,selectedCourses,schoolLevel,-1);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
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
        for (int i = 0; i < schools.size(); i ++) {
            Double schoolLatitude = schools.get(i).getLatitude();
            Double schoolLongitude = schools.get(i).getLongitude();
            String schoolName = schools.get(i).getSchoolName();
            LatLng schoolLocation = new LatLng(schoolLatitude, schoolLongitude);
            mMap.addMarker(new MarkerOptions().position(schoolLocation).title(schoolName));
        }
        mMap.moveCamera(CameraUpdateFactory.zoomTo(11));
        LatLng sgLocation = new LatLng(1.3521, 103.8198);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sgLocation));
        mMap.setOnInfoWindowClickListener(this);
    }

    /**
     * Display the school's detailed information when information window of the school's marker is selected.
     * @param marker school's marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        for (int i = 0; i < schools.size(); i ++) {
            SchoolEntity school = schools.get(i);
            if (school.getSchoolName().compareTo(marker.getTitle()) == 0) {
                System.out.println(marker.getTitle());
                Intent openSearch = new Intent(getApplicationContext(), InformationView.class);
                // Insert school info here
                openSearch.putExtra("name", school.getSchoolName());
                openSearch.putExtra("address", school.getPhysicalAddress());
                openSearch.putExtra("postalCode", school.getPostalCode());
                openSearch.putExtra("telephoneNumber1", school.getTelephoneNumber1());
                openSearch.putExtra("telephoneNumber2", school.getTelephoneNumber2());
                openSearch.putExtra("vision", school.getVision());
                openSearch.putExtra("mission", school.getMission());
                openSearch.putExtra("autonomyType", school.getSchoolAutonomyType());
                openSearch.putExtra("gender", school.getSchoolGender());
                openSearch.putExtra("giftedEducation", school.getGiftedEducationProgramOffered());
                openSearch.putExtra("integratedProgram", school.getIntegratedProgram());
                openSearch.putExtra("sap", school.getSAPSchool());
                openSearch.putExtra("zoneCode", school.getZoneCode());
                openSearch.putExtra("clusterCode", school.getClusterCode());
                openSearch.putExtra("website", school.getHomePageAddress());
                openSearch.putExtra("longitude", school.getLongitude());
                openSearch.putExtra("latitude", school.getLatitude());
                startActivity(openSearch);
                return;
            }
        }
    }
}
