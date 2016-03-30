package dit.assignment3;

import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Timer extends AppCompatActivity {
    //declaring all items on page in class
    EditText hoursIn, minIn;
    Button start, stop;
    TextView textViewTime;
    int totalTime;
    int inHr, inMin;
    int hoursMs, minMs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //initiallizing declared variables
        hoursIn = (EditText) findViewById(R.id.hoursET);
        minIn = (EditText) findViewById(R.id.minET);
        start = (Button) findViewById(R.id.startButton);
        stop = (Button) findViewById(R.id.stopButton);
        textViewTime = (TextView) findViewById(R.id.timeDisp);

        inHr = Integer.parseInt(String.valueOf(hoursIn));
        inMin = Integer.parseInt(String.valueOf(minIn));

        hoursMs = hrsToMs(inHr);
        minMs = minToMs(inMin);
        totalTime = hoursMs * minMs;

        //temp start time
        textViewTime.setText("TIME");

        //counter class below -> new CounterClass(time, interval)
        final CounterClass timer = new CounterClass(totalTime, 1000);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
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
            textViewTime.setText("DONE");
        }
    }


}
