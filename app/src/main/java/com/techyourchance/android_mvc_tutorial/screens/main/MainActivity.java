package com.techyourchance.android_mvc_tutorial.screens.main;

import android.os.Bundle;

import com.techyourchance.android_mvc_tutorial.MvcTutorialApplication;
import com.techyourchance.android_mvc_tutorial.R;
import com.techyourchance.android_mvc_tutorial.common.dependencyinjection.ActivityCompositionRoot;
import com.techyourchance.android_mvc_tutorial.common.permissions.PermissionsHelper;
import com.techyourchance.android_mvc_tutorial.screens.ScreensNavigator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private ActivityCompositionRoot mActivityCompositionRoot;

    private PermissionsHelper mPermissionsHelper;
    private ScreensNavigator mScreensNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPermissionsHelper = getCompositionRoot().getPermissionsHelper();
        mScreensNavigator = getCompositionRoot().getScreenNavigator();

        setContentView(R.layout.layout_single_frame);

        mScreensNavigator.init(savedInstanceState);
    }

    public ActivityCompositionRoot getCompositionRoot() {
        if (mActivityCompositionRoot == null) {
            mActivityCompositionRoot = new ActivityCompositionRoot(
                    ((MvcTutorialApplication)getApplication()).getApplicationCompositionRoot(),
                    this
            );
        }
        return mActivityCompositionRoot;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionsHelper.REQUEST_CODE) {
            mPermissionsHelper.onRequestPermissionsResult(permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        if (!mScreensNavigator.navigateBack()) {
            super.onBackPressed();
        }
    }
}
