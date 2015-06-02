package com.techyourchance.www.android_mvc_template.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.techyourchance.www.android_mvc_template.R;

import de.greenrobot.event.EventBus;

/**
 * This MVC view contains a list view and intercepts click events
 */
public class HomeFragmentViewMVC implements ViewMVC {


    View mRootView;

    ListView mListView;

    public HomeFragmentViewMVC(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);

        mListView = (ListView) mRootView.findViewById(R.id.list_sms_messages);

        // Register a listener for ListView's items
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Get the event bus
                EventBus eventBus = EventBus.getDefault();

                // Post a new event to the bus
                eventBus.post(new ListItemClickEvent(position, id));

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


    /**
     * This nested static class represents an item click event that will be posted on EventBus.
     */
    public static class ListItemClickEvent {
        public int mPosition;
        public long mId;

        public ListItemClickEvent(int position, long id) {
            mPosition = position;
            mId = id;
        }
    }
}
