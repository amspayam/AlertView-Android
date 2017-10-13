package ir.pkokabi.alertviewexample;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ir.pkokabi.alertviewexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Context context;

    boolean doubleBackToExitPressedOnce = false;
    int exitDelay = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;


        binding.btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertView(context, "سلام", AlertView.STATE_ERROR);
            }
        });

        binding.btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertView(context, "SUCCESS", AlertView.STATE_SUCCESS);
            }
        });

        binding.btnWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertView(context, "WARNING", AlertView.STATE_WARNING);
            }
        });

        binding.btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertView(context, "سلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلامسلام", AlertView.STATE_RELOAD);
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