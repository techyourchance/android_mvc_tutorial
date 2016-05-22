package com.techyourchance.www.android_mvc_template.views.home;

import android.widget.ListAdapter;

import com.techyourchance.www.android_mvc_template.views.ViewMvc;

/**
 * This interface corresponds to "home screen" of the app where a list containing many entries
 * should be displayed.
 */
public interface HomeViewMvc extends ViewMvc {

    public interface HomeViewMvcListener {
        /**
         * This callback method will be invoked when one of the list items is being clicked
         * @param position clicked item's position
         * @param id clicked item's ID
         */
        public void onListItemClick(int position, long id);
    }

    /**
     * Set an adapter that will be used in this MVC view
     * @param adapter instance of adapter to use
     */
    public void setListAdapter(ListAdapter adapter);

    /**
     * Set a listener that will be notified by this MVC view
     * @param listener listener that should be notified; null to clear
     */
    public void setListener(HomeViewMvcListener listener);

}
