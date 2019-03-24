package com.example.testapp.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.testapp.R;
import com.example.testapp.viewmodel.SchoolListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseSearchFragment extends Fragment {

    private SchoolListViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.coursesearch_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // initialize the SchoolListViewModel so stuff can be fetched. Must use the ViewModel Factory for this.
        mViewModel = ViewModelProviders.of(getActivity()).get(SchoolListViewModel.class);

        /* dynamically add checkboxes to layout based on list of Courses */
        // 1. Fetch all Courses first
        List<String> allCourses = mViewModel.getCourses();
        // 2. For each Course, create checkbox and add to layout
        LinearLayout CourseSearchMenuCheckboxContainer = getActivity().findViewById(R.id.CourseCheckboxInnerContainer);
        for (String course : allCourses)
        {
            CheckBox courseCheckbox = new CheckBox(getContext());
            courseCheckbox.setText(course);
            courseCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCourseCheckboxClicked(view);
                }
            });
            CourseSearchMenuCheckboxContainer.addView(courseCheckbox);
        }

        // Toggle visibility of the CCASearchMenu layout once the CCASearchButton is clicked
        FloatingActionButton CourseSearchMenuButton = getActivity().findViewById(R.id.CourseSearchMenuButton);
        CourseSearchMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout CCASearchMenuLayout = getActivity().findViewById(R.id.CCASearchMenuLayout);
                LinearLayout CourseSearchMenuLayout = getActivity().findViewById(R.id.CourseSearchMenuLayout);
                if (CourseSearchMenuLayout.getVisibility() == View.VISIBLE)
                {
                    CourseSearchMenuLayout.setVisibility(View.GONE);
                }
                else
                {
                    CourseSearchMenuLayout.setVisibility(View.VISIBLE);
                    if (CCASearchMenuLayout.getVisibility() == View.VISIBLE)
                    {
                        CCASearchMenuLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    public void onCourseCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String course = ((CheckBox) view).getText().toString();
        if (checked)
        {
            // add the CCA into the list of selected CCAs in the viewmodel
            mViewModel.addToSelectedCourses(course);
        }
        else
        {
            // remove the CCA from the list of selected CCAs in the viewmodel
            mViewModel.removeFromSelectedCourses(course);
        }
    }
}
