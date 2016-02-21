package com.udacity.popularmovies.entities.enums;

import android.support.design.widget.CoordinatorLayout;

/**
 * Created by vaibhav on 20/2/16.
 */
public enum Coordinator {

    INSTANCE;

    CoordinatorLayout coordinatorLayout;

    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }

    public void setCoordinatorLayout(CoordinatorLayout coordinatorLayout) {
        this.coordinatorLayout = coordinatorLayout;
    }
}
