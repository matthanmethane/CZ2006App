package com.example.testapp.viewmodel;

import android.app.Application;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.db.entity.SchoolEntity;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * The type SchoolEntity list view model. Used as an additional abstraction between DataRepository and SchoolList.
 */
public class SchoolListViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<SchoolEntity>> mObservableSchools;

    /**
     * Instantiates a new SchoolEntity list view model.
     *
     * @param application the application
     */
    public SchoolListViewModel(Application application) {
        super(application);

        mObservableSchools = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableSchools.setValue(null);

        mRepository = ((EmpathyApp) application).getRepository();
        LiveData<List<SchoolEntity>> schoolNames = mRepository.getSchools();

        // observe the changes of the products from the database and forward them
        mObservableSchools.addSource(schoolNames, mObservableSchools::setValue);
    }

    /**
     * Expose the LiveData Schools query so the UI can observe it.
     *
     * @return the schools
     */
    public LiveData<List<SchoolEntity>> getSchools() {
        return mObservableSchools;
    }

    /**
     * Gets schools by search name.
     *
     * @param schoolName the school name
     * @return the schools by search name
     */
    public LiveData<List<SchoolEntity>> getSchoolsBySearchName(String schoolName) {
        return mRepository.getSchoolsBySearchPattern(schoolName);
    }

}
