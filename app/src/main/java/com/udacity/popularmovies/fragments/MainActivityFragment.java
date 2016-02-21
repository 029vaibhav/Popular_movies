package com.udacity.popularmovies.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.adapters.MoviesDisplayAdapter;
import com.udacity.popularmovies.entities.MovieOrg;
import com.udacity.popularmovies.entities.enums.Coordinator;
import com.udacity.popularmovies.utils.Client;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static String TAG = "MainActivityFragment";

    MovieOrg movieOrg;
    Client client;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    GridLayoutManager gridLayoutManager;
    Snackbar snackbar;
    String sortKey;

    public MainActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSavedInstance(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        initViews(viewGroup);
        if (movieOrg != null) {
            displayResults(movieOrg);
        } else {
            getDataFromServer();
        }
        return viewGroup;
    }

    private void checkSavedInstance(Bundle savedInstanceState) {
        sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        sortKey = sharedPreferences.getString(getString(R.string.sort_key), getString(R.string.popular_movie_cache_key));
        client = new Client(getActivity());
        if (savedInstanceState != null && savedInstanceState.containsKey(sortKey)) {
            movieOrg = savedInstanceState.getParcelable(sortKey);
        } else {
        }
    }

    private void initViews(ViewGroup viewGroup) {
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recycler_view);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        snackbar = Snackbar.make(Coordinator.INSTANCE.getCoordinatorLayout(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry), v -> getDataFromServer());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(sortKey, movieOrg);
        super.onSaveInstanceState(outState);
    }

    public void getDataFromServer() {

        Call<MovieOrg> moviesDetails = client.getMoviesDetails(sortKey);
        moviesDetails.enqueue(new Callback<MovieOrg>() {
            @Override
            public void onResponse(Call<MovieOrg> call, Response<MovieOrg> response) {
                if (response.isSuccess()) {
                    movieOrg = response.body();
                    displayResults(movieOrg);
                }
            }

            @Override
            public void onFailure(Call<MovieOrg> call, Throwable t) {
                if (t instanceof IOException) {
                    snackbar.show();
                }
            }
        });
    }

    private void displayResults(MovieOrg movieOrg) {
        MoviesDisplayAdapter moviesDisplayAdapter = new MoviesDisplayAdapter(this, movieOrg.getResults());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(moviesDisplayAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                updateSortingDetails(item, getString(R.string.popular_movie_cache_key));
                return true;
            case R.id.action_rated:
                updateSortingDetails(item, getString(R.string.voted_movie_cache_key));
                return true;
            default:
                break;
        }
        return false;
    }

    private void updateSortingDetails(MenuItem item, String sortingKey) {
        if (!item.isChecked()) {
            item.setChecked(true);
            sortKey = sortingKey;
            sharedPreferences.edit().putString(getString(R.string.sort_key), getString(R.string.popular_movie_cache_key)).apply();
            getDataFromServer();
        }
    }

}
