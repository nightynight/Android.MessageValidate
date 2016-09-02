package com.brokepal.messagevalidate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/8/30.
 */
public class ValidateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validate);

        Intent intent=getIntent();
        final String phone=intent.getStringExtra("phone");

        //初始化
        SMSSDK.initSDK(this, Const.APPKEY, Const.APPSECRET);
        EventHandler eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    //TODO 在这里做登录操作
                    System.out.println("------------success");
                }
            }else{
                try {
                    //获取错误信息
                    JSONObject a = new JSONObject(((Throwable)data).getMessage());
                    String information=a.get("detail").toString();
                    System.out.println(information);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ((Throwable)data).printStackTrace();
            }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调

        Button button=(Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validation=((EditText)findViewById(R.id.text_validate)).getText().toString();
                SMSSDK.submitVerificationCode(Const.CHINA,phone,validation);    //提交验证码
            }
        });
    }
}
