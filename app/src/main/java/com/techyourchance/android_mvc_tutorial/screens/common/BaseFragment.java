package com.techyourchance.android_mvc_tutorial.screens.common;

import com.techyourchance.android_mvc_tutorial.common.dependencyinjection.ActivityCompositionRoot;
import com.techyourchance.android_mvc_tutorial.screens.main.MainActivity;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected ActivityCompositionRoot getCompositionRoot() {
        return ((MainActivity)requireActivity()).getCompositionRoot();
    }

}
