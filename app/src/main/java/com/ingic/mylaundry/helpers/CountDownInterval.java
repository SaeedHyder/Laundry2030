package com.ingic.mylaundry.helpers;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;

import java.util.concurrent.TimeUnit;

/**
 * Created by adnanahmed on 8/29/2017.
 */

public abstract class CountDownInterval extends CountDownTimer {

    TextView txtTime;
    String min;
    String sec;
    private MainActivity activityRefrence;

    public CountDownInterval(TextView textView, long millisInFuture, long countDownInterval, final MainActivity activityRefrence) {
        super(millisInFuture, countDownInterval);
        this.txtTime = textView;
        this.activityRefrence = activityRefrence;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (txtTime != null)
            min = (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) + "");
        sec = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)) + "";

        if (min.length() == 1)
            min = "0" + min;

        if (sec.length() == 1)
            sec = "0" + sec;

        txtTime.setText(activityRefrence.getString(R.string.didn_t_get_a_code_yet_wait_for_3_43_seconds) + " " + min + ":" + sec + " " + activityRefrence.getString(R.string.second));
    }

}
