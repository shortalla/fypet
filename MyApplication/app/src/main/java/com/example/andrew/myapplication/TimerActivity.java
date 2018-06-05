package com.example.andrew.myapplication;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {
    private GestureDetectorCompat gestureDetectorCompat;
    CheckBox optSingleShot;
    Button btnStart, btnCancel;
    TextView textCounter;

    Timer timer;
    MyTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        optSingleShot = (CheckBox)findViewById(R.id.singleshot);
        btnStart = (Button)findViewById(R.id.start);
        btnCancel = (Button)findViewById(R.id.cancel);
        textCounter = (TextView)findViewById(R.id.counter);

        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (timer != null) {
                    timer.cancel();
                }

                //re-schedule timer here
                //otherwise, IllegalStateException of
                //"TimerTask is scheduled already"
                //will be thrown
                timer = new Timer();
                myTask = new MyTask();

                if (optSingleShot.isChecked()) {
                    //singleshot delay 1000 ms
                    timer.schedule(myTask, 1000);
                } else {
                    //delay 1000ms, repeat in 5000ms
                    timer.schedule(myTask, 1000, 5000);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (timer!=null){
                    timer.cancel();
                    timer = null;
                }
            }
        });
        gestureDetectorCompat = new GestureDetectorCompat(this, new My2ndGestureListener());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class My2ndGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe right' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

         /*
         Toast.makeText(getBaseContext(),
          event1.toString() + "\n\n" +event2.toString(),
          Toast.LENGTH_SHORT).show();
         */

            if(event2.getX() > event1.getX()){
                Toast.makeText(getBaseContext(),
                        "Swipe right - finish()",
                        Toast.LENGTH_SHORT).show();

                finish();
            }

            return true;
        }
    }


    class MyTask extends TimerTask {

        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
            final String strDate = simpleDateFormat.format(calendar.getTime());

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    textCounter.setText(strDate);
                }
            });
        }

    }

}