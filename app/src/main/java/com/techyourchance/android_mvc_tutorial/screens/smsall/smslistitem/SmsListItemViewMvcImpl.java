package com.techyourchance.android_mvc_tutorial.screens.smsall.smslistitem;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techyourchance.android_mvc_tutorial.R;
import com.techyourchance.android_mvc_tutorial.screens.common.BaseViewMvc;
import com.techyourchance.android_mvc_tutorial.sms.SmsMessage;

public class SmsListItemViewMvcImpl extends BaseViewMvc<SmsListItemViewMvc.Listener>
        implements SmsListItemViewMvc {

    private final TextView mTxtAddress;
    private final TextView mTxtDate;

    public SmsListItemViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.layout_sms_list_item, container, false));

        mTxtAddress = findViewById(R.id.txt_sms_address);
        mTxtDate = findViewById(R.id.txt_sms_date);
    }

    @Override
    public void bindSmsMessage(SmsMessage smsMessage) {
        mTxtAddress.setText(smsMessage.getAddress());
        mTxtDate.setText(fromUnixTimestampToHumanReadableFormat(smsMessage.getDateString()));

        // Change the background depending on whether the message has already been read
        if (smsMessage.isUnread()) {
            getRootView().setBackgroundColor(getColor(android.R.color.holo_green_light));
        } else {
            getRootView().setBackgroundColor(getColor(android.R.color.white));
        }
    }

}
