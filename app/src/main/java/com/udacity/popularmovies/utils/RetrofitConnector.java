package com.udacity.popularmovies.utils;

import android.widget.RemoteViews;

import com.udacity.popularmovies.entities.MovieOrg;
import com.udacity.popularmovies.entities.Reviews;
import com.udacity.popularmovies.entities.Video;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by vaibhav on 18/2/16.
 */
public interface RetrofitConnector {

    @GET("discover/movie")
    Call<MovieOrg> getMovieDetailsByPopularity(@Query("api_key") String apiKey, @Query("sort_by") String sortKey);

    @GET("movie/{id}/videos")
    Call<Video> getVideoDetails(@Path("id") String id,@Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<Reviews> getReviewDetails(@Path("id") String id,@Query("api_key") String apiKey);
}

