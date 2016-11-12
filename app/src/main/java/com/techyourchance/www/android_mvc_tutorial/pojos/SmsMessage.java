package com.techyourchance.www.android_mvc_tutorial.pojos;

/**
 * Instances of this class are POJOs (Plain Old Java Object) which represent SMS messages.
 */
public class SmsMessage {

    private long mId;
    private String mAddress;
    private String mBody;
    private String mDate;
    private boolean mUnread;

    public SmsMessage(long id, String address, String body, String date, boolean unread) {
        mId = id;
        mAddress = address;
        mBody = body;
        mDate = date;
        mUnread = unread;
    }

    public long getId() {
        return mId;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getBody() {
        return mBody;
    }

    public String getDate() {
        return mDate;
    }

    public boolean isUnread() {
        return mUnread;
    }
}
