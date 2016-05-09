package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

/**
 * Created by pepe on 2016/5/3.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Test12Act extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test12);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test12_btn1://Cold observables
                coldObservable();
                break;
            case R.id.test12_btn2://Hot observables
                hotObservable();
                break;
            case R.id.test12_btn3://connect返回的Subscription，取消订阅，会结束数据流
                unsubscribe_connect();
                break;
            case R.id.test12_btn4://subscribe返回的Subscription，取消订阅，不会结束数据流
                unsubscribe_subscribe();
                break;
            case R.id.test12_btn5://refCount
                refCount();
                break;
            case R.id.test12_btn6://replay
                replay();
                break;
            case R.id.test12_btn7://cache
                cache();
                break;
        }
    }

    /**
     * cold Observable每次订阅都会再次单独发射数据
     */
    private void coldObservable() {
        try {
            Observable<Long> cold = Observable.interval(200, TimeUnit.MILLISECONDS);
            Subscription firstSubs = cold.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log("First: " + aLong);
                }
            });
            Thread.sleep(500);
            Subscription secondSubs = cold.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log("Second: " + aLong);
                }
            });
            Thread.sleep(500);
            firstSubs.unsubscribe();
            secondSubs.unsubscribe();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hot Observable，每次持续发射，多次订阅不会重新发射数据
     * <p/>
     * ConnectableObservable 如果不调用 connect 函数则不会触发数据流的执行。
     * 当调用 connect 函数以后，会创建一个新的 subscription 并订阅到源 Observable （调用 publish 的那个 Observable）。
     * 这个 subscription 开始接收数据并把它接收到的数据转发给所有的订阅者。
     * 这样，所有的订阅者在同一时刻都可以收到同样的数据。
     */
    private void hotObservable() {
        try {
            ConnectableObservable<Long> cold = Observable.interval(200, TimeUnit.MILLISECONDS).publish();
            cold.connect();
            Subscription firstSubs = cold.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log("First: " + aLong);
                }
            });
            Thread.sleep(500);
            Subscription secondSubs = cold.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log("Second: " + aLong);
                }
            });
            Thread.sleep(500);
            firstSubs.unsubscribe();
            secondSubs.unsubscribe();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用conect返回的Subscription在unsubscribe之后，结束了publish的数据流
     * 可以再次通过connect订阅,原始Observable重新发射数据
     */
    private void unsubscribe_connect() {
        try {
            ConnectableObservable<Long> connectable = Observable.interval(200, TimeUnit.MILLISECONDS).publish();
            Subscription connectSubs = connectable.connect();
            connectable.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log(aLong.toString());
                }
            });
            Thread.sleep(500);
            log("Close connection");
            connectSubs.unsubscribe();//取消订阅，结束数据流
            Thread.sleep(500);
            log("Reconnecting");
            connectable.connect();//再次连接，数据流重新发射
            Subscription subs = connectable.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log(aLong.toString());
                }
            });
            Thread.sleep(500);
            subs.unsubscribe();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用subscribe返回的Subscription在unsubscribe之后，不会结束publish的数据流，
     * 再次subscribe订阅，会接收publish接下来发射的数据
     */
    private void unsubscribe_subscribe() {
        try {
            ConnectableObservable<Long> connectable = Observable.interval(200, TimeUnit.MILLISECONDS).publish();
            Subscription connectSubs = connectable.connect();
            Subscription firstSubs = connectable.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log(aLong.toString());
                }
            });
            Thread.sleep(500);
            log("Close first Subscription");
            firstSubs.unsubscribe();//只是取消订阅，不结束数据流
            Thread.sleep(500);
            log("Start second Subscription");
            Subscription secondSubs = connectable.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log(aLong.toString());
                }
            });
            Thread.sleep(500);
            secondSubs.unsubscribe();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * refCount 返回一个特殊的 Observable， 这个 Observable 只要有订阅者就会继续发射数据。
     * 如果没有订阅者，就停止
     */
    private void refCount() {
        try {
            Observable<Long> cold = Observable.interval(200, TimeUnit.MILLISECONDS).publish().refCount();
            Subscription firstSubs = cold.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log("First: " + aLong);
                }
            });
            Thread.sleep(500);
            Subscription secondSubs = cold.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log("Second: " + aLong);
                }
            });
            Thread.sleep(500);
            log("Unsubscribe second");
            secondSubs.unsubscribe();
            Thread.sleep(500);
            log("Unsubscribe first");
            firstSubs.unsubscribe();

            log("First connection again");
            Thread.sleep(500);
            firstSubs = cold.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log("First: " + aLong);
                }
            });
            Thread.sleep(500);
            firstSubs.unsubscribe();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在publish的基础上添加了缓存
     */
    private void replay() {
        try {
            ConnectableObservable<Long> cold = Observable.interval(200, TimeUnit.MILLISECONDS).doOnNext(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log("doNext:" + aLong.toString());
                }
            }).replay();
//            }).replay(2);
            cold
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            log("Subscribed");
                        }
                    })
                    .doOnUnsubscribe(new Action0() {
                        @Override
                        public void call() {
                            log("Unsubscribed");
                        }
                    });
            Subscription connectSubs = cold.connect();
            log("Subscribe first" + "--time:" + System.currentTimeMillis());
            Subscription firstSubs = cold.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log("First: " + aLong + "--time:" + System.currentTimeMillis());
                }
            });
            Thread.sleep(700);
            log("Subscribe second" + "--time:" + System.currentTimeMillis());
            Subscription secondSubs = cold.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    log("Second: " + aLong + "--time:" + System.currentTimeMillis());
                }
            });
            Thread.sleep(500);
            firstSubs.unsubscribe();
            secondSubs.unsubscribe();
            Thread.sleep(500);
            connectSubs.unsubscribe();//可以通过connectSubs断开长连接
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * cache 操作函数和 replay 类似，但是隐藏了 ConnectableObservable ，并且不用管理 subscription 了。
     * 只要第一个订阅者订阅了，内部的 ConnectableObservable 就链接到源 Observable上了，并且不会取消订阅了。
     * 后来的订阅者会收到之前缓存的数据，但是并不会重新订阅到源 Observable 上。.
     * 即使所有订阅者都取消，内部的ConnectableObservable仍然也不会停止
     * 也就是一个长连接一直存在，因为在内部实现connect，没有返回connectSubs，也就无法调用connectSubs.unsubscribe();
     */
    private void cache() {
        try {
            Observable<Long> obs = Observable.interval(100, TimeUnit.MILLISECONDS)
                    .take(8)
                    .doOnNext(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            log("doNext:" + aLong.toString());
                        }
                    })
                    .cache()
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            log("Subscribed");
                        }
                    })
                    .doOnUnsubscribe(new Action0() {
                        @Override
                        public void call() {
                            log("Unsubscribed");
                        }
                    });
            Subscription subscription = obs.subscribe();
            Thread.sleep(500);
            subscription.unsubscribe();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void log(String string) {
        Log.d("pepe", string);

    }


}
