package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
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
        String autonomyType = intent.getStringExtra("autonomyType");
        String gender = intent.getStringExtra("gender");
        String giftedEducation = intent.getStringExtra("giftedEducation");
        String integratedProgram = intent.getStringExtra("integratedProgram");
        String sapSchool = intent.getStringExtra("sap");
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
        //postalCodeText.setText(postalCode);

        TextView tele1Text = findViewById(R.id.full_textbox_school_telephone_number_1);
        tele1Text.setText(telephoneNumber1);

        TextView tele2Text = findViewById(R.id.full_textbox_school_telephone_number_2);
        tele2Text.setText(telephoneNumber2);

        TextView visionText = findViewById(R.id.full_textbox_school_vision);
        visionText.setText(vision);

        TextView missionText = findViewById(R.id.full_textbox_school_mission);
        missionText.setText(mission);

        TextView autonomyText = findViewById(R.id.full_textbox_school_autonomy_type);
        autonomyText.setText(autonomyType);

        TextView genderText = findViewById(R.id.full_textbox_school_gender);
        genderText.setText(gender);

        TextView giftedText = findViewById(R.id.full_textbox_gifted_education_program_offered);
        giftedText.setText(giftedEducation);

        TextView integratedText = findViewById(R.id.full_textbox_integrated_program);
        integratedText.setText(integratedProgram);

        TextView sapText = findViewById(R.id.full_textbox_SAP_school);
        sapText.setText(sapSchool);

        TextView zoneText = findViewById(R.id.full_textbox_zone_code);
        zoneText.setText(zoneCode);

        TextView clusterText = findViewById(R.id.full_textbox_cluster_code);
        clusterText.setText(clusterCode);

        TextView ccaText = findViewById(R.id.full_textbox_cca);
        ccaText.setText(String.join(", ",ccaList));

        TextView courseText = findViewById(R.id.full_textbox_course);
        courseText.setText(String.join(", ",courseList));

        // Connect button
    }
}
