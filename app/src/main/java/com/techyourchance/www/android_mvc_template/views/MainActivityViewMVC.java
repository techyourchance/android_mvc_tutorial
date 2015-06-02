package com.techyourchance.www.android_mvc_template.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techyourchance.www.android_mvc_template.R;

/**
 * This is the MVC view associated with MainActivity MVC controller. In fact, since this view
 * is extremely simple, its existence might even be unjustified...
 */
public class MainActivityViewMVC implements ViewMVC{

    private View mRootView;

    public MainActivityViewMVC(Context context, ViewGroup container) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.activity_main, container);
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getViewState() {
        // This MVC view has no state that could be retrieved
        return null;
    }
}
