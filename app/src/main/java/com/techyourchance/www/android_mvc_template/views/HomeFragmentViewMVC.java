package com.techyourchance.www.android_mvc_template.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techyourchance.www.android_mvc_template.R;

/**
 * This MVC view contains a list view and intercepts click events
 */
public class HomeFragmentViewMVC implements ViewMVC {


    View mRootView;

    public HomeFragmentViewMVC(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }
}
