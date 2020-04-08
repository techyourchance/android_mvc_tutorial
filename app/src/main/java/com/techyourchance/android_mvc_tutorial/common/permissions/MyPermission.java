package com.techyourchance.android_mvc_tutorial.common.permissions;

import android.Manifest;

public enum MyPermission {

    READ_SMS(Manifest.permission.READ_SMS);

    private final String mCode;

    MyPermission(String code) {
        mCode = code;
    }

    public String getCode() {
        return mCode;
    }
}
