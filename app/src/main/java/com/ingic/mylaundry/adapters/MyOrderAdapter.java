package com.ingic.mylaundry.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.fragments.ActiveOrderFragment;
import com.ingic.mylaundry.fragments.CompletedOrderFragment;

/**
 * Created by khanubaid on 1/18/2018.
 */

public class MyOrderAdapter extends FragmentStatePagerAdapter {

    String[] fragments = new String[2];

    public MyOrderAdapter(FragmentManager supportFragmentManager, Context applicationContext, MainActivity activity) {
        super(supportFragmentManager);

        fragments[0] = activity.getString(R.string.active_order);
        fragments[1] = activity.getString(R.string.complete_order);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ActiveOrderFragment();

            case 1:
                return new CompletedOrderFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {


        return fragments[position];
    }
}
