package com.hr.toy.rxjava;

import com.hr.toy.BaseActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Creating an Observable via the create( ) method
 */
public class ObservableCreateActivity extends BaseActivity {

    @Override
    protected void click() {
        create();
    }

    private void create() {
        Observable<String> ob = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("hello world! 1"); //1
//                String str = null; //这两行用来测试加入在call方法里面抛异常了之后的流程，流程是，call方法这里之后的代码都不会执行，sub的onError会接收到异常并调用
//                str.charAt(0);
                emitter.onNext("hello world! 2"); //2
                //下面用来测试onError被调用之后，之后的方法会不会执行，经测试之执行了1，2，并且会执行sub的onError方法
//                subscriber.onError(new RuntimeException()); //3
                emitter.onNext("hello world! 3"); //4
                emitter.onComplete(); //5 onCompleted方法假如不调用的话，下面的还会执行，问题：不调用onCompleted的后果？
                //下面用来测试onCompleted 调用之后，onNext是否还生效, 经测试验证是不生效的，下面的onNext没有执行。
                emitter.onNext("hello world! 4"); //6
            }
        });

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

        //当Observable 与Subscriber通过subscribe完成订阅之后，Observable里面的call方法马上就会执行
        ob.subscribe(sub);
    }
}
