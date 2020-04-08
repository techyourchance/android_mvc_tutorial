package com.techyourchance.android_mvc_tutorial.sms;

import android.provider.Telephony;

import androidx.appcompat.app.AppCompatActivity;

public class DefaultSmsAppTester {

    private final AppCompatActivity mActivity;

    public DefaultSmsAppTester(AppCompatActivity activity) {
        mActivity = activity;
    }

    public boolean isDefaultSmsApp() {
        String defaultSmsPackage = Telephony.Sms.getDefaultSmsPackage(mActivity);
        return mActivity.getPackageName().equals(defaultSmsPackage);
    }
}
