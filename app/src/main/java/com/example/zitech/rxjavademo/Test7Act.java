package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;

/**
 * Created by pepe on 2016/4/27.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Test7Act extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test7);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test7_btn1://map
                map();
                break;
            case R.id.test7_btn2://cast
                cast();
                break;
            case R.id.test7_btn4://timestamp
                timestamp();
                break;
            case R.id.test7_btn5://timeInterval
                timeInterval();
                break;
            case R.id.test7_btn6://materialize
                materialize();
                break;
            case R.id.test7_btn7://dematerialize
                dematerialize();
                break;
            case R.id.test7_btn8://flatMap1:转换
                flatMap1();
                break;
            case R.id.test7_btn9://flatMap2:过滤
                flatMap2();
                break;
            case R.id.test7_btn10://flatMap3:异步
                flatMap3();
                break;
            case R.id.test7_btn11://concatMap
                concatMap();
                break;
            case R.id.test7_btn12://switchMap
                switchMap();
                break;
            case R.id.test7_btn13://flatMapIterable1
                flatMapIterable1();
                break;
            case R.id.test7_btn14://flatMapIterable2
                flatMapIterable2();
                break;
            case R.id.test7_btn15://buffer
                buffer();
                break;
            case R.id.test7_btn16://window
                window();
                break;


        }
    }

    private void map() {
        Observable<Integer> values = Observable.just("0", "1", "2", "3")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return Integer.parseInt(s);
                    }
                });
        values.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log(integer + "");
            }
        });

    }

    private void cast() {
        Observable<String> values = Observable.just("0", "1", "2", "3");
        values
                .cast(Object.class)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object object) {
                        log(object.toString() + ":" + object.getClass());
                    }
                });
    }



    private void timestamp() {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
        values.take(3)
                .timestamp()
                .subscribe(new Action1<Timestamped>() {
                    @Override
                    public void call(Timestamped mTimestamped) {
                        log(mTimestamped.toString());
                    }
                });
    }

    private void timeInterval() {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
        values.take(3)
                .timeInterval()
                .subscribe(new Action1<TimeInterval>() {
                    @Override
                    public void call(TimeInterval mTimeInterval) {
                        log(mTimeInterval.toString());
                    }
                });
    }

    private void materialize() {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
        values.take(3)
                .dematerialize()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void dematerialize() {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
        values.take(3)
                .materialize()
                .dematerialize()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void flatMap1() {
        Observable<Integer> values = Observable.range(1, 3);
        values
                .flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        return Observable.range(0, integer);
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
        Observable<Integer> values2 = Observable.just(1);
        values2
                .flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        return Observable.just(Character.valueOf((char) (integer + 64)));
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void flatMap2() {
        Observable<Integer> values = Observable.range(0, 30);
        values
                .flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        if (0 < integer && integer <= 26)
                            return Observable.just(Character.valueOf((char) (integer + 64)));
                        else
                            return Observable.empty();
                    }
                })
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void flatMap3() {
        Observable.just(100, 150)
                .flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(final Integer integer) {
                        return Observable.interval(integer, TimeUnit.MILLISECONDS)
                                .map(new Func1<Long, Object>() {
                                    @Override
                                    public Object call(Long aLong) {
                                        return integer;
                                    }
                                });
                    }
                })
                .take(6)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void concatMap() {
        Observable.just(100, 150)
                .concatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(final Integer integer) {
                        return Observable.interval(integer, TimeUnit.MILLISECONDS)
                                .map(new Func1<Long, Object>() {
                                    @Override
                                    public Object call(Long aLong) {
                                        return integer;
                                    }
                                })
                                .take(3);
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void switchMap() {
        //flatMap操作符的运行结果
        Observable.just(10, 22, 34).flatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                //10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
                int delay = 200;
                if (integer > 10)
                    delay = 180;

                return Observable.from(new Integer[]{integer, integer / 2}).delay(delay, TimeUnit.MILLISECONDS);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log("flatMap Next:" + integer);
            }
        });

        //concatMap操作符的运行结果
        Observable.just(10, 22, 34).concatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                //10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
                int delay = 200;
                if (integer > 10)
                    delay = 180;

                return Observable.from(new Integer[]{integer, integer / 2}).delay(delay, TimeUnit.MILLISECONDS);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log("concatMap Next:" + integer);
            }
        });

        //switchMap操作符的运行结果
        Observable.just(10, 22, 34).switchMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                //10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
                int delay = 200;
                if (integer > 10)
                    delay = 180;

                return Observable.from(new Integer[]{integer, integer / 2}).delay(delay, TimeUnit.MILLISECONDS);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log("switchMap Next:" + integer);
            }
        });
    }

    private void flatMapIterable1() {
        Observable.range(1, 3)
                .flatMapIterable(new Func1<Integer, Iterable<?>>() {
                    @Override
                    public Iterable<?> call(Integer integer) {
                        List<Integer> list = new ArrayList<>();
                        for (int i = 1; i <= integer; i++) {
                            list.add(i);
                        }
                        return list;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void flatMapIterable2() {
        Observable.range(1, 3)
                .flatMapIterable(new Func1<Integer, Iterable<?>>() {
                    @Override
                    public Iterable<?> call(Integer integer) {
                        List<Integer> list = new ArrayList<>();
                        for (int i = 1; i <= integer; i++) {
                            list.add(i);
                        }
                        return list;
                    }
                }, new Func2<Integer, Object, Object>() {
                    @Override
                    public Object call(Integer integer, Object o) {
                        return integer * (Integer) o;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void buffer() {
        final int[] items = new int[]{1, 3, 5, 7, 9};
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    if (subscriber.isUnsubscribed()) return;
                    Random random = new Random();
                    while (true) {
                        int i = items[random.nextInt(items.length)];
                        subscriber.onNext(i);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
        observable.buffer(3, TimeUnit.SECONDS).subscribe(new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> integers) {
                log(integers.toString());
            }
        });
    }

    private void window() {
        Observable.interval(1, TimeUnit.SECONDS).take(12)
                .window(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Observable<Long>>() {
                    @Override
                    public void call(Observable<Long> observable) {
                        log("subdivide begin......");
                        observable.subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                log("Next:" + aLong);
                            }
                        });
                    }
                });
    }

    private void log(String string) {
        Log.d("pepe", string);
    }

}
