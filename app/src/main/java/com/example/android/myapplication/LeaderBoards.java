package com.example.android.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LeaderBoards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_boards);
        final EditText editText = findViewById(R.id.name);
        final EditText seditText = findViewById(R.id.esearch);
        final String mode = getIntent().getStringExtra("mode");
        final int points = getIntent().getIntExtra("points",1);
        final Button search = findViewById(R.id.search);
        final Button submit = findViewById(R.id.submit);
        final Button lb = findViewById(R.id.lb);
        final TextView textView = findViewById(R.id.text);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText.getText().toString() == ""){
                    submit.setText("Nah I am good");
                } else {
                    submit.setText("Submit");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable name = editText.getText();
                Editable filename = name;
                String fileContents = name + ": Mode = " + mode + " | Points = " + points;
                FileOutputStream outputStream;
                editText.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                seditText.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                try {
                    outputStream = openFileOutput(filename.toString(), Context.MODE_PRIVATE);
                    outputStream.write(fileContents.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FileInputStream in = null;
                        try {
                            in = openFileInput(seditText.getText().toString());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            textView.setText("User not found :(");
                        }
                        InputStreamReader inputStreamReader;
                        if (in != null) {
                            inputStreamReader = new InputStreamReader(in);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            StringBuilder sb = new StringBuilder();
                            String line = null;
                            try {
                                line = bufferedReader.readLine();
                            } catch (IOException e) {
                                e.printStackTrace();
                                textView.setText("User not found :(");
                            }
                            sb.append(line);
                            textView.setText(line);
                        } else {
                            textView.setText("User not found :(");
                        }
                    }
                });
            }
        });

    }

    public void startActivity(View view) {
        Intent flb = new Intent(LeaderBoards.this, FullLeaderBoard.class);
        startActivity(flb);
        finish();
    }
}
