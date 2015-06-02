package com.techyourchance.www.android_mvc_template.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.techyourchance.www.android_mvc_template.R;
import com.techyourchance.www.android_mvc_template.pojos.SmsMessage;

import de.greenrobot.event.EventBus;

/**
 * This MVC view contains a list view and intercepts click events
 */
public class SmsDetailsFragmentViewMVC implements ViewMVC {


    View mRootView;

    TextView mTxtAddress;
    TextView mTxtDate;
    TextView mTxtBody;
    Button mBtnMarkRead;

    public SmsDetailsFragmentViewMVC(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.fragment_sms_details, container, false);

        initialize();

        mBtnMarkRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get EventBus instance
                EventBus eventBus = EventBus.getDefault();
                // Post a click event to EventBus
                eventBus.post(new ButtonMarkReadClickEvent());
            }
        });
    }


    private void initialize() {
        mTxtAddress = (TextView) mRootView.findViewById(R.id.txt_sms_address);
        mTxtDate = (TextView) mRootView.findViewById(R.id.txt_sms_date);
        mTxtBody = (TextView) mRootView.findViewById(R.id.txt_sms_body);
        mBtnMarkRead = (Button) mRootView.findViewById(R.id.btn_mark_read);
    }

    public void showSmsDetails(SmsMessage smsMessage) {
        mTxtAddress.setText(smsMessage.mAddress);
        mTxtDate.setText(smsMessage.mDate);
        mTxtBody.setText(smsMessage.mBody);

        if (smsMessage.mUnread) {
            mRootView.setBackgroundColor(mRootView.getResources().getColor(android.R.color.holo_green_light));
            mBtnMarkRead.setVisibility(View.VISIBLE);
        } else {
            mRootView.setBackgroundColor(mRootView.getResources().getColor(android.R.color.white));
            mBtnMarkRead.setVisibility(View.GONE);
        }
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

    /**
     * This nested static class represents a button click event that will be posted on EventBus.
     */
    public static class ButtonMarkReadClickEvent {
        public ButtonMarkReadClickEvent() {}
    }
}
