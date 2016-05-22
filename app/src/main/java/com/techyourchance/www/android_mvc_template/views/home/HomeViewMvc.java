package com.techyourchance.www.android_mvc_template.views.home;

import android.database.Cursor;
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
     * Bind a cursor containing SMS messages data which should be shown by this MVC view
     * @param cursor a valid cursor object; NOTE: it is assumed that the cursors passed to this
     *               method are managed externally, but will not be closed or invalidated as long
     *               as it is not "cleared" from this MVC view by passing another Cursor or null to
     *               this method
     */
    public void swapCursor(Cursor cursor);

    /**
     * Set a listener that will be notified by this MVC view
     * @param listener listener that should be notified; null to clear
     */
    public void setListener(HomeViewMvcListener listener);

}
