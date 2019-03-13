package com.ingic.mylaundry.helpers;

import android.content.Context;
import android.graphics.Typeface;

public class FontUtils {

    ////////////////////RALEWAY FONTS//////////////////////////////////////

    public static String RALEWAY_BLACK = "fonts/Raleway_Black.ttf";
    public static String RALEWAY_BLACK_ITALIC = "fonts/Raleway_BlackItalic.ttf";
    public static String RALEWAY_BOLD = "fonts/Raleway_Bold.ttf";
    public static String RALEWAY_BOLD_ITALIC = "fonts/Raleway_BoldItalic.ttf";
    public static String RALEWAY_EXTRA_BOLD = "fonts/Raleway_ExtraBold.ttf";
    public static String RALEWAY_EXTRA_BOLD_ITALIC = "fonts/Raleway_ExtraBoldItalic.ttf";
    public static String RALEWAY_EXTRA_LIGHT = "fonts/Raleway_ExtraLight.ttf";
    public static String RALEWAY_EXTRA_LIGHT_ITALIC = "fonts/Raleway_ExtraLightItalic.ttf";
    public static String RALEWAY_ITALIC = "fonts/Raleway_Italic.ttf";
    public static String RALEWAY_LIGHT = "fonts/Raleway_Light.ttf";
    public static String RALEWAY_LIGHT_ITALIC = "fonts/Raleway_LightItalic.ttf";
    public static String RALEWAY_MEDIUM = "fonts/Raleway_Medium.ttf";
    public static String RALEWAY_MEDIUM_ITALIC = "fonts/Raleway_MediumItalic.ttf";
    public static String RALEWAY_REGULAR = "fonts/Raleway_Regular.ttf";
    public static String RALEWAY_SEMI_BOLD = "fonts/Raleway_SemiBold.ttf";
    public static String RALEWAY_SEMI_BOLD_ITLAIC = "fonts/Raleway_SemiBoldItalic.ttf";
    public static String RALEWAY_THIN = "fonts/Raleway_Thin.ttf";
    public static String RALEWAY_THIN_ITALIC = "fonts/Raleway_ThinItalic.ttf";
    public static String ROBOTO_REGULAR = "fonts/Roboto_Regular.ttf";



    public static Typeface typefaceRaleway_Black = null;
    public static Typeface typefaceRaleway_BlackItalic = null;
    public static Typeface typefaceRaleway_Bold = null;
    public static Typeface typefaceRaleway_BoldItalic = null;
    public static Typeface typefaceRaleway_ExtraBold = null;
    public static Typeface typefaceRaleway_ExtraBoldItalic = null;
    public static Typeface typefaceRaleway_ExtraLight = null;
    public static Typeface typefaceRaleway_ExtraLightItalic = null;
    public static Typeface typefaceRaleway_Italic = null;
    public static Typeface typefaceRaleway_Light = null;
    public static Typeface typefaceRaleway_LightItalic = null;
    public static Typeface typefaceRaleway_Medium = null;
    public static Typeface typefaceRaleway_MediumItalic= null;
    public static Typeface typefaceRaleway_Regular = null;
    public static Typeface typefaceRaleway_SemiBold = null;
    public static Typeface typefaceRaleway_SemiBoldItalic = null;
    public static Typeface typefaceRaleway_Thin = null;
    public static Typeface typefaceRaleway_ThinItalic = null;
    public static Typeface typefaceRobotoRegular = null;

