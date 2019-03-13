package com.ingic.mylaundry.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Utils {
    public static final String SPECIAL_CHARACTERS = "!@#$%^&*()~`-=_+[]{}|:\";',./<>?";
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 20;

    public static boolean isEmptyOrNull(String string) {
        if (string == null)
            return true;

        return (string.trim().length() <= 0);
    }

    public static void showToast(Context ctx, String text) {
        Toast.makeText(ctx, text + "", Toast.LENGTH_SHORT).show();
    }

    public static void showToastInCenter(Context ctx, String message) {

        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void setImageWithGlide(final Context ctx, final ImageView imageView, String url) {
//if (!Utils.isEmptyOrNull(url)) {

        Glide.with(ctx)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.person_placeholder).error(R.drawable.person_placeholder))
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                        Log.d("onError", ctx.getResources().getString(R.string.glide_image_error));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }


                }).into(imageView);


//}
    }

    public static void setImageServiceGlide(final Context ctx, final ImageView imageView, String url) {
//if (!Utils.isEmptyOrNull(url)) {

        Glide.with(ctx)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.noimage).error(R.drawable.noimage))
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                        Log.d("onError", ctx.getResources().getString(R.string.glide_image_error));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }


                }).into(imageView);


//}
    }

    public static void setEventImageWithGlide(final Context ctx, final ImageView imageView, String url) {
//if (!Utils.isEmptyOrNull(url)) {

        Glide.with(ctx)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.noimage).error(R.drawable.noimage))
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(GlideException e, Object o, Target<Drawable> target, boolean b) {
                        Log.d("onError", ctx.getResources().getString(R.string.glide_image_error));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }

                }).into(imageView);


//}
    }

