package dit.assignment3;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class Timer extends AppCompatActivity {

    long totalTimeMs;
    TextView timeDisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timeDisp = (TextView) findViewById(R.id.timeView);

        Bundle time = getIntent().getExtras();

        if(time == null)
        {
            timeDisp.setText("nope");
        }
        else
        {
            timeDisp.setText("YASSSS");
        }


        final CounterClass timer = new CounterClass(30000, 1000);
        timer.start();
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

        }

        @Override
        public void onFinish() {

        }
    }
}
