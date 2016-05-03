package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by pepe on 2016/4/7.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Demo2Act extends Activity {
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_demo2);
        final LinearLayout rootView = (LinearLayout) findViewById(R.id.root_view);
        Button startRxOperationButton = (Button) findViewById(R.id.start_btn);
        startRxOperationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setClickable(false);
                //当一个操作仅仅只需要发出一个结果然后就完成的情况我们可以有另外一个选择。
                // RxJava 发布的 1.0.13 版本介绍了 Single 类。
                // Single 类可以用于创建像下面这样的方法：
                subscription = Single.create(new Single.OnSubscribe<String>() {
                    @Override
                    public void call(SingleSubscriber<? super String> singleSubscriber) {
                        String value = longRunningOperation();
                        singleSubscriber.onSuccess(value);
                    }
                })
                        .subscribeOn(Schedulers.io()) // subscribeOn the I/O thread
                        .observeOn(AndroidSchedulers.mainThread()) // observeOn the UI Thread
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String value) {
                                v.setClickable(true);
                                Snackbar.make(rootView, value, Snackbar.LENGTH_SHORT).show();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });
                //当给一个 Single 类做订阅时，
                // 只有一个 onSuccess 的 Action 和 onError 的 action。Single 类有不同于 Observable 的操作符，
                // 有几个操作符具有将 Single 转换到 Observable 的机制。
                // 例如：用 Single.mergeWith() 操作符，两个或更多同类型的 Singles 可以合并到一起去创建一个 Observable，
                // 发出每个 Single 的结果给一个 Observable。
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

    /**
     * Subscription 类只有两个方法，unsubscribe() 和 isUnsubscribed()。
     * 为了防止可能的内存泄露，在你的 Activity 或 Fragment 的 onDestroy 里，
     * 用 Subscription.isUnsubscribed() 检查你的 Subscription 是否是 unsubscribed。
     * 如果调用了 Subscription.unsubscribe() ，Unsubscribing将会对 items 停止通知给你的 Subscriber，
     * 并允许垃圾回收机制释放对象，防止任何 RxJava 造成内存泄露。
     * 如果你正在处理多个 Observables 和 Subscribers，
     * 所有的 Subscription 对象可以添加到 CompositeSubscription，
     * 后可以使用 CompositeSubscription.unsubscribe() 方法在同一时间进行退订(unsubscribed)。
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
