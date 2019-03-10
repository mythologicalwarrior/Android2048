package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class ChooseActivity extends AppCompatActivity {

    private Button bt_ks, bt_js, bt_tc;
    private Intent a,b,c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        ChooseActivity.activityList.add(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        bt_ks = (Button)findViewById(R.id.button_ks);
        bt_js = (Button)findViewById(R.id.button_js);
        bt_tc = (Button)findViewById(R.id.button_tc);
        bt_ks.setOnClickListener(new ButtonListener());
        bt_js.setOnClickListener(new ButtonListener());
        bt_tc.setOnClickListener(new ButtonListener());
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button_ks:
                    a = new Intent(ChooseActivity.this, MainActivity.class);
                    startActivity(a);
                    break;
                case R.id.button_js:
                    b = new Intent(ChooseActivity.this, IntroduceActivity.class);
                    startActivity(b);
                    break;
                case R.id.button_tc:
                    exit();
            }
        }
    }

    public static List<Activity> activityList = new LinkedList();

    public void exit(){
        for (Activity act:activityList){
            act.finish();
        }
        System.exit(0);
    }

    private long exitTime = 0;

    @SuppressLint("WrongConstant")
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis() - exitTime) > 2000){
                Toast.makeText(this,"再按一次退出", 1000).show();
                exitTime = System.currentTimeMillis();
            }
            else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
