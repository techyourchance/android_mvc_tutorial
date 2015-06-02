package com.techyourchance.www.android_mvc_template.pojos;

import android.database.Cursor;

/**
 * Instances of this class are POJOs (Plain Old Java Object) which represent SMS messages.
 */
public class SmsMessage {

    private static final String LOG_TAG = SmsMessage.class.getSimpleName();

    public String mAddress;
    public String mBody;
    public String mDate;
    public boolean mUnread;

    /**
     * Private constructor - object of this class should be instantiated using
     * the static "factory method" provided below
     */
    private SmsMessage() {}


    /**
     * Factory method that constructs SmsMessage object from the values stored in Cursor at the
     * current position.
     * @throws IllegalArgumentException if any of the required fields are missing from Cursor.
     * Required fields: "address", "body", "date", "read".
     */
    public static SmsMessage createSmsMessage(Cursor cursor) throws IllegalArgumentException {
        SmsMessage smsMessage = new SmsMessage();

        try {
            smsMessage.mAddress = cursor.getString(
                    cursor.getColumnIndexOrThrow("address"));

            smsMessage.mBody = cursor.getString(
                    cursor.getColumnIndexOrThrow("body"));

            smsMessage.mDate = cursor.getString(
                    cursor.getColumnIndexOrThrow("date"));

            smsMessage.mUnread = cursor.getInt(cursor.getColumnIndexOrThrow("read")) == 0;

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return smsMessage;
    }


}
