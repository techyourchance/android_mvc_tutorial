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
                // TODO: add messaging to the controller
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
}
