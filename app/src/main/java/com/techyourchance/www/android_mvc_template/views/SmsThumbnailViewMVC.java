package com.techyourchance.www.android_mvc_template.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techyourchance.www.android_mvc_template.R;
import com.techyourchance.www.android_mvc_template.pojos.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This MVC view represents a single element in ListView.
 *
 * In order to be used in ListAdapters, this class must extend android.view.View class (or one
 * of its descendants). In our case, we extend LinearLayout.
 */
public class SmsThumbnailViewMVC extends LinearLayout implements ViewMVC {

    private TextView mTxtAddress;
    private TextView mTxtDate;


    public SmsThumbnailViewMVC(Context context) {
        super(context);

        setOrientation(LinearLayout.VERTICAL);

        /*
         Inflate the underlying layout from a static xml file.

         Note: since this class extends LinearLayout, the inflated xml file can have <merge> tags
         enclosing it.
        */
        LayoutInflater.from(context).inflate(R.layout.layout_sms_thumbnail, this, true);


        initialize();
    }

    /**
     * This method initializes member fields of this object
     */
    private void initialize() {
        mTxtAddress = (TextView) findViewById(R.id.txt_sms_address);
        mTxtDate = (TextView) findViewById(R.id.txt_sms_date);
    }

    public void showSmsThumbnail(SmsMessage smsMessage) {
        mTxtAddress.setText(smsMessage.mAddress);
        mTxtDate.setText(convertToHumanReadableDate(smsMessage.mDate));

        // Change the background depending on whether the message has already been read
        if (smsMessage.mUnread) {
            setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else {
            setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }

    /**
     * Convert Unix timestamp to a human readable representation of date
     */
    private String convertToHumanReadableDate(String timestamp) {
        SimpleDateFormat fmtOut = new SimpleDateFormat();
        return fmtOut.format(new Date(Long.valueOf(timestamp)));
    }

    @Override
    public Bundle getViewState() {
        return null;
    }
}
