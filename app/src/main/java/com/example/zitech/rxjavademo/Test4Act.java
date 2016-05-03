package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pepe on 2016/4/26.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Test4Act extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test4);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test4_btn1://filter
                filter();
                break;
            case R.id.test4_btn2://distinct
                distinct1();
                break;
            case R.id.test4_btn3://distinct(keySelector)
                distinct2();
                break;
            case R.id.test4_btn4://distinctUntilChanged
                distinctUntilChanged1();
                break;
            case R.id.test4_btn5://distinctUntilChanged(keySelector)
                distinctUntilChanged2();
                break;
            case R.id.test4_btn6://firstAndLast
                firstAndLast();
                break;
            case R.id.test4_btn7://ignoreElements
                ignoreElements();
                break;
            case R.id.test4_btn8://takeAndSkip
                takeAndSkip();
                break;
            case R.id.test4_btn9://takeLastAndSkipLast
                takeLastAndSkipLast();
                break;
            case R.id.test4_btn10://takeWhileAndSkipWhile
                takeWhileAndSkipWhile();
                break;
            case R.id.test4_btn11://takeUntilAndSkipUntil
                takeUntilAndSkipUntil();
                break;
            case R.id.test4_btn12://elementAt
                elementAt();
                break;
            case R.id.test4_btn13://sample
                sample();
                break;

            case R.id.test4_btn15://debounce
                debounce();
                break;
        }
    }

    private void filter() {
        Observable<Integer> values = Observable.range(0, 10);
        Subscription oddNumbers = values
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                })
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



    private void distinct1() {
        Observable<Integer> values = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onNext(2);
                subscriber.onCompleted();
            }
        });
        Subscription subscription = values
                .distinct()
                .subscribe(
                        new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                log(integer + "");
                            }
                        }
                );
    }

    private void distinct2() {
        Observable<String> values = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("First");
                subscriber.onNext("Second");
                subscriber.onNext("Third");
                subscriber.onNext("Fourth");
                subscriber.onNext("Fifth");
                subscriber.onCompleted();
            }
        });
        Subscription subscription = values
                .distinct(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        return s.charAt(0);
                    }
                })
                .subscribe(
                        new Action1<String>() {
                            @Override
                            public void call(String s) {
                                log(s);
                            }
                        }
                );
    }

    private void distinctUntilChanged1() {
        Observable<Integer> values = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onNext(2);
                subscriber.onCompleted();
            }
        });
        Subscription subscription = values
                .distinctUntilChanged()
                .subscribe(
                        new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                log(integer + "");
                            }
                        }
                );
    }

    private void distinctUntilChanged2() {
        Observable<String> values = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("First");
                subscriber.onNext("Second");
                subscriber.onNext("Third");
                subscriber.onNext("Fourth");
                subscriber.onNext("Fifth");
                subscriber.onCompleted();
            }
        });
        Subscription subscription = values
                .distinctUntilChanged(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        return s.charAt(0);
                    }
                })
                .subscribe(
                        new Action1<String>() {
                            @Override
                            public void call(String s) {
                                log(s);
                            }
                        }
                );
    }

    private void firstAndLast() {
        Integer[] items = new Integer[]{1, 2, 3};
        List<Integer> list = Arrays.asList(items);
        Observable.from(list)
//                .first()
                .last()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer + "");
                    }
                });

    }

    private void ignoreElements() {
        Observable<Integer> values = Observable.range(0, 10);
        Subscription subscription = values
                .ignoreElements()
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

    private void takeAndSkip() {
//        Observable<Integer> values = Observable.range(0, 5);
//        Subscription first2 = values
//                .take(2)
//                .subscribe(
//                        new Action1<Integer>() {
//                            @Override
//                            public void call(Integer integer) {
//                                log(integer+"");
//                            }
//                        }
//                );

        //take测试
        Observable<Integer> values = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onError(new Exception("Oops"));
                subscriber.onNext(2);
//                ----如果这里有个subscriber.onNext(2);
            }
        });
        Subscription subscription = values
                .take(2)
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
        //skip测试
//        Observable<Integer> values = Observable.range(0, 5);
//        Subscription subscription = values
//                .skip(2)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onCompleted() {
//                        log("Complete!");
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        log(e.getMessage().toString());
//                    }
//                    @Override
//                    public void onNext(Integer integer) {
//                        log(integer+"");
//                    }
//                });
        //重载方法测试
//        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
//        Subscription subscription = values
//                .take(250, TimeUnit.MILLISECONDS)
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onCompleted() {
//                        log("Complete!");
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        log(e.getMessage().toString());
//                    }
//                    @Override
//                    public void onNext(Long aLong) {
//                        log(aLong+"");
//                    }
//                });
    }

    private void takeLastAndSkipLast() {
        Observable<Integer> values = Observable.range(0, 5);
        Subscription subscription = values
                .skipLast(2)
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

    private void takeWhileAndSkipWhile() {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
        Subscription subscription = values
                .takeWhile(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        return aLong < 2;
                    }
                })
//                .skipWhile(new Func1<Long, Boolean>() {
//                    @Override
//                    public Boolean call(Long aLong) {
//                        return aLong<2;
//                    }
//                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        log(aLong + "");
                    }
                });
    }

    private void takeUntilAndSkipUntil() {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
        Observable<Long> cutoff = Observable.timer(250, TimeUnit.MILLISECONDS);
        Subscription subscription = values
//                .takeUntil(cutoff)
                .skipUntil(cutoff)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        log(aLong + "");
                    }
                });
    }

    private void elementAt() {
        Observable<Integer> values = Observable.range(0, 10);
        Subscription subscription = values
                .elementAt(3)
//                .elementAtOrDefault(11, 5)
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


    private void sample() {

    }

    private void debounce() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                try {
                    //产生结果的间隔时间分别为100、200、300...900毫秒
                    for (int i = 1; i < 10; i++) {
                        subscriber.onNext(i);
                        Thread.sleep(i * 100);
                    }
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .debounce(500, TimeUnit.MILLISECONDS)  //超时时间为400毫秒
                .subscribe(
                        new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                log("Next:" + integer);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                log("Error:" + throwable.getMessage());
                            }
                        }, new Action0() {
                            @Override
                            public void call() {
                                log("Complete!");
                            }
                        });
    }

    private void log(String string) {
        Log.d("pepe", string);
    }
}
