package com.techyourchance.android_mvc_tutorial.screens.smsall;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.techyourchance.android_mvc_tutorial.screens.common.ViewMvcFactory;
import com.techyourchance.android_mvc_tutorial.screens.smsall.smslistitem.SmsListItemViewMvc;
import com.techyourchance.android_mvc_tutorial.sms.SmsMessage;

public class SmsAllListAdapter extends ArrayAdapter<SmsMessage> {

    private final ViewMvcFactory mViewMvcFactory;

    public SmsAllListAdapter(Context context, ViewMvcFactory viewMvcFactory) {
        super(context, 0);
        mViewMvcFactory = viewMvcFactory;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SmsListItemViewMvc smsListItemViewMvc;
        if (convertView == null) {
            smsListItemViewMvc = mViewMvcFactory.newSmsListItemViewMvc(parent);
            /*
             Since this kind of adapters store just references to Android Views, we need to "attach"
             the entire MVC view as a tag to its root view in order to be able to retrieve it later.
             Usage of MVC view in such a way completely eliminates a need for ViewHolder.
            */
            smsListItemViewMvc.getRootView().setTag(smsListItemViewMvc);
        } else {
            smsListItemViewMvc = ((SmsListItemViewMvc) convertView.getTag());
        }

        smsListItemViewMvc.bindSmsMessage(getItem(position));
        return smsListItemViewMvc.getRootView();
    }

}
