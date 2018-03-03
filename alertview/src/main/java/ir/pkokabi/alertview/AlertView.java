package ir.pkokabi.alertview;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by p.kokabi on 6/26/17.
 */

public class AlertView implements AlertViewCallBack, View.OnClickListener {

    private Context context;
    private View baseView;
    private AlertView alertView;
    private static AlertView lastAlertView;
    private CardView rootLayout;
    private AppCompatImageView dialogImgv;
    private TextView dialogMessageTV;

    public static final int STATE_ERROR = 0;
    public static final int STATE_WARNING = 1;
    public static final int STATE_SUCCESS = 2;
    public static final int STATE_RELOAD = 3;

    private static final Pattern RTL_CHARACTERS =
            Pattern.compile("[\u0600-\u06FF\u0750-\u077F\u0590-\u05FF\uFE70-\uFEFF]");

    private AlertView() {
    }

    public AlertView(Context context, String message, int actionType) {
        init(context, message, actionType);
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
            int h = baseView.getHeight();
            baseView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            ObjectAnimator oa = ObjectAnimator.ofFloat(AlertView.this.baseView, "translationY", (float) (-h), 0.0f);
            oa.setDuration(250);
            oa.start();
        }
    }

    private class ShowHideAnimation extends AnimatorListenerAdapter {
        ShowHideAnimation() {
        }

        public void onAnimationEnd(Animator animation) {
            if (baseView != null) {
                try {
                    ((Activity) context).getWindowManager().removeViewImmediate(baseView);
                } catch (Exception e) {
                    return;
                }
            }
            baseView = null;
        }
    }

    private void init(final Context context, String message, int actionType) {
        if (lastAlertView != null) {
            lastAlertView.hideFromWindow(false);
            lastAlertView = null;
        }
        alertView = new AlertView();
        alertView.context = context;
        alertView.baseView = LayoutInflater.from(context).inflate(R.layout.view_alert_view, null);

        rootLayout = alertView.baseView.findViewById(R.id.rootLayout);
        dialogImgv = alertView.baseView.findViewById(R.id.dialogImgv);
        dialogMessageTV = alertView.baseView.findViewById(R.id.dialogMessageTV);

        setupDialog(message, actionType);

        Matcher matcher = RTL_CHARACTERS.matcher(message);
        if (matcher.find())
            rootLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

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
    }

    private void setupDialog(String message, int actionType) {
        dialogMessageTV.setText(message);
        rootLayout.setOnClickListener(this);
        switch (actionType) {
            case STATE_ERROR:
                dialogImgv.setImageResource(R.drawable.ic_error_alert_view);
                break;
            case STATE_WARNING:
                dialogImgv.setImageResource(R.drawable.ic_warning_alert_view);
                break;
            case STATE_SUCCESS:
                dialogImgv.setImageResource(R.drawable.ic_success_alert_view);
                break;
            case STATE_RELOAD:
                dialogImgv.setImageResource(R.drawable.ic_refresh_alert_view);
                break;
        }
    }

    private void showInWindow() {
        baseView.setTag(this);
        try {
            ((Activity) context).getWindowManager().addView(baseView, getActionBarLayoutParams(context));
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
                ((Activity) context).getWindowManager().removeViewImmediate(baseView);
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
