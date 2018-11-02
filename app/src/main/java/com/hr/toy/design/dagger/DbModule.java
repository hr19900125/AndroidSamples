package com.hr.toy.design.dagger;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    DbWrapper provideDbWrapper(DbHelper helper) {
        return new DbWrapper(helper);
    }

    @Provides
    DbHelper provideDbHelper() {
        return new DbHelper();
    }

}
