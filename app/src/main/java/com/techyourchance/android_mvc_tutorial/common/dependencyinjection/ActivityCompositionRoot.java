package com.techyourchance.android_mvc_tutorial.common.dependencyinjection;

import android.content.ContentResolver;
import android.view.LayoutInflater;

import com.ncapdevi.fragnav.FragNavController;
import com.techyourchance.android_mvc_tutorial.R;
import com.techyourchance.android_mvc_tutorial.common.permissions.PermissionsHelper;
import com.techyourchance.android_mvc_tutorial.screens.ScreensNavigator;
import com.techyourchance.android_mvc_tutorial.screens.common.ViewMvcFactory;
import com.techyourchance.android_mvc_tutorial.sms.DefaultSmsAppTester;
import com.techyourchance.android_mvc_tutorial.sms.FetchAllSmsUseCase;
import com.techyourchance.android_mvc_tutorial.sms.FetchSmsByIdUseCase;
import com.techyourchance.android_mvc_tutorial.sms.FetchSmsUseCaseSync;
import com.techyourchance.android_mvc_tutorial.sms.MarkSmsAsReadUseCase;
import com.techyourchance.threadposter.BackgroundThreadPoster;
import com.techyourchance.threadposter.UiThreadPoster;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityCompositionRoot {

    private final ApplicationCompositionRoot mApplicationCompositionRoot;
    private final AppCompatActivity mActivity;

    private PermissionsHelper mPermissionsHelper;
    private ScreensNavigator mScreensNavigator;

    public ActivityCompositionRoot(ApplicationCompositionRoot applicationCompositionRoot, AppCompatActivity activity) {
        mApplicationCompositionRoot = applicationCompositionRoot;
        mActivity = activity;
    }

    public PermissionsHelper getPermissionsHelper() {
        if (mPermissionsHelper == null) {
            mPermissionsHelper = new PermissionsHelper(mActivity);
        }
        return mPermissionsHelper;
    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(LayoutInflater.from(mActivity));
    }

    private ContentResolver getContentResolver() {
        return mActivity.getContentResolver();
    }

    private BackgroundThreadPoster getBackgroundThreadPoster() {
        return mApplicationCompositionRoot.getBackgroundThreadPoster();
    }

    private UiThreadPoster getUiThreadPoster() {
        return mApplicationCompositionRoot.getUiThreadPoster();
    }

    public ScreensNavigator getScreenNavigator() {
        if (mScreensNavigator == null) {
            mScreensNavigator = new ScreensNavigator(
                    new FragNavController(mActivity.getSupportFragmentManager(), R.id.frame_contents)
            );
        }
        return mScreensNavigator;
    }

    public FetchAllSmsUseCase getFetchAllSmsUseCase() {
        return new FetchAllSmsUseCase(
                getUiThreadPoster(),
                getBackgroundThreadPoster(),
                getFetchSmsUseCaseSync()
        );
    }

    public FetchSmsByIdUseCase getFetchSmsByIdUseCase() {
        return new FetchSmsByIdUseCase(
                getUiThreadPoster(),
                getBackgroundThreadPoster(),
                getFetchSmsUseCaseSync()
        );
    }

    private FetchSmsUseCaseSync getFetchSmsUseCaseSync() {
        return new FetchSmsUseCaseSync(getContentResolver());
    }

    public MarkSmsAsReadUseCase getMarkSmsAsReadUseCase() {
        return new MarkSmsAsReadUseCase(
                getUiThreadPoster(),
                getBackgroundThreadPoster(),
                getContentResolver()
        );
    }

    public DefaultSmsAppTester getDefaultSmsAppTester() {
        return new DefaultSmsAppTester(mActivity);
    }
}
