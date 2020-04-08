package com.techyourchance.android_mvc_tutorial.screens;

import android.os.Bundle;

import com.ncapdevi.fragnav.FragNavController;
import com.techyourchance.android_mvc_tutorial.screens.smsall.SmsAllFragment;
import com.techyourchance.android_mvc_tutorial.screens.smsdetails.SmsDetailsFragment;

import androidx.fragment.app.Fragment;

public class ScreensNavigator {

    private final FragNavController mFragNavController;

    public ScreensNavigator(FragNavController fragNavController) {
        mFragNavController = fragNavController;
    }

    public void init(Bundle savedInstanceState) {
        mFragNavController.setRootFragmentListener(new FragNavController.RootFragmentListener() {
            @Override
            public int getNumberOfRootFragments() {
                return 1;
            }

            @Override
            public Fragment getRootFragment(int i) {
                return SmsAllFragment.newInstance();
            }
        });

        mFragNavController.initialize(FragNavController.TAB1, savedInstanceState);
    }

    public boolean navigateBack() {
        if (mFragNavController.isRootFragment()) {
            return false;
        } else {
            mFragNavController.popFragment();
            return true;
        }
    }

    public void toSmsAllScreen() {
        mFragNavController.pushFragment(SmsAllFragment.newInstance());
    }

    public void toSmsDetailsScreen(long smsId) {
        mFragNavController.pushFragment(SmsDetailsFragment.newInstance(smsId));
    }
}
