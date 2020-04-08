package com.techyourchance.android_mvc_tutorial.common.dependencyinjection;

import android.app.Application;

import com.techyourchance.threadposter.BackgroundThreadPoster;
import com.techyourchance.threadposter.UiThreadPoster;

public class ApplicationCompositionRoot {

    private final Application mApplication;
    
    private UiThreadPoster mUiThreadPoster;
    private BackgroundThreadPoster mBackgroundThreadPoster;

    public ApplicationCompositionRoot(Application application) {
        mApplication = application;
    }
    
    public UiThreadPoster getUiThreadPoster() {
        if (mUiThreadPoster == null) {
            mUiThreadPoster = new UiThreadPoster();
        }
        return mUiThreadPoster;
    }

    public BackgroundThreadPoster getBackgroundThreadPoster() {
        if (mBackgroundThreadPoster == null) {
            mBackgroundThreadPoster = new BackgroundThreadPoster();
        }
        return mBackgroundThreadPoster;
    }
}
