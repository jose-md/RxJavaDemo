package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by pepe on 2016/4/27.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Test5Act extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test5);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test5_btn1://all:满足条件
                all1();
                break;
            case R.id.test5_btn2://all:不满足条件
                all2();
                break;
            case R.id.test5_btn3://all:数据错误
                all3();
                break;
            case R.id.test4_btn10://ofType
                ofType();
                break;
            case R.id.test4_btn11://single
                single();
                break;
            case R.id.test5_btn4://exists
                exists();
                break;
            case R.id.test5_btn5://isEmpty
                isEmpty();
                break;
            case R.id.test5_btn6://contains
                contains();
                break;
            case R.id.test5_btn7://defaultIfEmpty
                defaultIfEmpty();
                break;
            case R.id.test5_btn8://sequenceEqual
                sequenceEqual();
                break;
            case R.id.test5_btn9://takeLastAndSkipLast
                break;

        }
    }

    private void all1() {
        Observable<Integer> values = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(0);
                subscriber.onNext(10);
                subscriber.onNext(20);
                subscriber.onNext(2);
                subscriber.onCompleted();
            }
        });

        Subscription evenNumbers = values
                .all(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log(aBoolean + "");
                    }
                });

    }

    private void all2() {
        Observable<Long> values = Observable.interval(150, TimeUnit.MILLISECONDS).take(5);
        Subscription subscription = values
                .all(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        return aLong < 3;
                    }
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("First:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("First:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log("First:" + aBoolean);
                    }
                });
        Subscription subscription2 = values
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        log("Second:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Second:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        log("Second:" + aLong);
                    }
                });
    }

    private void all3() {
        Observable<Integer> values = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(0);
//                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onError(new Exception("Oops"));
            }
        });
        Subscription subscription = values
                .all(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log(aBoolean + "");
                    }
                });
    }

    private void ofType() {
        Observable.just(0, "1", 2, "3").ofType(String.class)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object object) {
                        log(object.toString() + ":" + object.getClass());
                    }
                });
    }
    private void single() {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
        values.take(10) // 获取前 10 个数据 的 Observable
                .single(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        return aLong == 5L;
                    }
                })  // 有且仅有一个 数据为 5L
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        log("Single1:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Single1:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        log("Single1:" + aLong);
                    }
                });
        values
                .single(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        return aLong == 5L;
                    }
                }) // 由于源 Observable 为无限的，所以这个不会打印任何东西
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        log("Single2:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Single2:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        log("Single2:" + aLong);
                    }
                });
    }


    private void exists() {
        Observable<Integer> values = Observable.range(0, 2);
        Subscription subscription = values
                .exists(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 2;
                    }
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log(aBoolean + "");
                    }
                });
    }

    private void isEmpty() {
        Observable<Long> values = Observable.timer(1000, TimeUnit.MILLISECONDS);
        Subscription subscription = values
                .isEmpty()
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log(aBoolean + "");
                    }
                });
    }

    private void contains() {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
        Subscription subscription = values
                .contains(4L)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log(aBoolean + "");
                    }
                });
    }

    private void defaultIfEmpty() {
        Observable<Integer> values = Observable.empty();
        Subscription subscription = values
                .defaultIfEmpty(2)
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

    private void sequenceEqual() {
        Observable<String> strings = Observable.just("1", "2", "3");
        Observable<Integer> ints = Observable.just(1, 2, 3);
        Observable.sequenceEqual(strings, ints, new Func2<Serializable, Serializable, Boolean>() {
            @Override
            public Boolean call(Serializable serializable, Serializable serializable2) {
                return serializable.equals(serializable2.toString());
            }
        })//Observable.sequenceEqual(strings, ints)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log(aBoolean + "");
                    }
                });
    }

    private void log(String string) {
        Log.d("pepe", string);
    }
}
