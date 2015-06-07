package com.techyourchance.www.android_mvc_template.controllers.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * This class contains some convenience logic which is common to all fragments in the app.
 */
public class AbstractFragment extends Fragment {


    AbstractFragmentCallback mCallback;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (AbstractFragmentCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + AbstractFragmentCallback.class.getCanonicalName());
        }

    }

    /**
     * This method replaces the currently shown fragment with a new fragment of a particular class.
     * If a fragment of the required class already shown - does nothing.
     * @param claz the class of the fragment to show
     * @param addToBackStack whether the replacement should be added to back-stack
     * @param args arguments for the newly created fragment (can be null)
     */
    public void replaceFragment(Class<? extends Fragment> claz, boolean addToBackStack,
                                Bundle args) {
        mCallback.replaceFragment(claz, addToBackStack, args);
    }


    /**
     * The enclosing activity must implement this interface
     */
    public interface AbstractFragmentCallback {

        /**
         * Call to this method replaces the currently shown fragment with a new one
         *
         * @param claz           the class of the new fragment
         * @param addToBackStack whether the old fragment should be added to the back stack
         * @param args           arguments to be set for the new fragment
         */
        public void replaceFragment(Class<? extends Fragment> claz, boolean addToBackStack,
                                    Bundle args);

    }

}
