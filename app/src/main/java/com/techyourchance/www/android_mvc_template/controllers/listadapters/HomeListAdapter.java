package com.techyourchance.www.android_mvc_template.controllers.listadapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.techyourchance.www.android_mvc_template.pojos.SmsMessage;
import com.techyourchance.www.android_mvc_template.views.SmsThumbnailViewMvc;
import com.techyourchance.www.android_mvc_template.views.SmsThumbnailViewMvcImpl;

/**
 * This implementation of CursorAdapter is used for parsing Cursor's entries into SmsMessage
 * objects, and displaying their contents in ListView
 */
public class HomeListAdapter extends CursorAdapter {


    private final static String TAG = "HomeListAdapter";


    public HomeListAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        /*
         Since this kind of adapters store just references to Android Views, we need to "attach"
         the entire MVC view as a tag to its root view in order to be able to retrieve it later.
         This is just a workaround though, the better option would be to create our own adapter
         for MVC views...
          */

        SmsThumbnailViewMvc smsThumbnailViewMvc =  new SmsThumbnailViewMvcImpl(context, viewGroup);
        View newView = smsThumbnailViewMvc.getRootView();
        newView.setTag(smsThumbnailViewMvc);
        return newView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        SmsMessage smsMessage;

        // Create SmsMessage object
        try {
            smsMessage = SmsMessage.createSmsMessage(cursor);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Couldn't create SmsMessage from the provided Cursor...");
            e.printStackTrace();
            return;
        }

        // Order the MVC View to update itself
        ((SmsThumbnailViewMvc) view.getTag()).showSmsThumbnail(smsMessage);
    }



}
