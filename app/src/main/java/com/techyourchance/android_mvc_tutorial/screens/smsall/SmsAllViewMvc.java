package com.techyourchance.android_mvc_tutorial.screens.smsall;

import com.techyourchance.android_mvc_tutorial.sms.SmsMessage;
import com.techyourchance.android_mvc_tutorial.screens.common.ViewMvc;

import java.util.List;

public interface SmsAllViewMvc extends ViewMvc {


    interface Listener {
        void onSmsMessageClicked(long id);

        void onAskForPermissionClicked();
    }

    void showPermissionDenied();
    void showPermissionDeniedAndDontAskAgain();

    void bindSmsMessages(List<SmsMessage> smsMessages);

    void registerListener(Listener listener);
    void unregisterListener(Listener listener);

}
