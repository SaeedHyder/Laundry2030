package com.ingic.mylaundry.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.EditText;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by khanubaid on 4/6/2018.
 */

public class TextUtility {

    private static com.ingic.mylaundry.helpers.TextUtility textUtilities;

    public static com.ingic.mylaundry.helpers.TextUtility getInstance() {
        if (textUtilities == null)
            textUtilities = new com.ingic.mylaundry.helpers.TextUtility();
        return textUtilities;
    }

    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {

        }
    }

    public static Typeface setPoppinsBold(Context context){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Poppins-SemiBold.ttf");
        return typeface;
    }

    public static Typeface setPoppinsSemiBold(Context context){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Poppins-Medium.ttf");
        return typeface;
    }

    public static Typeface setPoppinsRegular(Context context){
        return Typeface.createFromAsset(context.getAssets(),"fonts/Poppins-Regular.ttf");
    }

    public static Typeface setPoppinsLight(Context context){
        return Typeface.createFromAsset(context.getAssets(),"fonts/Poppins-Light.ttf");
    }
}
