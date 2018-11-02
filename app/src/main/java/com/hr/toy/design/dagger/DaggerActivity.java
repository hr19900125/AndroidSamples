package com.hr.toy.design.dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hr.toy.R;
import javax.inject.Inject;

import timber.log.Timber;

public class DaggerActivity extends AppCompatActivity {

    private static final String TAG = "DaggerActivity";

    @Inject
    DbWrapper mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        DaggerDbComponent.builder().build().inject(this);

        if (mDb != null) {
            Timber.tag(TAG).d("mDb is not null");
            if(mDb.dbHelper != null) {
                Timber.tag(TAG).d("mDb.dbHelper is not null");
            }
        } else {
            Timber.tag(TAG).d("mDb is null");
        }
    }
}
