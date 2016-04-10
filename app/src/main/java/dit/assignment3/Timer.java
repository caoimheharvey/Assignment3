package dit.assignment3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// NEW FOUND BUGS: 5 APR 2016
/*
 * stop alarm button doesn't disappear when start button is pressed after timer finishes
 * notification causes app to crash
 * the display of countdown when number greater than 1 is entered in minutes
 * main_activity not used
 *
 * TODO: start working on the GUI
 * TODO: fix all bugs and get a popup notification working
 * TODO: have app run in OS background
 * TODO: find ways to divide code into multiple classes for simplicity
 */

public class Timer extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

    }
}