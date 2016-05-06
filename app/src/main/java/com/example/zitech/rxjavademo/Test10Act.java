package com.example.zitech.rxjavademo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by pepe on 2016/5/6.
 * E_mail: 494778200@qq.com
 * Company:小知科技 http://www.zizizizizi.com/
 */
public class Test10Act extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test10);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test10_btn1://OnErrorReturn
                break;
            case R.id.test10_btn2://OnErrorResume
                break;
            case R.id.test10_btn3://OnExceptionResumeNext
                break;
            case R.id.test10_btn4://
                break;
            case R.id.test10_btn5://
                break;
            case R.id.test10_btn6://
                break;
            case R.id.test10_btn7://
                break;
            case R.id.test10_btn8://
                break;
        }
    }
}
