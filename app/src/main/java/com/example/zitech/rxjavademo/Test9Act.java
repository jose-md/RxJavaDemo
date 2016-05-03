package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by pepe on 2016/5/3.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Test9Act extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test9);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test9_btn1://
                test1();
                break;
            case R.id.test9_btn2://
                test2();
                break;
            case R.id.test9_btn3://
                test3();
                break;
            case R.id.test9_btn4://
                break;
            case R.id.test9_btn5://
                break;
        }
    }
    private void test1(){
        Observable<String> values = Observable.just("请", "不要", "有", "副作用");

        final Inc index = new Inc();
        Observable<String> indexed =
                values.map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        index.inc();
                        return  s;
                    }
                });
        indexed.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                log(index.getCount() + ": " + s);
            }
        });


    }
    private  void test2(){
        Observable<String> values = Observable.just("请", "不要", "有", "副作用");

        final Inc index = new Inc();
        Observable<String> indexed =
                values.map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        index.inc();
                        return  s;
                    }
                });
        indexed.subscribe(new Action1<String>() {
                              @Override
                              public void call(String s) {
                                  log("1st observer: " + index.getCount() + ": " + s);
                              }
                          });
        indexed.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                log("2nd observer: " + index.getCount() + ": " + s);
            }
        });
    }
    private void test3(){
        Observable<String> values = Observable.just("No", "side", "effects", "please");

        Observable<Indexed<String>> indexed =
                values.scan(
                        new Indexed<String>(0, null), new Func2<Indexed<String>, String, Indexed<String>>() {
                            @Override
                            public Indexed<String> call(Indexed<String> stringIndexed, String s) {
                                return new Indexed<String>(stringIndexed.index+1, s);
                            }
                        })
                        .skip(1);
        indexed.subscribe(new Action1<Indexed<String>>() {
            @Override
            public void call(Indexed<String> stringIndexed) {
                log("1st observer: " + stringIndexed.index + ": " + stringIndexed.item);
            }
        });
        indexed.subscribe(new Action1<Indexed<String>>() {
            @Override
            public void call(Indexed<String> stringIndexed) {
                log("2nd observer: " + stringIndexed.index + ": " + stringIndexed.item);
            }
        });
    }
    class Inc {
        private int count = 0;
        public void inc() {
            count++;
        }
        public int getCount() {
            return count;
        }
    }
    private void log(String string) {
        Log.d("pepe", string);

    }
    class Indexed <T> {
        public final int index;
        public final T item;
        public Indexed(int index, T item) {
            this.index = index;
            this.item = item;
        }
    }


}
