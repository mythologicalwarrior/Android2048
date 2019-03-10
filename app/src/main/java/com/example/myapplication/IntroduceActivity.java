package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroduceActivity extends AppCompatActivity {

    private Button bt_fh;
    Intent a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        ChooseActivity.activityList.add(this);

        bt_fh = (Button)findViewById(R.id.button_fh);
        bt_fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(IntroduceActivity.this,ChooseActivity.class);
                startActivity(intent);
            }
        });
//
//        bt_fh = (Button)findViewById(R.id.button_fh);
//        a = new Intent(IntroduceActivity.this, ChooseActivity.class);
//        startActivity(a);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

}
