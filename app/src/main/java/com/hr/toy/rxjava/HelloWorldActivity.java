package com.hr.toy.rxjava;

import com.hr.toy.BaseActivity;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * RxJava Hello world
 */
public class HelloWorldActivity extends BaseActivity {

    @Override
    protected void click() {
        hello("hello world! 1", "hello world! 2", "hello world! 3");
    }

    private void hello(String... names) {
        Observable.fromArray(names).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                printlnToTextView(s);
            }
        });
    }
}
