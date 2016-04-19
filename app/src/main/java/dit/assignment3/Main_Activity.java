/*
    OOP-Video: https://youtu.be/cgYL19X5zms
 */

package dit.assignment3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Main_Activity extends AppCompatActivity {

    Button timerBtn, pomoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerBtn = (Button) findViewById(R.id.go2Timer);
        pomoBtn = (Button) findViewById(R.id.go2Pomodoro);

        timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextTimer();
            }
        });

        pomoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPomo();
            }
        });
    }

    void nextTimer()
    {
        Intent i = new Intent(this, Timer.class);
        startActivity(i);
    }

    void nextPomo()
    {
        Intent i = new Intent(this, Pomodoro.class);
        startActivity(i);
    }
}
