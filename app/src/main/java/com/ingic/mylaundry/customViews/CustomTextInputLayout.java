package com.ingic.mylaundry.customViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.mylaundry.R;


/**
 * Created by developer007.ingic on 12/22/2017.
 */

public class CustomTextInputLayout extends LinearLayout {


    TextView tvError;
    Context ctx;

    public CustomTextInputLayout(Context context) {
        this(context, null);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        this.setOrientation(VERTICAL);
        ctx = context;
    }


    public void setErrorEnabled(){
        this.tvError = new AppCompatTextView(ctx);
        this.tvError.setTextColor(ContextCompat.getColor(ctx, R.color.error_color));
        this.tvError.setTextSize(10.0f);
        this.tvError.setVisibility(INVISIBLE);
        this.tvError.setPadding(14,0,0,0);
        this.addView(this.tvError);
    }




    public void setError(@Nullable final CharSequence error) {
        this.tvError.setText(error);
        errorEnable(true);
    }


    public void setErrorMessage(@Nullable final CharSequence error) {
        this.tvError.setText(error);
    }

    public void errorEnable(boolean isErrorShow) {
        if(isErrorShow){
            tvError.setVisibility(VISIBLE);
        }else{
            tvError.setVisibility(INVISIBLE);
            tvError.setText("");
        }
    }



    public boolean isErrorEnable() {
        return tvError.getVisibility() == VISIBLE;
    }

}
