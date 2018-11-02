package com.hr.toy.design.dagger;

public class DbWrapper {

    DbHelper dbHelper;

    public DbWrapper(DbHelper helper) {
        this.dbHelper = helper;
    }
}
