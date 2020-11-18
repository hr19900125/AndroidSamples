package com.hr.toy.widget;

import android.os.Bundle;

import com.hr.toy.R;
import com.hr.toy.widget.common.LoadDataView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoadDataViewActivity extends AppCompatActivity {

    private LoadDataView loadDataView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddataview);
        loadDataView = findViewById(R.id.load_data_view);
        loadDataView.showProgress("正在加载");
    }
}
