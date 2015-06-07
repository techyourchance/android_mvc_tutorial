package com.techyourchance.www.android_mvc_template.controllers.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.techyourchance.www.android_mvc_template.R;
import com.techyourchance.www.android_mvc_template.controllers.fragments.AbstractFragment;
import com.techyourchance.www.android_mvc_template.controllers.fragments.HomeFragment;
import com.techyourchance.www.android_mvc_template.views.MainActivityViewMVC;


public class MainActivity extends Activity implements AbstractFragment.AbstractFragmentCallback{

    MainActivityViewMVC mViewMVC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        The MVC view associated with this activity is very simple, therefore the below lines could
        be replaced with:
        setContentView(R.layout.activity_main);
        and there would be no need for MainActivityViewMVC class.
        However, for the sake of consistency, we should try to stick to a single coding style,
        therefore we employ full MVC approach even in this simple case!
        */

        // Instantiate MVC view associated with this activity
        mViewMVC = new MainActivityViewMVC(this, null);

        // Set the root view of the associated MVC view as the content of this activity
        setContentView(mViewMVC.getRootView());

        // Show the default fragment if the application is not restored
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment.class, false, null);
        }
    }




    // ---------------------------------------------------------------------------------------------
    //
    // Fragments management

    @Override
    public void replaceFragment(Class<? extends Fragment> claz, boolean addToBackStack,
                                Bundle args) {


        // If the required fragment is already shown - do nothing
        if (isFragmentShown(claz)) {
            return;
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();


        Fragment newFragment;

        try {
            // Create new fragment
            newFragment = claz.newInstance();
            if (args != null) newFragment.setArguments(args);
        } catch (InstantiationException e) {
            e.printStackTrace();
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        }

        if (addToBackStack) {
            // Add this transaction to the back stack
            ft.addToBackStack(null);
        }

        // Change to a new fragment
        ft.replace(R.id.frame_contents, newFragment, claz.getClass().getSimpleName());
        ft.commit();

    }

    /**
     * Check whether a fragment of a specific class is currently shown
     * @param claz class of fragment to test. Null considered as "test no fragment shown"
     * @return true if fragment of the same class (or a superclass) is currently shown
     */
    private boolean isFragmentShown(Class<? extends Fragment> claz) {
        Fragment currFragment = getFragmentManager().findFragmentById(R.id.frame_contents);


        return (currFragment == null && claz == null) ||
                (currFragment != null && claz.isInstance(currFragment));
    }

    // End of fragments management
    //
    // ---------------------------------------------------------------------------------------------


}
