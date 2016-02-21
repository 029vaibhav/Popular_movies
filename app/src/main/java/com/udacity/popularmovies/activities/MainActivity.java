package com.udacity.popularmovies.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.entities.enums.Coordinator;
import com.udacity.popularmovies.fragments.MainActivityFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        });

        intiViews();

    }

    private void intiViews() {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        Coordinator.INSTANCE.setCoordinatorLayout(coordinatorLayout);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MainActivityFragment.TAG);
        if (fragment == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainActivityFragment(), MainActivityFragment.TAG).commit();
    }


    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        return super.onSupportNavigateUp();
    }

}
