package com.techyourchance.android_mvc_tutorial.screens.smsall.smslistitem;

import com.techyourchance.android_mvc_tutorial.sms.SmsMessage;
import com.techyourchance.android_mvc_tutorial.screens.common.ViewMvc;

public interface SmsListItemViewMvc extends ViewMvc {

    interface Listener {
        // currently no interactions
    }

    void bindSmsMessage(SmsMessage smsMessage);
}
