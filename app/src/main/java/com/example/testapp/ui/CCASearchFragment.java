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

public class CCASearchFragment extends Fragment {

    private SchoolListViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ccasearch_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // initialize the SchoolListViewModel so stuff can be fetched. Must use the ViewModel Factory for this.
        mViewModel = ViewModelProviders.of(getActivity()).get(SchoolListViewModel.class);

        System.out.println("From CCASearchFragment, schoolLevel: " + mViewModel.schoolLevel);

        /* dynamically add checkboxes to layout based on list of CCAs */
        // 1. Fetch all CCAs first
        List<String> allCCAs = mViewModel.getCCAs();
        // 2. For each CCA, create checkbox and add to layout
        LinearLayout CCASearchMenuCheckboxContainer = getActivity().findViewById(R.id.CCACheckboxInnerContainer);
        for (String cca : allCCAs)
        {
            CheckBox ccaCheckbox = new CheckBox(getContext());
            ccaCheckbox.setText(cca);
            ccaCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCCACheckboxClicked(view);
                }
            });
            CCASearchMenuCheckboxContainer.addView(ccaCheckbox);
        }

        // Toggle visibility of the CCASearchMenu layout once the CCASearchButton is clicked
        FloatingActionButton CCASearchMenuButton = getActivity().findViewById(R.id.CCASearchMenuButton);
        CCASearchMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout CCASearchMenuLayout = getActivity().findViewById(R.id.CCASearchMenuLayout);
                LinearLayout CourseSearchMenuLayout = getActivity().findViewById(R.id.CourseSearchMenuLayout);
                if (CCASearchMenuLayout.getVisibility() == View.VISIBLE)
                {
                    CCASearchMenuLayout.setVisibility(View.GONE);
                }
                else
                {
                    CCASearchMenuLayout.setVisibility(View.VISIBLE);
                    if (CourseSearchMenuLayout.getVisibility() == View.VISIBLE)
                    {
                        CourseSearchMenuLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    public void onCCACheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String cca = ((CheckBox) view).getText().toString();
        if (checked)
        {
            // add the CCA into the list of selected CCAs in the viewmodel
            mViewModel.addToSelectedCCAs(cca);
        }
        else
        {
            // remove the CCA from the list of selected CCAs in the viewmodel
            mViewModel.removeFromSelectedCCAs(cca);
        }
    }
}
