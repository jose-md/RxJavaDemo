package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;
import rx.subscriptions.Subscriptions;

/**
 * Created by pepe on 2016/4/26.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Test2Act extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test2);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test2_btn1://基本使用
                baseUse();
                break;
            case R.id.test2_btn2://subscribeTest
                subscribeTest();
                break;
            case R.id.test2_btn3://unSubscribetest2
                unSubscribetest2();
                break;
            case R.id.test2_btn4://unSubscribeTest2
                unSubscribeTest2();
                break;
            case R.id.test2_btn5://onError 和 onCompleted
                errorAndCompletedTest();
                break;
            case R.id.test2_btn6://释放资源
                unSubscribeTest3();
                break;
        }
    }

    private void baseUse() {
        //一个最简单的实现
        //一个Observable（被观察者）
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello RxJava");
                subscriber.onCompleted();
            }
        });
        //一个Subscriber（订阅者）
        Subscriber<String> subscriber = new Subscriber<String>() {
            public void onCompleted() {
                log("Complete!");
            }

            @Override
            public void onError(Throwable e) {
                log(e.getMessage().toString());
            }

            @Override
            public void onNext(String s) {
                log(s);
            }
        };
        //订阅，Observable执行subscribe之后，调用call方法
        observable.subscribe(subscriber);
    }


    private void subscribeTest() {
        Subject<Integer, Integer> s = ReplaySubject.create();
        s.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log(integer + "");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                log(throwable.getMessage().toString());
            }
        });
        s.onNext(0);
        s.onError(new Exception("Oops"));
    }


    private void unSubscribetest2(){
        Subject<Integer, Integer>  values = ReplaySubject.create();
        Subscription subscription = values.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log(integer + "");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                log(throwable.getMessage().toString());
            }
        }, new Action0() {
            @Override
            public void call() {
                log("Done");
            }
        });
        values.onNext(0);
        values.onNext(1);
        subscription.unsubscribe();
        values.onNext(2);
    }
    private void unSubscribeTest2(){
        Subject<Integer, Integer>  values = ReplaySubject.create();
        Subscription subscription1 = values.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                log("Complete!" );
            }

            @Override
            public void onError(Throwable e) {
                log("First: "+e.getMessage().toString() );
            }

            @Override
            public void onNext(Integer integer) {
                log("First: "+integer);
            }
        });
        Subscription subscription2 = values.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                log("Complete!" );
            }

            @Override
            public void onError(Throwable e) {
                log("Second: "+e.getMessage().toString() );
            }

            @Override
            public void onNext(Integer integer) {
                log("Second: "+integer);
            }
        });
        values.onNext(0);
        values.onNext(1);
        subscription1.unsubscribe();
        log("Unsubscribed first" );
        values.onNext(2);
    }

    private void errorAndCompletedTest(){
        Subject<Integer, Integer>  values = ReplaySubject.create();
        Subscription subscription1 = values.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                log("Complete!" );
            }

            @Override
            public void onError(Throwable e) {
                log("First: "+e.getMessage().toString() );
            }

            @Override
            public void onNext(Integer integer) {
                log("First: "+integer);
            }
        });
        values.onNext(0);
        values.onNext(1);
        values.onCompleted();
        values.onNext(2);
    }

    private void unSubscribeTest3(){
        Subscription s= Subscriptions.create(new Action0() {
            @Override
            public void call() {
                log("Clean" );
            }
        });
        s.unsubscribe();
    }
    private void log(String string) {
        Log.d("pepe", string);
    }
}
