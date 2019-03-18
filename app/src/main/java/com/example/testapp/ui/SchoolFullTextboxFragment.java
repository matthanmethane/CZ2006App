package com.example.testapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testapp.R;
import com.example.testapp.databinding.SchoolFullTextboxBinding;
import com.example.testapp.viewmodel.FullTextboxViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * The type SchoolEntity full textbox fragment. It is the fragment for showing the details of each school in a textbox format.
 * TODO: complete this
 */
public class SchoolFullTextboxFragment extends Fragment {
    private static final String KEY_SCHOOL_NAME = "school_name"; // used to identify the fragment since there are multiple schools

    private SchoolFullTextboxBinding schoolFullTextboxBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate this data binding layout
        schoolFullTextboxBinding = DataBindingUtil.inflate(inflater, R.layout.school_full_textbox, container, false);

        return schoolFullTextboxBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FullTextboxViewModel.Factory factory = new FullTextboxViewModel.Factory(
                getActivity().getApplication(), getArguments().getString(KEY_SCHOOL_NAME));

        final FullTextboxViewModel model = ViewModelProviders.of(this, factory)
                .get(FullTextboxViewModel.class);

        schoolFullTextboxBinding.setSchoolFullTextboxViewModel(model);

//        subscribeToModel(model);
    }

//    private void subscribeToModel(final FullTextboxViewModel model) {
//
//        // Observe school data
//        model.getObservableProduct().observe(this, new Observer<SchoolDetailsView>() {
//            @Override
//            public void onChanged(@Nullable SchoolDetailsView schoolDetailsView) {
//                model.setSchool(schoolDetailsView);
//            }
//        });
//    }

    /** Creates SchoolEntity FullTextbox Fragment for specific school name */
    public static SchoolFullTextboxFragment forSchool(String schoolName) {
        SchoolFullTextboxFragment fragment = new SchoolFullTextboxFragment();
        Bundle args = new Bundle();
        args.putString(KEY_SCHOOL_NAME, schoolName);
        fragment.setArguments(args);
        return fragment;
    }
}
