package com.example.zitech.rxjavademo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext=MainActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //关于操作符just，将对象或者对象集合转换为一个会发射这些对象的Observable
        //简单来说就是挨个来,将传入的参数依次发送出来
//        Observable.just(4,5,6).subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onCompleted() {
//                Log.d("pepe","onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("pepe","onError");
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.d("pepe","integer--"+integer);
//            }
//        });
//        Observable.just(1,2,3,4,5,6).filter(new Func1<Integer, Boolean>() {
//            @Override
//            public Boolean call(Integer integer) {
//                return integer%2==1;
//            }
//        }).map(new Func1<Integer, Double>() {
//            @Override
//            public Double call(Integer integer) {
//                return Math.sqrt(integer);
//            }
//        }).subscribe(new Subscriber<Double>() {
//            @Override
//            public void onCompleted() {
//                Log.d("pepe","onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("pepe","onError");
//            }
//
//            @Override
//            public void onNext(Double aDouble) {
//                Log.d("pepe","aDouble---"+aDouble);
//            }
//        });
    }



    public void onClick(View view){
        switch (view.getId()){
            case R.id.main_btn1://关于Observable
                break;
            case R.id.main_btn2://基本使用
                startActivity(new Intent(mContext,Test2Act.class));
                break;
            case R.id.main_btn3://操作符：创建Observable
                startActivity(new Intent(mContext,Test3Act.class));
                break;
            case R.id.main_btn4://操作符：过滤Observable
                startActivity(new Intent(mContext,Test4Act.class));
                break;
            case R.id.main_btn5://操作符：检查Observable
                startActivity(new Intent(mContext,Test5Act.class));
                break;
            case R.id.main_btn6://操作符：聚合Observable
                startActivity(new Intent(mContext,Test6Act.class));
                break;
            case R.id.main_btn7://操作符：转换Observable
                startActivity(new Intent(mContext,Test7Act.class));
                break;
            case R.id.main_btn8://操作符：组合Observable
                startActivity(new Intent(mContext,Test8Act.class));
                break;
            case R.id.main_btn9://
                break;
            case R.id.main_btn10://
                break;
        }
    }
//    @Override
//    /**
//     * 点击menu按钮时
//     */
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(Menu.NONE,0,0,"Main");    //添加选项
//        menu.add(Menu.NONE, 1, 0, "demo1");    //添加选项
//        menu.add(Menu.NONE, 2, 0, "demo2");    //添加选项
//        return true;
//    }
//
//    @Override
//    /**
//     * 点击menu菜单中某一个选项时
//     */
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case 0:
//                break;
//            case 1:
//                startActivity(new Intent(mContext,Demo1Act.class));
//                break;
//            case 2:
//                startActivity(new Intent(mContext,Demo2Act.class));
//                break;
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }


}