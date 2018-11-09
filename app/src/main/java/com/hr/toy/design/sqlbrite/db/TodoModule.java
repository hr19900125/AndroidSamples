package com.hr.toy.design.sqlbrite.db;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DbModule.class})
public class TodoModule {

    private final Application application;

    public TodoModule(Application app) {
        this.application = app;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return application;
    }

}
