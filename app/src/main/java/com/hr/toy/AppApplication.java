package com.hr.toy;

import android.app.Application;
import android.content.Context;

import com.hr.toy.appframework.logback.FileLoggingTree;
import com.hr.toy.design.sqlbrite.DaggerTodoComponent;
import com.hr.toy.design.sqlbrite.TodoComponent;
import com.hr.toy.design.sqlbrite.db.TodoModule;

import timber.log.Timber;

/**
 *
 */
public class AppApplication extends Application {

    private static final boolean DEBUG = true;

    private static AppApplication application;

    private TodoComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        if (DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new FileLoggingTree());
        }
        mainComponent = DaggerTodoComponent.builder().todoModule(new TodoModule(this)).build();
    }

    public static Context getContext() {
        return application;
    }

    public static TodoComponent getComponent(Context context) {
        return ((AppApplication) context.getApplicationContext()).mainComponent;
    }

}
