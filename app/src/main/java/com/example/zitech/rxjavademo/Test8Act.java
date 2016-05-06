package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;

/**
 * Created by pepe on 2016/4/28.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Test8Act extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test8);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test8_btn4://repeat
                repeat();
                break;
            case R.id.test8_btn5://repeatWhen
                repeatWhen();
                break;
            case R.id.test8_btn6://startWith
                startWith();
                break;
            case R.id.test8_btn9://merge
                merge();
                break;
            case R.id.test8_btn10://mergeWith
                mergeWith();
                break;
            case R.id.test8_btn11://mergeDelayError
                mergeDelayError();
                break;
            case R.id.test8_btn12://switchOnNext
                switchOnNext();
                break;
            case R.id.test8_btn13://switchMap
                switchMap();
                break;
            case R.id.test8_btn14://zip
                zip();
                break;
            case R.id.test8_btn15://zip2
                zip2();
                break;
            case R.id.test8_btn16://zip3
                zip3();
                break;
            case R.id.test8_btn17://zip4
                zip4();
                break;
            case R.id.test8_btn18://zipWith
                zipWith();
                break;
            case R.id.test8_btn19://combineLatest
                combineLatest();
                break;
            case R.id.test8_btn20://join
                join();
                break;
            case R.id.test8_btn21://groupJoin
                groupJoin();
                break;


        }
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

    private void repeatWhen() {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
        values
                .take(2)
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return observable.take(2);
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });

//        Observable<Long> values2 = Observable.interval(100, TimeUnit.MILLISECONDS);
//        values2
//                .take(5)
//                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
//                    @Override
//                    public Observable<?> call(Observable<? extends Void> observable) {
//                        return Observable.interval(2, TimeUnit.SECONDS);
//                    }
//                })
//                .subscribe(new Action1<Object>() {
//                    @Override
//                    public void call(Object o) {
//                        log(o.toString());
//                    }
//                });
    }

    private void startWith() {
        Observable<Integer> values = Observable.range(0, 3);
        values.startWith(-1, -2)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }



    private void merge() {
        Observable.merge(
                Observable.interval(250, TimeUnit.MILLISECONDS).map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        return "First";
                    }
                }),
                Observable.interval(150, TimeUnit.MILLISECONDS).map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        return "Second";
                    }
                }))
                .take(10)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void mergeWith() {
        Observable.interval(250, TimeUnit.MILLISECONDS).map(new Func1<Long, Object>() {
            @Override
            public Object call(Long aLong) {
                return "First";
            }
        })
                .mergeWith(Observable.interval(150, TimeUnit.MILLISECONDS).map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        return "Second";
                    }
                }))
                .take(10)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void mergeDelayError() {
        Observable<Object> failAt200 = Observable.concat(
                Observable.interval(100, TimeUnit.MILLISECONDS).take(2),
                Observable.error(new Exception("Failed")));
        Observable<Long> completeAt400 =
                Observable.interval(100, TimeUnit.MILLISECONDS)
                        .take(4);
        Observable.mergeDelayError(failAt200, completeAt400)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        log(throwable.getMessage().toString());
                    }
                });
//        Observable<Object> failAt200 = Observable.concat(
//                        Observable.interval(100, TimeUnit.MILLISECONDS).take(2),
//                        Observable.error(new Exception("Failed")));
//        Observable<Object> failAt300 = Observable.concat(
//                        Observable.interval(100, TimeUnit.MILLISECONDS).take(3),
//                        Observable.error(new Exception("Failed")));
//        Observable<Long> completeAt400 = Observable.interval(100, TimeUnit.MILLISECONDS)
//                        .take(4);
//        Observable.mergeDelayError(failAt200, failAt300, completeAt400)
//                .subscribe(new Action1<Object>() {
//                    @Override
//                    public void call(Object o) {
//                        log(o.toString());
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        log(throwable.getMessage().toString());
//                    }
//                });
    }

    private void switchOnNext() {
        Observable.switchOnNext(
                Observable.interval(100, TimeUnit.MILLISECONDS).map(new Func1<Long, Observable<?>>() {
                    @Override
                    public Observable<?> call(final Long aLong1) {
                        return Observable.interval(30, TimeUnit.MILLISECONDS).map(new Func1<Long, Object>() {
                            @Override
                            public Object call(Long aLong2) {
                                return aLong1;
                            }
                        });
                    }
                }))
                .take(9)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void switchMap() {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .switchMap(new Func1<Long, Observable<?>>() {
                    @Override
                    public Observable<?> call(final Long aLong1) {
                        return Observable.interval(30, TimeUnit.MILLISECONDS)
                                .map(new Func1<Long, Object>() {
                                    @Override
                                    public Object call(Long aLong2) {
                                        return aLong1;
                                    }
                                });
                    }
                })
                .take(9)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void zip() {
        Observable.zip(
                Observable.interval(100, TimeUnit.MILLISECONDS).doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log("Left emits " + aLong);
                    }
                }),
                Observable.interval(150, TimeUnit.MILLISECONDS).doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log("Right emits " + aLong);
                    }
                }),
                new Func2<Long, Long, Object>() {
                    @Override
                    public Object call(Long aLong, Long aLong2) {
                        return aLong + " - " + aLong2;
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

    private void zip2() {
        Observable.zip(
                Observable.interval(100, TimeUnit.MILLISECONDS),
                Observable.interval(150, TimeUnit.MILLISECONDS),
                Observable.interval(50, TimeUnit.MILLISECONDS),
                new Func3<Long, Long, Long, Object>() {
                    @Override
                    public Object call(Long aLong, Long aLong2, Long aLong3) {
                        return aLong + " - " + aLong2 + " - " + aLong3;
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

    private void zip3() {
        Observable.zip(
                Observable.range(0, 5),
                Observable.range(0, 3),
                Observable.range(0, 8),
                new Func3<Integer, Integer, Integer, Object>() {
                    @Override
                    public Object call(Integer integer, Integer integer2, Integer integer3) {
                        return integer + " - " + integer2 + " - " + integer3;
                    }
                })
                .count()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void zip4() {
        Observable.range(0, 5)
                .zipWith(Arrays.asList(0, 2, 4, 6, 8), new Func2<Integer, Integer, Object>() {
                    @Override
                    public Object call(Integer integer, Integer integer2) {
                        return integer + " - " + integer2;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void zipWith() {
        Observable.interval(100, TimeUnit.MILLISECONDS).zipWith(
                Observable.interval(150, TimeUnit.MILLISECONDS),
                new Func2<Long, Long, Object>() {
                    @Override
                    public Object call(Long aLong, Long aLong2) {
                        return aLong + " - " + aLong2;
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

    private void combineLatest() {
        Observable.combineLatest(
                Observable.interval(100, TimeUnit.MILLISECONDS).doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log("Left emits " + aLong);
                    }
                }),
                Observable.interval(150, TimeUnit.MILLISECONDS).doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log("Right emits " + aLong);
                    }
                })
                , new Func2<Long, Long, Object>() {
                    @Override
                    public Object call(Long aLong, Long aLong2) {
                        return aLong + " - " + aLong2;
                    }
                }
        )
                .take(6)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void join() {
        //产生0,2,4,6,8数列
        Observable<Long> observable1 = Observable.timer(0, 1000, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return aLong * 2;
                    }
                }).take(5);

        //产生0,3,6,9,12数列
        Observable<Long> observable2 = Observable.timer(500, 1000, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return aLong * 3;
                    }
                }).take(5);

        observable1.join(observable2, new Func1<Long, Observable<Long>>() {
            @Override
            public Observable<Long> call(Long aLong) {
                //使Observable持续600毫秒有效
                return Observable.just(aLong).delay(600, TimeUnit.MILLISECONDS);
            }
        }, new Func1<Long, Observable<Long>>() {
            @Override
            public Observable<Long> call(Long aLong) {
                //使Observable持续600毫秒有效
                return Observable.just(aLong).delay(600, TimeUnit.MILLISECONDS);
            }
        }, new Func2<Long, Long, Long>() {
            @Override
            public Long call(Long aLong, Long aLong2) {
                return aLong + aLong2;
            }
        })
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log(aLong + "");
                    }
                });
    }

    private void groupJoin() {
        //产生100,110,120,130,140数列
        Observable<Long> observable1 = Observable.timer(0, 1000, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        log(System.currentTimeMillis()+"--1--");
                        return aLong*10+100;
                    }
                }).take(5);

        //产生1,3,5,7,9数列
        Observable<Long> observable2 = Observable.timer(500, 1000, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        log(System.currentTimeMillis()+"--2--");
                        return aLong*2+1;
                    }
                }).take(5);

        observable1.groupJoin(observable2, new Func1<Long, Observable<Long>>() {
            @Override
            public Observable<Long> call(Long aLong) {
                //使Observable持续1600毫秒有效
                return Observable.just(aLong).delay(1600, TimeUnit.MILLISECONDS);
            }
        }, new Func1<Long, Observable<Long>>() {
            @Override
            public Observable<Long> call(Long aLong) {
                //使Observable持续600毫秒有效
                return Observable.just(aLong).delay(600, TimeUnit.MILLISECONDS);
            }
        }, new Func2<Long, Observable<Long>, Observable<Long>>() {
            @Override
            public Observable<Long> call(final Long aLong, Observable<Long> observable) {
                return observable.map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong2) {
                        return aLong + aLong2;
                    }
                });
            }
        })
                .subscribe(new Action1<Observable<Long>>() {
                    @Override
                    public void call(Observable<Long> longObservable) {
                        longObservable.subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                log(aLong +"");
                            }
                        });
                    }
                });
    }

    private void log(String string) {
        Log.d("pepe", string);

    }
}
