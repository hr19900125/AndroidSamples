package com.hr.toy;

import android.app.Application;
import android.content.Context;

import com.hr.toy.appframework.logback.FileLoggingTree;

import timber.log.Timber;

/**
 *
 */
public class AppApplication extends Application {

    private static final boolean DEBUG = true;

    private static AppApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        if (DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new FileLoggingTree());
        }
    }

    public static Context getContext() {
        return application;
    }

}
