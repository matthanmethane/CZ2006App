package com.example.testapp.viewmodel;

import android.app.Application;

import com.example.testapp.db.entity.SchoolEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

public class ResultViewModel extends ViewModel {
    public int filterMode = 0;
    /*
    filter_mode =   0 is default alphabetically
                    1 is distance
                    2 is cut-off points
     */

    public int userPostalCode;

    public void setFilterMode(int filter_mode) {
        this.filterMode = filter_mode;
    }

    public void setUserPostalCode(int userPostalCode) {
        this.userPostalCode = userPostalCode;
    }

    public List<SchoolEntity> sortSchools(List<SchoolEntity> schools) {
        List<SchoolEntity> sorted = new ArrayList<>();
        switch (filterMode) {
            case 0:
                sorted = alphabetSort(schools);
                break;
            case 1:
                break;
            case 2:
                break;
        }
        return sorted;
    }

    private List<SchoolEntity> alphabetSort(List<SchoolEntity> schools) {
        List<SchoolEntity> sorted = new ArrayList<SchoolEntity>();
        for (int i = 0; i < schools.size(); i ++) {
            int j;
            for (j = 0; j < i; j ++) {
                if (schools.get(i).getSchoolName().compareTo(sorted.get(j).getSchoolName()) < 0)
                    break;
            }
            sorted.add(j,schools.get(i));
        }
        return sorted;
    }

//    private List<SchoolEntity> distanceSort(List<SchoolEntity> schools) {
//        // API expires every 3 days
//        String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjI1NTcsInVzZXJfaWQiOjI1NTcsImVtYWlsIjoiamVzc2x5bi5jaGV3LnMueUBnbWFpbC5jb20iLCJmb3JldmVyIjpmYWxzZSwiaXNzIjoiaHR0cDpcL1wvb20yLmRmZS5vbmVtYXAuc2dcL2FwaVwvdjJcL3VzZXJcL3Nlc3Npb24iLCJpYXQiOjE1NTQwMjY4NDcsImV4cCI6MTU1NDQ1ODg0NywibmJmIjoxNTU0MDI2ODQ3LCJqdGkiOiJlZjg1MzhkODE5YzliNDc5MTgxOWY1Y2IwODAzNmEzYyJ9.rQNaZazgc-r26i6LEQImrqgCUh3CknoZxY1yinA296w";
//        List<SchoolEntity> sorted = new ArrayList<>();
//        List<ArrayList> distanceList = new ArrayList<>();
//        // Map index to distance
//        for (int i = 0; i < schools.size(); i ++) {
//
//        }
//    }
}

