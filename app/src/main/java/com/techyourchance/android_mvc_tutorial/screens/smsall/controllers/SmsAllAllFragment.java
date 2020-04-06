package com.techyourchance.android_mvc_tutorial.screens.smsall.controllers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techyourchance.android_mvc_tutorial.sms.SmsMessagesManager;
import com.techyourchance.android_mvc_tutorial.sms.SmsMessage;
import com.techyourchance.android_mvc_tutorial.screens.common.controllers.BaseFragment;
import com.techyourchance.android_mvc_tutorial.screens.smsdetails.controllers.SmsDetailsFragment;
import com.techyourchance.android_mvc_tutorial.screens.smsall.mvcviews.SmsAllViewMvc;
import com.techyourchance.android_mvc_tutorial.screens.smsall.mvcviews.SmsAllViewMvcImpl;

import java.util.List;

/**
 * A Fragment containing a list of SMS messages
 */
public class SmsAllAllFragment extends BaseFragment implements
        SmsAllViewMvc.SmsAllViewMvcListener,
        SmsMessagesManager.SmsMessagesManagerListener {

    SmsAllViewMvc mViewMVC;

    SmsMessagesManager mSmsMessagesManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // in general, better use dependency injection library (e.g. Dagger 2) for members' init
        mSmsMessagesManager = new SmsMessagesManager(
                getActivity().getContentResolver(),
                getMainThreadPoster(),
                getBackgroundThreadPoster());

        // Instantiate MVC view and set the fragment as listener
        mViewMVC = new SmsAllViewMvcImpl(inflater, container);
        mViewMVC.setListener(this);

        // Return the root view of the associated MVC view
        return mViewMVC.getRootView();
    }


    @Override
    public void onStart() {
        super.onStart();
        mSmsMessagesManager.registerListener(this);
        mSmsMessagesManager.fetchAllSmsMessages();
    }

    @Override
    public void onStop() {
        super.onStop();
        mSmsMessagesManager.unregisterListener(this);
    }

    @Override
    public void onSmsMessageClicked(long id) {
        // Create a bundle that will pass the ID of the clicked SMS message to the new fragment
        Bundle args = new Bundle(1);
        args.putLong(SmsDetailsFragment.ARG_SMS_MESSAGE_ID, id);

        // Replace this fragment with a new one and pass args to it
        replaceFragment(SmsDetailsFragment.class, true, args);
    }

    @Override
    public void onSmsMessagesFetched(List<SmsMessage> smsMessages) {
        mViewMVC.bindSmsMessages(smsMessages);
    }
}
