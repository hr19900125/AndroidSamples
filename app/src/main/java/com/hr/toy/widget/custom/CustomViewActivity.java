package com.hr.toy.widget.custom;

import android.os.Bundle;

import com.hr.toy.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 自定义View，主要是了解如何自定义view和attr，并且获取layout中的attr
 */
public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
    }
}
