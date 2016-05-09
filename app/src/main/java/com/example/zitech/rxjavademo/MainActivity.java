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
    }



    public void onClick(View view){
        switch (view.getId()){
            case R.id.main_btn1://关于Observable
                break;
            case R.id.main_btn2://基本使用
                startActivity(new Intent(mContext,Test2Act.class));
                break;
            case R.id.main_btn3://操作符：创建操作符
                startActivity(new Intent(mContext,Test3Act.class));
                break;
            case R.id.main_btn4://操作符：过滤操作符
                startActivity(new Intent(mContext,Test4Act.class));
                break;
            case R.id.main_btn5://操作符：条件和布尔操作符
                startActivity(new Intent(mContext,Test5Act.class));
                break;
            case R.id.main_btn6://操作符：算术和聚合操作符
                startActivity(new Intent(mContext,Test6Act.class));
                break;
            case R.id.main_btn7://操作符：转换操作符
                startActivity(new Intent(mContext,Test7Act.class));
                break;
            case R.id.main_btn8://操作符：组合操作符
                startActivity(new Intent(mContext,Test8Act.class));
                break;
            case R.id.main_btn9://操作符：辅助操作符
                startActivity(new Intent(mContext, Test9Act.class));
                break;
            case R.id.main_btn10://操作符：错误处理
                startActivity(new Intent(mContext, Test10Act.class));
                break;
            case R.id.main_btn11://
                break;
            case R.id.main_btn12://副作用
                startActivity(new Intent(mContext,Test13Act.class));
                break;
            case R.id.main_btn13://
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
