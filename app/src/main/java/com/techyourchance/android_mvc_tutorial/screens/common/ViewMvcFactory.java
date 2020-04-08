package com.techyourchance.android_mvc_tutorial.screens.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.techyourchance.android_mvc_tutorial.screens.smsall.SmsAllViewMvc;
import com.techyourchance.android_mvc_tutorial.screens.smsall.SmsAllViewMvcImpl;
import com.techyourchance.android_mvc_tutorial.screens.smsall.smslistitem.SmsListItemViewMvc;
import com.techyourchance.android_mvc_tutorial.screens.smsall.smslistitem.SmsListItemViewMvcImpl;
import com.techyourchance.android_mvc_tutorial.screens.smsdetails.SmsDetailsViewMvc;
import com.techyourchance.android_mvc_tutorial.screens.smsdetails.SmsDetailsViewMvcImpl;

import androidx.annotation.Nullable;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    public SmsAllViewMvc newSmsAllViewMvc(@Nullable ViewGroup parent) {
        return new SmsAllViewMvcImpl(mLayoutInflater, parent, this);
    }

    public SmsListItemViewMvc newSmsListItemViewMvc(@Nullable ViewGroup parent) {
        return new SmsListItemViewMvcImpl(mLayoutInflater, parent);
    }

    public SmsDetailsViewMvc newSmsDetailsViewMvc(@Nullable ViewGroup parent) {
        return new SmsDetailsViewMvcImpl(mLayoutInflater, parent);
    }
}
