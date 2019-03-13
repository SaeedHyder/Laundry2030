package com.ingic.mylaundry.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.mylaundry.helpers.DialogFactory;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.ScheduleOrderModel;

import java.util.Locale;


/**
 * Created by developer.ingic on 11/6/2017.
 */

public abstract class DockActivity extends BaseActivity implements Utils.Utilinterface {

    public static final String KEY_FRAG_FIRST = "firstFrag";
    private static final String TAG = DockActivity.class.getSimpleName();
    public BasePreferenceHelper prefHelper;
    public BaseFragment baseFragment;

    public abstract int getDockFrameLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefHelper = new BasePreferenceHelper(this);
    }

    public void addDockableSupportFragment(BaseFragment frag, String tag) {

        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        transaction.replace(getDockFrameLayoutId(), frag, tag);
        transaction.addToBackStack(getSupportFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST : null).commit();// AllowingStateLoss();

    }

    public void addDockableSupportFragment(BaseFragment frag, boolean addtoBackstack) {
        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        transaction.replace(getDockFrameLayoutId(), frag);
        if (!frag.isAdded()) {
            if (addtoBackstack) {
                transaction.addToBackStack(getSupportFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST : null).commit();// AllowingStateLoss();
            } else {
                transaction.commit();
            }
        }
    }
    public void addDockableFragment(BaseFragment frag, boolean addtoBackstack) {
        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        transaction.add(getDockFrameLayoutId(), frag);
        if (!frag.isAdded()) {
            if (addtoBackstack) {
                transaction.addToBackStack(getSupportFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST : null).commit();// AllowingStateLoss();
            } else {
                transaction.commit();
            }
        }
    }
    public void replaceDockableSupportFragment(BaseFragment frag, boolean addtoBackstack) {
        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        transaction.replace(getDockFrameLayoutId(), frag);
        if (!frag.isAdded()) {
            if (addtoBackstack) {
                transaction.addToBackStack(getSupportFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST : null).commit();// AllowingStateLoss();
            } else {
                transaction.commit();
            }
        }
    }

    public void addDockableAnimationFragment(BaseFragment frag, int start, int end) {

        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(start, end);
        transaction.replace(getDockFrameLayoutId(), frag, frag.getClass().getSimpleName());
        transaction.addToBackStack(getSupportFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST : null).commit();// AllowingStateLoss();

    }


    public void realAddDockableSupportFragment(BaseFragment frag, String tag) {

        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(getDockFrameLayoutId(), frag, tag);

        transaction.addToBackStack(
                getSupportFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST
                        : null).commit();// AllowingStateLoss();

    }

    public void openFragment(BaseFragment frag, String tag) {
        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        transaction.add(getDockFrameLayoutId(), frag, tag);

        transaction.commit();// AllowingStateLoss();
    }
    public void addDockableSupportFragmentReplace(BaseFragment frag, String tag) {
        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        transaction.add(getDockFrameLayoutId(), frag, tag);

        transaction.addToBackStack(getSupportFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST : null).commit();// AllowingStateLoss();

    }

    public void openFragment1(Fragment frag, boolean isAddToBackStack) {
        //  CommonUtils.hideSoftKeyboard(this);
        FragmentManager fragment = getSupportFragmentManager();
        if (fragment != null &&
                fragment.findFragmentById(R.id.main_container) != null &&
                fragment.findFragmentById(R.id.main_container).getClass() != null &&
                frag.getClass() != null &&
                fragment.findFragmentById(R.id.main_container).getClass().getSimpleName().equals(frag.getClass().getSimpleName())) {
            //  closeDrawers();
        } else {
            if (!(frag.isAdded())) {
                FragmentTransaction transaction;
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
                transaction.replace(R.id.main_container, frag, frag.getClass().getSimpleName());
                if (isAddToBackStack)
                    transaction.addToBackStack(fragment.getClass().getSimpleName());
                transaction.commit();
            }
        }
    }




    public void addDockableSupportFragmentRemove(BaseFragment frag, String tag) {

        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(getDockFrameLayoutId(), frag, tag);

        transaction
                .addToBackStack(null).commit();// AllowingStateLoss();

    }

    public void addDockableSupportFragmentWithData(BaseFragment frag, String tag, Bundle args) {

        frag.setArguments(args);
        baseFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        transaction.replace(getDockFrameLayoutId(), frag, tag);

        transaction.addToBackStack(null).commit();// AllowingStateLoss();

    }

    public void addAndShowDialogFragment(DialogFragment dialog) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        dialog.show(transaction, "tag");

    }

    public void prepareAndShowDialog(DialogFragment frag, String TAG) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(TAG);

        if (prev != null)
            transaction.remove(prev);

        transaction.addToBackStack(null);

        frag.show(transaction, TAG);
    }

    public void onPageBack() {
        if (prefHelper.getCartData() != null && prefHelper.getCartData().getItems().size() > 0 && (getSupportFragmentManager().getBackStackEntryCount() == 2)) {
            cartClearDialog();
        } else
            super.onBackPressed();
    }

    public void onPageBackSignup() {

            super.onBackPressed();
    }


    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
           baseFragment.onCustomBackPressed();
            //super.onBackPressed();
        } else {

            //if (baseFragment.getClass().getName() != null && !baseFragment.getClass().getName().equals(SignInFragment.class.getName())) {

            DialogFactory.createQuitDialog(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }, R.string.message_quit).show();

