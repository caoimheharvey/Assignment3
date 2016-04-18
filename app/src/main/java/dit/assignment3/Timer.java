package dit.assignment3;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
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
 * TODO: get the timer to appear right
 */

public class Timer extends AppCompatActivity {

    //ALARM SOUND
    MediaPlayer alarmSound;

    Button ten, twenty, thirty, sixty;
    private Button startBtn, cancelBtn, demoButton;
    EditText minIn, hoursIn;
    TableLayout table1, table2;

    TextView timeDisp;
    //additional variables to make timer work
    int minMs, hoursMs;
    int totalTime;

    // Alert Dialog
    AlertDialog.Builder a_builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        /*
            Alert Dialog
             */
        a_builder = new AlertDialog.Builder(Timer.this);
        a_builder.setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alarmSound.stop();
                        dialog.dismiss();
                    }
                });

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

        demoButton = (Button) findViewById(R.id.demobutton);
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
                    if(intTime > 11 || intTime < 0)
                    {
                        Toast.makeText(Timer.this, "Hours must be between 0 and 11", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        hoursMs = hrsToMils(intTime);
                    }
                }
                else
                {
                    hoursMs = 0;
                }
                if (! minIn.getText().toString().isEmpty())
                {
                    int intTime = Integer.parseInt(minIn.getText().toString());
                    if(intTime > 59 || intTime < 0)
                    {
                        Toast.makeText(Timer.this, "Minutes must be between 0 and 59", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        minMs = minToMils(intTime);
                    }
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
                    demoButton.setVisibility(View.GONE);

                    //start timer
                    timer[0] = new CounterClass(totalTime, 1000);
                    timer[0].start();
                }
            }
        });

        demoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalTime = 15000;
                table1.setVisibility(View.GONE);
                table2.setVisibility(View.GONE);

                //change the start button to a cancel button
                startBtn.setVisibility(View.GONE);
                cancelBtn.setVisibility(View.VISIBLE);
                timeDisp.setVisibility(View.VISIBLE);
                demoButton.setVisibility(View.GONE);

                //start timer
                timer[0] = new CounterClass(totalTime, 1000);
                timer[0].start();
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
                demoButton.setVisibility(View.VISIBLE);
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


    private class CounterClass extends CountDownTimer {

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
            /*String hms = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));*/
            int hours   = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);

            timeDisp.setText(""+String.format("%02d:%02d:%02d", hours,
                    TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
        }

        @Override
        public void onFinish() {
            alarmSound = MediaPlayer.create(Timer.this, R.raw.alarm_noise);
            alarmSound.start();

            table1.setVisibility(View.VISIBLE);
            table2.setVisibility(View.VISIBLE);

            //change the start button to a cancel button
            startBtn.setVisibility(View.VISIBLE);
            cancelBtn.setVisibility(View.GONE);
            timeDisp.setVisibility(View.GONE);

            AlertDialog alert = a_builder.create();
            alert.setTitle("Your Timer is DONE");
            alert.show();
        }
    }
}