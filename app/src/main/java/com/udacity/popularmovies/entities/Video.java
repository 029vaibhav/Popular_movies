package com.udacity.popularmovies.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by vaibhav on 27/3/16.
 */
public class Video implements Parcelable {

    private String id;

    private List<VideoResults> results;

    public Video() {
    }

    protected Video(Parcel in) {
        id = in.readString();
        results = in.createTypedArrayList(VideoResults.CREATOR);
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public List<VideoResults> getResults ()
    {
        return results;
    }

    public void setResults (List<VideoResults> results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", results = "+results+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeTypedList(results);
    }
}
