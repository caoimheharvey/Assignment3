package dit.assignment3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button timerBtn = (Button) findViewById(R.id.timerButton);
        //Button pomodoroBtn = (Button) findViewById(R.id.pomoButton);

        timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
    }

    private void next(){
        Intent intent = new Intent(this, Timer.class);
        startActivity(intent);
    }
}
