package com.techyourchance.android_mvc_tutorial.screens.smsall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.techyourchance.android_mvc_tutorial.R;
import com.techyourchance.android_mvc_tutorial.screens.common.BaseViewMvc;
import com.techyourchance.android_mvc_tutorial.screens.common.ViewMvcFactory;
import com.techyourchance.android_mvc_tutorial.sms.SmsMessage;

import java.util.List;

public class SmsAllViewMvcImpl extends BaseViewMvc<SmsAllViewMvc.Listener> implements SmsAllViewMvc {

    private ListView mListView;
    private SmsAllListAdapter mSmsAllListAdapter;
    private View mViewPermissionDenied;
    private Button mBtnAskForPermission;
    private View mViewPermissionDeniedDontAskAgain;

    public SmsAllViewMvcImpl(LayoutInflater inflater, ViewGroup container, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.layout_sms_all, container, false));

        mSmsAllListAdapter = new SmsAllListAdapter(inflater.getContext(), viewMvcFactory);
        mListView = findViewById(R.id.list_sms_messages);
        mListView.setAdapter(mSmsAllListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                for (Listener listener : getListeners()) {
                    listener.onSmsMessageClicked(mSmsAllListAdapter.getItem(position).getId());
                }
            }
        });

        mViewPermissionDenied = findViewById(R.id.view_permission_denied);
        mBtnAskForPermission = findViewById(R.id.btn_request_permission);

        mBtnAskForPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Listener listener : getListeners()) {
                    listener.onAskForPermissionClicked();
                }
            }
        });

        mViewPermissionDeniedDontAskAgain = findViewById(R.id.view_permission_denied_dont_ask_again);
    }

    @Override
    public void showPermissionDenied() {
        hideAllViews();
        mViewPermissionDenied.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPermissionDeniedAndDontAskAgain() {
        hideAllViews();
        mViewPermissionDeniedDontAskAgain.setVisibility(View.VISIBLE);
    }

    @Override
    public void bindSmsMessages(List<SmsMessage> smsMessages) {
        hideAllViews();
        mListView.setVisibility(View.VISIBLE);

        mSmsAllListAdapter.clear();
        mSmsAllListAdapter.addAll(smsMessages);
        mSmsAllListAdapter.notifyDataSetChanged();
    }

    private void hideAllViews() {
        mListView.setVisibility(View.GONE);
        mViewPermissionDenied.setVisibility(View.GONE);
        mViewPermissionDeniedDontAskAgain.setVisibility(View.GONE);
    }
}
