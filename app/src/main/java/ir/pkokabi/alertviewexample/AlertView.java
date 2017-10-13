package ir.pkokabi.alertviewexample;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.pkokabi.alertview.databinding.ViewAlertViewBinding;

/**
 * Created by p.kokabi on 6/26/17.
 */

public class AlertView implements AlertViewCallBack, View.OnClickListener {

    private ViewAlertViewBinding binding;
    private Context context;
    private View baseView;
    private AlertView alertView;
    private static AlertView lastAlertView;

    public static final int STATE_ERROR = 0;
    public static final int STATE_WARNING = 1;
    public static final int STATE_SUCCESS = 2;
    public static final int STATE_RELOAD = 3;

    private static final Pattern RTL_CHARACTERS =
            Pattern.compile("[\u0600-\u06FF\u0750-\u077F\u0590-\u05FF\uFE70-\uFEFF]");

    private AlertView() {
    }

    public AlertView(Context context, String message, int actionType) {
        show(context, message, actionType);
    }

    private AlertView show(Context context, String message, int actionType) {
        return init(context, message, actionType);
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onRefresh() {
        alertView.hideFromWindow(true);
    }

    @Override
    public void onClick(View view) {
        onRefresh();
    }

    private class AlertViewListener implements ViewTreeObserver.OnGlobalLayoutListener {
        AlertViewListener() {
        }

        public void onGlobalLayout() {
            int h = AlertView.this.baseView.getHeight();
            AlertView.this.baseView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            ObjectAnimator oa = ObjectAnimator.ofFloat(AlertView.this.baseView, "translationY", (float) (-h), 0.0f);
            oa.setDuration(200);
            oa.start();
        }
    }

    private class ShowHideAnimation extends AnimatorListenerAdapter {
        ShowHideAnimation() {
        }

        public void onAnimationEnd(Animator animation) {
            if (AlertView.this.baseView != null) {
                try {
                    ((Activity) AlertView.this.context).getWindowManager().removeViewImmediate(AlertView.this.baseView);
                } catch (Exception e) {
                    return;
                }
            }
            AlertView.this.baseView = null;
        }
    }

    private AlertView init(final Context context, String message, int actionType) {
        if (lastAlertView != null) {
            lastAlertView.hideFromWindow(false);
            lastAlertView = null;
        }
        alertView = new AlertView();
        alertView.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), ir.pkokabi.alertview.R.layout.view_alert_view, null, true);
        alertView.baseView = binding.getRoot();

        setupDialog(message, actionType);

        Matcher matcher = RTL_CHARACTERS.matcher(message);
        if (matcher.find())
            binding.getRoot().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        alertView.showInWindow();
        if (actionType != STATE_RELOAD)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (context != null) {
                        alertView.hideFromWindow(true);
                        onFinish();
                    }
                }
            }, 3000);
        lastAlertView = alertView;
        return alertView;
    }

    private void setupDialog(String message, int actionType) {
        binding.dialogMessageTV.setText(message);
        binding.getRoot().setOnClickListener(this);
        switch (actionType) {
            case STATE_ERROR:
                binding.dialogImgv.setImageResource(ir.pkokabi.alertview.R.drawable.ic_error_alert_view);
                break;
            case STATE_WARNING:
                binding.dialogImgv.setImageResource(ir.pkokabi.alertview.R.drawable.ic_warning_alert_view);
                break;
            case STATE_SUCCESS:
                binding.dialogImgv.setImageResource(ir.pkokabi.alertview.R.drawable.ic_success_alert_view);
                break;
            case STATE_RELOAD:
                binding.dialogImgv.setImageResource(ir.pkokabi.alertview.R.drawable.ic_refresh_alert_view);
                break;
        }
    }

    private void showInWindow() {
        baseView.setTag(this);
        try {
            ((Activity) this.context).getWindowManager().addView(baseView, getActionBarLayoutParams(this.context));
            baseView.getViewTreeObserver().addOnGlobalLayoutListener(new AlertViewListener());
        } catch (Exception ignored) {
        }
    }

    private void hideFromWindow(boolean animate) {
        try {
            if (baseView != null && baseView.getWindowToken() != null) {
                if (animate) {
                    int h = baseView.getHeight();
                    ObjectAnimator oa = ObjectAnimator.ofFloat(baseView, "translationY", 0.0f, (float) (-h));
                    oa.setDuration(250);
                    oa.addListener(new ShowHideAnimation());
                    oa.start();
                    return;
                }
                ((Activity) this.context).getWindowManager().removeViewImmediate(baseView);
                baseView = null;
            }
        } catch (Exception ignored) {

        }
    }

    private WindowManager.LayoutParams getActionBarLayoutParams(Context context) {
        Rect rect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(-1, -2, 2, 264, -3);
        params.gravity = 48;
        params.x = 0;
        params.y = statusBarHeight;
        return params;
    }

}
