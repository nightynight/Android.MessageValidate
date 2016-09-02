package com.brokepal.messagevalidate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.StringTokenizer;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        SMSSDK.initSDK(this, Const.APPKEY, Const.APPSECRET);

        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册手机号
                RegisterPage registerPage=new RegisterPage();
                //注册回调事件
                registerPage.setRegisterCallback(new EventHandler(){
                    //事件完成后调用
                    @Override
                    public void afterEvent(int event, int result, Object data) {
                    //判断是否验证成功
                    if (result==SMSSDK.RESULT_COMPLETE){
                        //获取数据data
                        HashMap<String,Object> map=(HashMap<String,Object>)data;
                        //国家信息
                        String country=(String)map.get("country");
                        //手机号信息
                        String phone=(String)map.get("phone");
                        submitUserInfo(country,phone);  //提交用户信息
                    }
                    }
                });
                //显示注册界面
                registerPage.show(MainActivity.this);
            }
        });
    }

    /**
     * 提交用户信息
     * 通过调用submitUserInfo方法向我们的服务器提交用户资料，提交的资料将当作“通信录好友”功能的建议资料。
     * @param country
     * @param phone
     */
    public void submitUserInfo(String country,String phone){
        Random random=new Random();
        String uid=Math.abs(random.nextInt())+"";
        String nickName="test";
        SMSSDK.submitUserInfo(uid,nickName,null,country,phone);
    }

    public void onClick(View v){
        Intent intent=new Intent(MainActivity.this,MyRegisterActivity.class);
        startActivity(intent);
    }
}
