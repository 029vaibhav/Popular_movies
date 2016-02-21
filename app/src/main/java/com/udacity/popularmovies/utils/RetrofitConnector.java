package com.udacity.popularmovies.utils;

import com.udacity.popularmovies.entities.MovieOrg;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vaibhav on 18/2/16.
 */
public interface RetrofitConnector {

    @GET("discover/movie")
    Call<MovieOrg> getMovieDetailsByPopularity(@Query("api_key") String apiKey,@Query("sort_by") String sortKey);

//    @GET("/discover/movie?sort_by=vote_count.desc")
//    Call<MovieOrg> getMovieDetailsByRating(@Query("api_key") String apiKey, Callback<MovieOrg> responseCallback);
}

