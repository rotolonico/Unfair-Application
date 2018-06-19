package com.example.android.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

public class Main2Activity extends AppCompatActivity {
    MediaPlayer theme;

    public void startActivity(View view){
        Intent act1 = new Intent(Main2Activity.this, MainActivity.class);
        CheckBox difficulty = findViewById(R.id.difficulty);
        CheckBox soundcheck = findViewById(R.id.sound);
        CheckBox panic = findViewById(R.id.panic);
        DiscreteSeekBar time = findViewById(R.id.time);
        act1.putExtra("difficulty",difficulty.isChecked());
        act1.putExtra("sound",soundcheck.isChecked());
        act1.putExtra("time",time.getProgress());
        act1.putExtra("panic",panic.isChecked());
        startActivity(act1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        theme = MediaPlayer.create(this, R.raw.theme);
        theme.start();
        final DiscreteSeekBar time = findViewById(R.id.time);
        time.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Timer = " + time.getProgress() + " seconds", Toast.LENGTH_SHORT).show();
            }
        });

        final CheckBox panic = findViewById(R.id.panic);
        final CheckBox difficulty = findViewById(R.id.difficulty);
        final DiscreteSeekBar notime = findViewById(R.id.time);

        panic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (panic.isChecked()) {
                difficulty.setEnabled(false);
                notime.setVisibility(View.GONE);
                } else {
                    difficulty.setEnabled(true);
                    notime.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        theme.stop();
        theme.release();

    }
}
