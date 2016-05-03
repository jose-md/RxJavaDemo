package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pepe on 2016/4/7.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Demo1Act extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_demo1);

        //对于任何 Observable 你可以定义在两个不同的线程，Observable 会操作在它上面。
        // 使用 Observable.observeOn() 可以定义在一个线程上，可以用来监听和检查从 Observable 最新发出的
        // items （Subscriber 的 onNext，onCompleted 和 onError 方法会执行在 observeOn 所指定的线程上），
        // 并使用 Observable.subscribeOn() 来定义一个线程，将其运行我们 Observable 的代码（长时间运行的操作）。
        final Observable operationObserable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber subscriber) {
                subscriber.onNext(longRunningOperation());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()) // subscribeOn the I/O thread,也就是call执行的线程
          .observeOn(AndroidSchedulers.mainThread()); // observeOn the UI Thread,也就是onNext()执行的线程
       //我们修改 Observable 将用 Schedulers.io() 去订阅，并用 AndroidSchedulers.mainThread() 方法将观察的结果返回到 UI 线程上 。
       // 现在，当我们建立我们的 APP 并点击我们的 Rx 操作的按钮，我们可以看到当操作运行时它将不再阻塞 UI 线程。


        Button startRxOperationButton = (Button) findViewById(R.id.start_btn);
        final LinearLayout rootView = (LinearLayout) findViewById(R.id.root_view);
        startRxOperationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setClickable(false);
                operationObserable.subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        v.setClickable(true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String str) {
                        Snackbar.make(rootView, str, Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public String longRunningOperation() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // error
        }
        return "Complete!";
    }


}

