package com.example.testapp.viewmodel;

import android.app.Application;
import android.content.Intent;

import com.example.testapp.db.entity.SchoolEntity;
import com.example.testapp.filter.AlphabetFilter;
import com.example.testapp.filter.DistanceFilter;
import com.example.testapp.filter.Filter;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

public class ResultViewModel extends ViewModel {
    private String userPostalCode;
    private int filterMode = 0;
    /*
    filter_mode =   0 is default alphabetically
                    1 is distance
                    2 is cut-off points
     */

    public void setFilterMode(int filter_mode) {
        this.filterMode = filter_mode;
    }

    public void setUserPostalCode(String userPostalCode) {
        this.userPostalCode = userPostalCode;
    }

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

                break;
        }
        return sorted;
    }
}