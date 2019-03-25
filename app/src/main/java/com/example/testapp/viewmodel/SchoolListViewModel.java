package com.example.testapp.viewmodel;

import android.app.Application;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.db.entity.SchoolEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * The type SchoolEntity list view model. Used as an additional abstraction between DataRepository and SchoolList.
 */
public class SchoolListViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    private LiveData<List<SchoolEntity>> mObservableSchools;

    // save school level as well
    public final int schoolLevel;

    // list of CCAs associated with that school level
    private List<String> allCCAs;

    // list of selected CCAs
    private List<String> selectedCCAs = new ArrayList<String>();

    // list of selected courses
    private List<String> selectedCourses = new ArrayList<String>();

    /**
     * Instantiates a new SchoolEntity list view model.
     *
     * @param application the application
     */
    private SchoolListViewModel(Application application, int schoolLevel) {
        super(application);

        // set school level
        this.schoolLevel = schoolLevel;

        mRepository = ((EmpathyApp) application).getRepository();

        instantiateData();
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
     * Get all CCAs associated with the education level specified
     * @return
     */
    public List<String> getCCAs() {
        return allCCAs;
    }

    /**
     * Add to list of selected CCAs
     */
    public void addToSelectedCCAs(String cca) {
        selectedCCAs.add(cca);
        // TODO: update mObservableSchools and thus the UI somehow
    }

    /**
     * Removes from the list of selected CCAs
     * @param cca
     */
    public void removeFromSelectedCCAs(String cca) {
        selectedCCAs.remove(cca);
        // TODO: update mObservableSchools and thus the UI somehow
    }

    /**
     * Gets the list of selected CCAs
     */
    public List<String> getSelectedCCAs()
    {
        return selectedCCAs;
    }

    /**
     * Gets the list of selected courses
     */
    public List<String> getSelectedCourses()
    {
        return selectedCourses;
    }


    /**
     * Gets schools by search name.
     *
     * @param schoolName the school name
     * @return the schools by search name
     */
    public LiveData<List<SchoolEntity>> getSchoolsBySearchPattern(String schoolName, List<String> ccas, List<String> courses) {
        return mRepository.getSchoolsBySearchPattern(schoolName, ccas, courses, schoolLevel);
    }

    /**
     * Gets courses for that schooling level
     */
    public List<String> getCourses() {
        return mRepository.getCourses(schoolLevel);
    }

    /**
     * Add to selected courses
     */
    public void addToSelectedCourses(String course)
    {
        selectedCourses.add(course);
        // TODO: update mObservableSchools and thus the UI somehow
    }

    /**
     * Remove from selected courses
     */
    public void removeFromSelectedCourses(String course)
    {
        selectedCourses.remove(course);
    }

    /**
     * A creator used to inject the school name into the instantiation of a new ViewModel (so that data inside can be tailored to it)
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int schoolLevel;

        public Factory(@NonNull Application application, int schoolLevel) {
            mApplication = application;
            this.schoolLevel = schoolLevel;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            try {
                return (T) new SchoolListViewModel(mApplication, schoolLevel);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Instantiate mObservableSchools with data based on school level
     */
    private void instantiateData() {
        System.out.println("From SchoolListViewModel: " + schoolLevel);
        if (schoolLevel == 1) {
            mObservableSchools = mRepository.getPrimarySchools();
            allCCAs = mRepository.getPrimarySchoolCCAs();
        } else if (schoolLevel == 2 ){
            System.out.println("Secondary schools fetched from SchoolListViewModel");
            mObservableSchools = mRepository.getSecondarySchools();
            allCCAs = mRepository.getSecondarySchoolCCAs();
        } else if (schoolLevel == 3) {
            mObservableSchools = mRepository.getJuniorColleges();
            allCCAs = mRepository.getJuniorCollegeCCAs();
        } else {
            mObservableSchools = null;
            allCCAs = null;
        }
    }
}
