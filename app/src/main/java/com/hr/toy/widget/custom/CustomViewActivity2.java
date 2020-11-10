package com.hr.toy.widget.custom;

import android.os.Bundle;

import com.hr.toy.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 自定义的View，使用<declare-styleable>和obtainStyledAttributes方法
 */
public class CustomViewActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_2);
    }
}
