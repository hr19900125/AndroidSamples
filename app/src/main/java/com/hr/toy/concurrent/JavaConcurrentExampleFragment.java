package com.hr.toy.concurrent;

import android.content.Intent;

import com.hr.toy.BaseExampleFragment;

/**
 * Java 并发
 */
public class JavaConcurrentExampleFragment extends BaseExampleFragment {

    @Override
    protected String[] initData() {
        return new String[]{
                "Java Concurrent : ExecutorService (SingleThreadExecutor)",
                "Java Concurrent : BlockingQueue",
                "Java Concurrent : Callable and Future",
                "Java Concurrent : FutureTask",
                "Java Concurrent : ExecutorCompletionService",
                "Java Concurrent : join",
                "Java Concurrent : Wait and notify"
        };
    }

    @Override
    protected void handleClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent();
                intent.setClass(getActivity(), ExecutorServiceExampleActivity.class);
                break;
            case 1:
                intent = new Intent();
                intent.setClass(getActivity(), BlockingQueueExampleActivity.class);
                break;
            case 2:
                intent = new Intent();
                intent.setClass(getActivity(), CallableAndFutureExampleActivity.class);
                break;
            case 3:
                intent = new Intent();
                intent.setClass(getActivity(), FutureTaskExampleActivity.class);
                break;
            case 4:
                intent = new Intent();
                intent.setClass(getActivity(), ExecutorCompletionServiceExampleActivity.class);
                break;
            case 5:
                intent = new Intent();
                intent.setClass(getActivity(), JoinExampleActivity.class);
                break;
            case 6:
                intent = new Intent();
                intent.setClass(getActivity(), WaitAndNotifyExampleActivity.class);
                break;
        }

        if (intent != null) {
            getActivity().startActivity(intent);
        }
    }
}
