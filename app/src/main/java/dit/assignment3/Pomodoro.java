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

public class Pomodoro extends AppCompatActivity {

    Button startButton, cancelButton;

    long five, twentyFive;

    MediaPlayer pAlarm;
    AlertDialog.Builder p_alert;

    boolean control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        startButton = (Button) findViewById(R.id.pStartBtn);
        cancelButton = (Button) findViewById(R.id.pStopBtn);

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

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(control)
                {
                    PomodoroTimer t = new PomodoroTimer(twentyFive, 1000);
                    control = !control;
                }
                else
                {
                    PomodoroTimer t = new PomodoroTimer(five, 1000);
                    control =! control;
                }
            }
        });
    }

    private class PomodoroTimer extends CountDownTimer{

        public PomodoroTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            pAlarm = MediaPlayer.create(Pomodoro.this,R.raw.Beep);
            pAlarm.start();

            AlertDialog alert = p_alert.create();
            alert.setTitle("Pomodoro is done");
            alert.show();
        }
    }
}
