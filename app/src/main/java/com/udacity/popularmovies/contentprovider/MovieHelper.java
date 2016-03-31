package com.udacity.popularmovies.contentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.udacity.popularmovies.entities.MovieOrgResults;
import com.udacity.popularmovies.entities.MovieOrgResultsTable;

import java.util.List;

/**
 * Created by vaibhav on 31/3/16.
 */
public class MovieHelper {

    static String TAG = MovieHelper.class.getSimpleName();


    public static Uri createMovieOrgResults(ContentResolver resolver, MovieOrgResults item) {
        Uri uri = resolver.insert(MovieOrgResultsTable.CONTENT_URI, MovieOrgResultsTable.getContentValues(item, false));
        if (uri != null) {
            Log.d(TAG, "added new task");
        }
        return uri;
    }

    public static int deleteMovieOrgResults(ContentResolver resolver, MovieOrgResults item) {
        int result = resolver.delete(MovieOrgResultsTable.CONTENT_URI, MovieOrgResultsTable.FIELD_ID + "=?", new String[]{String.valueOf(item.getId())});
        if (result > 0) {
            Log.d(TAG, "deleted task");
        }
        return result;
    }

    public static MovieOrgResults getMovieOrgResults(ContentResolver resolver, String id) {
        Cursor cursor = resolver.query(MovieOrgResultsTable.CONTENT_URI, null, MovieOrgResultsTable.FIELD_ID + "=?", new String[]{String.valueOf(id)}, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return MovieOrgResultsTable.getRow(cursor, true);
        }
        return null;
    }

    public static List<MovieOrgResults> getAllMovieResults(ContentResolver resolver) {
        Cursor cursor = resolver.query(MovieOrgResultsTable.CONTENT_URI, null, null, null, null);
        return MovieOrgResultsTable.getRows(cursor, true);
    }


    public static int updateMovieOrgResults(ContentResolver resolver, MovieOrgResults item) {
        return resolver.update(MovieOrgResultsTable.CONTENT_URI, MovieOrgResultsTable.getContentValues(item, true), MovieOrgResultsTable.FIELD_ID + "=?", new String[]{String.valueOf(item.getId())});
    }

    public static void deleteAll(ContentResolver resolver) {
        resolver.delete(MovieOrgResultsTable.CONTENT_URI, null, null);
    }

}
