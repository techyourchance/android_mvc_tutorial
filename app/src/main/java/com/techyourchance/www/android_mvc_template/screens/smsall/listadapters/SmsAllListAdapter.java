package com.techyourchance.www.android_mvc_template.screens.smsall.listadapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.techyourchance.www.android_mvc_template.pojos.SmsMessage;
import com.techyourchance.www.android_mvc_template.screens.smsall.mvcviews.SmsThumbnailViewMvc;
import com.techyourchance.www.android_mvc_template.screens.smsall.mvcviews.SmsThumbnailViewMvcImpl;

/**
 * This adapter handles population of a list with instances of {@link SmsThumbnailViewMvc}
 */
public class SmsAllListAdapter extends ArrayAdapter<SmsMessage> {

    public SmsAllListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SmsThumbnailViewMvc smsThumbnailViewMvc;
        if (convertView == null) {
            smsThumbnailViewMvc =  new SmsThumbnailViewMvcImpl(getContext(), parent);
            /*
             Since this kind of adapters store just references to Android Views, we need to "attach"
             the entire MVC view as a tag to its root view in order to be able to retrieve it later.
             Usage of MVC view in such a way completely eliminates a need for ViewHolder.
             This is just a workaround though, the better option would be to create our own adapter
             for MVC views...
            */
            smsThumbnailViewMvc.getRootView().setTag(smsThumbnailViewMvc);
        } else {
            smsThumbnailViewMvc = ((SmsThumbnailViewMvc) convertView.getTag());
        }

        smsThumbnailViewMvc.bindSmsMessage(getItem(position));
        return smsThumbnailViewMvc.getRootView();
    }

}
