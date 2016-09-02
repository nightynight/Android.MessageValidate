package com.brokepal.messagevalidate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/8/30.
 */
public class MyRegisterActivity extends AppCompatActivity {
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //初始化
        SMSSDK.initSDK(this, Const.APPKEY, Const.APPSECRET);
        EventHandler eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    //开启另一个Activity（输入验证码）
                    Intent intent=new Intent(MyRegisterActivity.this,ValidateActivity.class);
                    intent.putExtra("phone",phone); //  提交验证码需要手机号
                    startActivity(intent);
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                try {
                    JSONObject a = new JSONObject(((Throwable)data).getMessage());
                    System.out.println(a.get("detail"));
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
            phone=((EditText)findViewById(R.id.text_phone)).getText().toString();
            SMSSDK.getVerificationCode(Const.CHINA,phone);  //获取验证码
            }
        });
    }
}
