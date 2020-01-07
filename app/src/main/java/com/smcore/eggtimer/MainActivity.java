package com.smcore.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;
    private SeekBar timer;
    private boolean counterIsActive = false;
    private CountDownTimer countdown;
    private Button go;
    public void updateTimer(int secondsLeft){

        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;
        String secondString = Integer.toString(seconds);

        if (seconds <= 9){
            secondString = "0" + secondString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    public void resetTimer(){
        timerTextView.setText("0:30");
        timer.setProgress(30);
        countdown.cancel();
        timer.setEnabled(true);
        go.setText("GO!");
        counterIsActive = false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find views
        timer = findViewById(R.id.set_timer);
        ImageView egg = findViewById(R.id.egg);
        timerTextView = findViewById(R.id.timer);
        go = findViewById(R.id.start);

        timer.setMax(600);
        timer.setProgress(30);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !counterIsActive ) {
                    counterIsActive = true;
                    timer.setEnabled(false);

                    // plus 100 miliseconds to process the first tick and put timer on time;
                        countdown = new CountDownTimer(timer.getProgress() * 1000 + 100, 1000) {
                        @Override
                        public void onTick(long l) {
                            updateTimer((int) l / 1000);
                        }

                        @Override
                        public void onFinish() {
                            resetTimer();
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                            mediaPlayer.start();
                        }
                    }.start();
                    go.setText("Stop");
                }
                else{
                    resetTimer();
                }
            }
        });
        timer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean user) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
