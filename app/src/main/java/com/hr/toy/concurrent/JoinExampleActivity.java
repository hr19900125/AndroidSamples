package com.hr.toy.concurrent;

import com.hr.toy.BaseActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 阐述Java join
 */
public class JoinExampleActivity extends BaseActivity {

    @Override
    protected void click() {
        test();
    }

    private void test() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                printlnToTextView(mResultTextView, "Thread1 in running [" + Thread.currentThread().getId() + "]");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printlnToTextView(mResultTextView, "Thread1 is finish");
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {

            Thread t = thread1;

            @Override
            public void run() {
                printlnToTextView(mResultTextView, "thread2 in running [" + Thread.currentThread().getId() + "]");
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printlnToTextView(mResultTextView, "thread2 is finish");
            }
        });
        //发现使用executorService.submit去执行thread1还不行，得调用start
//        executorService.submit(thread1);
        executorService.submit(thread2);
        executorService.shutdown();
    }
}
