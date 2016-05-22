package com.techyourchance.www.android_mvc_template.views.smsdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techyourchance.www.android_mvc_template.R;
import com.techyourchance.www.android_mvc_template.pojos.SmsMessage;

/**
 * This MVC view contains a list view and intercepts click events
 */
public class SmsDetailsViewMvcImpl implements SmsDetailsViewMvc {


    View mRootView;
    ShowDetailsViewMvcListener mListener;

    TextView mTxtAddress;
    TextView mTxtDate;
    TextView mTxtBody;
    Button mBtnMarkAsRead;

    public SmsDetailsViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.mvc_view_sms_details, container, false);

        initialize();

        mBtnMarkAsRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onMarkAsReadClick();
                }
            }
        });
    }


    private void initialize() {
        mTxtAddress = (TextView) mRootView.findViewById(R.id.txt_sms_address);
        mTxtDate = (TextView) mRootView.findViewById(R.id.txt_sms_date);
        mTxtBody = (TextView) mRootView.findViewById(R.id.txt_sms_body);
        mBtnMarkAsRead = (Button) mRootView.findViewById(R.id.btn_mark_as_read);
    }

    @Override
    public void showSmsDetails(SmsMessage smsMessage) {
        mTxtAddress.setText(smsMessage.mAddress);
        mTxtDate.setText(smsMessage.mDate);
        mTxtBody.setText(smsMessage.mBody);

        if (smsMessage.mUnread) {
            mRootView.setBackgroundColor(mRootView.getResources().getColor(android.R.color.holo_green_light));
            mBtnMarkAsRead.setVisibility(View.VISIBLE);
        } else {
            mRootView.setBackgroundColor(mRootView.getResources().getColor(android.R.color.white));
            mBtnMarkAsRead.setVisibility(View.GONE);
        }
    }

    @Override
    public void setListener(ShowDetailsViewMvcListener listener) {
        mListener = listener;
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
