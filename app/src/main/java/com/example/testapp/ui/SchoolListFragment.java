package com.example.testapp.ui;


import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testapp.R;
import com.example.testapp.databinding.ListFragmentBinding;
import com.example.testapp.db.entity.SchoolEntity;
import com.example.testapp.viewmodel.SchoolListViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * A fragment which is used to store all search results in the form of a summary textbox for each result.
 */
public class SchoolListFragment extends Fragment {
    public static final String TAG = "SchoolListFragment"; // Used to identify this fragment.

    private SchoolsAdapter mSchoolAdapter; // Used to generate each summary textbox.

    private ListFragmentBinding mBinding; // ListFragmentBinding is a class generated through databinding. It extends ViewDataBinding.

    /**
     * The correct part of a Fragment's lifecycle to add an Adapter to. See https://www.journaldev.com/9266/android-fragment-lifecycle
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);

        mSchoolAdapter = new SchoolsAdapter(mSchoolClickCallback); // create a new SchoolsAdapter

        mBinding.schoolNamesList.setAdapter(mSchoolAdapter); // set an adapter for the school_names_list RecyclerView.

        return mBinding.getRoot(); // must return a view from onCreateView. See https://developer.android.com/reference/android/support/v4/app/Fragment.html#oncreateview
    }

    /**
     * Called whenever this fragment is created together with the activity.
     * This is called after onCreateView (see https://www.journaldev.com/9266/android-fragment-lifecycle)
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // initialize the SchoolListViewModel so stuff can be fetched.
        // Must use the ViewModel Factory for this,
        // since SchoolListFragment is the first Fragment to be created.
        // Subsequent fragments which require the ViewModel do not need to create it using the factory.
        // LEARNING POINT:
        //  source: https://developer.android.com/topic/libraries/architecture/viewmodel.html#sharing
        //  As we are sharing SchoolListViewModel between different fragments, the viewmodel should be
        //  instantiated in the context of an Activity, and not a fragment. Specifically:
        //  - ViewModelProviders.of(this, ...) will cause a runtime error on another fragment
        //  because the viewmodel will not be in the activity's scope.
        //  - ViewModelProviders.of(getActivity(), ...) will fix the error.
        SchoolListViewModel.Factory factory =
                new SchoolListViewModel.Factory(
                getActivity().getApplication(),
                getArguments().getInt("schoolLevel")
        );

        final SchoolListViewModel viewModel = ViewModelProviders.of(getActivity(), factory).get(SchoolListViewModel.class);

        System.out.println("School level of viewModel: " + viewModel.schoolLevel);

        // Change the list of schools to show up in the summary textboxes whenever the school search button is clicked.
        mBinding.schoolSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable query = mBinding.schoolSearchBox.getText();
                if (query == null || query.toString().isEmpty()) {
                    subscribeUi(viewModel.getSchools()); // return all schools
                } else {
                    subscribeUi(viewModel.getSchoolsBySearchName(query.toString())); // return schools with name containing the text specified in the search box.
                }
            }
        });

        // instantiate the list of schools first
        subscribeUi(viewModel.getSchools());
    }

    /**
     * Notifys the schoolAdapter (which generates the summary textbox of all schools) of a change in data.
     *
     * @param liveData
     */
    private void subscribeUi(LiveData<List<SchoolEntity>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, new Observer<List<SchoolEntity>>() {
            @Override
            public void onChanged(@Nullable List<SchoolEntity> mySchoolEntities) {
                if (mySchoolEntities != null) {
                    mBinding.setIsLoading(false);
                    mSchoolAdapter.setSchoolsList(mySchoolEntities);
                } else {
                    mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                mBinding.executePendingBindings();
            }
        });
    }

    /**
     * set a listener for each school 'card'
     */
    private final SchoolNameTextboxClickCallback mSchoolClickCallback = new SchoolNameTextboxClickCallback() {
        @Override
        public void onClick(String schoolName) {

            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) { //ensure that this fragment has started already

                SchoolFullTextboxFragment schoolFullTextboxFragment = SchoolFullTextboxFragment.forSchool(schoolName);

                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack("school")
                        .replace(R.id.fragment_container,
                                schoolFullTextboxFragment, null).commit();
            }
        }
    };
}
