package com.techyourchance.www.android_mvc_template.controllers.fragments;

import android.app.Fragment;
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
import android.widget.ListView;

import com.techyourchance.www.android_mvc_template.R;
import com.techyourchance.www.android_mvc_template.controllers.listadapters.HomeFragmentListAdapter;
import com.techyourchance.www.android_mvc_template.views.HomeFragmentViewMVC;


/**
 * A Fragment containing a list of phone's contacts
 */
public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = HomeFragment.class.getSimpleName();

    // ID for a loader employed in this controller
    private static final int SMS_LOADER = 0;

    HomeFragmentViewMVC mViewMVC;

    HomeFragmentListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Instantiate MVC view associated with this fragment
        mViewMVC = new HomeFragmentViewMVC(inflater, container);

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

        mAdapter = new HomeFragmentListAdapter(getActivity(), null, 0);

        /*
         MVC Controller is allowed to be exposed to the implementation details of MVC view.
         In this case, we made this MVC controller dependent upon existence of a particular
         ListView in the corresponding MVC view, which is acceptable tradeof.
         */
        ((ListView)mViewMVC.getRootView().findViewById(R.id.list_contacts)).setAdapter(mAdapter);


        // This line of code initializes the LoaderManager framework and instructs it "manage"
        // a Loader (in our case - CursorLoader) with the specified ID (SMS_LOADER)
        getLoaderManager().initLoader(SMS_LOADER, null, this);
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
            Log.e(LOG_TAG, "onCreateLoader() called with unrecognized id: " + id);
            return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (loader.getId() == SMS_LOADER) {
            // When the load is finished - pass the Cursor with the results to our CursorAdapter
            mAdapter.swapCursor(cursor);
        } else {
            Log.e(LOG_TAG, "onLoadFinished() called with unrecognized loader id: " + loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == SMS_LOADER) {
            // Releasing the resources
            mAdapter.swapCursor(null);
        } else {
            Log.e(LOG_TAG, "onLoaderReset() called with unrecognized loader id: " + loader.getId());
        }

    }


    // End of LoaderCallback methods
    //
    // ---------------------------------------------------------------------------------------------



}
