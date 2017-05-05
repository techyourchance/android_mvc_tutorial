package com.techyourchance.www.android_mvc_tutorial.common;

import android.os.Handler;
import android.os.Looper;

/**
 * This class should be used in order to execute {@link Runnable} objects on UI thread
 */
public class MainThreadPoster {

    private final Handler mMainHandler;

    public MainThreadPoster() {
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Post {@link Runnable} for execution on main thread
     */
    public void post(Runnable runnable) {
        mMainHandler.post(runnable);
    }

}
