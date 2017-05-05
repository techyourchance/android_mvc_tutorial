package com.techyourchance.www.android_mvc_tutorial.screens.smsdetails.mvcviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techyourchance.www.android_mvc_tutorial.R;
import com.techyourchance.www.android_mvc_tutorial.sms.SmsMessage;

/**
 * An implementation of {@link SmsDetailsViewMvc} interface
 */
public class SmsDetailsViewMvcImpl implements SmsDetailsViewMvc {


    private View mRootView;
    private ShowDetailsViewMvcListener mListener;
    private boolean mMarkAsReadSupported = true;

    private TextView mTxtAddress;
    private TextView mTxtDate;
    private TextView mTxtBody;
    private Button mBtnMarkAsRead;

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
    public void markAsReadNotSupported() {
        mMarkAsReadSupported = false;
    }


    @Override
    public void bindSmsMessage(SmsMessage smsMessage) {
        mTxtAddress.setText(smsMessage.getAddress());
        mTxtDate.setText(smsMessage.getDate());
        mTxtBody.setText(smsMessage.getBody());


        mRootView.setBackgroundColor(smsMessage.isUnread() ?
                mRootView.getResources().getColor(android.R.color.holo_green_light) :
                mRootView.getResources().getColor(android.R.color.white));

        mBtnMarkAsRead.setVisibility(smsMessage.isUnread() && mMarkAsReadSupported ?
                View.VISIBLE : View.GONE);

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
