package com.techyourchance.www.android_mvc_tutorial.screens.smsdetails.mvcviews;

import com.techyourchance.www.android_mvc_tutorial.sms.SmsMessage;
import com.techyourchance.www.android_mvc_tutorial.screens.common.mvcviews.ViewMvc;

/**
 * This interface corresponds to "details" screen of the app, where details of a single SMS
 * message should be displayed
 */
public interface SmsDetailsViewMvc extends ViewMvc {


    interface ShowDetailsViewMvcListener {
        /**
         * This callback will be invoked when "mark as read" button is being clicked
         */
        void onMarkAsReadClick();
    }

    /**
     * Hide "mark as read" button
     */
    void markAsReadNotSupported();

    /**
     * Show details of a particular SMS message
     * @param smsMessage a message to show
     */
    void bindSmsMessage(SmsMessage smsMessage);


    /**
     * Set a listener that will be notified by this MVC view
     * @param listener listener that should be notified; null to clear
     */
    void setListener(ShowDetailsViewMvcListener listener);

}
