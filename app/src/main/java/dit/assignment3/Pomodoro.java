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
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Pomodoro extends AppCompatActivity {

    Button startButton, cancelButton, about;

    long five, twentyFive;

    MediaPlayer pAlarm;
    AlertDialog.Builder p_alert;

    TextView timeView;

    boolean control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        startButton = (Button) findViewById(R.id.pStartBtn);
        cancelButton = (Button) findViewById(R.id.pStopBtn);
        cancelButton.setVisibility(View.GONE);
        about = (Button) findViewById(R.id.go2About);

        timeView = (TextView) findViewById(R.id.timeView);
        timeView.setText("25:00");


        control = true;
        //five minutes in milliseconds
        five = 300000;

        //twenty-five minutes in milliseconds
        twentyFive = five * five;

        p_alert = new AlertDialog.Builder(Pomodoro.this);
        p_alert.setCancelable(false)
            .setPositiveButton("Move onto the next step", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pAlarm.stop();
                    dialog.dismiss();
                }
            });

        final PomodoroTimer[] t = new PomodoroTimer[1];

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (control == true) {
                    t[0] = new PomodoroTimer(twentyFive, 1000);
                    Log.i("CHY", "25 seconds");
                    control = false;
                } else {
                    t[0] = new PomodoroTimer(five, 1000);
                    Log.i("CHY", "5 seconds");
                    control = true;
                }
                startButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.VISIBLE);
                t[0].start();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t[0].cancel();
                startButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.GONE);
                timeView.setText("25:00");
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go2About();
            }
        });
    }

    private void go2About()
    {
        Intent i = new Intent(this, aboutPomodoro.class);
        startActivity(i);
    }
    private class PomodoroTimer extends CountDownTimer{

        public PomodoroTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String ms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
            timeView.setText(ms);
        }

        @Override
        public void onFinish() {
            pAlarm = MediaPlayer.create(Pomodoro.this,R.raw.beep);
            pAlarm.start();
            cancelButton.setVisibility(View.GONE);
            startButton.setVisibility(View.VISIBLE);

            AlertDialog alert = p_alert.create();
            alert.setTitle("Pomodoro is done");
            if (control == true)
            {
                timeView.setText("25:00");
            }
            else
            {
                timeView.setText("05:00");
            }
            alert.show();
        }
    }
}
