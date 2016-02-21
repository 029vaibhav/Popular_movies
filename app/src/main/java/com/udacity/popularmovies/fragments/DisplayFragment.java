package com.udacity.popularmovies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.entities.MovieOrgResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment {


    private static String key = "movie";
    TextView title, releaseDate, rating, overView;
    ImageView poster;
    MovieOrgResults movieOrgResults;

    public DisplayFragment() {
        // Required empty public constructor
    }

    public static DisplayFragment newInstance(MovieOrgResults movieOrgResults) {
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();
        args.putParcelable(key, movieOrgResults);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        movieOrgResults = getArguments() != null ? getArguments().getParcelable(key) : null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_dis, container, false);

        initViews(viewGroup);
        checkSavedInstance(savedInstanceState);

        return viewGroup;
    }


    private void checkSavedInstance(Bundle savedInstanceState) {

        if (savedInstanceState == null || !savedInstanceState.containsKey(key)) {
            displayResults(movieOrgResults);
        } else {
            movieOrgResults = savedInstanceState.getParcelable(key);
            displayResults(movieOrgResults);
        }
    }

    private void displayResults(MovieOrgResults movieOrgResults) {

        Picasso.with(getActivity())
                .load(getString(R.string.image_url) + movieOrgResults.getPoster_path())
                .placeholder(R.mipmap.ic_launcher)
                .into(poster);
        title.setText(movieOrgResults.getTitle());
        releaseDate.setText(movieOrgResults.getRelease_date());
        rating.setText(movieOrgResults.getVote_average());
        overView.setText(movieOrgResults.getOverview());
    }

    private void initViews(ViewGroup viewGroup) {


        title = (TextView) viewGroup.findViewById(R.id.title);
        releaseDate = (TextView) viewGroup.findViewById(R.id.release_date);
        rating = (TextView) viewGroup.findViewById(R.id.rating);
        overView = (TextView) viewGroup.findViewById(R.id.overview);
        poster = (ImageView) viewGroup.findViewById(R.id.poster_image);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.menu_group, false);
        super.onPrepareOptionsMenu(menu);
    }

}
