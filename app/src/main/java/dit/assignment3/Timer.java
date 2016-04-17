package dit.assignment3;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class Timer extends AppCompatActivity {

    //ALARM SOUND
    MediaPlayer alarmSound;

    //small button minutes
    Button ten, twenty, thirty, sixty;

    Button startBtn, stopBtn;
    EditText minIn, hoursIn;
    int minMs, hoursMs;

    //additional variables to make timer work
    public int totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //media
        alarmSound = MediaPlayer.create(this, R.raw.alarm_noise);

       //finding by ID
        ten = (Button) findViewById(R.id.tenMin);
        twenty = (Button) findViewById(R.id.twentyMin);
        thirty = (Button) findViewById(R.id.thirtyMin);
        sixty = (Button) findViewById(R.id.sixtyMin);

        startBtn = (Button) findViewById(R.id.startButton);

        hoursIn = (EditText) findViewById(R.id.hoursInput);
        minIn = (EditText) findViewById(R.id.minsInput);

        //Parsing to correct value

        //changing times through smaller buttons
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoursIn.setText("00");
                minIn.setText("10");
            }
        });
        twenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoursIn.setText("00");
                minIn.setText("20");
            }
        });
        thirty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoursIn.setText("00");
                minIn.setText("30");
            }
        });
        sixty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoursIn.setText("01");
                minIn.setText("00");
            }
        });

        //start button working
        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (! hoursIn.getText().toString().isEmpty())
                {
                    int intTime = Integer.parseInt(hoursIn.getText().toString());
                    hoursMs = hrsToMils(intTime);
                }
                else
                {
                    hoursMs = 0;
                }
                if (! minIn.getText().toString().isEmpty())
                {
                    int intTime = Integer.parseInt(minIn.getText().toString());
                    minMs = minToMils(intTime);
                }
                else
                {
                    minMs = 0;
                }

                totalTime = minMs + hoursMs;

                if (totalTime == 0)
                {
                    Toast.makeText(Timer.this, "ENTER TIME", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //start timer
                    final CounterClass1 timer = new CounterClass1(totalTime, 1000);
                    timer.start();
                }
            }
        });
    }

    private int hrsToMils(int hours)
    {
        int minutes = hours * 60;
        int seconds = minutes * 60;
        int millis = seconds * 1000;
        return  millis;
    }

    private  int minToMils(int mins)
    {
        int seconds = mins * 60;
        int millis = seconds * 1000;
        return  millis;
    }

    private void nextPage()
    {
        Intent intent = new Intent(this, Timer.class);
        startActivity(intent);
    }

    public class CounterClass1 extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CounterClass1(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));


            //timeDisp.setText(hms);
        }

        @Override
        public void onFinish() {
            alarmSound.start();
        }
    }
}

