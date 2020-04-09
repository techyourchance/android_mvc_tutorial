package com.techyourchance.android_mvc_tutorial.screens.smsdetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techyourchance.android_mvc_tutorial.R;
import com.techyourchance.android_mvc_tutorial.screens.common.BaseViewMvc;
import com.techyourchance.android_mvc_tutorial.screens.common.toolbar.MyToolbar;
import com.techyourchance.android_mvc_tutorial.sms.SmsMessage;

public class SmsDetailsViewMvcImpl extends BaseViewMvc<SmsDetailsViewMvc.Listener>
        implements SmsDetailsViewMvc {

    private MyToolbar mToolbar;
    private TextView mTxtAddress;
    private TextView mTxtDate;
    private TextView mTxtBody;
    private Button mBtnMarkAsRead;

    private boolean mMarkAsReadSupported = true;

    public SmsDetailsViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.layout_sms_details, container, false));

        mToolbar = findViewById(R.id.toolbar);
        mTxtAddress = findViewById(R.id.txt_sms_address);
        mTxtDate = findViewById(R.id.txt_sms_date);
        mTxtBody = findViewById(R.id.txt_sms_body);
        mBtnMarkAsRead = findViewById(R.id.btn_mark_as_read);

        mToolbar.setNavigateUpListener(() -> {
            for (Listener listener : getListeners()) {
                listener.onNavigateUpClicked();
            }
        });

        mBtnMarkAsRead.setOnClickListener(view -> {
            for (Listener listener : getListeners()) {
                listener.onMarkAsReadClicked();
            }
        });
    }

    @Override
    public void hideMarkAsReadButton() {
        mMarkAsReadSupported = false;
    }


    @Override
    public void bindSmsMessage(SmsMessage smsMessage) {
        mTxtAddress.setText(smsMessage.getAddress());
        mTxtDate.setText(smsMessage.getDateString());
        mTxtBody.setText(smsMessage.getBody());

        int backgroundColor =
                smsMessage.isUnread() ? android.R.color.holo_green_light : android.R.color.white;

        getRootView().setBackgroundColor(getColor(backgroundColor));

        mBtnMarkAsRead.setVisibility(
                smsMessage.isUnread() && mMarkAsReadSupported ? View.VISIBLE : View.GONE
        );

    }
}
