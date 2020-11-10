package com.hr.toy.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hr.toy.R;
import com.hr.toy.widget.view.WaterRippleView;

import androidx.appcompat.app.AppCompatActivity;


/**
 *
 */

public class WaterRippleViewActivity extends AppCompatActivity implements View.OnClickListener {

    private WaterRippleView mWaterRippleView;
    private Button mStartBtn;
    private Button mStopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_ripple_view);
        mWaterRippleView = (WaterRippleView) findViewById(R.id.wrv_water);
        mStartBtn = (Button) findViewById(R.id.start_btn);
        mStopBtn = (Button) findViewById(R.id.stop_btn);
        mStartBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                mWaterRippleView.start();
                break;
            case R.id.stop_btn:
                mWaterRippleView.stop();
                break;
        }
    }
}
