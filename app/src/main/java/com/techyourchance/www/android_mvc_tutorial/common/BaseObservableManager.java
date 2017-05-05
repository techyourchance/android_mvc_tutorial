package com.techyourchance.www.android_mvc_tutorial.common;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base class for managers in the application
 * @param <LISTENER_CLASS> generics that designates the class of the listeners
 */
public class BaseObservableManager<LISTENER_CLASS> {

    // thread-safe set of listeners
    private Set<LISTENER_CLASS> mListeners = Collections.newSetFromMap(
            new ConcurrentHashMap<LISTENER_CLASS, Boolean>(1));


    public void registerListener(LISTENER_CLASS listener) {
        mListeners.add(listener);
    }

    public void unregisterListener(LISTENER_CLASS listener) {
        mListeners.remove(listener);
    }

    protected Set<LISTENER_CLASS> getListeners() {
        return mListeners;
    }

}
