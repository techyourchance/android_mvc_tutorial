package com.techyourchance.www.android_mvc_tutorial.managers;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;

import com.techyourchance.www.android_mvc_tutorial.pojos.SmsMessage;
import com.techyourchance.www.android_mvc_tutorial.util.BackgroundThreadPoster;
import com.techyourchance.www.android_mvc_tutorial.util.MainThreadPoster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class encapsulates the logic related to SMS messages, including interactions with MVC model.
 */
public class SmsMessagesManager {

    /*
     * MVC model of the app is a database of SMS messages stored on the device. The model is accessed
     * via a non-standard ContentProvider having CONTENT_URI "content://sms/inbox".
     */
    private static final String CONTENT_URI = "content://sms/inbox";

    // The "data columns" used in our app
    private static final String[] COLUMNS_OF_INTEREST = new String[] {
            "_id",
            "address",
            "date",
            "body",
            "read"
    };

    // Default sort order by descending date
    private static final String DEFAULT_SORT_ORDER = "date DESC";

    /**
     * Classes implementing this interface can be registered as callbacks with
     * {@link SmsMessagesManager}
     */
    public interface SmsMessagesManagerListener {
        /**
         * This method will be called on UI thread when fetching of requested SMS messages
         * completes.
         * @param smsMessages a list of fetched SMS messages; will never be null
         */
        void onSmsMessagesFetched(List<SmsMessage> smsMessages);
    }


    private ContentResolver mContentResolver;
    private final MainThreadPoster mMainThreadPoster;
    private final BackgroundThreadPoster mBackgroundThreadPoster;


    // making the set of listeners thread-safe in order to support registration/unregistration from
    // threads other than UI
    private Set<SmsMessagesManagerListener> mListeners = Collections.newSetFromMap(
            new ConcurrentHashMap<SmsMessagesManagerListener, Boolean>(1));


    public SmsMessagesManager(ContentResolver contentResolver,
                              MainThreadPoster mainThreadPoster,
                              BackgroundThreadPoster backgroundThreadPoster) {
        mContentResolver = contentResolver;
        mMainThreadPoster = mainThreadPoster;
        mBackgroundThreadPoster = backgroundThreadPoster;
    }

    /**
     * Fetch an SMS message by its ID. Fetch will be done on background thread and registered
     * listeners will be notified of result on UI thread.
     * @param id ID of message to fetch
     */
    public void fetchSmsMessageById(final long id) {
        mBackgroundThreadPoster.post(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = mContentResolver.query(
                        ContentUris.withAppendedId(Uri.parse(CONTENT_URI), id),
                        COLUMNS_OF_INTEREST,
                        null,
                        null,
                        DEFAULT_SORT_ORDER
                );

                List<SmsMessage> result = extractSmsMessagesFromCursor(cursor);

                if (cursor != null) cursor.close();

                notifySmsMessagesFetched(result);
            }
        });
    }


    /**
     * Fetch all SMS messages. Fetch will be done on background thread and registered
     * listeners will be notified of result on UI thread.
     */
    public void fetchAllSmsMessages() {
        mBackgroundThreadPoster.post(new Runnable() {
            @Override
            public void run() {

                Cursor cursor = mContentResolver.query(
                        Uri.parse(CONTENT_URI),
                        COLUMNS_OF_INTEREST,
                        null,
                        null,
                        DEFAULT_SORT_ORDER
                );

                List<SmsMessage> result = extractSmsMessagesFromCursor(cursor);

                if (cursor != null) cursor.close();

                notifySmsMessagesFetched(result);
            }
        });
    }

    private List<SmsMessage> extractSmsMessagesFromCursor(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            List<SmsMessage> result = new ArrayList<>(cursor.getCount());
            do {
                result.add(new SmsMessage(
                        cursor.getLong(cursor.getColumnIndexOrThrow("_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("address")),
                        cursor.getString(cursor.getColumnIndexOrThrow("body")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("read")) == 0));
            } while (cursor.moveToNext());
            return result;
        } else {
            return new ArrayList<>(0);
        }
    }


    public void registerListener(SmsMessagesManagerListener listener) {
        if (listener != null) mListeners.add(listener);
    }

    public void unregisterListener(SmsMessagesManagerListener listener) {
        if (listener != null) mListeners.remove(listener);
    }

    private void notifySmsMessagesFetched(final List<SmsMessage> smsMessages) {
        mMainThreadPoster.post(new Runnable() {
            @Override
            public void run() {
                for (SmsMessagesManagerListener listener : mListeners) {
                    listener.onSmsMessagesFetched(smsMessages);
                }
            }
        });
    }

}
