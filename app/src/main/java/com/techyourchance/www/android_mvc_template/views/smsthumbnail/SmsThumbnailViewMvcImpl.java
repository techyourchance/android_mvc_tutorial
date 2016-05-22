package com.techyourchance.www.android_mvc_template.views.smsthumbnail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techyourchance.www.android_mvc_template.R;
import com.techyourchance.www.android_mvc_template.Utils;
import com.techyourchance.www.android_mvc_template.pojos.SmsMessage;

/**
 * This MVC view represents a single element in ListView.
 */
public class SmsThumbnailViewMvcImpl implements SmsThumbnailViewMvc {

    private final Context context;
    View mRootView;

    private TextView mTxtAddress;
    private TextView mTxtDate;


    public SmsThumbnailViewMvcImpl(Context context, ViewGroup container) {
        this.context = context;
        mRootView = LayoutInflater.from(context)
                .inflate(R.layout.mvc_view_sms_thumbnail, container, false);

        initialize();
    }

    /**
     * This method initializes member fields of this object
     */
    private void initialize() {
        mTxtAddress = (TextView) mRootView.findViewById(R.id.txt_sms_address);
        mTxtDate = (TextView) mRootView.findViewById(R.id.txt_sms_date);
    }

    @Override
    public void showSmsThumbnail(SmsMessage smsMessage) {
        mTxtAddress.setText(smsMessage.mAddress);
        mTxtDate.setText(Utils.convertToHumanReadableDate(smsMessage.mDate));

        // Change the background depending on whether the message has already been read
        if (smsMessage.mUnread) {
            mRootView.setBackgroundColor(
                    context.getResources().getColor(android.R.color.holo_green_light));
        } else {
            mRootView.setBackgroundColor(
                    context.getResources().getColor(android.R.color.white));
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
