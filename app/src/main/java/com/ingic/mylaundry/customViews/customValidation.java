package com.ingic.mylaundry.customViews;

import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by khanubaid on 1/23/2018.
 */

public class customValidation {

    public static boolean validateEmail(EditText editText, CustomTextInputLayout textInputLayout, String error) {
        String email = editText.getText().toString().trim();
        textInputLayout.errorEnable(false);
        if (email.isEmpty() || !isValidEmail(email)) {
            textInputLayout.setError(error);
//            Animation.animation(Techniques.RubberBand,300,0,textInputLayout);
            textInputLayout.requestFocus();
//            btnLogin.setAlpha(0.5f);
            return false;
        } else {

            textInputLayout.setError("");

        }

        return true;
    }

    public static boolean isValidNumber(String etMobileNumber, CustomTextInputLayout mobileInputLayout, String s1) {
        String emailPattern = "/^(\\+[1-9][0-9]*([0-9]*[0−9]∗|-[0-9]*-))?[0]?[1-9][0-9\\- ]*$/";
        CharSequence inputStr = etMobileNumber;
        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            return true;
        }
        mobileInputLayout.setError(s1);
        mobileInputLayout.requestFocus();
        return false;
    }

    public static boolean validatePassword(EditText txtPassword, CustomTextInputLayout textInputLayout, String error) {
        if (txtPassword.getText().toString().trim().isEmpty() && txtPassword.getText().toString().trim().length() < 8) {
            textInputLayout.setError(error);
//            Animation.animation(Techniques.RubberBand,300,0,textInputLayout);
            textInputLayout.requestFocus();
//            btnLogin.setAlpha(0.5f);
            return false;
        } else {
            textInputLayout.setError("");
        }

        return true;
    }

    public static boolean isValidEditText(String text, CustomTextInputLayout textInputLayout, String error) {
        String emailPattern = "^(?=\\s*\\S).*$";
        CharSequence inputStr = text;
        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            textInputLayout.setError("");
            return true;
        }
        textInputLayout.requestFocus();
        textInputLayout.setError(error);
        //  Animation.animation(Techniques.RubberBand,300,0,textInputLayout);

        return false;
    }

    public static boolean isValidNumericField(EditText text, CustomTextInputLayout textInputLayout, String error) {
        String emailPattern = "^[0-9]$";
        CharSequence inputStr = text.getText().toString();
        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            textInputLayout.errorEnable(false);
            return true;
        }
        textInputLayout.requestFocus();
        textInputLayout.setErrorEnabled();
        textInputLayout.setError(error);
        //  Animation.animation(Techniques.RubberBand,300,0,textInputLayout);

        return false;
    }

    public static boolean isValidPassword(EditText text, EditText conformpass, CustomTextInputLayout textInputLayout, String error) {
        if (text.getText().toString().equals(conformpass.getText().toString())) {
            textInputLayout.setError("");
            return true;
        }
        textInputLayout.setError(error);
        //  Animation.animation(Techniques.RubberBand,300,0,textInputLayout);

        return false;
    }

    public static boolean validateLength(EditText editText, final CustomTextInputLayout textInputLayout, String error, String min, String max) {

        if (editText.getText().toString().trim().length() >= Integer.parseInt(min) && editText.getText().toString().length() <= Integer.parseInt(max)) {
            textInputLayout.setError("");
            return true;
        }
        textInputLayout.requestFocus();
        textInputLayout.setError(error);
        return false;
    }

    public static boolean validateLength(TextView editText, final CustomTextInputLayout textInputLayout, String error, String min, String max) {

        if (editText.getText().toString().length() >= Integer.parseInt(min) && editText.getText().toString().length() <= Integer.parseInt(max)) {
            textInputLayout.setError("");
            return true;
        }
        textInputLayout.requestFocus();
        textInputLayout.setError(error);
        return false;
    }

    public static boolean validateLength(String editText, final CustomTextInputLayout textInputLayout, String error, String min, String max) {

        if (editText.length() >= Integer.parseInt(min) && editText.length() <= Integer.parseInt(max)) {
            textInputLayout.setError("");
            return true;
        }
        textInputLayout.requestFocus();
        textInputLayout.setError(error);
        return false;
    }

    public static boolean validateTextViewLength(TextView textView, final CustomTextInputLayout textInputLayout, String error, String min, String max) {
        if (textView.getText().length() >= Integer.parseInt(min) && textView.getText().length() <= Integer.parseInt(max)) {
            textInputLayout.setError("");
            return true;
        }
        textInputLayout.requestFocus();
        textInputLayout.setError(error);

        return false;
    }

    public static boolean isValidEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }
}
