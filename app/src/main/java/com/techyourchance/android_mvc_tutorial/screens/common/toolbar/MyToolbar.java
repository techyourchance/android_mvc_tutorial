package com.techyourchance.android_mvc_tutorial.screens.common.toolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.techyourchance.android_mvc_tutorial.R;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

public class MyToolbar extends Toolbar {

    public interface NavigateUpListener {
        void onNavigationUpClicked();
    }

    private NavigateUpListener mNavigateUpListener;

    private FrameLayout mNavigateUp;

    public MyToolbar(Context context) {
        super(context);
        init(context);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_my_toolbar, this, true);
        setContentInsetsRelative(0, 0);

        mNavigateUp = view.findViewById(R.id.navigate_up);

        setListeners();
    }

    private void setListeners() {
        mNavigateUp.setOnClickListener(view -> mNavigateUpListener.onNavigationUpClicked());
    }

    public void setNavigateUpListener(NavigateUpListener navigateUpListener) {
        mNavigateUpListener = navigateUpListener;
        mNavigateUp.setVisibility(VISIBLE);
    }

}
