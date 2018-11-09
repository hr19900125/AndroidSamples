package com.hr.toy.rxjava;

import android.os.SystemClock;

import com.hr.toy.BaseActivity;

import org.reactivestreams.Subscriber;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava 变换操作
 */
public class ObservableTransformOperatorsActivity extends BaseActivity {
    @Override
    protected void click() {
//        map();
//        flatMap();
//        buffer();
//        groupBy();
//        scan();
        window();
    }

    /**
     * 将一个事件转化为另外一个事件，即映射
     */
    private void map() {
        printlnToTextView("map-------------------------------");
        Observable.just("hello world").map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                printlnToTextView(s);
                return 5428;
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                printlnToTextView(String.valueOf(integer));
                return String.valueOf(integer);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                printlnToTextView(s);
            }
        });
    }

    /**
     * flatMap()接收一个Observable的输出作为输入，同时输出另外一个Observable，可以认为是处理上一个Observable的结果并做处理，处理的结果作为新的Observable供Subscribe订阅
     */
    private void flatMap() {
        printlnToTextView("flatMap-------------------------------");
        List<String> s = Arrays.asList("hello world 1", "hello world 2", "hello world 3");
        Observable.just(s).flatMap(new Function<List<String>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }

        }).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                printlnToTextView("---");
                printlnToTextView(s);
                return Observable.just("hi !" + s);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                printlnToTextView("---");
                printlnToTextView(s);
            }
        });
    }

    /**
     *
     */
    private void buffer() {
        printlnToTextView("buffer(int)-------------------------------");
        Observable.range(1, 10).buffer(3).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                printlnToTextView("####");
                for (int i = 0; i < integers.size(); i++) {
                    printlnToTextView("" + integers.get(i));
                }
            }
        });

        printlnToTextView("buffer(int, int) count > skip-------------------------------");
        Observable.range(1, 10).buffer(3, 2).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                printlnToTextView("####");
                for (int i = 0; i < integers.size(); i++) {
                    printlnToTextView("" + integers.get(i));
                }
            }
        });

        printlnToTextView("buffer(int, int) count < skip-------------------------------");
        Observable.range(1, 10).buffer(2, 3).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                printlnToTextView("####");
                for (int i = 0; i < integers.size(); i++) {
                    printlnToTextView("" + integers.get(i));
                }
            }
        });

        printlnToTextView("buffer(int, TimeUnit) 周期性发送buffer-------------------------------");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (emitter.isDisposed()) return;
                while (true) {
                    emitter.onNext("消息" + SystemClock.elapsedRealtime());
                    SystemClock.sleep(2000);//每隔2s发送消息
                }
            }
        }).subscribeOn(Schedulers.io()).
                buffer(3, TimeUnit.SECONDS).//每隔3秒 取出消息
                subscribe(new Observer<List<String>>() {
            @Override
            public void onComplete() {
                printlnToTextView("-----------------onCompleted:");
            }

            @Override
            public void onError(Throwable e) {
                printlnToTextView("----------------->onError:");
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<String> strings) {
                printlnToTextView("----------------->onNext:" + strings);
            }
        });
    }

    private void groupBy() {
        printlnToTextView("groupBy-------------------------------");
        Observable.interval(1, TimeUnit.SECONDS).take(10).groupBy(new Function<Long, Object>() {
            @Override
            public Object apply(Long aLong) throws Exception {
                return aLong % 4;
            }
        }).subscribe(new Consumer<GroupedObservable<Object, Long>>() {
            @Override
            public void accept(final GroupedObservable<Object, Long> result) throws Exception {
                result.subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        printlnToTextView("key:" + result.getKey() + ",value:" + aLong);
                    }
                });
            }
        });
    }

    /**
     * Scan操作符对原始Observable发射的第一项数据应用一个函数，然后将那个函数的结果作为自己的第一项数据发射。它将函数的结果同第二项数据一起填充给这个函数来产生它自己的第二项数据。它持续进行这个过程来产生剩余的数据序列。这个操作符在某些情况下被叫做accumulator。
     */
    private void scan() {
        printlnToTextView("scan-------------------------------");
        Observable.just(1, 2, 3, 4, 5).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer sum, Integer item) throws Exception {
                return sum + item;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer sum) throws Exception {
                printlnToTextView("sum = " + sum);
            }
        });
    }

    /**
     * window操作符非常类似于buffer操作符，区别在于buffer操作符产生的结果是一个List缓存，而window操作符产生的结果是一个Observable，订阅者可以对这个结果Observable重新进行订阅处理。
     */
    private void window() {
        printlnToTextView("window-------------------------------");
        Observable.interval(1, TimeUnit.SECONDS).take(12).window(3).subscribe(new Consumer<Observable<Long>>() {
            @Override
            public void accept(Observable<Long> observable) throws Exception {
                printlnToTextView("divide ---");
                observable.subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        printlnToTextView(String.valueOf(aLong));
                    }
                });
            }
        });
    }
}
