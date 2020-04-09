package com.techyourchance.android_mvc_tutorial.screens.smsdetails;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.techyourchance.android_mvc_tutorial.screens.ScreensNavigator;
import com.techyourchance.android_mvc_tutorial.screens.common.ViewMvcFactory;
import com.techyourchance.android_mvc_tutorial.sms.DefaultSmsAppTester;
import com.techyourchance.android_mvc_tutorial.sms.FetchSmsByIdUseCase;
import com.techyourchance.android_mvc_tutorial.sms.MarkSmsAsReadUseCase;
import com.techyourchance.android_mvc_tutorial.sms.SmsMessage;
import com.techyourchance.android_mvc_tutorial.screens.common.BaseFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SmsDetailsFragment extends BaseFragment implements
        SmsDetailsViewMvc.Listener,
        MarkSmsAsReadUseCase.Listener,
        FetchSmsByIdUseCase.Listener {

    private static final String ARG_SMS_MESSAGE_ID = "arg_sms_message_id";

    public static Fragment newInstance(long smsId) {
        Bundle args = new Bundle(1);
        args.putLong(SmsDetailsFragment.ARG_SMS_MESSAGE_ID, smsId);
        Fragment fragment = new SmsDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ViewMvcFactory mViewMvcFactory;
    private ScreensNavigator mScreensNavigator;
    private FetchSmsByIdUseCase mFetchSmsByIdUseCase;
    private MarkSmsAsReadUseCase mMarkMessageAsReadUseCase;
    private DefaultSmsAppTester mDefaultSmsAppTester;

    private SmsDetailsViewMvc mViewMVC;

    private long mSmsId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFetchSmsByIdUseCase = getCompositionRoot().getFetchSmsByIdUseCase();
        mViewMvcFactory = getCompositionRoot().getViewMvcFactory();
        mScreensNavigator = getCompositionRoot().getScreenNavigator();
        mMarkMessageAsReadUseCase = getCompositionRoot().getMarkSmsAsReadUseCase();
        mDefaultSmsAppTester = getCompositionRoot().getDefaultSmsAppTester();

        mSmsId = getArguments().getLong(ARG_SMS_MESSAGE_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mViewMVC = mViewMvcFactory.newSmsDetailsViewMvc(container);

        /*
        Starting with API 19 (KitKat), only applications designated as default SMS applications
        can alter SMS attributes (though they still can read SMSs). Therefore, on post KitKat
        versions "mark as read" button is only relevant if this app is the default SMS app.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!mDefaultSmsAppTester.isDefaultSmsApp()) {
                mViewMVC.hideMarkAsReadButton();
            }
        }

        return mViewMVC.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);
        mFetchSmsByIdUseCase.registerListener(this);
        mMarkMessageAsReadUseCase.registerListener(this);

        mFetchSmsByIdUseCase.fetchSmsById(mSmsId);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMVC.unregisterListener(this);
        mFetchSmsByIdUseCase.unregisterListener(this);
        mMarkMessageAsReadUseCase.unregisterListener(this);
    }


    @Override
    public void onSmsFetched(SmsMessage sms) {
        mViewMVC.bindSmsMessage(sms);
    }

    @Override
    public void onSmsFetchFailed(long smsId) {
        Toast.makeText(getActivity(), "Couldn't fetch the SMS message of interest!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNavigateUpClicked() {
        mScreensNavigator.navigateBack();
    }

    @Override
    public void onMarkAsReadClicked() {
        mMarkMessageAsReadUseCase.markSmsAsRead(mSmsId);
    }

    @Override
    public void onSmsMarkedAsRead(long smsId) {
        if (smsId == mSmsId) {
            mFetchSmsByIdUseCase.fetchSmsById(mSmsId);
        }
    }
}
