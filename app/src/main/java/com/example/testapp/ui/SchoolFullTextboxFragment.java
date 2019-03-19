package com.example.testapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.testapp.R;
import com.example.testapp.databinding.SchoolFullTextboxBinding;
import com.example.testapp.generated.callback.OnClickListener;
import com.example.testapp.viewmodel.FullTextboxViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * The School full textbox fragment. It is the fragment for showing the details of each school in a textbox format.
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

    /**
     * Create the ViewModel associated with the School selected and bind it to the UI.
     * **/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // This Factory pattern allows you to inject the school name into the new View Model.
        FullTextboxViewModel.Factory factory = new FullTextboxViewModel.Factory(
                getActivity().getApplication(), getArguments().getString(KEY_SCHOOL_NAME));

        final FullTextboxViewModel model = ViewModelProviders.of(this, factory)
                .get(FullTextboxViewModel.class);

        schoolFullTextboxBinding.setSchoolFullTextboxViewModel(model);

        // set a listener for the school website link button
        Button schoolWebsiteButton = this.getActivity().findViewById(R.id.schoolWebsiteButton);

        schoolWebsiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // get the URI to open up from the FullTextboxViewModel
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(schoolFullTextboxBinding.getSchoolFullTextboxViewModel().sekolah.homePageAddress));
                startActivity(intent);
            }
        });

        // TODO: set a listener for the map view button
        // TODO: create map view
    }

    /** Creates SchoolEntity FullTextbox Fragment for specific school name */
    public static SchoolFullTextboxFragment forSchool(String schoolName) {
        SchoolFullTextboxFragment fragment = new SchoolFullTextboxFragment();
        Bundle args = new Bundle();
        args.putString(KEY_SCHOOL_NAME, schoolName);
        fragment.setArguments(args);
        return fragment;
    }
}
