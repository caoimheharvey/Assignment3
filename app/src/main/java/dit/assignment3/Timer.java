package dit.assignment3;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/*
 * TODO: convert bundle to long
 * TODO: play noise, AlertDialog, Notification
 */
public class Timer extends AppCompatActivity {

    //Media Player
    MediaPlayer alarmSound;

    int totalTimeMs;
    TextView timeDisp;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        alarmSound = MediaPlayer.create(this, R.raw.alarm_noise);

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


        final CounterClass timer = new CounterClass(60000, 1000);
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


            timeDisp.setText(hms);
        }

        @Override
        public void onFinish() {
            timeDisp.setText("Completed.");
            DoneAlert done = new DoneAlert();
            //done.show();
            alarmSound.start();
        }
    }

    /*
     *  Alert Dialog
     */
    public class DoneAlert extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("TEST")
                    .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton("SNOOZE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
