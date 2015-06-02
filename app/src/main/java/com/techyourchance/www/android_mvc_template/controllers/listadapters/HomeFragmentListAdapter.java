package com.techyourchance.www.android_mvc_template.controllers.listadapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.techyourchance.www.android_mvc_template.pojos.SmsMessage;
import com.techyourchance.www.android_mvc_template.views.SmsThumbnailViewMVC;

/**
 * This implementation of CursorAdapter is used for parsing Cursor's entries into SmsMessage
 * objects, and displaying their contents in ListView
 */
public class HomeFragmentListAdapter extends CursorAdapter {


    private final static String LOG_TAG = HomeFragmentListAdapter.class.getSimpleName();


    public HomeFragmentListAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return new SmsThumbnailViewMVC(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        SmsMessage smsMessage;

        try {
            smsMessage = SmsMessage.createSmsMessage(cursor);
        } catch (IllegalArgumentException e) {
            Log.e(LOG_TAG, "Couldn't create SmsMessage from the provided Cursor...");
            e.printStackTrace();
            return;
        }

        ((SmsThumbnailViewMVC) view).showSmsThumbnail(smsMessage);
    }



}
