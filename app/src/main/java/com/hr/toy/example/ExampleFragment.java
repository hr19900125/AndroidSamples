package com.hr.toy.example;

import android.content.Intent;

import com.hr.toy.BaseExampleFragment;

/**
 *
 */
public class ExampleFragment extends BaseExampleFragment {

    @Override
    protected String[] initData() {
        return new String[]{
                "Activity生命周期",
                "Fragment生命周期",
                "View的滑动(layout)",
                "Android 事件传递机制(1)",
                "Android 事件传递机制(2)",
                "Android 事件传递机制(3)",
                "Android 异步消息处理机制(1)",
                "Android 异步消息处理机制(HandlerThread)",
                "Android AsyncTask",
                "Book"
        };
    }

    @Override
    protected void handleClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent();
                intent.setClass(getActivity(), ActLifeCycleActivity.class);
                break;
            case 1:
                intent = new Intent();
                intent.setClass(getActivity(), FragLifeCycleActivity.class);
                break;
            case 2:
                intent = new Intent();
                intent.setClass(getActivity(), MoveViewActivity.class);
                break;
            case 3:
                intent = new Intent();
                intent.setClass(getActivity(), TouchEventDispatch1Activity.class);
                break;
            case 4:
                intent = new Intent();
                intent.setClass(getActivity(), TouchEventDispatch2Activity.class);
                break;
            case 5:
                intent = new Intent();
                intent.setClass(getActivity(), TouchEventDispatch3Activity.class);
                break;
            case 6:
                intent = new Intent();
                intent.setClass(getActivity(), AndroidMessageActivity.class);
                break;
            case 7:
                intent = new Intent();
                intent.setClass(getActivity(), HandlerThreadActivity.class);
                break;
            case 8:
                intent = new Intent();
                intent.setClass(getActivity(), AsyncTaskActivity.class);
                break;
        }
        if (intent != null) {
            getActivity().startActivity(intent);
        }
    }
}
