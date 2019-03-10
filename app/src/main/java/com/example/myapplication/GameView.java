package com.example.myapplication;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;


public class GameView extends GridLayout {

    private CardView[][] cardsMap = new CardView[4][4];
    private static GameView gameView = null;

    public static  GameView getGameView(){
        return gameView;
    }

    private List<Point> points = new ArrayList<Point>();


    public GameView(Context context) {
        super(context);
        gameView = this;
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);
        gameView = this;
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        gameView = this;
        initGameView();
    }

    private void initGameView(){
        Log.d("233", "0");
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        addCards(getCardWitch(), getCardWitch());
        startGame();
        setOnTouchListener(new OnTouchListener() {

            private float start_x, start_y;
            private float offset_x, offset_y;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                 case MotionEvent.ACTION_DOWN:
                    start_x = motionEvent.getX();
                    start_y = motionEvent.getY();
                    break;
                 case MotionEvent.ACTION_UP:
                     offset_x = motionEvent.getX() - start_x;
                     offset_y = motionEvent.getY() - start_y;
                     if (Math.abs(offset_x) > Math.abs(offset_y)){
                         if (offset_x < -5){
                             Log.d("233", "left");
                             swipeLeft();
                         }
                         else if (offset_x > 5){
                             Log.d("233", "right");
                             swipeRight();
                         }
                     }
                     else{
                         if (offset_y < -5){
                             Log.d("233", "up");
                             swipeUp();
                         }
                         else if (offset_y > 5){
                             Log.d("233", "down");
                             swipeDown();
                         }
                     }
                     break;
                }
                return true;
            }
        });
    }

    private void addCards(int cardWidth, int cardHeight){
        CardView c;
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                c = new CardView(getContext());
                c.setNum(0);
                Log.d("233","3");
                addView(c, cardWidth, cardHeight);
                Log.d("233", "4");
                cardsMap[x][y] = c;
            }
        }
    }

    private int getCardWitch(){
        Log.d("233", "5");
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();

        int carWitch;
        carWitch = displayMetrics.widthPixels;

        return (carWitch -10) / 4;
    }

    public void startGame(){
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                cardsMap[x][y].setNum(0);
            }
        }
        MainActivity.getMainActivity().score = 0;
        addRondomNum();
        addRondomNum();
    }

    private void addRondomNum(){
        points.clear();

        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                if (cardsMap[x][y].getNum() <= 0){
                    points.add(new Point(x, y));
                }
            }
        }
        Point p = points.remove((int)(Math.random() * points.size()));
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }

    private void addRandomNum(){
        points.clear();

        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                if (cardsMap[x][y].getNum() <= 0){
                    points.add(new Point(x, y));
                }
            }
        }

        Point p = points.remove((int)(Math.random() * points.size()));
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2:4);
    }

    private void swipeLeft(){
        boolean merge = false;

        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                for (int x1 = x + 1; x1 < 4; x1++){
                    if (cardsMap[x1][y].getNum() > 0){
                        if (cardsMap[x][y].getNum() <= 0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;
                            merge = true;
                        }
                        else if (cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            check();
        }
    }

    private void swipeRight(){
        boolean merge = false;

        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                for (int x1 = x -1; x1 >= 0; x1--){
                    if (cardsMap[x1][y].getNum() > 0){
                        if (cardsMap[x1][y].getNum() <= 0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            merge = true;
                        }
                        else if (cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            check();
        }
    }

    private void swipeUp(){
        boolean merge = false;

        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                for (int y1 = y + 1; y1 < 4; y1++){
                    if (cardsMap[x][y1].getNum() > 0){
                        if (cardsMap[x][y].getNum() <= 0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            merge = true;
                        }
                        else if (cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            check();
        }
    }

    private void swipeDown(){
        boolean merge = false;

        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                for (int y1 = y - 1; y1 >= 0; y1--){
                    if (cardsMap[x][y1].getNum() > 0){
                        if (cardsMap[x][y].getNum() <= 0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            merge = true;
                        }
                        else if (cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            check();
        }
    }

    private void check(){
        boolean complete = true;
        ALL: for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                if (cardsMap[x][y].getNum() == 0 || (x > 0 && cardsMap[x][y].equals(cardsMap[x - 1][y])) || (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y])) || (y > 0 && cardsMap[x][y].equals(cardsMap[x][y - 1])) || (y < 3 && cardsMap[x][y].equals(cardsMap[x][y + 1]))){
                    complete = false;
                    break ALL;
                }
            }
        }
        if (complete){
            new AlertDialog.Builder(getContext()).setTitle("游戏提示").setMessage("游戏结束，你输了！！！").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }

        for (int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                if (cardsMap[x][y].getNum() == 2048){
                    new AlertDialog.Builder(getContext()).setTitle("游戏提示").setMessage("游戏结束，你赢了！！！").setPositiveButton("重来麽", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startGame();
                        }
                    }).show();
                }
            }
        }
    }
}
