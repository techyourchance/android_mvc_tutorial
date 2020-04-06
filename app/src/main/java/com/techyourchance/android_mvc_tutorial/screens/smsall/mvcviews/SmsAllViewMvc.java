package com.techyourchance.android_mvc_tutorial.screens.smsall.mvcviews;

import com.techyourchance.android_mvc_tutorial.sms.SmsMessage;
import com.techyourchance.android_mvc_tutorial.screens.common.mvcviews.ViewMvc;

import java.util.List;

/**
 * This MVC view corresponds to a screen where a list containing all SMS messages is shown
 */
public interface SmsAllViewMvc extends ViewMvc {


    interface SmsAllViewMvcListener {
        /**
         * This callback will be invoked when the user clicks on one of the shown SMS messages
         * @param id clicked message's ID
         */
        void onSmsMessageClicked(long id);
    }

    /**
     * Bind SMS messages data which should be shown by this MVC view
     * @param smsMessages list of {@link SmsMessage} objects that need to be shown
     */
    void bindSmsMessages(List<SmsMessage> smsMessages);

    /**
     * Set a listener that will be notified by this MVC view
     * @param listener listener that should be notified; null to clear
     */
    void setListener(SmsAllViewMvcListener listener);

}
