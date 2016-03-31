package com.udacity.popularmovies.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.entities.enums.Coordinator;
import com.udacity.popularmovies.fragments.MainActivityFragment;
import com.udacity.popularmovies.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkIfTwoPaneMode();
        intiViews(savedInstanceState);
    }

    private void checkIfTwoPaneMode() {

        if (findViewById(R.id.display_fragment) != null) {
            mTwoPane = true;
        }
        Constants.mTwoPane = mTwoPane;
    }

    private void intiViews(Bundle savedInstanceState) {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        Coordinator.INSTANCE.setCoordinatorLayout(coordinatorLayout);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MainActivityFragment.TAG);
        if (fragment == null) {
            fragment = new MainActivityFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment, "displayFragment").commit();


    }


    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        return super.onSupportNavigateUp();
    }

}
