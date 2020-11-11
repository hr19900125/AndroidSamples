package com.hr.toy.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

public class Jumper {

    public static void startActivity(Activity fromAct, String toActClassName) throws IllegalAccessException {
        if (TextUtils.isEmpty(toActClassName)) {
            throw new IllegalAccessException("target Activity name must be not empty.");
        }
        Intent intent = new Intent();
        intent.setClassName(fromAct.getPackageName(), toActClassName);
        fromAct.startActivity(intent);
    }

}
