package com.ReddyGona.projectk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class QuestionActivity extends AppCompatActivity {

    TextView Min, Seconds;
    Button submit;
    EditText Qn;

    //timer
    private int duration = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Min = findViewById(R.id.min);
        Seconds = findViewById(R.id.seconds);
        submit = findViewById(R.id.submit_btn);
        Qn = findViewById(R.id.editText);

        //starting timer
        new CountDownTimer(duration * 1000, 1000) {
            @Override
            public void onTick(long l) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(l),
                                 TimeUnit.MILLISECONDS.toMinutes(l)-
                                 TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),
                                TimeUnit.MILLISECONDS.toSeconds(l)-
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                       final String[] minsec =time.split(":");
                       Min.setText(minsec[1]);
                       Seconds.setText(minsec[2]);
                    }
                });
            }

            @Override
            public void onFinish() {
                Qn.setEnabled(false);
                submit.setVisibility(View.VISIBLE);
            }
        }.start();
        
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuestionActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }
}