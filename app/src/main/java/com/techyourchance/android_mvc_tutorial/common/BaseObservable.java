package com.techyourchance.android_mvc_tutorial.common;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseObservable<LISTENER_CLASS> {

    private final Object MONITOR = new Object();

    private final Set<LISTENER_CLASS> mListeners = new HashSet<>();

    public void registerListener(LISTENER_CLASS listener) {
        synchronized (MONITOR) {
            boolean hadNoListeners = mListeners.size() == 0;
            mListeners.add(listener);
            if (hadNoListeners && mListeners.size() == 1) {
                onFirstListenerRegistered();
            }
        }
    }

    public void unregisterListener(LISTENER_CLASS listener) {
        synchronized (MONITOR) {
            boolean hadOneListener = mListeners.size() == 1;
            mListeners.remove(listener);
            if (hadOneListener && mListeners.size() == 0) {
                onLastListenerUnregistered();
            }
        }
    }

    protected Set<LISTENER_CLASS> getListeners() {
        synchronized (MONITOR) {
            return Collections.unmodifiableSet(new HashSet<>(mListeners));
        }
    }

    protected void onFirstListenerRegistered() {

    }

    protected void onLastListenerUnregistered() {

    }

}
