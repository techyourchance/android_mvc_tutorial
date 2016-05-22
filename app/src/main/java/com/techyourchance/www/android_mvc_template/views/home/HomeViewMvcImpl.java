package com.techyourchance.www.android_mvc_template.views.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.techyourchance.www.android_mvc_template.R;

/**
 * This MVC view contains a list view and intercepts click events
 */
public class HomeViewMvcImpl implements HomeViewMvc {


    View mRootView;

    ListView mListView;

    HomeViewMvcListener mListener;

    public HomeViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.mvc_view_home, container, false);

        mListView = (ListView) mRootView.findViewById(R.id.list_sms_messages);

        // Register a listener for ListView's items
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onListItemClick(position, id);
                }
            }

        });
    }

    @Override
    public void setListener(HomeViewMvcListener listener) {
        mListener = listener;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

    @Override
    public void setListAdapter(ListAdapter adapter) {
        mListView.setAdapter(adapter);
    }


}
