package com.udacity.popularmovies.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by vaibhav on 18/2/16.
 */
@SimpleSQLTable(table = "MovieOrgResults", provider = "MovieProvider")
public class MovieOrgResults implements Parcelable {

    @SimpleSQLColumn("vote_average")
    private String vote_average;
    @SimpleSQLColumn("backdrop_path")
    private String backdrop_path;
    @SimpleSQLColumn("adult")
    private String adult;
    @SimpleSQLColumn("id")
    private String id;
    @SimpleSQLColumn("title")
    private String title;
    @SimpleSQLColumn("overview")
    private String overview;
    @SimpleSQLColumn("original_language")
    private String original_language;
    private List<String> genre_ids;
    @SimpleSQLColumn("release_date")
    private String release_date;
    @SimpleSQLColumn("original_title")
    private String original_title;
    @SimpleSQLColumn("vote_count")
    private String vote_count;
    @SimpleSQLColumn("poster_path")
    private String poster_path;
    @SimpleSQLColumn("video")
    private String video;
    @SimpleSQLColumn("popularity")
    private String popularity;

    public MovieOrgResults() {
    }

    protected MovieOrgResults(Parcel in) {
        vote_average = in.readString();
        backdrop_path = in.readString();
        adult = in.readString();
        id = in.readString();
        title = in.readString();
        overview = in.readString();
        original_language = in.readString();
        genre_ids = in.createStringArrayList();
        release_date = in.readString();
        original_title = in.readString();
        vote_count = in.readString();
        poster_path = in.readString();
        video = in.readString();
        popularity = in.readString();
    }

    public static final Creator<MovieOrgResults> CREATOR = new Creator<MovieOrgResults>() {
        @Override
        public MovieOrgResults createFromParcel(Parcel in) {
            return new MovieOrgResults(in);
        }

        @Override
        public MovieOrgResults[] newArray(int size) {
            return new MovieOrgResults[size];
        }
    };

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public List<String> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<String> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return "ClassPojo [vote_average = " + vote_average + ", backdrop_path = " + backdrop_path + ", adult = " + adult + ", id = " + id + ", title = " + title + ", overview = " + overview + ", original_language = " + original_language + ", genre_ids = " + genre_ids + ", release_date = " + release_date + ", original_title = " + original_title + ", vote_count = " + vote_count + ", poster_path = " + poster_path + ", video = " + video + ", popularity = " + popularity + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vote_average);
        dest.writeString(backdrop_path);
        dest.writeString(adult);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(original_language);
        dest.writeStringList(genre_ids);
        dest.writeString(release_date);
        dest.writeString(original_title);
        dest.writeString(vote_count);
        dest.writeString(poster_path);
        dest.writeString(video);
        dest.writeString(popularity);
    }
}
