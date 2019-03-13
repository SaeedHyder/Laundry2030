package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.adapters.MyOrderAdapter;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyOrderFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager_Order)
    ViewPager viewPagerOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        loadingStarted();

        viewPagerOrder.setAdapter(new MyOrderAdapter(getChildFragmentManager(), activityReference.getApplicationContext(),activityReference));
        tabLayout.setupWithViewPager(viewPagerOrder);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerOrder.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPagerOrder.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPagerOrder.setCurrentItem(tab.getPosition());
            }
        });

        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.setTitle(activityReference.getString(R.string.my_order));
        titleBar.showHeader(true);
        titleBar.hideHomeLogo();
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCustomBackPressed();
            }
        });
    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
