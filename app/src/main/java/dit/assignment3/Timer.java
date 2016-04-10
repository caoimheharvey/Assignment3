package dit.assignment3;

import android.app.NotificationManager;
import android.app.PendingIntent;
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
 * IN PROCESS OF SPLITTING CODE INTO TWO (OR MORE) CLASSES
 */

public class Timer extends AppCompatActivity {

    //declaring all items on page in class
    //sound player
    MediaPlayer alarmSound;

    //data from XML file
    EditText hoursIn, minIn;
    Button start, stop, stopSoundBtn;
    TextView textViewTime;
    int totalTime;
    int inHr, inMin;
    int hoursMs, minMs;
    TableLayout table;

    //notification
    public NotificationCompat.Builder mBuilder;
    private Intent resultAction;
    PendingIntent resultPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intent to act on the notification to leave it null
        resultAction = new Intent(this, MainActivity.class);

        //pendingIntent.getActivity(Context, int, Intent, int)
        resultPendingIntent = PendingIntent.getActivity(this, 0, resultAction, PendingIntent.FLAG_UPDATE_CURRENT);

        //notification
        mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.small_icon)
                .setContentTitle("TimerApp").setContentText("Your Timer is done!");

        mBuilder.setContentIntent(resultPendingIntent);

        //playing the alarm sound
        alarmSound = MediaPlayer.create(this, R.raw.alarm_noise);

        //initiallizing declared variables
        hoursIn = (EditText) findViewById(R.id.hoursET);
        minIn = (EditText) findViewById(R.id.minET);
        start = (Button) findViewById(R.id.startButton);
        stop = (Button) findViewById(R.id.stopButton);
        textViewTime = (TextView) findViewById(R.id.timeDisp);
        table = (TableLayout) findViewById(R.id.tableDisp);
        stopSoundBtn = (Button) findViewById(R.id.stopSoundButton);

        //hiding
//        textViewTime.setVisibility(View.GONE);
 //       stopSoundBtn.setVisibility(View.GONE);

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
                    textViewTime.setVisibility(View.VISIBLE);
                    timer[0].start();
                    table.setVisibility(View.GONE);
                }
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                table.setVisibility(View.VISIBLE);
  //              textViewTime.setVisibility(View.GONE);
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

            //changing visibility settings
            table.setVisibility(View.VISIBLE);
            stopSoundBtn.setVisibility(View.VISIBLE);
            textViewTime.setVisibility(View.GONE);

            alarmSound.start();

            //gets timer to stop once button is clicked
            //only accesible once timer finishes
            stopSoundBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alarmSound.stop();
                }
            });

        }
    }
}