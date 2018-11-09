package com.hr.toy.rxjava;

import com.hr.toy.BaseActivity;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 使用RxJava 提供的Action来订阅（subscribe）
 */
public class SubscribeActionActivity extends BaseActivity {

    @Override
    protected void click() {
        test();
    }

    private void test() {
        Observable<String> ob = Observable.fromArray(new String[]{"hello world 1", "hello world 2", "hello world 3"});
        ob.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                printlnToTextView("onNext:" + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                printlnToTextView("onError");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                printlnToTextView("onCompleted");
            }
        });
    }
}
