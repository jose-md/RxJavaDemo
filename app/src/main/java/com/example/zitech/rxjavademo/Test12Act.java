package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by pepe on 2016/5/3.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Test12Act extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test12);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test12_btn1://
                break;
            case R.id.test12_btn2://
                break;
            case R.id.test12_btn3://
                break;
            case R.id.test12_btn4://
                break;
            case R.id.test12_btn5://
                break;
            case R.id.test12_btn6://
                break;
            case R.id.test12_btn7://
                break;
            case R.id.test12_btn8://
                break;
            case R.id.test12_btn9://
                break;
            case R.id.test12_btn10://
                break;
            case R.id.test12_btn11://
                break;
        }
    }

    private void log(String string) {
        Log.d("pepe", string);

    }
}
