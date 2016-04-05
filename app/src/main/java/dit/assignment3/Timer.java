package dit.assignment3;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Timer extends AppCompatActivity {

    MediaPlayer alarmSound;
    //declaring all items on page in class
    EditText hoursIn, minIn;
    Button start, stop;
    TextView textViewTime;
    int totalTime;
    int inHr, inMin;
    int hoursMs, minMs;
    public NotificationCompat.Builder mBuilder;
    private Intent resultAction;
    PendingIntent resultPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //intent to act on the notification to leave it null
        resultAction = new Intent(this, Timer.class);

        //pendingIntent.getActivity(Context, int, Intent, int)
        resultPendingIntent = PendingIntent.getActivity(this, 0, resultAction, PendingIntent.FLAG_UPDATE_CURRENT);

        //notification
        mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.small_icon)
                .setContentTitle("My notification").setContentText("Hello World!");

        mBuilder.setContentIntent(resultPendingIntent);


        //playing the alarm sound
        alarmSound = MediaPlayer.create(this, R.raw.alarm_noise);

        //initiallizing declared variables
        hoursIn = (EditText) findViewById(R.id.hoursET);
        minIn = (EditText) findViewById(R.id.minET);
        start = (Button) findViewById(R.id.startButton);
        stop = (Button) findViewById(R.id.stopButton);
        textViewTime = (TextView) findViewById(R.id.timeDisp);

        //counter class below -> new CounterClass(time, interval)
        // in array format due to nature of totalTime initialization
        final CounterClass[] timer = {new CounterClass(totalTime, 1000)};
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (! hoursIn.getText().toString().isEmpty())
                {
                    inHr = Integer.parseInt(hoursIn.getText().toString());
                    hoursMs = hrsToMs(inHr);
                }
                else
                {
                    hoursMs = 0;
                }
                if (! minIn.getText().toString().isEmpty())
                {
                    inMin = Integer.parseInt(minIn.getText().toString());
                    minMs = minToMs(inMin);
                }
                else
                {
                    minMs = 0;
                }
                totalTime = hoursMs + minMs;
                //checking if user has input time for the timer and displaying appropiate message
                if(totalTime == 0)
                {
                    Toast.makeText(Timer.this, "ENTER TIME", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    timer[0] = new CounterClass(totalTime, 1000);
                    timer[0].start();
                }
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer[0].cancel();
            }
        });


    }


    private int hrsToMs(int hours)
    {
        //site for conversions:
        //http://amsi.org.au/teacher_modules/D0/D0g2.png

        int minutes = hours * 60;
        int seconds = minutes * 60;
        int millis = seconds * 1000;
        return  millis;
    }

    private int minToMs(int min)
    {
        //site for conversions:
        //http://amsi.org.au/teacher_modules/D0/D0g2.png

        int seconds = min * 60;
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
            //every tick
            long millis = millisUntilFinished;
            String hrsminsec = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toMinutes(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toSeconds(millis)))
            );

            textViewTime.setText(hrsminsec);
        }

        @Override
        public void onFinish() {

            // Sets an ID for the notification
            int mNotificationId = 001;
            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());

            textViewTime.setText("00:00:00");
            alarmSound.start();
        }
    }
}