//            } else {
//                finish();
//            }
        }
    }

    public void cartClearDialog() {
        Utils.alertDialog(getString(R.string.are_you_exit_cart), this, this, getString(R.string.yes), getString(R.string.no));
    }

    public BaseFragment getLastAddedSuppFragment() {
        return baseFragment;
    }

    public void emptyBackStack() {
        popBackStackTillEntry(0);
    }


    public void popFragment() {
        if (getSupportFragmentManager() == null)
            return;
        try {
            getSupportFragmentManager().popBackStack();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void popBackStackTillEntry(int entryIndex) {
        if (getSupportFragmentManager() == null)
            return;
        if (getSupportFragmentManager().getBackStackEntryCount() <= entryIndex)
            return;
        FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(
                entryIndex);
        if (entry != null) {
            getSupportFragmentManager().popBackStack(entry.getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

//    public void popBackStack() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
//
//            getSupportFragmentManager().popBackStack();
//        } else {
//
//
//            DialogFactory.createQuitDialog(this, new
//                    DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//                    }, R.string.message_quit).show();
//
//        }
//
//    }

    public Fragment isFragmentPresent(String tag) {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(tag);
        if (frag instanceof Fragment) {
            return frag;
        } else
            return null;
    }

    @Override
    public void dialogPositive_Click(DialogInterface dialog) {
        prefHelper.putCartData(null);
        this.onPageBack();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if(prefHelper.getBooleanPrefrence(BasePreferenceHelper.LANGUAGE)){
//            setLocale("ar");
//        }else{
//            setLocale("ar");
//        }
    }


//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    public void setLocale(String lang) {
//        Locale locale = new Locale(lang);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.setLocale(locale);
//        conf.locale = locale;
//        conf.setLayoutDirection(locale);
//        res.updateConfiguration(conf, dm);
//        activity.finish();
//        Intent refresh = new Intent(activity, TelerMainActivity.class);
//        startActivity(refresh);


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(locale);
        conf.locale = locale;
        res.updateConfiguration(conf, dm);

        Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
        finish();
        startActivity(refresh);
    }

//    public void changeLanguage(Context context, String language) {
//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
//        Configuration config = context.getResources().getConfiguration();
//        config.setLocale(locale);
//        context.createConfigurationContext(config);
//        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
//
//        Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(refresh);
//    }

}
