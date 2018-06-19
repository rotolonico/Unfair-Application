package com.example.android.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button button1;
    TextView pointsView;
    TextView timeView;
    int height, width, randomScreenw, randomScreenh, randomMock, randomSound;
    int points = 0;
    static boolean timer = false;
    int Time = 10;
    final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    boolean panicPress;
    boolean panicEnd = true;
    String mode = "";


    ArrayList<String> randomMockList = new ArrayList<>();
    ArrayList<MediaPlayer> randomSoundList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = true;
        final Button lb = findViewById(R.id.lb);
        final boolean sound = getIntent().getBooleanExtra("sound", true);
        final boolean difficulty = getIntent().getBooleanExtra("difficulty", false);
        final int MaxTime = getIntent().getIntExtra("time", 10);
        boolean panic = getIntent().getBooleanExtra("panic", false);
        Time = MaxTime;

        if (!difficulty && !panic) {
            mode = "Normal";
        }
        if (difficulty && !panic) {
            mode = "Impossible";
        }
        if (panic) {
            mode = "Panic";
        }

        button1 = findViewById(R.id.button1);
        pointsView = findViewById(R.id.points);
        timeView = findViewById(R.id.timer);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        randomMockList.add("I am here");
        randomMockList.add("Catch Me");
        randomMockList.add("You are slow");
        randomMockList.add("Over here");
        randomMockList.add("Here I am");
        randomMockList.add("Don't you press me");
        randomMockList.add("I am here");
        randomMockList.add("Catch Me");

        randomSoundList.add(MediaPlayer.create(this, R.raw.a));
        randomSoundList.add(MediaPlayer.create(this, R.raw.b));
        randomSoundList.add(MediaPlayer.create(this, R.raw.c));
        randomSoundList.add(MediaPlayer.create(this, R.raw.d));
        randomSoundList.add(MediaPlayer.create(this, R.raw.e));
        randomSoundList.add(MediaPlayer.create(this, R.raw.f));

        if (panic) {
            final Handler handler = new Handler();
            final int delay = 1000;
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (panicEnd) {
                        panicPress = false;
                    button1.setVisibility(View.VISIBLE);
                    randomMock = (int) (Math.random() * 8);
                    button1.setText(randomMockList.get(randomMock));
                    if (points == 1) {
                        pointsView.setText("You have " + points + " point");
                    } else {
                        pointsView.setText("You have " + points + " points");
                    }
                    handler.postDelayed(this, delay);
                    final Handler handler = new Handler();
                    final int delay = 2000;
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            button1.setVisibility(View.INVISIBLE);
                            randomScreenw = (int) (Math.random() * (width / 100 * 70) + 1);
                            randomScreenh = (int) (Math.random() * (height / 100 * 70) + 1);
                            lp.setMargins(randomScreenw, randomScreenh, 0, 0);
                            button1.setLayoutParams(lp);
                            if (!panicPress) {
                                panicEnd = false;
                                button1.setVisibility(View.GONE);
                                lb.setVisibility(View.VISIBLE);
                                pointsView.setText("You Lost! Final score = " + points);
                            }
                            handler.postDelayed(this, delay);
                        }}, delay);
                }
                }
            }, delay);

            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    points = points + 1;
                    randomSound = (int) (Math.random() * 5);
                    if (sound) {
                        randomSoundList.get(randomSound).start();
                    }
                    button1.setVisibility(View.INVISIBLE);
                    panicPress = true;

                }
            });

        }
        if (!panic) {
            button1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    randomScreenw = (int) (Math.random() * (width / 100 * 70) + 1);
                    randomScreenh = (int) (Math.random() * (height / 100 * 70) + 1);
                    lp.setMargins(randomScreenw, randomScreenh, 0, 0);
                    button1.setLayoutParams(lp);
                    randomMock = (int) (Math.random() * 8);
                    randomSound = (int) (Math.random() * 5);
                    button1.setText(randomMockList.get(randomMock));
                    if (sound) {
                        randomSoundList.get(randomSound).start();
                    }
                    points = points + 1;
                    if (points == 1) {
                        pointsView.setText("You have " + points + " point");
                    } else {
                        pointsView.setText("You have " + points + " points");
                    }
                    timer = true;
                }
            });

            if (difficulty) {
                final Handler handler = new Handler();
                final int delay = 1; //milliseconds

                handler.postDelayed(new Runnable() {
                    public void run() {
                        randomScreenw = (int) (Math.random() * (width / 100 * 70) + 1);
                        randomScreenh = (int) (Math.random() * (height / 100 * 70) + 1);
                        lp.setMargins(randomScreenw, randomScreenh, 0, 0);
                        handler.postDelayed(this, delay);
                    }
                }, delay);
            }

            final Handler handler = new Handler();
            final int delay = 1000; //milliseconds

            handler.postDelayed(new Runnable() {
                public void run() {
                    if (Time <= 0) {
                        button1.setVisibility(View.GONE);
                        pointsView.setText("You Lost! Final score = " + points);
                        Time = 1;
                        timeView.setVisibility(timeView.GONE);
                        lb.setVisibility(View.VISIBLE);
                    }
                    if (timer == true) {
                        Time--;
                        if (Time == 1) {
                            timeView.setText(Time + " second left");
                        } else {
                            timeView.setText(Time + " seconds left");
                        }
                    }

                    handler.postDelayed(this, delay);
                }
            }, delay);
        }

    }

    public void startActivity(View view){
        Intent lb = new Intent(MainActivity.this, LeaderBoards.class);
        MediaPlayer.create(this,R.raw.fre).start();
        lb.putExtra("mode",mode);
        lb.putExtra("points",points);
        startActivity(lb);
        finish();
    }
}
