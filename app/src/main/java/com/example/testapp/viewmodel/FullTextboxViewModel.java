package com.example.testapp.viewmodel;

import android.app.Application;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.db.entity.SchoolEntity;
import com.example.testapp.db.entity.SchoolToCCA;
import com.example.testapp.db.entity.SchoolToCourse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FullTextboxViewModel extends AndroidViewModel {

    public final String mSchoolName;

//    public ObservableField<SchoolEntity> sekolah = new ObservableField<>();

    public final SchoolEntity sekolah;

//    private final LiveData<SchoolEntity> mObservableSchool;

    private final LiveData<List<SchoolToCCA>> mObservableSchoolToCCAs;

    private final LiveData<List<SchoolToCourse>> mObservableSchoolToCourses;

    public FullTextboxViewModel(@NonNull Application application, DataRepository repository,
                            final String schoolName) throws Exception {
        super(application);

        mSchoolName = schoolName;

//        mObservableSchool = repository.getSchool(mSchoolName);
        sekolah = repository.getSchool(mSchoolName);
        mObservableSchoolToCCAs = repository.getCCAsOfASchool(mSchoolName);
        mObservableSchoolToCourses = repository.getCoursesOfASchool(mSchoolName);
    }

    /**
     * Expose the LiveData CCA and Courses query so the UI can observe it.
     */
//    public LiveData<SchoolEntity> getSchool() {return mObservableSchool; }

    public LiveData<List<SchoolToCCA>> getCCAs() {
        return mObservableSchoolToCCAs;
    }

    public LiveData<List<SchoolToCourse>> getCourses() {
        return mObservableSchoolToCourses;
    }

////    public String getSchoolName() {return sekolah.schoolName;}
//
//    public void setSekolah(SchoolEntity sekolah) {
//        System.out.println("I got called. Yay!");
//        this.sekolah.set(sekolah);
//    }

    /**
     * A creator used to inject the school name into the ViewModel
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
            //noinspection unchecked
            try {
                return (T) new FullTextboxViewModel(mApplication, mRepository, mSchoolName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
