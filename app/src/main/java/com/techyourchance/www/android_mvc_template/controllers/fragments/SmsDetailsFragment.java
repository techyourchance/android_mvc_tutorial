package com.techyourchance.www.android_mvc_template.controllers.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techyourchance.www.android_mvc_template.pojos.SmsMessage;
import com.techyourchance.www.android_mvc_template.views.SmsDetailsFragmentViewMVC;

/**
 * This fragment is used to show the details of a SMS message and mark it as read
 */
public class SmsDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {

    private static final String LOG_TAG = SmsDetailsFragment.class.getSimpleName();

    // ID for a loader employed in this controller
    private static final int SMS_LOADER = 0;

    /**
     * This constant should be used as a key in a Bundle passed to this fragment as an argument
     * at creation time. This key should correspond to the ID of the particular SMS message
     * which details will be shown in this fragment
     */
    public static final String ARG_SMS_MESSAGE_ID = "arg_sms_message_id";


    private SmsDetailsFragmentViewMVC mViewMVC;

    private long mSmsMessageId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Instantiate MVC view associated with this fragment
        mViewMVC = new SmsDetailsFragmentViewMVC(inflater, container);


        // Return the root view of the associated MVC view
        return mViewMVC.getRootView();
    }


    // ---------------------------------------------------------------------------------------------
    //
    // LoaderCallback methods

    /*
     MVC model of the app is the database of SMS messages stored on the device. The model
     is accessed via standard ContentProvider supplied as part of Android (starting API 19).
     In order to fetch data from ContentProvider we will use CursorLoader passed into LoaderManager
     framework.
     The below methods are callbacks executed by the LoaderManager framework.
      */

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {

        if (id == SMS_LOADER) {

            Uri contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://sms/inbox"), mSmsMessageId);

            // The "data columns" used in our app
            String[] projection = new String[] {
                    "_id",
                    "address",
                    "date",
                    "body",
                    "read"
            };

            // Change these values when adding filtering and sorting
            String selection = null;
            String[] selectionArgs = null;
            String sortOrder = "date DESC";


            // Creating and returning a Loader which should be used by LoaderManager

            //noinspection ConstantConditions
            return new CursorLoader(getActivity(),
                    contentUri,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder);

        } else {
            Log.e(LOG_TAG, "onCreateLoader() called with unrecognized id: " + id);
            return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (loader.getId() == SMS_LOADER) {

            if (cursor.moveToFirst()) {

                SmsMessage smsMessage;

                // Create SmsMessage object
                try {
                    smsMessage = SmsMessage.createSmsMessage(cursor);
                } catch (IllegalArgumentException e) {
                    Log.e(LOG_TAG, "Couldn't create SmsMessage from the provided Cursor...");
                    e.printStackTrace();
                    return;
                }

                // Order the MVC view to update itself
                mViewMVC.showSmsDetails(smsMessage);

            } else {
                Log.e(LOG_TAG, "the Cursor provided to onLoadFinished() is empty");
            }
        } else {
            Log.e(LOG_TAG, "onLoadFinished() called with unrecognized loader id: " + loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == SMS_LOADER) {
            // Nothing to do here
        } else {
            Log.e(LOG_TAG, "onLoaderReset() called with unrecognized loader id: " + loader.getId());
        }

    }


    // End of LoaderCallback methods
    //
    // ---------------------------------------------------------------------------------------------


}
