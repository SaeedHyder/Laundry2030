package com.ingic.mylaundry.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import com.ingic.mylaundry.Interface.CountryPickerListenerInterface;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.helpers.KeyboardHelper;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.ui.CountryPickerDialog;
import com.mukesh.countrypicker.CountryPickerListener;


/**
 * Created by developer.ingic on 11/6/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public <T> void changeActivity(Class<T> cls, boolean isActivityFinish) {
        Intent intent = new Intent(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
            intent.putExtras(bundle);

        startActivity(intent);
        if (isActivityFinish) {
            finish();
        }

    }

    public void showCountryPicker(final boolean isDialCodeShow, final CountryPickerListenerInterface countryPickerListener) {
        final CountryPickerDialog picker = new CountryPickerDialog("Select Country", new CountryPickerDialog.OnDestroyListener() {
            @Override
            public void onDestroy() {
                KeyboardHelper.hideSoftKeyboard(BaseActivity.this, BaseActivity.this
                        .getWindow().getDecorView());
            }
        });

        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                KeyboardHelper.hideSoftKeyboard(BaseActivity.this, BaseActivity.this
                        .getWindow().getDecorView());
                picker.dismiss();
                countryPickerListener.onCountrySelected(isDialCodeShow, name, code, dialCode, flagDrawableResID);
            }
        });
        picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");

    }





}