package com.techyourchance.android_mvc_tutorial.sms;

import com.techyourchance.android_mvc_tutorial.common.BaseObservable;
import com.techyourchance.threadposter.BackgroundThreadPoster;
import com.techyourchance.threadposter.UiThreadPoster;

import java.util.Collections;
import java.util.List;

public class FetchSmsByIdUseCase extends BaseObservable<FetchSmsByIdUseCase.Listener> {

    public interface Listener {
        void onSmsFetched(SmsMessage smsMessage);
        void onSmsFetchFailed(long smsId);
    }

    private final UiThreadPoster mUiThreadPoster;
    private final BackgroundThreadPoster mBackgroundThreadPoster;
    private final FetchSmsUseCaseSync mFetchSmsUseCaseSync;

    public FetchSmsByIdUseCase(UiThreadPoster uiThreadPoster, BackgroundThreadPoster backgroundThreadPoster, FetchSmsUseCaseSync fetchSmsUseCaseSync) {
        mUiThreadPoster = uiThreadPoster;
        mBackgroundThreadPoster = backgroundThreadPoster;
        mFetchSmsUseCaseSync = fetchSmsUseCaseSync;
    }

    public void fetchSmsById(long id) {
        mBackgroundThreadPoster.post(() -> {
            List<SmsMessage> smsMessages = mFetchSmsUseCaseSync.fetchSms(Collections.singletonList(id));
            if (!smsMessages.isEmpty()) {
                notifySuccess(smsMessages.get(0));
            } else {
                notifyFailure(id);
            }
        });
    }

    private void notifySuccess(final SmsMessage smsMessage) {
        mUiThreadPoster.post(() -> {
            for (Listener listener : getListeners()) {
                listener.onSmsFetched(smsMessage);
            }
        });
    }

    private void notifyFailure(long id) {
        mUiThreadPoster.post(() -> {
            for (Listener listener : getListeners()) {
                listener.onSmsFetchFailed(id);
            }
        });
    }
}
