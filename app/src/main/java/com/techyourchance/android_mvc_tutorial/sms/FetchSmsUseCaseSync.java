package com.techyourchance.android_mvc_tutorial.sms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

public class FetchSmsUseCaseSync {

    private static final String CONTENT_URI = "content://sms/inbox";

    private static final String[] COLUMNS_OF_INTEREST = new String[] {
            "_id",
            "address",
            "date",
            "body",
            "read"
    };

    private static final String DEFAULT_SORT_ORDER = "date DESC";


    private final ContentResolver mContentResolver;

    public FetchSmsUseCaseSync(ContentResolver contentResolver) {
        mContentResolver = contentResolver;
    }

    /**
     * @param smsIds IDs of SMS messages to fetch; if empty - all messages will be fetched
     */
    @WorkerThread
    public List<SmsMessage> fetchSms(List<Long> smsIds) {
        Cursor cursor = null;
        try {
            cursor = mContentResolver.query(
                    Uri.parse(CONTENT_URI),
                    COLUMNS_OF_INTEREST,
                    selectionStringForIds(smsIds),
                    selectionArgsForIds(smsIds),
                    DEFAULT_SORT_ORDER
            );

            return extractSmsFromCursorAndClose(cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    private @Nullable String selectionStringForIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return null;
        }

        String selection = "_id in (";
        for (int i = 0; i < ids.size(); i++) {
            selection += "?, ";
        }
        selection = selection.substring(0, selection.length() - 2) + ")";
        return selection;
    }

    private @Nullable String[] selectionArgsForIds(List<Long> smsIds) {
        if (smsIds.isEmpty()) {
            return null;
        }

        String[] stingIds = new String[smsIds.size()];
        for (int i = 0; i < smsIds.size(); i++) {
            stingIds[i] = String.valueOf(smsIds.get(i));
        }
        return stingIds;
    }

    public List<SmsMessage> extractSmsFromCursorAndClose(Cursor cursor) {
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
}
