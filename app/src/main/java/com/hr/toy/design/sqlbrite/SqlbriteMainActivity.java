package com.hr.toy.design.sqlbrite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

public class SqlbriteMainActivity extends AppCompatActivity implements ListsFragment.Listener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, ListsFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onListClicked(long id) {

    }

    @Override
    public void onNewListClicked() {

    }
}
