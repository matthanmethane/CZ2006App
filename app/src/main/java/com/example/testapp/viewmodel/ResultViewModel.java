package com.example.testapp.viewmodel;

import com.example.testapp.DataRepository;
import com.example.testapp.db.entity.SchoolEntity;
import com.example.testapp.filter.AlphabetFilter;
import com.example.testapp.filter.DistanceFilter;
import com.example.testapp.filter.Filter;
import com.example.testapp.filter.ScoreFilter;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

/**
 * Class to retrieve sorted schools for ResultView
 */
public class ResultViewModel extends ViewModel {
    private String userPostalCode;
    private int filterMode = 0;
    private int schoolLevel;
    private DataRepository repo;
    /*
    filter_mode =   0 is default alphabetically
                    1 is distance
                    2 is cut-off points
     */

    /**
     * Set the type of filters. 0 for default (alphabetical order), 1 for distance, 2 for entryscore.
     * @param filter_mode
     */
    public void setFilterMode(int filter_mode) {
        this.filterMode = filter_mode;
    }

    /**
     * Set school level.
     * @param schoolLevel school level
     */
    public void setSchoolLevel(int schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    /**
     * Set data respository.
     * @param repo data respository
     */
    public void setRepo(DataRepository repo) {
        this.repo = repo;
    }

    /**
     * Set user's postal code.
     * @param userPostalCode postal code
     */
    public void setUserPostalCode(String userPostalCode) {
        this.userPostalCode = userPostalCode;
    }

    /**
     * Return sorted schools.
     * @param schools list of schools
     * @return list of sorted schools
     */
    public List<SchoolEntity> sortSchools(List<SchoolEntity> schools) {
        List<SchoolEntity> sorted = new ArrayList<>();
        switch (filterMode) {
            case 0:
                Filter alphabetFilter = new AlphabetFilter(schools);
                sorted = alphabetFilter.getSorted();
                break;
            case 1:
                DistanceFilter distanceFilter = new DistanceFilter(userPostalCode,schools);
                try {
                    sorted = distanceFilter.execute().get();

                } catch (Exception e) {
                    System.out.println("Error occurred.");
                }
                break;
            case 2:
                Filter scoreFilter = new ScoreFilter(schools, schoolLevel, repo);
                sorted = scoreFilter.getSorted();
                break;
        }
        return sorted;
    }
}