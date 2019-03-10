package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.IllegalFormatCodePointException;

public class MainActivity extends Activity implements View.OnClickListener {

    public TextView tv_Score;
    public TextView tv_BextScore;
    public int score = 0;
    private int bestScores;
    private Button bt, bt_cd;
    private Intent a;

    private static MainActivity mainActivity = null;

    public MainActivity() {
        mainActivity = this;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChooseActivity.activityList.add(this);

        bt_cd = (Button) findViewById(R.id.bt_cd);
        bt_cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ChooseActivity.class);
                startActivity(intent);
            }
        });

        inital();
    }

    @SuppressLint("SetTextI18n")
    public void inital() {
        tv_BextScore = (TextView) findViewById(R.id.max_score);
        tv_Score = (TextView) findViewById(R.id.tv_score);
        bt = (Button) findViewById(R.id.bt_cx);
        bt.setOnClickListener(this);
        BastScode bastScode = new BastScode(this);
        bestScores = bastScode.getBestScode();
        tv_BextScore.setText(bestScores + "");
    }

    @Override
    public void onClick(View v) {
        GameView.getGameView().startGame();
    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    public void showScore() {
        tv_Score.setText(score + "");
    }

    public void addScore(int s) {
        score += s;
        showScore();
        if (score > bestScores) {
            bestScores = score;
            BastScode bs = new BastScode(this);
            bs.setBestScode(bestScores);
            tv_BextScore.setText(bestScores + "");
        }
    }
}
