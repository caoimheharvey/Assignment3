package dit.assignment3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

// NEW FOUND BUGS: 10 APR 2016
/*
 * TODO: start working on the GUI
 * TODO: fix all bugs and get a popup notification working
 * TODO: have app run in OS background
 * TODO: find ways to divide code into multiple classes for simplicity
 * TODO: Complete new UI. Start button is an intent to get to the Timer page where the actual countdown occurs
 */

public class MainActivity extends AppCompatActivity {

    //small button minutes
    Button ten, twenty, thirty, sixty;

    Button startBtn;
    EditText minIn, hoursIn;
    int minMs, hoursMs;

    //additional variables to make timer work
    long totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding by ID
        ten = (Button) findViewById(R.id.tenMin);
        twenty = (Button) findViewById(R.id.twentyMin);
        thirty = (Button) findViewById(R.id.thirtyMin);
        sixty = (Button) findViewById(R.id.sixtyMin);

        startBtn = (Button) findViewById(R.id.startButton);

        hoursIn = (EditText) findViewById(R.id.hoursInput);
        minIn = (EditText) findViewById(R.id.minsInput);

        //Parsing to correct value

        //changing times through smaller buttons
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minIn.setText("01");
            }
        });
        twenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minIn.setText("20");
            }
        });
        thirty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minIn.setText("30");
            }
        });
        sixty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoursIn.setText("01");
                minIn.setText("00");
            }
        });


        //start button working
        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (! hoursIn.getText().toString().isEmpty())
                {
                    int intTime = Integer.parseInt(hoursIn.getText().toString());
                    hoursMs = hrsToMils(intTime);
                }
                else
                {
                    hoursMs = 0;
                }
                if (! minIn.getText().toString().isEmpty())
                {
                    int intTime = Integer.parseInt(minIn.getText().toString());
                    minMs = minToMils(intTime);
                }
                else
                {
                    minMs = 0;
                }

                totalTime = minMs + hoursMs;

                if (totalTime == 0)
                {
                    Toast.makeText(MainActivity.this, "ENTER TIME", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    nextPage();
                }


            }
        });
    }

    private int hrsToMils(int hours)
    {
        int minutes = hours * 60;
        int seconds = minutes * 60;
        int millis = seconds * 1000;
        return  millis;
    }

    private  int minToMils(int mins)
    {
        int seconds = mins * 60;
        int millis = seconds * 1000;
        return  millis;
    }

    private void nextPage()
    {
        Intent intent = new Intent(this, Timer.class).putExtra("totalTime", totalTime);
        startActivity(intent);
    }
}

