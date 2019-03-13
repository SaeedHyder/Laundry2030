package com.ingic.mylaundry.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.adapters.WalkthroughAdapter;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;

public class WelcomeFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        unbinder = ButterKnife.bind(this, view);
        Utils.hideKeyboard(
                activityReference);
        setupViewPager(viewPager);
        loadingFinished();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityReference = (MainActivity) getActivity();

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.showHeader(false);

    }

    private void setupViewPager(ViewPager viewPager) {
        WalkthroughAdapter adapter = new WalkthroughAdapter(getChildFragmentManager());
        adapter.addFragment(WalkthroughFragment.newInstance("1",viewPager));
        adapter.addFragment(WalkthroughFragment.newInstance("2",viewPager));
        adapter.addFragment(WalkthroughFragment.newInstance("3",viewPager));

        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();


    }

}