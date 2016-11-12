package com.techyourchance.www.android_mvc_tutorial.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class should be used in order to execute {@link Runnable} objects on background threads
 */
public class BackgroundThreadPoster {

    private final ExecutorService mBackgroundExecutor;

    public BackgroundThreadPoster() {
        mBackgroundExecutor = Executors.newCachedThreadPool();
    }

    /**
     * Post {@link Runnable} for execution on a random background thread
     */
    public void post(Runnable runnable) {
        mBackgroundExecutor.execute(runnable);
    }

}