    public static Typeface setTypefaceRaleway_SemiBoldItalic(Context ctx) {
        if(typefaceRaleway_SemiBoldItalic == null){
            typefaceRaleway_SemiBoldItalic = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_SEMI_BOLD_ITLAIC);
            return typefaceRaleway_SemiBoldItalic;
        }
        return typefaceRaleway_SemiBoldItalic;
    }
    public static Typeface setTypefaceRaleway_Black(Context ctx) {
        if(typefaceRaleway_Black == null){
            typefaceRaleway_Black = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_BLACK);
            return typefaceRaleway_Black;
        }
        return typefaceRaleway_Black;
    }
    public static Typeface setTypefaceRaleway_BlackItalic(Context ctx) {
        if(typefaceRaleway_BlackItalic == null){
            typefaceRaleway_BlackItalic = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_BLACK_ITALIC);
            return typefaceRaleway_BlackItalic;
        }
        return typefaceRaleway_BlackItalic;
    }
    public static Typeface setTypefaceRaleway_Bold(Context ctx) {
        if(typefaceRaleway_Bold == null){
            typefaceRaleway_Bold = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_BOLD);
            return typefaceRaleway_Bold;
        }
        return typefaceRaleway_Bold;
    }
    public static Typeface setTypefaceRaleway_BoldItalic(Context ctx) {
        if(typefaceRaleway_BoldItalic == null){
            typefaceRaleway_BoldItalic = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_BOLD_ITALIC);
            return typefaceRaleway_BoldItalic;
        }
        return typefaceRaleway_BoldItalic;
    }
    public static Typeface setTypefaceRaleway_ExtraBold(Context ctx) {
        if(typefaceRaleway_ExtraBold == null){
            typefaceRaleway_ExtraBold = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_EXTRA_BOLD);
            return typefaceRaleway_ExtraBold;
        }
        return typefaceRaleway_ExtraBold;
    }
    public static Typeface setTypefaceRaleway_ExtraBoldItalic(Context ctx) {
        if(typefaceRaleway_ExtraBoldItalic == null){
            typefaceRaleway_ExtraBoldItalic = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_EXTRA_BOLD_ITALIC);
            return typefaceRaleway_ExtraBoldItalic;
        }
        return typefaceRaleway_ExtraBoldItalic;
    }
    public static Typeface setTypefaceRaleway_ExtraLight(Context ctx) {
        if(typefaceRaleway_ExtraLight == null){
            typefaceRaleway_ExtraLight = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_EXTRA_LIGHT);
            return typefaceRaleway_ExtraLight;
        }
        return typefaceRaleway_ExtraLight;
    }
    public static Typeface setTypefaceRaleway_ExtraLightItalic(Context ctx) {
        if(typefaceRaleway_ExtraLightItalic == null){
            typefaceRaleway_ExtraLightItalic = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_EXTRA_LIGHT_ITALIC);
            return typefaceRaleway_ExtraLightItalic;
        }
        return typefaceRaleway_ExtraLightItalic;
    }
    public static Typeface setTypefaceRaleway_Italic(Context ctx) {
        if(typefaceRaleway_Italic == null){
            typefaceRaleway_Italic = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_ITALIC);
            return typefaceRaleway_Italic;
        }
        return typefaceRaleway_Italic;
    }
    public static Typeface setTypefaceRaleway_Light(Context ctx) {
        if(typefaceRaleway_Light == null){
            typefaceRaleway_Light = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_LIGHT);
            return typefaceRaleway_Light;
        }
        return typefaceRaleway_Light;
    }
    public static Typeface setTypefaceRaleway_LightItalic(Context ctx) {
        if(typefaceRaleway_LightItalic == null){
            typefaceRaleway_LightItalic = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_LIGHT_ITALIC);
            return typefaceRaleway_LightItalic;
        }
        return typefaceRaleway_LightItalic;
    }
    public static Typeface setTypefaceRaleway_Medium(Context ctx) {
        if(typefaceRaleway_Medium == null){
            typefaceRaleway_Medium = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_MEDIUM);
            return typefaceRaleway_Medium;
        }
        return typefaceRaleway_Medium;
    }
    public static Typeface setTypefaceRaleway_MediumItalic(Context ctx) {
        if(typefaceRaleway_MediumItalic == null){
            typefaceRaleway_MediumItalic = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_MEDIUM_ITALIC);
            return typefaceRaleway_MediumItalic;
        }
        return typefaceRaleway_MediumItalic;
    }
    public static Typeface setTypefaceRaleway_Regular(Context ctx) {
        if(typefaceRaleway_Regular == null){
            typefaceRaleway_Regular = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_REGULAR);
            return typefaceRaleway_Regular;
        }
        return typefaceRaleway_Regular;
    }
    public static Typeface setTypefaceRaleway_SemiBold(Context ctx) {
        if(typefaceRaleway_SemiBold == null){
            typefaceRaleway_SemiBold = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_SEMI_BOLD);
            return typefaceRaleway_SemiBold;
        }
        return typefaceRaleway_SemiBold;
    }
    public static Typeface setTypefaceRaleway_Thin(Context ctx) {
        if(typefaceRaleway_Thin == null){
            typefaceRaleway_Thin = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_THIN);
            return typefaceRaleway_Thin;
        }
        return typefaceRaleway_Thin;
    }
    public static Typeface setTypefaceRaleway_ThinItalic(Context ctx) {
        if(typefaceRaleway_ThinItalic == null){
            typefaceRaleway_ThinItalic = Typeface.createFromAsset(ctx.getAssets(), RALEWAY_THIN_ITALIC);
            return typefaceRaleway_ThinItalic;
        }
        return typefaceRaleway_ThinItalic;
    }
    public static Typeface setTypefaceRobotoRegular(Context ctx) {
        if(typefaceRobotoRegular == null){
            typefaceRobotoRegular = Typeface.createFromAsset(ctx.getAssets(), ROBOTO_REGULAR);
            return typefaceRobotoRegular;
        }
        return typefaceRobotoRegular;
    }
}
