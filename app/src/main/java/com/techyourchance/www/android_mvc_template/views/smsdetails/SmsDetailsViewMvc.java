package com.techyourchance.www.android_mvc_template.views.smsdetails;

import com.techyourchance.www.android_mvc_template.pojos.SmsMessage;
import com.techyourchance.www.android_mvc_template.views.ViewMvc;

/**
 * This interface corresponds to "details" screen of the app, where details of a single SMS
 * message should be displayed
 */
public interface SmsDetailsViewMvc extends ViewMvc {


    public interface ShowDetailsViewMvcListener {
        /**
         * This callback will be invoked when "mark as read" button is being clicked
         */
        public void onMarkAsReadClick();
    }

    /**
     * Hide "mark as read" button
     */
    void hideMarkAsReadButton();

    /**
     * Show "mark as read" button
     */
    void showMarkAsReadButton();

    /**
     * Show details of a particular SMS message
     * @param smsMessage a message to show
     */
    void showSmsDetails(SmsMessage smsMessage);


    /**
     * Set a listener that will be notified by this MVC view
     * @param listener listener that should be notified; null to clear
     */
    public void setListener(ShowDetailsViewMvcListener listener);

}
