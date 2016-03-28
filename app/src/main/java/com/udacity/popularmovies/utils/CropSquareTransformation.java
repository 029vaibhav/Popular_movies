package com.udacity.popularmovies.utils;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

import com.squareup.picasso.Transformation;

/**
 * Created by vaibhav on 21/2/16.
 */
public class CropSquareTransformation implements Transformation {


    AppCompatActivity context;

    public CropSquareTransformation(AppCompatActivity context) {
        this.context = context;
    }

    @Override
    public Bitmap transform(Bitmap source) {

        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        Bitmap result = Bitmap.createScaledBitmap(source, width / 2, width / 2, true);
        if (result != source) {
            source.recycle();
        }
        return result;
    }

    @Override
    public String key() {
        return "square()";
    }
}
