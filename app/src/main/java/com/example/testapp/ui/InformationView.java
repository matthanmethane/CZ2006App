package com.example.testapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.R;
import com.example.testapp.db.entity.SchoolToCCA;
import com.example.testapp.db.entity.SchoolToCourse;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class InformationView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_view);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        int postalCode = intent.getIntExtra("postalCode",-1);
        String telephoneNumber1 = intent.getStringExtra("telephoneNumber1");
        String telephoneNumber2 = intent.getStringExtra("telephoneNumber2");
        String vision = intent.getStringExtra("vision");
        String mission = intent.getStringExtra("mission");
        int autonomyType = intent.getIntExtra("autonomyType",-1);
        String gender = intent.getStringExtra("gender");
        int giftedEducation = intent.getIntExtra("giftedEducation",-1);
        int integratedProgram = intent.getIntExtra("integratedProgram",-1);
        int sapSchool = intent.getIntExtra("sap",-1);
        String zoneCode = intent.getStringExtra("zoneCode");
        String clusterCode = intent.getStringExtra("clusterCode");

        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();
        List<SchoolToCCA> ccas = dataRepository.getSchoolCCAs(name);
        String[] ccaList = new String[ccas.size()];
        for (int i = 0; i < ccas.size(); i ++) {
            ccaList[i] = ccas.get(i).ccaName;
        }
        List<SchoolToCourse> courses = dataRepository.getSchoolCourses(name);
        String[] courseList = new String[courses.size()];
        for (int i = 0; i < courses.size(); i ++) {
            courseList[i] = courses.get(i).courseName;
        }

        // Display information
        TextView nameText = findViewById(R.id.full_textbox_school_name);
        nameText.setText(name);

        TextView addressText = findViewById(R.id.full_textbox_school_physical_address);
        addressText.setText(address);

        TextView postalCodeText = findViewById(R.id.full_textbox_school_postal_code);
        postalCodeText.setText(Integer.toString(postalCode));

        TextView tele1Text = findViewById(R.id.full_textbox_school_telephone_number_1);
        tele1Text.setText(telephoneNumber1);

        TextView tele2Text = findViewById(R.id.full_textbox_school_telephone_number_2);
        tele2Text.setText(telephoneNumber2);

        TextView visionText = findViewById(R.id.full_textbox_school_vision);
        visionText.setText(vision);

        TextView missionText = findViewById(R.id.full_textbox_school_mission);
        missionText.setText(mission);

        TextView autonomyText = findViewById(R.id.full_textbox_school_autonomy_type);
        if (autonomyType == 0) {
            autonomyText.setText("YES");
        } else {
            autonomyText.setText("NO");
        }

        TextView genderText = findViewById(R.id.full_textbox_school_gender);
        genderText.setText(gender);

        TextView giftedText = findViewById(R.id.full_textbox_gifted_education_program_offered);
        if (giftedEducation == 0) {
            giftedText.setText("YES");
        } else {
            giftedText.setText("NO");
        }

        TextView integratedText = findViewById(R.id.full_textbox_integrated_program);
        if (integratedProgram == 0) {
            integratedText.setText("YES");
        } else {
            integratedText.setText("NO");
        }

        TextView sapText = findViewById(R.id.full_textbox_SAP_school);
        if (sapSchool == 0) {
            sapText.setText("YES");
        } else {
            sapText.setText("NO");
        }

        TextView zoneText = findViewById(R.id.full_textbox_zone_code);
        zoneText.setText(zoneCode);

        TextView clusterText = findViewById(R.id.full_textbox_cluster_code);
        clusterText.setText(clusterCode);

        TextView ccaText = findViewById(R.id.full_textbox_cca);
        String ccaString = "";
        if (ccaList.length > 1)
            ccaString = ccaList[0];
        for (int i = 1; i < ccaList.length; i ++) {
            ccaString = ccaString.concat(", ");
            ccaString = ccaString.concat(ccaList[i]);
        }
        ccaText.setText(ccaString);


        TextView courseText = findViewById(R.id.full_textbox_course);
        String courseString = "";
        if (courseList.length > 1)
            courseString = courseList[0];
        for (int i = 1; i < courseList.length; i ++) {
            courseString = courseString.concat(", ");
            courseString = courseString.concat(courseList[i]);
        }
        courseText.setText(courseString);

        // set a listener for the school website link button
        Button schoolWebsiteButton = findViewById(R.id.schoolWebsiteButton);
        schoolWebsiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // get the URI to open up from the FullTextboxViewModel
                String website = intent.getStringExtra("website");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                startActivity(intent);
            }
        });

        // set a listener for the map view button
        Button viewOnMapButton = findViewById(R.id.schoolOnMapButton);
        // add school name, longitude and latitude to the map activity
        viewOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // go to the SchoolOnMapView activity
                Intent mapIntent = new Intent(getApplicationContext(),SchoolOnMapView.class);
                mapIntent.putExtra("school_name",name);
                mapIntent.putExtra("longitude",intent.getDoubleExtra("longitude",-1));
                mapIntent.putExtra("latitude",intent.getDoubleExtra("latitude",-1));
                startActivity(mapIntent);
            }
        });
    }
}
