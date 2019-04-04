package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.R;
import com.example.testapp.db.entity.Bookmark;
import com.example.testapp.db.entity.SchoolEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Bookmark view.
 */
public class BookmarkView extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_view);

        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();

        List<SchoolEntity> schools = getBookmarkedSchools();
        displaySchool(schools);
    }

    /**
     * Retrieve user's bookmarks.
     * @return list of bookmark schools
     */
    public List<SchoolEntity> getBookmarkedSchools() {
        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();

        final List<Bookmark> bookmarks = dataRepository.getBookmarks();
        List<SchoolEntity> schools = new ArrayList<>();
        for (Bookmark b: bookmarks) {
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
        if (schools.size() <= 0)
            return;

        LinearLayout schoolList = findViewById(R.id.bookmarkList);
        if (schoolList.getChildCount() > 0)
            schoolList.removeAllViews();

        for (int i = 0; i < schools.size(); i++) {
            LinearLayout box = new LinearLayout(this);
            box.setOrientation(LinearLayout.HORIZONTAL);
            TextView schoolName = new TextView(this);
            schoolName.setText(schools.get(i).getSchoolName());

            SchoolEntity school = schools.get(i);
            Button button = new Button(this);
            box.addView(schoolName);
            box.addView(button);
            schoolList.addView(box);

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
                    schoolList.removeView(box);
                }
            });
        }
    }
}