package com.techyourchance.www.android_mvc_tutorial.screens.smsall.mvcviews;

import com.techyourchance.www.android_mvc_tutorial.pojos.SmsMessage;
import com.techyourchance.www.android_mvc_tutorial.screens.common.mvcviews.ViewMvc;

/**
 * This interface corresponds to a single SMS preview element (thumbnail) which is intended to
 * be used in a list of similar elements
 */
public interface SmsThumbnailViewMvc extends ViewMvc {

    /**
     * Show thumbnail of a particular SMS message
     * @param smsMessage the message to show
     */
    void bindSmsMessage(SmsMessage smsMessage);
}
