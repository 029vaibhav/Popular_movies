package com.udacity.popularmovies.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colintmiller.simplenosql.NoSQL;
import com.colintmiller.simplenosql.NoSQLEntity;
import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.adapters.ReviewDisplayAdapter;
import com.udacity.popularmovies.adapters.VideoDisplayAdapter;
import com.udacity.popularmovies.contentprovider.MovieHelper;
import com.udacity.popularmovies.entities.MovieOrgResults;
import com.udacity.popularmovies.entities.Reviews;
import com.udacity.popularmovies.entities.Video;
import com.udacity.popularmovies.entities.enums.Coordinator;
import com.udacity.popularmovies.utils.Client;
import com.udacity.popularmovies.utils.Constants;
import com.udacity.popularmovies.utils.CropSquareTransformation;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment {


    public static final String TAG = "DisplayFragment";
    private static String key = "movie";
    TextView title, releaseDate, rating, overView;
    ImageView poster;
    MovieOrgResults movieOrgResults;
    RecyclerView recyclerView, reviewRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog progressDialog;
    Video video;
    private Snackbar snackbar;
    FloatingActionButton fab;
    TextView reviewTextView;
    LinearLayout reviewLinearLayout, trailerLinearLayout;
    boolean reviewToggle;
    Client client;

    @Override
    public void onStop() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
        super.onStop();
    }

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
    public void onPrepareOptionsMenu(Menu menu) {
        if (!Constants.mTwoPane)
            menu.setGroupVisible(R.id.menu_group, false);
        super.onPrepareOptionsMenu(menu);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_dis, container, false);
        initViews(viewGroup);
        checkIfFav(movieOrgResults.getId());
        getReviews();
        checkSavedInstance(savedInstanceState);

        return viewGroup;
    }

    private void getReviews() {

        Call<Reviews> reviewDetails = client.getReviewDetails(movieOrgResults.getId());
        reviewDetails.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {

                if (response.isSuccess()) {
                    ReviewDisplayAdapter reviewDisplayAdapter = new ReviewDisplayAdapter(DisplayFragment.this, response.body().getResults());
                    reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    reviewRecyclerView.setAdapter(reviewDisplayAdapter);
                }

            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {

            }
        });
    }

    private void checkIfFav(String id) {


        MovieOrgResults movieOrgResults = MovieHelper.getMovieOrgResults(getActivity().getContentResolver(), id);
        if (movieOrgResults != null) {
            fab.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.pink));

        }

    }

    private void getMovieVideos(String id) {

        NoSQL.with(getActivity()).using(Video.class)
                .bucketId(getString(R.string.local_video_key))
                .entityId(id)
                .retrieve(noSQLEntities -> {
                    if (noSQLEntities != null && noSQLEntities.size() > 0) {
                        video = noSQLEntities.get(0).getData();

                        if (video == null) {

                            getVideoDataFromServer();

                        } else {
                            displayVideoResults(video);
                        }
                    } else {
                        getVideoDataFromServer();
                    }
                });


    }

    private void displayVideoResults(Video video) {
        VideoDisplayAdapter videoDisplayAdapter = new VideoDisplayAdapter(this, video.getResults());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(videoDisplayAdapter);
    }


    private void checkSavedInstance(Bundle savedInstanceState) {

        if (savedInstanceState == null || !savedInstanceState.containsKey(key)) {
            displayResults(movieOrgResults);
            getMovieVideos(movieOrgResults.getId());
        } else {
            movieOrgResults = savedInstanceState.getParcelable(key);
            video = savedInstanceState.getParcelable(movieOrgResults.getId());
            displayResults(movieOrgResults);
            if (video != null)
                displayVideoResults(video);
            else getMovieVideos(movieOrgResults.getId());

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(movieOrgResults.getId(), video);
        outState.putParcelable(key, movieOrgResults);

        super.onSaveInstanceState(outState);
    }

    private void displayResults(MovieOrgResults movieOrgResults) {

        Picasso.with(getActivity())
                .load(getString(R.string.image_url) + movieOrgResults.getPoster_path())
                .transform(new CropSquareTransformation((AppCompatActivity) getActivity()))
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
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        reviewRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.recycler_view_review);
        reviewRecyclerView.setNestedScrollingEnabled(false);
        reviewTextView = (TextView) viewGroup.findViewById(R.id.review);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        reviewLinearLayout = (LinearLayout) viewGroup.findViewById(R.id.ll_review);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        trailerLinearLayout = (LinearLayout) viewGroup.findViewById(R.id.ll_trailer);
        fab = (FloatingActionButton) viewGroup.findViewById(R.id.fab);
        snackbar = Snackbar.make(Coordinator.INSTANCE.getCoordinatorLayout(), getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry), v -> getMovieVideos(movieOrgResults.getId()));
        fab.setOnClickListener(v -> fabClickListener());
        reviewTextView.setOnClickListener(v -> toggleReview());
        client = new Client(getActivity());
    }

    private void toggleReview() {
        reviewToggle = !reviewToggle;
        if (reviewToggle) {
            reviewLinearLayout.setVisibility(View.VISIBLE);
            trailerLinearLayout.setVisibility(View.GONE);
        } else {
            reviewLinearLayout.setVisibility(View.GONE);
            trailerLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void fabClickListener() {
        retrieveLocalMovieDetail(movieOrgResults.getId());
    }

    private void retrieveLocalMovieDetail(String id) {

        MovieOrgResults movieOrgResults = MovieHelper.getMovieOrgResults(getActivity().getContentResolver(), id);

        if(movieOrgResults==null)
        {
            fab.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.pink));
            MovieHelper.createMovieOrgResults(getActivity().getContentResolver(), this.movieOrgResults);

            NoSQLEntity<Video> videoNoSQLEntity = new NoSQLEntity<Video>(getString(R.string.local_video_key), this.movieOrgResults.getId());
            videoNoSQLEntity.setData(video);
            NoSQL.with(getActivity()).using(Video.class).save(videoNoSQLEntity);
        }
        else {
            fab.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.colorAccent));
            MovieHelper.deleteMovieOrgResults(getActivity().getContentResolver(), this.movieOrgResults);

            NoSQL.with(getActivity()).using(Video.class)
                    .bucketId(getString(R.string.local_video_key))
                    .entityId(this.movieOrgResults.getId())
                    .delete();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                share();
                return true;
            default:
                break;
        }
        return false;
    }

    private void share() {

        if (video != null && video.getResults() != null && video.getResults().size() > 0) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "http://www.youtube.com/watch?v=" + video.getResults().get(0).getKey());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

    }

    public void getVideoDataFromServer() {
        {
            progressDialog.show();

            Call<Video> videoDetails = client.getVideoDetails(movieOrgResults.getId());
            videoDetails.enqueue(new Callback<Video>() {
                @Override
                public void onResponse(Call<Video> call, Response<Video> response) {

                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (response.isSuccess()) {
                        video = response.body();
                        displayVideoResults(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Video> call, Throwable t) {
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (t instanceof IOException) {
                        snackbar.show();
                    }

                }
            });
        }
    }
}
