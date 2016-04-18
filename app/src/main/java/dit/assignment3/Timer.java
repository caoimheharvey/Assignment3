package dit.assignment3;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/*
 * TODO: Notifications
 * TODO: ALERT DIALOG
 * TODO: get the timer to appear right
 */

public class Timer extends AppCompatActivity {

    //ALARM SOUND
    MediaPlayer alarmSound;

    Button ten, twenty, thirty, sixty;
    Button startBtn, cancelBtn;
    EditText minIn, hoursIn;
    TableLayout table1, table2;

    TextView timeDisp;
    //additional variables to make timer work
    int minMs, hoursMs;
    public int totalTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //media
        alarmSound = MediaPlayer.create(this, R.raw.alarm_noise);

        /*
         * Declaring countdown timer
         * Has to be in array form. el 0 is the actual timer and el 1 is the size of the array
         */
        final CounterClass[] timer = new CounterClass[1];

       //finding by ID
        ten = (Button) findViewById(R.id.tenMin);
        twenty = (Button) findViewById(R.id.twentyMin);
        thirty = (Button) findViewById(R.id.thirtyMin);
        sixty = (Button) findViewById(R.id.sixtyMin);

        startBtn = (Button) findViewById(R.id.startButton);
        cancelBtn = (Button) findViewById(R.id.cancelButton);

        hoursIn = (EditText) findViewById(R.id.hoursInput);
        minIn = (EditText) findViewById(R.id.minsInput);

        table1 = (TableLayout) findViewById(R.id.inputTable);
        table2 = (TableLayout) findViewById(R.id.numberTable);

        timeDisp = (TextView) findViewById(R.id.timeleftdisp);

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
                    //change visibility of objects
                    //hide the tables
                    table1.setVisibility(View.GONE);
                    table2.setVisibility(View.GONE);

                    //change the start button to a cancel button
                    startBtn.setVisibility(View.GONE);
                    cancelBtn.setVisibility(View.VISIBLE);
                    timeDisp.setVisibility(View.VISIBLE);

                    //start timer
                    timer[0] = new CounterClass(3000, 1000);
                    timer[0].start();
                }
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer[0].cancel();
                table1.setVisibility(View.VISIBLE);
                table2.setVisibility(View.VISIBLE);

                //change the start button to a cancel button
                startBtn.setVisibility(View.VISIBLE);
                cancelBtn.setVisibility(View.GONE);
                timeDisp.setVisibility(View.GONE);

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


    public class CounterClass extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));

            timeDisp.setText(hms);
        }

        @Override
        public void onFinish() {
            table1.setVisibility(View.VISIBLE);
            table2.setVisibility(View.VISIBLE);

            //change the start button to a cancel button
            startBtn.setVisibility(View.VISIBLE);
            cancelBtn.setVisibility(View.GONE);
            timeDisp.setVisibility(View.GONE);
            alarmSound.start();
        }
    }
}