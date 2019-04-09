package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.R;
import com.example.testapp.db.entity.Bookmark;
import com.example.testapp.db.entity.SchoolEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Bookmark view.
 */
public class BookmarkView extends AppCompatActivity {
    int bookmarkCnt = 0;
    List<SchoolEntity> compareSchools = new ArrayList<SchoolEntity>();

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
     * Generate the UI display of all the bookmarks saved by the user. The bookmarks are arranged by education level.
     * @param savedInstanceState the saved instane state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_view);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Bookmark");

        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();

        List<SchoolEntity> schools = getBookmarkedSchools();
        displaySchool(schools);

        Button compareBtn = findViewById(R.id.compare_btn);
        compareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bookmarkCnt==2){
                    // Check whether the schools are of the same type
                    boolean flag = false;

                    if (dataRepository.findSchools(compareSchools.get(0).getSchoolName(),new ArrayList<>(), new ArrayList<>(), 1,-1).size() == 1) {
                        if (dataRepository.findSchools(compareSchools.get(1).getSchoolName(),new ArrayList<>(), new ArrayList<>(), 1,-1).size() == 1)
                            flag = true;
                    }
                    if (dataRepository.findSchools(compareSchools.get(0).getSchoolName(),new ArrayList<>(), new ArrayList<>(), 2,-1).size() == 1) {
                        if (dataRepository.findSchools(compareSchools.get(1).getSchoolName(),new ArrayList<>(), new ArrayList<>(), 2,-1).size() == 1)
                            flag = true;
                    }
                    if (dataRepository.findSchools(compareSchools.get(0).getSchoolName(),new ArrayList<>(), new ArrayList<>(), 3,-1).size() == 1) {
                        if (dataRepository.findSchools(compareSchools.get(1).getSchoolName(),new ArrayList<>(), new ArrayList<>(), 3,-1).size() == 1)
                            flag = true;
                    }

                    if (flag == false) {
                        Toast.makeText(getApplicationContext(),"Please choose 2 schools of the same education level",Toast.LENGTH_LONG).show();
                        return;
                    }

                    Intent openCompare = new Intent(getApplicationContext(),compareView.class);
                    int i = 0;
                    for(SchoolEntity school:compareSchools){

                        openCompare.putExtra("name".concat(Integer.toString(i)), school.getSchoolName());
                        openCompare.putExtra("address".concat(Integer.toString(i)), school.getPhysicalAddress());
                        openCompare.putExtra("postalCode".concat(Integer.toString(i)), school.getPostalCode());
                        openCompare.putExtra("telephoneNumber1".concat(Integer.toString(i)), school.getTelephoneNumber1());
                        openCompare.putExtra("telephoneNumber2".concat(Integer.toString(i)), school.getTelephoneNumber2());
                        openCompare.putExtra("vision".concat(Integer.toString(i)), school.getVision());
                        openCompare.putExtra("mission".concat(Integer.toString(i)), school.getMission());
                        openCompare.putExtra("autonomyType".concat(Integer.toString(i)), school.getSchoolAutonomyType());
                        openCompare.putExtra("gender".concat(Integer.toString(i)), school.getSchoolGender());
                        openCompare.putExtra("giftedEducation".concat(Integer.toString(i)), school.getGiftedEducationProgramOffered());
                        openCompare.putExtra("integratedProgram".concat(Integer.toString(i)), school.getIntegratedProgram());
                        openCompare.putExtra("sap".concat(Integer.toString(i)), school.getSAPSchool());
                        openCompare.putExtra("zoneCode".concat(Integer.toString(i)), school.getZoneCode());
                        openCompare.putExtra("clusterCode".concat(Integer.toString(i)), school.getClusterCode());
                        i++;
                    }
                    startActivity(openCompare);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please choose 2 schools",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     * Retrieve user's bookmarks.
     * @return list of bookmark schools
     */
    public List<SchoolEntity> getBookmarkedSchools() {
        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();

        final List<Bookmark> bookmarks = dataRepository.getBookmarks();
        List<SchoolEntity> schools = new ArrayList<>();
        for (Bookmark b : bookmarks) {
            SchoolEntity s;
            try {
                s = dataRepository.getSchool(b.getSchoolName());
            } catch (Exception e) {
                break;
            }
            schools.add(s);
        }
        return schools;
    }

    /**
     * Display bookmarks.
     * @param schools bookmarked schools
     */
    public void displaySchool(List<SchoolEntity> schools) {

        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();

        if (schools.size() <= 0)
            return;

        LinearLayout schoolListPri = findViewById(R.id.bookmarkListPri);
        LinearLayout schoolListSec = findViewById(R.id.bookmarkListSec);
        LinearLayout schoolListJc = findViewById(R.id.bookmarkListJc);

        if (schoolListPri.getChildCount() > 0)
            schoolListPri.removeAllViews();
        if (schoolListSec.getChildCount() > 0)
            schoolListSec.removeAllViews();
        if (schoolListJc.getChildCount() > 0)
            schoolListJc.removeAllViews();

        for (int i = 0; i < schools.size(); i++) {
            RelativeLayout box = new RelativeLayout(this);

            RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );
            RelativeLayout.LayoutParams LayoutParamsButton = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            RelativeLayout.LayoutParams LayoutParamsText = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            RelativeLayout.LayoutParams LayoutParamsCheck = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );


            TextView schoolName = new TextView(this);
            schoolName.setText(schools.get(i).getSchoolName());

            SchoolEntity school = schools.get(i);
            CheckBox button = new CheckBox(this, null, android.R.attr.starStyle);
            button.setChecked(true);

            CheckBox bookmarkBtn = new CheckBox(this, null, android.R.attr.checkboxStyle);
            bookmarkBtn.setId(i);

            LayoutParamsButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            LayoutParamsCheck.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            LayoutParamsText.addRule(RelativeLayout.CENTER_VERTICAL);
            LayoutParamsText.addRule(RelativeLayout.CENTER_HORIZONTAL);

            box.setLayoutParams(layoutparams);
            bookmarkBtn.setLayoutParams(LayoutParamsCheck);
            button.setLayoutParams(LayoutParamsButton);
            schoolName.setLayoutParams(LayoutParamsText);

            box.addView(bookmarkBtn);
            box.addView(schoolName);
            box.addView(button);
            box.setPadding(30,30,30,30);

            if(dataRepository.findSchools(schools.get(i).getSchoolName(),new ArrayList<>(), new ArrayList<>(), 1,-1).size() == 1)
                schoolListPri.addView(box);
            else if(dataRepository.findSchools(schools.get(i).getSchoolName(),new ArrayList<>(), new ArrayList<>(), 2,-1).size() == 1)
                schoolListSec.addView(box);
            else if(dataRepository.findSchools(schools.get(i).getSchoolName(),new ArrayList<>(), new ArrayList<>(), 3,-1).size() == 1)
                schoolListJc.addView(box);
            schoolName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
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
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();
                    dataRepository.deleteBookmark(school.getSchoolName());
                    if(dataRepository.findSchools(school.getSchoolName(),new ArrayList<>(), new ArrayList<>(), 1,-1).size() == 1)
                        schoolListPri.removeView(box);
                    else if(dataRepository.findSchools(school.getSchoolName(),new ArrayList<>(), new ArrayList<>(), 2,-1).size() == 1)
                        schoolListSec.removeView(box);
                    else if(dataRepository.findSchools(school.getSchoolName(),new ArrayList<>(), new ArrayList<>(), 3,-1).size() == 1)
                        schoolListJc.removeView(box);

                    if(bookmarkBtn.isChecked()){
                        compareSchools.remove(school);
                        bookmarkCnt--;
                    }
                }
            });

            bookmarkBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        compareSchools.add(school);
                        bookmarkCnt++;
                    }
                    else {
                        compareSchools.remove(school);
                        bookmarkCnt--;
                    }
                    if (bookmarkCnt > 2) {
                        bookmarkCnt--;
                        Toast.makeText(getApplicationContext(),"2 schools already selected",Toast.LENGTH_LONG).show();
                        bookmarkBtn.setChecked(false);
                    }
                }
            });


        }
        return;
    }

    @Override
    public void onResume() {
        super.onResume();
        bookmarkCnt = 0;
        compareSchools.clear();
        List<SchoolEntity> schools = getBookmarkedSchools();
        displaySchool(schools);
    }
}