package com.udacity.popularmovies.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaibhav on 18/2/16.
 */
public class MovieOrg implements Parcelable {

    private List<MovieOrgResults> results;

    private String page;

    private String total_pages;

    private String total_results;

    public MovieOrg() {
    }

    protected MovieOrg(Parcel in) {
        page = in.readString();
        total_pages = in.readString();
        this.results = new ArrayList<>();
        in.readTypedList(results, MovieOrgResults.CREATOR);
        total_results = in.readString();
    }

    public static final Creator<MovieOrg> CREATOR = new Creator<MovieOrg>() {
        @Override
        public MovieOrg createFromParcel(Parcel in) {
            return new MovieOrg(in);
        }

        @Override
        public MovieOrg[] newArray(int size) {
            return new MovieOrg[size];
        }
    };

    public List<MovieOrgResults> getResults() {
        return results;
    }

    public void setResults(List<MovieOrgResults> MovieOrgResultss) {
        this.results = MovieOrgResultss;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(page);
        dest.writeString(total_pages);
        dest.writeString(total_results);
        dest.writeTypedList(results);

    }
}