package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

/**
 * Created by pepe on 2016/5/3.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Test9Act extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test9);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test9_btn1://timeout1
                timeout1();
                break;
            case R.id.test9_btn21://timeout2
                timeout2();
                break;
            case R.id.test9_btn2://timestamp
                timestamp();
                break;
            case R.id.test9_btn3://timeInterval
                timeInterval();
                break;
            case R.id.test9_btn4://delay
                delay();
                break;
            case R.id.test9_btn5://delaySubscription
                delaySubscription();
                break;
            case R.id.test9_btn6://doOnEach
                doOnEach();
                break;
            case R.id.test9_btn7://doOnNext
                doOnNext();
                break;
            case R.id.test9_btn8://doOnSubscribe
                doOnSubscribe();
                break;
            case R.id.test9_btn9://doOnUnSubscribe
                doOnUnSubscribe();
                break;
            case R.id.test9_btn10://doOnError
                doOnError();
                break;
            case R.id.test9_btn11://doOnComplete
                doOnComplete();
                break;
            case R.id.test9_btn12://doOnTerminate
                doOnTerminate();
                break;
            case R.id.test9_btn13://finallyDo
                finallyDo();
                break;
            case R.id.test9_btn14://materialize
                materialize();
                break;

            case R.id.test9_btn15://serialize
                serialize();
                break;
            case R.id.test9_btn16://dematerialize
                dematerialize();

                break;
            case R.id.test9_btn17://using
                using();

                break;
        }
    }

    private void timeout1() {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
        Subscription subscription = values
                .timeout(300, TimeUnit.MILLISECONDS)
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

    private void timeout2() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i <= 3; i++) {
                    try {
                        Thread.sleep(i * 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).timeout(200, TimeUnit.MILLISECONDS, Observable.just(5, 6)).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log(integer.toString());
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

    private void delay() {
        log("start subscrib:" + System.currentTimeMillis() / 1000);
        Observable<Long> observable = Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                for (int i = 1; i <= 2; i++) {
                    Long currentTime = System.currentTimeMillis() / 1000;
                    log("subscrib:" + currentTime);
                    subscriber.onNext(currentTime);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread());
        observable.delay(2000, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                log("delay:" + System.currentTimeMillis() / 1000 + "---" + (System.currentTimeMillis() / 1000 - aLong));
            }
        });
    }

    private void delaySubscription() {
        log("start subscrib:" + System.currentTimeMillis() / 1000);
        Observable<Long> observable = Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                for (int i = 1; i <= 2; i++) {
                    Long currentTime = System.currentTimeMillis() / 1000;
                    log("subscrib:" + currentTime);
                    subscriber.onNext(currentTime);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread());
        observable.delaySubscription(2000, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                log("delaySubscription:" + System.currentTimeMillis() / 1000 + "---" + (System.currentTimeMillis() / 1000 - aLong));
            }
        });
    }

    private void doOnEach() {
        Observable observable = Observable.just(1, 2, 3);
        observable.doOnEach(new Action1<Notification>() {
            @Override
            public void call(Notification notification) {
                log("doOnEach send " + notification.getValue() + " type:" + notification.getKind());
            }
        }).subscribe(new Action1() {
            @Override
            public void call(Object o) {
                log(o.toString());
            }
        });
        Subject<Integer, Integer> values = ReplaySubject.create();
        values.doOnEach(new Action1<Notification<? super Integer>>() {
            @Override
            public void call(Notification<? super Integer> notification) {
                log("doOnEach send " + notification.getValue() + " type:" + notification.getKind());
            }
        }).subscribe(new Action1() {
            @Override
            public void call(Object o) {
                log(o.toString());
            }
        });
        values.onNext(4);
        values.onNext(5);
        values.onNext(6);
        values.onError(new Exception("Oops"));
    }

    private void doOnNext() {
        Subject<Integer, Integer> values = ReplaySubject.create();
        values.doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log("doOnNext send :" + integer.toString());
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log(integer.toString());
            }
        });
        values.onNext(4);
        values.onError(new Exception("Oops"));

    }

    private void doOnSubscribe() {
        Observable observable = Observable.just(1, 2).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                log("I'm be subscribed!");
            }
        });
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                log("first:" + o.toString());
            }
        });
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                log("second:" + o.toString());
            }
        });

    }

    private void doOnUnSubscribe() {
        Observable observable = Observable.just(1, 2).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                log("I'm be unSubscribed!");
            }
        });
        Subscription subscribe1 = observable.subscribe();
        Subscription subscribe2 = observable.subscribe();
        subscribe1.unsubscribe();
        subscribe2.unsubscribe();
    }

    private void doOnError() {
        try {
            Observable observable = Observable.error(new Throwable("呵呵哒")).doOnError(new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    log(throwable.getMessage().toString());
                }
            });
            observable.subscribe();
        } catch (Exception e) {
            log("catch the exception");
        }
    }

    private void doOnComplete() {
        Observable observable = Observable.empty().doOnCompleted(new Action0() {
            @Override
            public void call() {
                log("Complete!");
            }
        });
        observable.subscribe();
    }

    private void doOnTerminate() {
        Subject<Integer, Integer> values = ReplaySubject.create();
        values.doOnTerminate(new Action0() {
            @Override
            public void call() {
                log("order to terminate");
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log(integer.toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                log(throwable.getMessage().toString());
            }
        });
        values.onNext(4);
        values.onError(new Exception("Oops"));
    }

    private void finallyDo() {
        Observable observable = Observable.empty().finallyDo(new Action0() {
            @Override
            public void call() {
                log("already terminate");
            }
        });
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
            }
        }, new Action0() {
            @Override
            public void call() {
                log("Complete!");
            }
        });
    }

    private void materialize() {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
        values.take(3)
                .materialize()
                .subscribe(new Action1<Notification>() {
                    @Override
                    public void call(Notification notification) {
                        log("meterialize:" + notification.getValue() + "--type:" + notification.getKind());
                    }
                });
    }

    private void serialize() {
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onCompleted();
                subscriber.onNext(3);
                subscriber.onCompleted();
            }
        })
//                .cast(Integer.class)
//                .serialize()
                    ;

        observable.doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                log("Unsubscribed");
            }
        })
                .unsafeSubscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(Integer integer) {
                    }
                });
//        .subscribe(
//                new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                    }
//                }, new Action0() {
//                    @Override
//                    public void call() {
//                        log("Complete!");
//                    }
//                });
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

    private void using() {
        Observable observable = Observable.using(new Func0<Animal>() {
            @Override
            public Animal call() {
                return new Animal();
            }
        }, new Func1<Animal, Observable<?>>() {
            @Override
            public Observable<?> call(Animal animal) {
                return Observable.timer(3, TimeUnit.SECONDS);//三秒后发射一次就completed
//                return Observable.timer(4, 2, TimeUnit.SECONDS);//没有completed，不停的发射数据
//                return Observable.range(1,3);//一次发射三个数据，马上结束
//                return Observable.just(1,2,3);//一次发射三个数据，马上结束
            }
        }, new Action1<Animal>() {
            @Override
            public void call(Animal animal) {
                animal.relase();
            }
        });
        Subscriber subscriber = new Subscriber() {
            @Override
            public void onCompleted() {
                log("subscriber---onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log("subscriber---onError");
            }

            @Override
            public void onNext(Object o) {
                log("subscriber---onNext" + o.toString());//o是发射的次数统计，可以用timer(4, 2, TimeUnit.SECONDS)测试
            }
        };
        observable.count().subscribe(subscriber);
    }

    private class Animal {
        Subscriber subscriber = new Subscriber() {
            @Override
            public void onCompleted() {
                log("Animal----onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                log("animal eat");
            }
        };

        public Animal() {
            log("create animal");
            Observable.interval(1000, TimeUnit.MILLISECONDS)
                    .subscribe(subscriber);
        }

        public void relase() {
            log("animal released");
            subscriber.unsubscribe();
        }
    }

    private void log(String string) {
        Log.d("pepe", string);
    }
}
