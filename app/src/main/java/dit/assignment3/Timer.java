package dit.assignment3;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/*
 * TODO: convert bundle to long
 * TODO: play noise, AlertDialog, Notification
 */
public class Timer extends AppCompatActivity {

    int totalTimeMs;
    TextView timeDisp;
    Button cancelBtn;

    /* Notification
    public NotificationCompat.Builder mBuilder;
     private Intent resultAction; 
    PendingIntent resultPendingIntent;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

       /* //intent to act on the notification to leave it null
         resultAction = new Intent(this, MainActivity.class);  
        //pendingIntent.getActivity(Context, int, Intent, int) 
        resultPendingIntent = PendingIntent.getActivity(this, 0, resultAction, PendingIntent.FLAG_UPDATE_CURRENT);  
        //notification 
        mBuilder =  new NotificationCompat.Builder(this).setSmallIcon(R.drawable.small_icon) ;
        mBuilder.setContentTitle("TimerApp").setContentText("DONE");
        mBuilder.setContentIntent(resultPendingIntent);*/


        timeDisp = (TextView) findViewById(R.id.timeView);
        cancelBtn = (Button)findViewById(R.id.cancelButton);

        Bundle time = getIntent().getExtras();

        if(time == null)
        {
            timeDisp.setText("Failed.");
        }
        else
        {
            timeDisp.setText("YASS");
           // totalTimeMs = Integer.parseInt(String.valueOf(time));
           // timeDisp.setText(totalTimeMs);
        }


        final CounterClass timer = new CounterClass(120000, 1000);
        timer.start();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDisp.setText("CANCELED");
                timer.cancel();
                //displays cancelled then returns to main activity
                finish();
            }
        });
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
                    /*TimeUnit.MILLISECONDS.toHours(millisUntilFinished), 
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)), 
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)))
            );*/

            timeDisp.setText(hms);
        }

        @Override
        public void onFinish() {
            timeDisp.setText("Completed.");
        }
    }
}
