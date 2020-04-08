package com.techyourchance.android_mvc_tutorial;

import android.app.Application;

import com.techyourchance.android_mvc_tutorial.common.dependencyinjection.ApplicationCompositionRoot;

public class MvcTutorialApplication extends Application {

    private ApplicationCompositionRoot mApplicationCompositionRoot;

    public ApplicationCompositionRoot getApplicationCompositionRoot() {
        if (mApplicationCompositionRoot == null) {
            mApplicationCompositionRoot = new ApplicationCompositionRoot(this);
        }
        return mApplicationCompositionRoot;
    }
}
