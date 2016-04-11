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
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


/*
 * IN PROCESS OF SPLITTING CODE INTO TWO (OR MORE) CLASSES
 */

public class Timer extends AppCompatActivity {

    //declaring all items on page in class
    //sound player
    MediaPlayer alarmSound;

    //data from XML file
    Button stop, stopSoundBtn;
    TextView textViewTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle data = getIntent().getExtras();
        int totalTime = data.getInt("totalTime");
        textViewTime.setText(totalTime);


    }


}