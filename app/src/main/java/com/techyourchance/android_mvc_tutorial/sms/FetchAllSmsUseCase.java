package com.techyourchance.android_mvc_tutorial.sms;

import com.techyourchance.android_mvc_tutorial.common.BaseObservable;
import com.techyourchance.threadposter.BackgroundThreadPoster;
import com.techyourchance.threadposter.UiThreadPoster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FetchAllSmsUseCase extends BaseObservable<FetchAllSmsUseCase.Listener> {

    public interface Listener {
        void onAllSmsFetched(List<SmsMessage> smsMessages);
    }

    private final UiThreadPoster mUiThreadPoster;
    private final BackgroundThreadPoster mBackgroundThreadPoster;
    private final FetchSmsUseCaseSync mFetchSmsUseCaseSync;

    public FetchAllSmsUseCase(UiThreadPoster uiThreadPoster, BackgroundThreadPoster backgroundThreadPoster, FetchSmsUseCaseSync fetchSmsUseCaseSync) {
        mUiThreadPoster = uiThreadPoster;
        mBackgroundThreadPoster = backgroundThreadPoster;
        mFetchSmsUseCaseSync = fetchSmsUseCaseSync;
    }

    public void fetchAllSmsMessages() {
        mBackgroundThreadPoster.post(() -> {
            notifySuccess(mFetchSmsUseCaseSync.fetchSms(Collections.emptyList()));
        });
    }

    private void notifySuccess(final List<SmsMessage> smsMessages) {
        mUiThreadPoster.post(() -> {
            for (Listener listener : getListeners()) {
                listener.onAllSmsFetched(smsMessages);
            }
        });
    }

}
