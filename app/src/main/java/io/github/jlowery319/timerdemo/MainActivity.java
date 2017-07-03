package io.github.jlowery319.timerdemo;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controlButton;
    boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        controlButton = (Button)findViewById(R.id.controlButton);

        // set max and current
        timerSeekBar.setMax(600); // max 10 minutes
        timerSeekBar.setProgress(30); // current progress initially 30 seconds

        // on change, update text in timerTextView
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

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

    public void updateTimer(int secondsLeft) {

        // convert progress to minutes & seconds
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);
        if (seconds < 10) {
            secondString = "0" + secondString;
        }

        // update timer textview
        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    public void resetTimer() {
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controlButton.setText("Start");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
    }

    // called when button is clicked - start/stop
    public void controlTimer(View view) {

        // if counter is not currently running, start it
        if (counterIsActive == false) {

            counterIsActive = true; // counter has started
            timerSeekBar.setEnabled(false); // seekBar can not be moved
            controlButton.setText("Stop"); // update Button text

            // start the timer
            // get the seekbar progress value to put as starting value (1st parameter)
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // update value of timer
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    // set text to 0:00 when done
                    timerTextView.setText("0:00");

                    resetTimer()

                    // play sound
                    // use getApplicationContext() because 'this' means this CountDownTimer within a timer
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.firetruckhorn);
                    mplayer.start();
                }
            }.start();
        }
        // else counter is active, button says 'stop' when clicked so stop the counter
        else {
            resetTimer();
        }

    }

}




/*
        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisecondsUntilDone) {
                // code to run each second
            }

            public void onFinish() {
                // code to run after counter is done
            }

        };// counts down from 10 seconds to 0 seconds

        ///////

        Handler handler = new Handler();

        Runnable run = new Runnable() {
            @Override
            public void run() {
                // insert code to run every second

                // calls this run() method every 1 second
                handler.postDelayed(this, 1000);
            }
        }

        handler.post(run); // call Runnable's run() the first time

    }

    */

