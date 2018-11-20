package com.hr.toy.design.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import timber.log.Timber;

import static com.hr.toy.design.jobscheduler.JobSchedulerTestActivity.MESSENGER_INTENT_KEY;
import static com.hr.toy.design.jobscheduler.JobSchedulerTestActivity.MSG_JOB_START;
import static com.hr.toy.design.jobscheduler.JobSchedulerTestActivity.MSG_JOB_STOP;
import static com.hr.toy.design.jobscheduler.JobSchedulerTestActivity.WORK_DURATION_KEY;

public class MyJobService extends JobService {

    private static final String TAG = "MyJobService";

    private Messenger mActivityMessenger;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.tag(TAG).d("job service onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.tag(TAG).d("job service onDestroy");
    }

    // 当应用程序的MainActivity被创建时，它启动这个服务。
    // 这是为了使活动和此服务可以来回通信。 请参见“setUiCallback（）”
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mActivityMessenger = intent.getParcelableExtra(MESSENGER_INTENT_KEY);
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        // 返回true，表示该工作耗时，同时工作处理完成后需要调用onStopJob销毁（jobFinished）
        // 返回false，任务运行不需要很长时间，到return时已完成任务处理
        // The work that this service "does" is simply wait for a certain duration and finish
        // the job (on another thread).
        Log.e("Time", "onStartJob = " + System.currentTimeMillis());

        // 该服务做的工作只是等待一定的持续时间并完成作业（在另一个线程上）。
        sendMessage(MSG_JOB_START, params.getJobId());
        // 当然这里可以处理其他的一些任务

        // 获取在activity里边设置的每个任务的周期，其实可以使用setPeriodic()
        long duration = params.getExtras().getLong(WORK_DURATION_KEY);

        // 使用一个handler处理程序来延迟jobFinished（）的执行。
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendMessage(MSG_JOB_STOP, params.getJobId());
                jobFinished(params, false);
            }
        }, duration);
        Timber.tag(TAG).i("on start job: " + params.getJobId());
        // 返回true，很多工作都会执行这个地方，我们手动结束这个任务
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // 有且仅有onStartJob返回值为true时，才会调用onStopJob来销毁job
        // 返回false来销毁这个工作
        sendMessage(MSG_JOB_STOP, params.getJobId());
        Timber.tag(TAG).i("on stop job: " + params.getJobId());
        return false;
    }

    private void sendMessage(int messageID, @Nullable Object params) {
        // 如果此服务由JobScheduler启动，则没有回调Messenger。
        // 它仅在MainActivity在Intent中使用回调函数调用startService()时存在。
        if (mActivityMessenger == null) {
            Timber.tag(TAG).d("Service is bound, not started. There's no callback to send a message to.");
            return;
        }

        Message m = Message.obtain();
        m.what = messageID;
        m.obj = params;
        try {
            mActivityMessenger.send(m);
        } catch (RemoteException e) {
            Timber.tag(TAG).e("Error passing service object back to activity.");
        }
    }
}
