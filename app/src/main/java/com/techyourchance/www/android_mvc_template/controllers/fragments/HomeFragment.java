package com.techyourchance.www.android_mvc_template.controllers.fragments;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techyourchance.www.android_mvc_template.controllers.listadapters.HomeListAdapter;
import com.techyourchance.www.android_mvc_template.views.HomeViewMvc;
import com.techyourchance.www.android_mvc_template.views.HomeViewMvcImpl;

/**
 * A Fragment containing a list of SMS messages
 */
public class HomeFragment extends AbstractFragment implements
        LoaderManager.LoaderCallbacks<Cursor>, HomeViewMvc.HomeViewMvcListener {

    private static final String TAG = "HomeFragment";

    // ID for a loader employed in this controller
    private static final int SMS_LOADER = 0;

    HomeViewMvc mViewMVC;

    HomeListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Instantiate MVC view and set the fragment as listener
        mViewMVC = new HomeViewMvcImpl(inflater, container);
        mViewMVC.setListener(this);

        // Perform all initializations related to the ListView
        initializeList();

        // Return the root view of the associated MVC view
        return mViewMVC.getRootView();
    }


    private void initializeList() {
        /*
         Note that we are passing null instead of a Cursor - the actual Cursor with the
         results will be passed to this adapter after LoaderManager framework will call
         onLoadFinished() callback method.
          */
        mAdapter = new HomeListAdapter(getActivity(), null, 0);

        // Pass the adapter to the MVC view
        mViewMVC.setListAdapter(mAdapter);

        // This line of code initializes the LoaderManager framework and instructs it to "manage"
        // a Loader (in our case - CursorLoader) with the specified ID (SMS_LOADER)
        getLoaderManager().initLoader(SMS_LOADER, null, this);
    }

    @Override
    public void onListItemClick(int position, long id) {
        // Create a bundle that will pass the ID of the clicked SMS message to the new fragment
        Bundle args = new Bundle(1);
        args.putLong(SmsDetailsFragment.ARG_SMS_MESSAGE_ID, id);

        // Replace this fragment with a new one and pass args to it
        replaceFragment(SmsDetailsFragment.class, true, args);
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

            Uri contentUri = Uri.parse("content://sms/inbox");

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
            Log.e(TAG, "onCreateLoader() called with unrecognized id: " + id);
            return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (loader.getId() == SMS_LOADER) {
            // When the load is finished - pass the Cursor with the results to our CursorAdapter
            mAdapter.swapCursor(cursor);
        } else {
            Log.e(TAG, "onLoadFinished() called with unrecognized loader id: " + loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == SMS_LOADER) {
            // Releasing the resources
            mAdapter.swapCursor(null);
        } else {
            Log.e(TAG, "onLoaderReset() called with unrecognized loader id: " + loader.getId());
        }

    }


    // End of LoaderCallback methods
    //
    // ---------------------------------------------------------------------------------------------

}
