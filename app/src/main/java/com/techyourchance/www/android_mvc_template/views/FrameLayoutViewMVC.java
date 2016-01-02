package com.techyourchance.www.android_mvc_template.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techyourchance.www.android_mvc_template.R;

/**
 * Very simple MVC view containing just single FrameLayout
 */
public class FrameLayoutViewMVC implements ViewMVC {

    private View mRootView;

    public FrameLayoutViewMVC(Context context, ViewGroup container) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.mvc_view_frame_layout, container);
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
