package ir.pkokabi.alertviewexample;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ir.pkokabi.alertviewexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;


//        binding.btnError.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AlertView().show(context, "سلام", AlertView.STATE_ERROR);
//            }
//        });
//
//        binding.btnSuccess.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AlertView().show(context, "SUCCESS", AlertView.STATE_SUCCESS);
//            }
//        });
//
//        binding.btnWarning.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AlertView().show(context, "WARNING", AlertView.STATE_WARNING);
//            }
//        });
//
//        binding.btnReload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AlertView().show(context, "RELOAD", AlertView.STATE_RELOAD);
//            }
//        });

    }
}