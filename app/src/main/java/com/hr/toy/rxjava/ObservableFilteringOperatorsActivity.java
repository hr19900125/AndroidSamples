package com.hr.toy.rxjava;

import com.hr.toy.BaseActivity;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class ObservableFilteringOperatorsActivity extends BaseActivity {

    @Override
    protected void click() {
//        debounce();
//        distinct();
//        elementAt();
//        filter();
//        first();
//        ignoreElements();
//        last();
//        sample();
//        skip();
        skipLast();
        take();
        takeLast();
    }

    /**
     * debounce操作符
     * <p/>
     * debounce操作符对源Observable每产生一个结果后，如果在规定的间隔时间内没有别的结果产生，则把这个结果提交给订阅者处理，否则忽略该结果。
     * <p/>
     * 值得注意的是，如果源Observable产生的最后一个结果后在规定的时间间隔内调用了onCompleted，那么通过debounce操作符也会把这个结果提交给订阅者。
     * <p/>
     * 个人理解：就是假如发送A-(time1)-B-(time2)-C，A与B的发送时间间隔为time1，则debounce(int, TimeUnit)的时间为time，相当于过滤掉time1或time2小于time的之前的事件
     */
    private void debounce() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                if (emitter.isDisposed()) return;
                try {
                    for (int i = 1; i < 10; i++) {
                        emitter.onNext(i);
                        Thread.sleep(i * 100);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.newThread()).debounce(400, TimeUnit.MILLISECONDS).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                printlnToTextView("Next:" + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                printlnToTextView("Error:" + throwable);
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                printlnToTextView("onCompleted");
            }
        });
    }

    /**
     * Distinct的过滤规则是：只允许还没有发射过的数据项通过。
     */
    private void distinct() {
        Observable.just(1, 2, 2, 1, 3, 3).distinct().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                printlnToTextView("distinct number: " + integer);
            }
        });
    }

    /**
     * 只发射第N项数据
     */
    private void elementAt() {
        Observable.just(1, 2, 3, 4, 5, 6).elementAt(3).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                printlnToTextView("elementAt value: " + integer);
            }
        });
    }

    /**
     * 把不符合条件的过滤掉,留下符合条件的事件
     */
    private void filter() {
        printlnToTextView("filter-------------------------------");
        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer > 5;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        });
    }

    /**
     * 只发射第一项（或者满足某个条件的第一项）数据
     */
    private void first() {
        Observable.just(1, 2, 3, 4).first(1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                printlnToTextView("first value : " + integer);
            }
        });
    }

    /**
     * 不发射任何数据，只发射Observable的终止通知
     */
    private void ignoreElements() {
//        Observable.just(1, 2, 3).ignoreElements().subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer){
//                printlnToTextView("ignoreElements onNext : " + integer);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable){
//                printlnToTextView("ignoreElements onError : " + throwable);
//            }
//        }, new Action() {
//
//            @Override
//            public void run() throws Exception {
//                printlnToTextView("ignoreElements onCompleted");
//            }
//        });
    }

    /**
     * 只发射最后一项（或者满足某个条件的最后一项）数据
     */
    private void last() {
        Observable.just(1, 2, 3, 4).last(1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                printlnToTextView("last value : " + integer);
            }
        });
    }

    /**
     * sample操作符定期扫描源Observable产生的结果，在指定的时间间隔范围内对源Observable产生的结果进行采样
     */
    private void sample() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                if (emitter.isDisposed()) return;
                try {
                    //前8个数字产生的时间间隔为1秒，后一个间隔为3秒
                    for (int i = 1; i < 9; i++) {
                        emitter.onNext(i);
                        Thread.sleep(1000);
                    }
                    Thread.sleep(2000);
                    emitter.onNext(9);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread()).sample(2200, TimeUnit.MILLISECONDS).subscribe(new Observer<Integer>() {
            @Override
            public void onComplete() {
                printlnToTextView("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                printlnToTextView("onError : " + e);
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                printlnToTextView("onNext : " + integer);
            }
        });
    }

    /**
     * 抑制Observable发射的前N项数据
     */
    private void skip() {
        Observable.just(1, 2, 3, 4, 5, 6, 7).skip(3).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                printlnToTextView("skip value : " + integer);
            }
        });
    }

    /**
     * 抑制Observable发射的后N项数据
     */
    private void skipLast() {
        Observable.just(1, 2, 3, 4, 5, 6, 7).skipLast(3).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                printlnToTextView("skipLast value : " + integer);
            }
        });
    }

    /**
     * 取前面几个事件
     */
    private void take() {
        printlnToTextView("take-------------------------------");
        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}).take(4).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                printlnToTextView(String.valueOf(integer));
            }
        });
    }

    /**
     *
     */
    private void takeLast() {
        printlnToTextView("takeLast-------------------------------");
        Observable.fromArray(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}).takeLast(4).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                printlnToTextView(String.valueOf(integer));
            }
        });
    }

}
