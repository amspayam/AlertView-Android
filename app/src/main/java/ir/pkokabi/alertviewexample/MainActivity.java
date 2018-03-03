package ir.pkokabi.alertviewexample;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ir.pkokabi.alertview.AlertView;

public class MainActivity extends AppCompatActivity {

    Context context;

    boolean doubleBackToExitPressedOnce = false;
    int exitDelay = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = this;


        findViewById(R.id.btnError).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertView(context, "Error", AlertView.STATE_ERROR);
            }
        });

        findViewById(R.id.btnSuccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertView(context, "SUCCESS", AlertView.STATE_SUCCESS);
            }
        });

        findViewById(R.id.btnWarning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertView(context, "WARNING", AlertView.STATE_WARNING);
            }
        });

        findViewById(R.id.btnReload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertView(context, "Reload Task", AlertView.STATE_RELOAD){
                    @Override
                    public void onRefresh() {
                        new AlertView(context, "SUCCESS", AlertView.STATE_SUCCESS);
                    }
                };
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }
        doubleBackToExitPressedOnce = true;
        if (getApplicationContext() != null)
            new AlertView(context, "To Exit Press Again", AlertView.STATE_WARNING);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, exitDelay);
    }
}