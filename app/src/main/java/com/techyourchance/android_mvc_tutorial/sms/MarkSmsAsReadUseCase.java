package com.techyourchance.android_mvc_tutorial.sms;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;

import com.techyourchance.android_mvc_tutorial.common.BaseObservable;
import com.techyourchance.threadposter.BackgroundThreadPoster;
import com.techyourchance.threadposter.UiThreadPoster;

import java.util.List;

public class MarkSmsAsReadUseCase extends BaseObservable<MarkSmsAsReadUseCase.Listener> {

    private static final String CONTENT_URI = "content://sms/inbox";

    public interface Listener {
        void onSmsMarkedAsRead(long smsId);
    }

    private final UiThreadPoster mUiThreadPoster;
    private final BackgroundThreadPoster mBackgroundThreadPoster;
    private final ContentResolver mContentResolver;

    public MarkSmsAsReadUseCase(UiThreadPoster uiThreadPoster, BackgroundThreadPoster backgroundThreadPoster, ContentResolver contentResolver) {
        mUiThreadPoster = uiThreadPoster;
        mBackgroundThreadPoster = backgroundThreadPoster;
        mContentResolver = contentResolver;
    }

    public void markSmsAsRead(long smsId) {
        mBackgroundThreadPoster.post(() -> {
            // Designating the fields that should be updated
            ContentValues values = new ContentValues();
            values.put("read", true);

            mContentResolver.update(
                    ContentUris.withAppendedId(Uri.parse(CONTENT_URI), smsId),
                    values,
                    null,
                    null);

            notifySuccess(smsId);
        });
    }

    private void notifySuccess(long smsId) {
        mUiThreadPoster.post(() -> {
            for (Listener listener : getListeners()) {
                listener.onSmsMarkedAsRead(smsId);
            }
        });
    }

}
