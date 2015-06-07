package com.techyourchance.www.android_mvc_template.controllers.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
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

import de.greenrobot.event.EventBus;

/**
 * This fragment is used to show the details of a SMS message and mark it as read
 */
public class SmsDetailsFragment extends AbstractFragment implements LoaderManager.LoaderCallbacks<Cursor>  {

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

    private EventBus mEventBus = EventBus.getDefault();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Instantiate MVC view associated with this fragment
        mViewMVC = new SmsDetailsFragmentViewMVC(inflater, container);


        // Get the argument of this fragment and look for the ID of the SMS message which should
        // be shown
        Bundle args = getArguments();
        if (args.containsKey(ARG_SMS_MESSAGE_ID)) {
            // Get the ID and init the LoaderManager
            mSmsMessageId = args.getLong(ARG_SMS_MESSAGE_ID);
            getLoaderManager().initLoader(SMS_LOADER, null, this);
        } else {
            // If no ID was provided in the arguments then LoaderManager will not be initiated,
            // and as a result of this - no data will be shown in this fragment
            Log.e(LOG_TAG, "no SMS message ID was passed in arguments");
        }

        // Return the root view of the associated MVC view
        return mViewMVC.getRootView();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register this fragment as a subscriber on EventBus
        mEventBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister this fragment from EventBus
        mEventBus.unregister(this);
    }

    /**
     * This method will be called by EventBus framework when events of type
     * {@link SmsDetailsFragmentViewMVC.ButtonMarkReadClickEvent} will be published.
     * @param event the event that was published on the bus
     */
    public void onEvent(SmsDetailsFragmentViewMVC.ButtonMarkReadClickEvent event) {

        /*
         onEvent() methods should return as quickly as possible in order not to delay notification
         of other subscribers on EventBus.

         Since marking the SMS message as read involves communication with "outer" ContentProvider,
         we will do it on a separate thread.
          */
        new Thread(new Runnable() {
            @Override
            public void run() {
                markRead();
            }
        }).start();
    }

    /**
     * Mark the SMS message as read
     */
    private void markRead() {
        // Uri of a particular SMS message
        Uri smsMessageUri = ContentUris.withAppendedId(
                Uri.parse("content://sms/inbox"), mSmsMessageId);

        // Designating the fields that should be updated
        ContentValues values = new ContentValues();
        values.put("read", true);

        getActivity().getContentResolver().update(smsMessageUri, values, null, null);
    }


    // ---------------------------------------------------------------------------------------------
    //
    // LoaderCallback methods

    /*
     MVC model of the app is the database of SMS messages stored on the device. The model
     is accessed via a non-standard ContentProvider having CONTENT_URI "content://sms/inbox"
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
