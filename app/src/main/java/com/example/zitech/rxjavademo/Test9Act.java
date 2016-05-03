package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;

/**
 * Created by pepe on 2016/5/3.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Test9Act  extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test9);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test9_btn1://timeout
                timeout();
                break;
            case R.id.test9_btn2://timestamp
                timestamp();
                break;
            case R.id.test9_btn3://timeInterval
                timeInterval();
                break;
            case R.id.test9_btn4://
                break;
            case R.id.test9_btn5://
                break;
            case R.id.test9_btn6://
                break;
            case R.id.test9_btn7://
                break;
            case R.id.test9_btn8://
                break;
            case R.id.test9_btn9://
                break;
            case R.id.test9_btn10://
                break;
            case R.id.test9_btn11://
                break;
            case R.id.test9_btn12://
                break;
            case R.id.test9_btn13://
                break;
            case R.id.test9_btn14://
                break;
            case R.id.test9_btn15://
                break;
            case R.id.test9_btn16://
                break;
           


        }
    }
    private void timeout() {
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

    private void log(String string) {
        Log.d("pepe", string);

    }
}
