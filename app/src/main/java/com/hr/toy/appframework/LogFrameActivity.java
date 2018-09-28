package com.hr.toy.appframework;

import android.os.Bundle;

import com.hr.toy.BaseActivity;
import com.hr.toy.appframework.logback.FileLoggingTree;

import timber.log.Timber;

/**
 *
 */
public class LogFrameActivity extends BaseActivity {

    private boolean DEBUG = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new FileLoggingTree());
        }
    }

    @Override
    protected void click() {
        Timber.e("hello one");
        Timber.e("hello two");
    }
}
