package com.ingic.mylaundry.fragments.baseClass;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.DockActivity;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.LoginFragment;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.mylaundry.helpers.KeyboardHelper;
import com.ingic.mylaundry.helpers.Utils;

public abstract class BaseFragment extends Fragment implements Utils.Utilinterface {

    public static final String TAG = BaseFragment.class.getSimpleName();
    public BasePreferenceHelper preferenceHelper;
    public DockActivity myDockActivity;
    public boolean isLoading = false;
    protected MainActivity activityReference;
    private Activity activity;
    private int orderCount;

    public abstract void onCustomBackPressed();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // if (getMainActivity()==null) {
        activityReference = getMainActivity();

//            Utils.hideKeyboard(activityReference);


        //  }
        setPreferenceHelper();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myDockActivity = getDockActivity();
        setPreferenceHelper();

    }

    @Override
    public void onResume() {
        super.onResume();
        // if (getMainActivity() == null) {
        activityReference = getMainActivity();

        // }
        setTitleBar(activityReference.getTitleBar());
        setPreferenceHelper();
    }

    public void setPreferenceHelper() {
        preferenceHelper = activityReference.prefHelper;
        if (preferenceHelper == null) {
            preferenceHelper = new BasePreferenceHelper(activityReference);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }


    @Override
    public void onPause() {


        if (getDockActivity() != null && getDockActivity().getWindow() != null)
            if (getDockActivity().getWindow().getDecorView() != null)
                KeyboardHelper.hideSoftKeyboard(getDockActivity(), getDockActivity()
                        .getWindow().getDecorView());
        super.onPause();
    }

    protected DockActivity getDockActivity() {
        DockActivity activity = (DockActivity) this.activity;
        return activity;

    }

    protected MainActivity getMainActivity() {
        return (MainActivity) activity;
    }

    protected TitleBar getTitleBar() {
        return getMainActivity().getTitleBar();
    }

    public abstract void setTitleBar(TitleBar titleBar);

    public void loadingStarted() {
        isLoading = true;
        getMainActivity().onLoadingStarted();
    }

    public void orderloadingStarted() {
        orderCount += 1;
        isLoading = true;
        getMainActivity().onLoadingStarted();
    }

    public void loadingFinished() {
        isLoading = false;
        if (getMainActivity() != null)
            getMainActivity().onLoadingFinished();

    }

    public void orderloadingFinished() {
        orderCount -= 1;
        if (orderCount == 0) {
            isLoading = false;
            if (getMainActivity() != null)
                getMainActivity().onLoadingFinished();
        }

    }

    public View.OnClickListener guestListner() {
        View.OnClickListener listner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.alertDialog(getString(R.string.loginGuest), BaseFragment.this, activityReference, getString(R.string.yes), getString(R.string.no));
            }
        };
        return listner;
    }

    //Guest Dialog
    @Override
    public void dialogPositive_Click(DialogInterface dialog) {
//        activityReference.emptyBackStack();
//        preferenceHelper.setBooleanPrefrence(BasePreferenceHelper.GUEST_USER,false);
//        preferenceHelper.setLoginStatus(false);
//        activityReference.initFragments(new LoginFragment());
        preferenceHelper.setBooleanPrefrence(BasePreferenceHelper.GUEST_USER, false);
        preferenceHelper.setLoginStatus(false);
        activityReference.popBackStackTillEntry(0);
        activityReference.replaceDockableSupportFragment(new LoginFragment(), true);
    }
}
