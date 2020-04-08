package com.techyourchance.android_mvc_tutorial.screens.smsall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techyourchance.android_mvc_tutorial.common.permissions.MyPermission;
import com.techyourchance.android_mvc_tutorial.common.permissions.PermissionsHelper;
import com.techyourchance.android_mvc_tutorial.screens.ScreensNavigator;
import com.techyourchance.android_mvc_tutorial.screens.common.BaseFragment;
import com.techyourchance.android_mvc_tutorial.screens.common.ViewMvcFactory;
import com.techyourchance.android_mvc_tutorial.sms.FetchAllSmsUseCase;
import com.techyourchance.android_mvc_tutorial.sms.SmsMessage;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SmsAllFragment extends BaseFragment implements
        SmsAllViewMvc.Listener,
        FetchAllSmsUseCase.Listener,
        PermissionsHelper.Listener {

    public static Fragment newInstance() {
        return new SmsAllFragment();
    }

    private PermissionsHelper mPermissionsHelper;
    private ViewMvcFactory mViewMvcFactory;
    private ScreensNavigator mScreensNavigator;
    private FetchAllSmsUseCase mFetchAllSmsUseCase;

    private SmsAllViewMvc mViewMVC;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissionsHelper = getCompositionRoot().getPermissionsHelper();
        mViewMvcFactory = getCompositionRoot().getViewMvcFactory();
        mFetchAllSmsUseCase = getCompositionRoot().getFetchAllSmsUseCase();
        mScreensNavigator = getCompositionRoot().getScreenNavigator();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewMVC = mViewMvcFactory.newSmsAllViewMvc(container);
        return mViewMVC.getRootView();
    }


    @Override
    public void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);
        mFetchAllSmsUseCase.registerListener(this);
        mPermissionsHelper.registerListener(this);

        checkReadSmsPermissionAndFetchAllSms();
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMVC.unregisterListener(this);
        mFetchAllSmsUseCase.unregisterListener(this);
        mPermissionsHelper.unregisterListener(this);
    }

    private void checkReadSmsPermissionAndFetchAllSms() {
        if (mPermissionsHelper.hasPermission(MyPermission.READ_SMS)) {
            mFetchAllSmsUseCase.fetchAllSmsMessages();
        } else {
            mPermissionsHelper.requestPermission(MyPermission.READ_SMS);
        }
    }

    @Override
    public void onPermissionGranted(MyPermission permission) {
        if (permission == MyPermission.READ_SMS) {
            mFetchAllSmsUseCase.fetchAllSmsMessages();
        }
    }

    @Override
    public void onPermissionDenied(MyPermission permission) {
        mViewMVC.showPermissionDenied();
    }

    @Override
    public void onPermissionDeniedAndDontAskAgain(MyPermission permission) {
        mViewMVC.showPermissionDeniedAndDontAskAgain();
    }

    @Override
    public void onPermissionRequestCancelled() {
        checkReadSmsPermissionAndFetchAllSms();
    }

    @Override
    public void onSmsMessageClicked(long id) {
        mScreensNavigator.toSmsDetailsScreen(id);
    }

    @Override
    public void onAskForPermissionClicked() {
        checkReadSmsPermissionAndFetchAllSms();
    }

    @Override
    public void onAllSmsFetched(List<SmsMessage> smsMessages) {
        mViewMVC.bindSmsMessages(smsMessages);
    }
}
