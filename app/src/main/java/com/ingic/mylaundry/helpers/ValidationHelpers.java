package com.ingic.mylaundry.helpers;

import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.mylaundry.customViews.CustomTextInputLayout;

import java.util.ArrayList;

/**
 * Created by developer007.ingic on 12/23/2017.
 */

public class ValidationHelpers {

    public static void setButtonTransparency(float transparency, Button btn){
        btn.setAlpha(transparency);
        if(transparency == 1.0f)
            btn.setEnabled(true);
        else
            btn.setEnabled(false);
    }

    public static void resetAllErrors(ViewGroup v) {
        ArrayList<CustomTextInputLayout> customInputLayout = traverseAllCustomInputLayout(v);
        for (int layoutViewIndex  = 0; layoutViewIndex  < customInputLayout.size(); layoutViewIndex ++) {
            CustomTextInputLayout customTextInputLayout = customInputLayout.get(layoutViewIndex);
            customTextInputLayout.errorEnable(false);
        }
    }

    public static  ArrayList<CustomTextInputLayout> traverseAllCustomInputLayout(ViewGroup v) {
        ArrayList<CustomTextInputLayout> list = new ArrayList<>();
        for (int i = 0; i < v.getChildCount(); i++) {
            Object child = v.getChildAt(i);
            if (child instanceof CustomTextInputLayout) {
                list.add(((CustomTextInputLayout) child));
            }
        }
        return list;
    }
}
