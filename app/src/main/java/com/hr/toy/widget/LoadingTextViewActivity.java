package com.hr.toy.widget;

import android.os.Bundle;
import android.os.Handler;

import com.hr.toy.R;
import com.hr.toy.widget.common.LoadingTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingTextViewActivity extends AppCompatActivity {

    private LoadingTextView mLoadingTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingtextview);
        mLoadingTextView = findViewById(R.id.loading_text_view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingTextView.setErrorStatus("加载失败,再来一次!");
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingTextView.setLoadingStatus();
            }
        }, 5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingTextView.setErrorBitmapType(LoadingTextView.REFRESH_BITMAP);
                mLoadingTextView.setErrorStatus("加载失败,再来一次!");
            }
        }, 10000);
    }
}
