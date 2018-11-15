package com.hr.toy.design.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hr.toy.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * https://www.jianshu.com/p/9fb882cae239
 * <p>
 * http://gityuan.com/2017/03/10/job_scheduler_service/
 */
public class JobSchedulerTestActivity extends AppCompatActivity {

    private static final String TAG = "JobScheduler";

    public static final String MESSENGER_INTENT_KEY = TAG + ".MESSENGER_INTENT_KEY";
    public static final String WORK_DURATION_KEY = TAG + ".WORK_DURATION_KEY";
    public static final int MSG_JOB_START = 1;
    public static final int MSG_JOB_STOP = 2;
    public static final int MSG_ONJOB_START = 3;
    public static final int MSG_ONJOB_STOP = 4;

    private static final int COLOR_START_RECEIVED = android.R.color.holo_green_light;
    private static final int COLOR_STOP_RECEIVED = android.R.color.holo_red_light;
    private static final int COLOR_NONE_RECEIVED = android.R.color.white;

    private IncomingMessageHandler mHandler;
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
        mHandler = new IncomingMessageHandler(this);
        mComponentName = new ComponentName(this, MyJobService.class);
        mBeginJob.setOnClickListener(view -> {
            scheduleJob();
        });

        mStopAllJob.setOnClickListener(view -> {
            cancelAllJobs();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // 启动服务并提供一种与此类通信的方法。
        Intent startServiceIntent = new Intent(this, MyJobService.class);
        Messenger messengerIncoming = new Messenger(mHandler);
        startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming);
        startService(startServiceIntent);
    }

    @Override
    protected void onStop() {
        // 服务可以是“开始”和/或“绑定”。 在这种情况下，它由此Activity“启动”
        // 和“绑定”到JobScheduler（也被JobScheduler称为“Scheduled”）。
        // 对stopService（）的调用不会阻止处理预定作业。
        // 然而，调用stopService（）失败将使它一直存活。
        stopService(new Intent(this, MyJobService.class));
        super.onStop();
    }

    private void scheduleJob() {
        JobInfo.Builder builder = new JobInfo.Builder(mJobId++, mComponentName);

        //设置任务的延迟执行时间(单位是毫秒)
        String delay = mEtDelay.getText().toString();
        if (!TextUtils.isEmpty(delay)) {
            builder.setMinimumLatency(Long.valueOf(delay) * 1000);
        }
        //设置任务最晚的延迟时间。如果到了规定的时间时其他条件还未满足，你的任务也会被启动。
        String deadline = mEtDeadline.getText().toString();
        if (!TextUtils.isEmpty(deadline)) {
            builder.setOverrideDeadline(Long.valueOf(deadline) * 1000);
        }

        boolean requiresWifiCon = mRbWifiCon.isChecked();
        boolean requiresAnyConnectivity = mRbAnyCon.isChecked();

        //让你这个任务只有在满足指定的网络条件时才会被执行
        if (requiresWifiCon) {
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        } else if (requiresAnyConnectivity) {
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        }

        //你的任务只有当用户没有在使用该设备且有一段时间没有使用时才会启动该任务。
        builder.setRequiresDeviceIdle(mCbRequiresIdle.isChecked());
        //告诉你的应用，只有当设备在充电时这个任务才会被执行。
        builder.setRequiresCharging(mCbRequiresCharging.isChecked());

        PersistableBundle extras = new PersistableBundle();
        String durationTime = mEtDurationTime.getText().toString();
        if (TextUtils.isEmpty(durationTime)) {
            durationTime = "1";
        }
        extras.putLong(WORK_DURATION_KEY, Long.valueOf(durationTime) * 1000);

        Timber.tag(TAG).d("Scheduling job");

        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        // 开始调度定义的job
        //mJobScheduler.schedule(builder.build())会返回一个int类型的数据
        //如果schedule方法失败了，它会返回一个小于0的错误码。否则它会返回我们在JobInfo.Builder中定义的标识id。
        mJobScheduler.schedule(builder.build());

    }

    // 当用户点击取消所有时执行
    public void cancelAllJobs() {
        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        mJobScheduler.cancelAll();
        Toast.makeText(this, "取消所有job", Toast.LENGTH_SHORT).show();
    }

    /**
     * {@link Handler}允许您发送与线程相关联的消息。
     * {@link Messenger}使用此处理程序从{@link MyJobService}进行通信。
     * 它也用于使开始和停止视图在短时间内闪烁。
     */
    private static class IncomingMessageHandler extends Handler {

        // 使用弱引用防止内存泄露
        private WeakReference<JobSchedulerTestActivity> mActivity;

        IncomingMessageHandler(JobSchedulerTestActivity activity) {
            super(/* default looper */);
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            JobSchedulerTestActivity mSchedulerAcitvity = mActivity.get();
            if (mSchedulerAcitvity == null) {
                // 活动不再可用，退出。
                return;
            }

            // 获取到两个View，用于之后根据Job运行状态显示不同的运行状态（颜色变化）
            View showStartView = mSchedulerAcitvity.findViewById(R.id.onstart_textview);
            View showStopView = mSchedulerAcitvity.findViewById(R.id.onstop_textview);

            Message m;
            switch (msg.what) {
                // 当作业登录到应用程序时，从服务接收回调。 打开指示灯（上方View闪烁）并发送一条消息，在一秒钟后将其关闭。
                case MSG_JOB_START:
                    // Start received, turn on the indicator and show text.
                    // 开始接收，打开指示灯（上方View闪烁）并显示文字。
                    showStartView.setBackgroundColor(getColor(COLOR_START_RECEIVED));
                    updateParamsTextView(msg.obj, "started");

                    // Send message to turn it off after a second.
                    // 发送消息，一秒钟后关闭它。
                    m = Message.obtain(this, MSG_ONJOB_START);
                    sendMessageDelayed(m, 1000L);
                    break;

                // 当先前执行在应用程序中的作业必须停止执行时，
                // 从服务接收回调。 打开指示灯并发送一条消息，
                // 在两秒钟后将其关闭。
                case MSG_JOB_STOP:
                    // Stop received, turn on the indicator and show text.
                    // 停止接收，打开指示灯并显示文本。
                    showStopView.setBackgroundColor(getColor(COLOR_STOP_RECEIVED));
                    updateParamsTextView(msg.obj, "stopped");

                    // Send message to turn it off after a second.
                    // 发送消息，一秒钟后关闭它。
                    m = obtainMessage(MSG_ONJOB_STOP);
                    sendMessageDelayed(m, 2000L);
                    break;
                case MSG_ONJOB_START:
                    showStartView.setBackgroundColor(getColor(COLOR_NONE_RECEIVED));
                    updateParamsTextView(null, "job had started");
                    break;
                case MSG_ONJOB_STOP:
                    showStopView.setBackgroundColor(getColor(COLOR_NONE_RECEIVED));
                    updateParamsTextView(null, "job had stoped");
                    break;
            }
        }

        // 更新UI显示
        // @param jobId jobId
        // @param action 消息
        private void updateParamsTextView(@Nullable Object jobId, String action) {
            TextView paramsTextView = (TextView) mActivity.get().findViewById(R.id.result);
            if (jobId == null) {
                paramsTextView.setText("");
                return;
            }
            String jobIdText = String.valueOf(jobId);
            paramsTextView.setText(String.format("Job ID %s %s", jobIdText, action));
        }

        private int getColor(@ColorRes int color) {
            return mActivity.get().getResources().getColor(color);
        }
    }

}
