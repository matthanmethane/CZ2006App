package com.example.testapp.viewmodel;

import android.app.Application;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.db.entity.SchoolEntity;
import com.example.testapp.db.entity.SchoolToCCA;
import com.example.testapp.db.entity.SchoolToCourse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FullTextboxViewModel extends AndroidViewModel {

    //TODO: find out how to perform better encapsulation of data in the ViewModel
    public final String mSchoolName;

    public final SchoolEntity sekolah;

    public String stringOfCCAsOfferedBySchool = "";

    public String stringOfCoursesOfferedBySchool = "";

    public FullTextboxViewModel(@NonNull Application application, DataRepository repository,
                                final String schoolName) throws Exception {
        super(application);

        mSchoolName = schoolName;

        sekolah = repository.getSchool(mSchoolName);
        List<SchoolToCCA> schoolToCCAs = repository.getCCAsOfASchool(mSchoolName);
        for (SchoolToCCA schoolToCCA : schoolToCCAs) {
            stringOfCCAsOfferedBySchool += (schoolToCCA.ccaName + ", ");
        }

        // sanitize the string output
        if (stringOfCCAsOfferedBySchool.equalsIgnoreCase("")) {
            stringOfCCAsOfferedBySchool = "No Data Available";
        } else {
            stringOfCCAsOfferedBySchool =
                    stringOfCCAsOfferedBySchool.substring(0,
                            stringOfCCAsOfferedBySchool.lastIndexOf(','));
        }

        List<SchoolToCourse> schoolToCourses = repository.getCoursesOfASchool(mSchoolName);
        for (SchoolToCourse schoolToCourse : schoolToCourses) {
            stringOfCoursesOfferedBySchool += (schoolToCourse.courseName + ", ");
        }

        // sanitize the string output
        if (stringOfCoursesOfferedBySchool.equalsIgnoreCase("")) {
            stringOfCoursesOfferedBySchool = "No Data Available";
        } else {
            stringOfCoursesOfferedBySchool =
                    stringOfCoursesOfferedBySchool.substring(0,
                            stringOfCoursesOfferedBySchool.lastIndexOf(','));
        }
    }

    /**
     * A creator used to inject the school name into the instantiation of a new ViewModel (so that data inside can be tailored to it)
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final String mSchoolName;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, String schoolName) {
            mApplication = application;
            mSchoolName = schoolName;
            mRepository = ((EmpathyApp) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            try {
                return (T) new FullTextboxViewModel(mApplication, mRepository, mSchoolName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
