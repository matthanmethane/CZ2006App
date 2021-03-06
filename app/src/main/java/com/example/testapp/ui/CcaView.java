package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * View that enables user to choose that CCA(s) the school must have.
 */
public class CcaView extends AppCompatActivity {
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
     * Generate the UI display of the different CCAs the user can select from.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cca_view);

        Intent intent = getIntent();
        int schoolLevel = intent.getIntExtra("schoolLevel",-1);
        String address = intent.getStringExtra("address");
        String[] courses = intent.getStringArrayExtra("courses");

        String schoolLvl;

        switch(schoolLevel){
            case 1:
                schoolLvl = "Primary School";
                break;
            case 2:
                schoolLvl = "Secondary School";
                break;
            case 3 :
                schoolLvl = "Junior College";
                break;
            default:
                schoolLvl = ".";
                break;
        }
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Set CCA(Step 4 of 4)");

        TextView viewLvlAddressSubject = (TextView) findViewById(R.id.view_lvl_address_subject);
        String subjectStr = "";
        int num = 0;
        if(courses.length <3)
            num = courses.length;
        else
            num = 3;
        for(int i=0;i<num;i++){
            subjectStr += courses[i]+"|";
        }
        if(courses.length>3)
            subjectStr += "...";

        String viewLvlAddressSubjectText= "School Level: "+schoolLvl+"\nAddress: "
                +address+"\nSubject: "+subjectStr+"\n";
        viewLvlAddressSubject.setText(viewLvlAddressSubjectText);

        // Display all CCAs
        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();
        List<String> allCCAs = dataRepository.getCCAsForLevel(schoolLevel);
        LinearLayout ll = findViewById(R.id.ccaLL);

        for (String cca : allCCAs) {
            CheckBox ccaCheckbox = new CheckBox(this);
            ccaCheckbox.setText(cca);
            ll.addView(ccaCheckbox);
        }

        Button btn = (Button) findViewById(R.id.submit_cca);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get chosen cca
                List<String> selectedCCAs = new ArrayList<String>();
                final int childCount = ll.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    CheckBox box = (CheckBox)ll.getChildAt(i);
                    if (box.isChecked()) {
                        selectedCCAs.add(box.getText().toString());
                    }
                }
                String[] ccas = selectedCCAs.toArray(new String[0]);

                Intent openSearch = new Intent(getApplicationContext(),ResultView.class);
                openSearch.putExtra("schoolLevel",schoolLevel);
                openSearch.putExtra("address",address);
                openSearch.putExtra("courses",courses);
                openSearch.putExtra("ccas",ccas);
                startActivity(openSearch);
            }
        });

    }
}