package com.hr.toy.design.jobscheduler;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.hr.toy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * https://www.jianshu.com/p/9fb882cae239
 *
 * http://gityuan.com/2017/03/10/job_scheduler_service/
 */
public class JobSchedulerTestActivity extends AppCompatActivity {

    private static final String TAG = "JobScheduler";

    private ComponentName mComponentName;
    private int mJobId = 0;

    @BindView(R.id.et_delay)
    EditText mEtDelay; // 设置delay时间
    @BindView(R.id.et_deadline)
    EditText mEtDeadline; // 设置最长的截止时间
    @BindView(R.id.et_duration_time)
    EditText mEtDurationTime; // setPeriodic周期
    @BindView(R.id.rb_wifi_con)
    RadioButton mRbWifiCon; // 设置builder中的是否有WiFi连接
    @BindView(R.id.rb_any_con)
    RadioButton mRbAnyCon; // 设置builder中的是否有网络即可
    @BindView(R.id.cb_requires_charging)
    CheckBox mCbRequiresCharging; // 设置builder中的是否需要充电
    @BindView(R.id.cb_requires_idle)
    CheckBox mCbRequiresIdle; // 设置builder中的是否设备空闲
    @BindView(R.id.begin_job)
    Button mBeginJob; // 点击开始任务的按钮
    @BindView(R.id.stop_all_job)
    Button mStopAllJob; // 点击结束所有任务的按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler);
        ButterKnife.bind(this);
        mComponentName = new ComponentName(this, MyJobService.class);
        mBeginJob.setOnClickListener(view -> {

        });

        mStopAllJob.setOnClickListener(view -> {

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void scheduleJob() {
        JobInfo.Builder builder = new JobInfo.Builder(mJobId ++, mComponentName);

        //设置任务的延迟执行时间(单位是毫秒)
        String delay = mEtDelay.getText().toString();
        if(!TextUtils.isEmpty(delay)) {
            builder.setMinimumLatency(Long.valueOf(delay) * 1000);
        }



    }
}