//    public static void setImageFromGlide(Context ctx, final ImageView imageView, final ProgressBar progressBar, String url) {
//
//        if (!Utils.isEmptyOrNull(url)) {
//            Glide.with(ctx).load(url).listener(new RequestListener<Drawable>() {
//                @Override
//                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                    Log.d("onError", "onError");
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                    imageView.setVisibility(View.VISIBLE);
//                    if (progressBar != null)
//                        progressBar.setVisibility(View.GONE);
//                    return false;
//                }
//            }).into(imageView);
//        }
//    }

    public static void hideKeyboard( Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    public static void showSnackBar(Context ctx, View v, String text, int color) {

        Snackbar snackbar;
        snackbar = Snackbar.make(v, text, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(color);
        snackbar.show();
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {

        //Setting the width and height of the Bitmap that will be returned                        //equal to the original Bitmap that needs round corners.
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //Creating canvas with output Bitmap for drawing
        Canvas canvas = new Canvas(output);

        //Setting paint and rectangles.
        final int color = Color.BLACK;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        //SetXfermode applies PorterDuffXfermode to paint.
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

//    public static boolean isEmptyValidate(Context ctx, View view, String hint, String text) {
//
//        if (text.isEmpty()) {
//            Utils.showSnackBar(ctx, view, hint + " required", ctx.getResources().getColor(R.color.grayColor));
//
//            /*editText.setError("Empty field");
//            editText.requestFocus();
//            */
//            return false;
//        } else {
//            return true;
//        }
//
//    }
//
//    public static boolean isValidEmail(Context ctx, View view, String email, EditText editText) {
//     /*   String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//
//        Pattern pattern;
//        pattern = Pattern.compile(EMAIL_PATTERN);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();*/
//        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//
//
//        CharSequence inputStr = email;
//
//        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(inputStr);
//
//        if (matcher.matches())
//            return true;
//        else
//            Utils.showSnackBar(ctx, view, "Email Not Valid", ctx.getResources().getColor(R.color.grayColor));
//
//          /*  editText.setError("Email Not Valid");
//        editText.requestFocus();
//*/
//        return false;
//    }

//    public static boolean isAcceptablePassword(Context ctx, View view, String password, EditText editText) {
//        if (TextUtils.isEmpty(password)) {
//            Utils.showSnackBar(ctx, view, "Please enter the password", ctx.getResources().getColor(R.color.grayColor));
//
//            //  editText.setError("Please Enter Password");
//            // editText.requestFocus();
//            return false;
//        }
//        password = password.trim();
//        int len = password.length();
//        if (len < MIN_PASSWORD_LENGTH || len > MAX_PASSWORD_LENGTH) {
//            //    Toast.makeText(getContext(), "wrong size, it must have at least 8 characters and less than 20.", Toast.LENGTH_SHORT).show();
//            Utils.showSnackBar(ctx, view, "Password size must have at least 8 characters and less than 20", ctx.getResources().getColor(R.color.grayColor));
//
//            //   editText.setError("Password size must have at least 8 characters and less than 20.");
//            // editText.requestFocus();
//            return false;
//        }
//        char[] aC = password.toCharArray();
//        for (char c : aC) {
//            if (Character.isUpperCase(c)) {
//               /* PassWord.setError("password In uppercase");
//                PassWord.requestFocus();*/
//                //Toast.makeText(getContext(), "password Is uppercase", Toast.LENGTH_SHORT).show();
//
//                //    System.out.println(c + " is uppercase.");
//
//            } else if (Character.isLowerCase(c)) {
//                //  System.out.println(c + " is lowercase.");
//              /*  PassWord.setError("password In lowercase");
//                PassWord.requestFocus();*/
//                // Toast.makeText(getContext(), "password Is lowercase", Toast.LENGTH_SHORT).show();
//            } else if (Character.isDigit(c)) {
//             /*   PassWord.setError("Password is digit.");
//                PassWord.requestFocus();*/
//                // System.out.println(c + " is digit.");
//            } else if (SPECIAL_CHARACTERS.indexOf(String.valueOf(c)) >= 0) {
//                ///    System.out.println(c + " is valid symbol.");
//              /*  editText.setError("password contains symbol");
//                editText.requestFocus();
//              */
//
//                Utils.showSnackBar(ctx, view, "Password contains symbol", ContextCompat.getColor(ctx, R.color.grayColor));
//
//                return false;
//                // Toast.makeText(getContext(), "password contains symbol", Toast.LENGTH_SHORT).show();
//            } else {
//                Utils.showSnackBar(ctx, view, "Invalid character in the password", ctx.getResources().getColor(R.color.grayColor));
//
//                /*   editText.setError("invalid character in the password");
//                editText.requestFocus();
//             */   //Toast.makeText(getContext(), "invalid character in the password", Toast.LENGTH_SHORT).show();
//                //   System.out.println(c + " is an invalid character in the password.");
//                return false;
//            }
//        }
//        return true;
//    }

    public static void setImageWithGlide(final Context ctx, final ImageView imageView, final ProgressBar progressBar, String url) {
        if (!Utils.isEmptyOrNull(url)) {
            Glide.with(ctx).load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                    Log.d("onError", ctx.getResources().getString(R.string.glide_image_error));
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                    imageView.setVisibility(View.VISIBLE);
                    if (progressBar != null)
                        progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(imageView);
        }
    }

//    public static void animation(View view, int duration, Techniques techniques, int times) {
//
//        YoYo.with(techniques)
//                .duration(duration)
//                .repeat(times)
//                .playOn(view);
//    }

    public static void alertDialog(String msg, final Utilinterface utilinterface, Activity mainActivity, String posBtnTxt, String ngtvBtnTxt) {

        final AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(mainActivity);

        builder
                .setMessage(msg)
                .setPositiveButton(posBtnTxt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        utilinterface.dialogPositive_Click(dialog);
                    }
                })
                .setNegativeButton(ngtvBtnTxt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();

                    }
                })
                .show();

    }

    public static void alertDialog(String msg, final Utilinterface utilinterface, Activity mainActivity, String posBtnTxt) {

        final AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(mainActivity);

        builder
                .setMessage(msg)
                .setPositiveButton(posBtnTxt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        utilinterface.dialogPositive_Click(dialog);
                    }
                })
                .show();

    }

    public static String formatDatewithoututc(String date, String givenFormat, String formatstring) {
        SimpleDateFormat format = new SimpleDateFormat(givenFormat,Locale.US);
        Date newDate = null;
        try {
            newDate = format.parse(date);


        format = new SimpleDateFormat(formatstring,Locale.US);
        return format.format(newDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return date;
        }
    }

    public interface Utilinterface {
        void dialogPositive_Click(DialogInterface dialog);
    }
}