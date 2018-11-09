package com.hr.toy.rxjava;

import com.hr.toy.BaseActivity;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 使用Observable.just可以用来创建只发出一个事件就结束的Observable对象
 */
public class ObservableJustActivity extends BaseActivity {

    @Override
    protected void click() {
        just();
    }

    private void just() {
        Observable<String> ob = Observable.just("hello world");
        Observer<String> sub = new Observer<String>() {
            @Override
            public void onComplete() {
                printlnToTextView(mResultTextView, "Subscriber call onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                printlnToTextView(mResultTextView, "Subscriber call onError");
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                printlnToTextView(mResultTextView, s);
            }
        };
        ob.subscribe(sub);
    }
}
