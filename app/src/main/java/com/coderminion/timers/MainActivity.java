package com.coderminion.timers;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int TIME_IN_SECS = 1;
    int total = 5;
    TextView tv_time;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_time = (TextView)findViewById(R.id.tv_time);
        toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG);
        final Button button = (Button) findViewById(R.id.starttimer);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(button.getText().toString().equals("Start")) {
                    startTimer();
                    button.setText("Stop");
                    toast.setText("Start");
                    toast.show();
                }
                else
                {
                    stoptimertask();
                    button.setText("Start");
                    toast.setText("Stop");
                    toast.show();
                }
            }
        });

    }

    @Override
    public void onDestroy(){
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    public  void startTimer() {
        //set a new Timer
        timer = new Timer();

        initializeTimerTask();


        timer.schedule(timerTask, 5000, TIME_IN_SECS *1000 ); //Implies after every 5000 ms i.e. 5 secs timertask will execute after @param TIME_IN_SECS
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null

        if (timer != null) {
            timer.cancel();
            timer = null;
            tv_time.setText("Timer Stopped!!!!!!!!");
            toast.setText("Timer Stopped!!!!!!!");
            toast.show();
            total= 5; //Set timer to 5 again
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {

                        total = total+TIME_IN_SECS;
                        tv_time.setText(total+" Passed !!");
                        toast.setText(total+" Passed !!");
                        toast.show();
                    }
                });
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        stoptimertask();
    }
}
