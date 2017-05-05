package com.techyourchance.www.android_mvc_tutorial;

import android.app.Application;

import com.techyourchance.www.android_mvc_tutorial.common.BackgroundThreadPoster;
import com.techyourchance.www.android_mvc_tutorial.common.MainThreadPoster;

/**
 * Our custom {@link Application}
 */
public class MvcTutorialApplication extends Application {

    private final MainThreadPoster mMainThreadPoster = new MainThreadPoster();
    private final BackgroundThreadPoster mBackgroundThreadPoster = new BackgroundThreadPoster();

    public MainThreadPoster getMainThreadPoster() {
        return mMainThreadPoster;
    }

    public BackgroundThreadPoster getBackgroundThreadPoster() {
        return mBackgroundThreadPoster;
    }
}
