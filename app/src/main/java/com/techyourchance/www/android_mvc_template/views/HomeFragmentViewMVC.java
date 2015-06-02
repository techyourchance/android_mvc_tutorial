package com.techyourchance.www.android_mvc_template.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.techyourchance.www.android_mvc_template.R;

/**
 * This MVC view contains a list view and intercepts click events
 */
public class HomeFragmentViewMVC implements ViewMVC {


    View mRootView;

    ListView mListView;

    public HomeFragmentViewMVC(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);

        mListView = (ListView) mRootView.findViewById(R.id.list_sms_messages);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO: send message to the controller
            }
        });
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
