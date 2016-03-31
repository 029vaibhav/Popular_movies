package com.udacity.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.entities.MovieOrgResults;
import com.udacity.popularmovies.entities.enums.Coordinator;
import com.udacity.popularmovies.fragments.DisplayFragment;

public class DisplayActivity extends AppCompatActivity {

    public static String key = "movie";
    MovieOrgResults movieOrgResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        intiViews();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                movieOrgResults = extras.getParcelable(key);
            }
            DisplayFragment fragment = DisplayFragment.newInstance(movieOrgResults);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_fragment, fragment)
                    .commit();
        }
    }

    private void intiViews() {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        Coordinator.INSTANCE.setCoordinatorLayout(coordinatorLayout);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(key, movieOrgResults);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
