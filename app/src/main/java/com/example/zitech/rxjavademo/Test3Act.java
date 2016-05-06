package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Created by pepe on 2016/4/26.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Test3Act extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test3);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test3_btn1://creat
                break;
            case R.id.test3_btn2://just
                just();
                break;
            case R.id.test3_btn3://from
                from();
                break;
            case R.id.test3_btn5://defer
                defer();
                break;
            case R.id.test3_btn6://range
                range();
                break;
            case R.id.test3_btn7://interval
                interval();
                break;
            case R.id.test3_btn8://timer1
                timer1();
                break;
            case R.id.test3_btn9://timer2
                timer2();
                break;
            case R.id.test3_btn10://empty
                empty();
                break;
            case R.id.test3_btn11://never
                never();
                break;
            case R.id.test3_btn12://error
                error();
                break;

        }
    }

    private void just() {
        Observable.just(1, 2, 3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log(integer + "");
                    }
                });
    }

    private void from() {
        Integer[] items = new Integer[]{1, 2, 3};
        List<Integer> list = Arrays.asList(items);
        Observable.from(items)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer + "");
                    }
                });

    }

    private void repeat() {
        Observable.just(1, 2)
//                .repeat()//无限循环
                .repeat(2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer + "");
                    }
                });
    }

    private void defer() {
        Observable<Long> now = Observable.just(System.currentTimeMillis());
//        Observable<Long> now = Observable.defer(new Func0<Observable<Long>>() {
//            @Override
//            public Observable<Long> call() {
//                return Observable.just(System.currentTimeMillis());
//            }
//        });

        now.subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                log(String.valueOf(aLong));
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        now.subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                log(String.valueOf(aLong));
            }
        });

    }

    private void range() {
        Observable.range(5, 3)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer + "");
                    }
                });
    }

    private void interval() {
        Subscription subscription = Observable.interval(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log(aLong + "");
                    }
                });
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void timer1() {
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log(aLong + "");
                    }
                });
    }

    private void timer2() {
        Observable.timer(4, 2, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log(aLong + "");
                    }
                });
    }

    private void empty() {
        Observable<Integer> observable = Observable.empty();
        Subscription subscription = observable.subscribe(new Subscriber<Integer>() {
            public void onCompleted() {
                log("Complete!");
            }

            @Override
            public void onError(Throwable e) {
                log(e.getMessage().toString());
            }

            @Override
            public void onNext(Integer integer) {
                log(integer + "");
            }
        });
    }

    private void never() {
        Observable<Integer> observable = Observable.never();
        Subscription subscription = observable.subscribe(new Subscriber<Integer>() {
            public void onCompleted() {
                log("Complete!");
            }
            @Override
            public void onError(Throwable e) {
                log(e.getMessage().toString());
            }

            @Override
            public void onNext(Integer integer) {
                log(integer + "");
            }
        });
    }


    private void error() {
        Observable<Integer> observable = Observable.error(new Exception("Oops"));
        Subscription subscription = observable.subscribe(new Subscriber<Integer>() {
            public void onCompleted() {
                log("Complete!");
            }

            @Override
            public void onError(Throwable e) {
                log(e.getMessage().toString());
            }

            @Override
            public void onNext(Integer integer) {
                log(integer + "");
            }
        });
    }

    private void log(String string) {
        Log.d("pepe", string);
    }

}
