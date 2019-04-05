package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.R;
import com.example.testapp.db.entity.SchoolToCCA;
import com.example.testapp.db.entity.SchoolToCourse;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class compareView extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_view);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Bookmark Result");

        String[] name = new String[2];
        String[] address = new String[2];
        int[] postalCode = new int[2];
        String[] telephoneNumber1 = new String[2];
        String[] telephoneNumber2 = new String[2];
        String[] vision = new String[2];
        String[] mission = new String[2];
        int[] autonomyType = new int[2];
        String[] gender = new String[2];
        int[] giftedEducation = new int[2];
        int[] integratedProgram = new int[2];
        int[] sap = new int[2];
        String[] zoneCode = new String[2];
        String[] clusterCode = new String[2];

        Intent intent = getIntent();
        for(int i=0;i<2;i++){
            name[i]=intent.getStringExtra("name"+i);
            address[i]=intent.getStringExtra("address"+i);
            postalCode[i]=intent.getIntExtra("postalCode"+i,-1);
            telephoneNumber1[i]=intent.getStringExtra("telephoneNumber1"+i);
            telephoneNumber2[i]=intent.getStringExtra("telephoneNumber2"+i);
            vision[i]=intent.getStringExtra("vision"+i);
            mission[i]=intent.getStringExtra("mission"+i);
            autonomyType[i]=intent.getIntExtra("autonomyType"+i,-1);
            gender[i]=intent.getStringExtra("gender"+i);
            giftedEducation[i]=intent.getIntExtra("giftedEducation"+i,-1);
            integratedProgram[i]=intent.getIntExtra("integratedProgram"+i,-1);
            sap[i]=intent.getIntExtra("sap"+i,-1);
            zoneCode[i]=intent.getStringExtra("zoneCode"+i);
            clusterCode[i]=intent.getStringExtra("clusterCode"+i);
        }
        TextView name1 = findViewById(R.id.name1);
        name1.setText(name[0]);
        TextView name2 = findViewById(R.id.name2);
        name2.setText(name[1]);

        TextView address1 = findViewById(R.id.address1);
        address1.setText(address[0]);
        TextView address2 = findViewById(R.id.address2);
        address2.setText(address[1]);

        TextView postalCode1 = findViewById(R.id.postalCode1);
        postalCode1.setText(Integer.toString(postalCode[0]));
        TextView postalCode2 = findViewById(R.id.postalCode2);
        postalCode2.setText(Integer.toString(postalCode[1]));

        TextView tele11 = findViewById(R.id.telephoneNumber11);
        tele11.setText(telephoneNumber1[0]);
        TextView tele12 = findViewById(R.id.telephoneNumber12);
        tele12.setText(telephoneNumber1[1]);

        TextView tele21 = findViewById(R.id.telephoneNumber21);
        tele21.setText(telephoneNumber2[0]);
        TextView tele22 = findViewById(R.id.telephoneNumber22);
        tele22.setText(telephoneNumber2[1]);
        /*Todo: Format, can remove? Comparing vision and mission might be unnecessary
        TextView vision1 = findViewById(R.id.vision1);
        vision1.setText(vision[0]);
        TextView vision2 = findViewById(R.id.vision2);
        vision2.setText(vision[1]);

        TextView mission1 = findViewById(R.id.mission1);
        mission1.setText(mission[0]);
        TextView mission2 = findViewById(R.id.mission2);
        mission2.setText(mission[1]);
        */
        TextView autonomyType1 = findViewById(R.id.autonomyType1);
        if (autonomyType[0] == 0) {
            autonomyType1.setText("YES");
        } else {
            autonomyType1.setText("NO");
        }
        TextView autonomyType2 = findViewById(R.id.autonomyType2);
        if (autonomyType[1] == 0) {
            autonomyType2.setText("YES");
        } else {
            autonomyType2.setText("NO");
        }

        TextView gender1 = findViewById(R.id.gender1);
        gender1.setText(gender[0]);
        TextView gender2 = findViewById(R.id.gender2);
        gender2.setText(gender[1]);

        TextView giftedEducation1 = findViewById(R.id.giftedEducation1);
        if (giftedEducation[0] == 0) {
            giftedEducation1.setText("YES");
        } else {
            giftedEducation1.setText("NO");
        }
        TextView giftedEducation2 = findViewById(R.id.giftedEducation2);
        if (giftedEducation[1] == 0) {
            giftedEducation2.setText("YES");
        } else {
            giftedEducation2.setText("NO");
        }

        TextView integratedProgram1 = findViewById(R.id.integratedProgram1);
        if (integratedProgram[0] == 0) {
            integratedProgram1.setText("YES");
        } else {
            integratedProgram1.setText("NO");
        }
        TextView integratedProgram2 = findViewById(R.id.integratedProgram2);
        if (integratedProgram[1] == 0) {
            integratedProgram2.setText("YES");
        } else {
            integratedProgram2.setText("NO");
        }

        TextView sap1 = findViewById(R.id.sap1);
        if (sap[0] == 0) {
            sap1.setText("YES");
        } else {
            sap1.setText("NO");
        }
        TextView sap2 = findViewById(R.id.sap2);
        if (sap[1] == 0) {
            sap2.setText("YES");
        } else {
            sap2.setText("NO");
        }

        TextView zoneCode1 = findViewById(R.id.zoneCode1);
        zoneCode1.setText(zoneCode[0]);
        TextView zoneCode2 = findViewById(R.id.zoneCode2);
        zoneCode2.setText(zoneCode[1]);

        TextView clusterCode1 = findViewById(R.id.clusterCode1);
        clusterCode1.setText(clusterCode[0]);
        TextView clusterCode2 = findViewById(R.id.clusterCode2);
        clusterCode2.setText(clusterCode[1]);

        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();
        List<SchoolToCCA> ccas1 = dataRepository.getSchoolCCAs(name[0]);
        String[] ccaList1 = new String[ccas1.size()];
        for (int i = 0; i < ccas1.size(); i ++) {
            ccaList1[i] = ccas1.get(i).ccaName;
        }


        List<SchoolToCourse> courses1 = dataRepository.getSchoolCourses(name[0]);
        String[] courseList1 = new String[courses1.size()];
        for (int i = 0; i < courses1.size(); i ++) {
            courseList1[i] = courses1.get(i).courseName;
        }

        List<SchoolToCCA> ccas2 = dataRepository.getSchoolCCAs(name[1]);
        String[] ccaList2 = new String[ccas2.size()];
        for (int i = 0; i < ccas2.size(); i ++) {
            ccaList2[i] = ccas2.get(i).ccaName;
        }


        List<SchoolToCourse> courses2 = dataRepository.getSchoolCourses(name[1]);
        String[] courseList2 = new String[courses2.size()];
        for (int i = 0; i < courses2.size(); i ++) {
            courseList2[i] = courses2.get(i).courseName;
        }

        LinearLayout cca1 = findViewById(R.id.cca1);
        /* String ccaString = "";
        if (ccaList.length > 1)
            ccaString = ccaList[0];*/
        for (int i = 1; i < ccaList1.length; i ++) {
            TextView textView = new TextView(this);
            textView.setText(ccaList1[i]);
            cca1.addView(textView);
        }


        LinearLayout course1 = findViewById(R.id.course1);
        /*String courseString = "";
        if (courseList.length > 1)
            courseString = courseList[0];*/
        for (int i = 1; i < courseList1.length; i ++) {
            TextView textView = new TextView(this);
            textView.setText(courseList1[i]);
            course1.addView(textView);
        }
        LinearLayout cca2 = findViewById(R.id.cca2);
        /* String ccaString = "";
        if (ccaList.length > 1)
            ccaString = ccaList[0];*/
        for (int i = 1; i < ccaList2.length; i ++) {
            TextView textView = new TextView(this);
            textView.setText(ccaList2[i]);
            cca2.addView(textView);
        }


        LinearLayout course2 = findViewById(R.id.course2);
        /*String courseString = "";
        if (courseList.length > 1)
            courseString = courseList[0];*/
        for (int i = 1; i < courseList2.length; i ++) {
            TextView textView = new TextView(this);
            textView.setText(courseList2[i]);
            course2.addView(textView);
        }

        ToggleButton ccaToggle1 = findViewById(R.id.cca1_toggle);
        ccaToggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    cca1.setVisibility(View.GONE);
                }
                else{
                    cca1.setVisibility(View.VISIBLE);
                }
            }
        });

        ToggleButton courseToggle1 = findViewById(R.id.course1_toggle);
        courseToggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    course1.setVisibility(View.GONE);
                }
                else{
                    course1.setVisibility(View.VISIBLE);
                }
            }
        });
        ToggleButton ccaToggle2= findViewById(R.id.cca2_toggle);
        ccaToggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    cca2.setVisibility(View.GONE);
                }
                else{
                    cca2.setVisibility(View.VISIBLE);
                }
            }
        });

        ToggleButton courseToggle2 = findViewById(R.id.course2_toggle);
        courseToggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    course2.setVisibility(View.GONE);
                }
                else{
                    course2.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
