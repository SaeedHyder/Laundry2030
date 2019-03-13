package com.ingic.mylaundry.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by khanubaid on 1/16/2018.
 */

public class WalkthroughFragment extends BaseFragment {

    @BindView(R.id.tv_skip)
    TextView tvSkip;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.btn_next)
    Button btnNext;
    Unbinder unbinder;
    String key;
    ViewPager viewPager;
    @BindView(R.id.linerLayout_bg)
    LinearLayout linerLayoutBg;


    public static WalkthroughFragment newInstance(String key, ViewPager viewPager) {
        WalkthroughFragment fragment = new WalkthroughFragment();
        fragment.key = key;
        fragment.viewPager = viewPager;

        return fragment;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {

    }

    @Override
    public void onCustomBackPressed() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.walkthrough1, container, false);
        unbinder = ButterKnife.bind(this, view);
        setValue(key);
        setFonts();
        return view;

    }

    public void setFonts()
    {
        tvSkip.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(activityReference));
        tvDetail.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(activityReference));
        tvHeader.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsSemiBold(activityReference));
        btnNext.setTypeface(com.ingic.mylaundry.customViews.TextUtility.setPoppinsRegular(activityReference));
    }

    public void setValue(String key) {

        switch (key) {
            case "1":
                linerLayoutBg.setBackgroundDrawable(activityReference.getResources().getDrawable(R.drawable.bg));
                tvHeader.setText(activityReference.getString(R.string.welcome_to_laundry_app));
                tvDetail.setText(activityReference.getString(R.string.one_stop_destinantion_for_all_your_laundry_needs));
                break;
            case "2":
                linerLayoutBg.setBackgroundDrawable(activityReference.getResources().getDrawable(R.drawable.bg2));
                tvHeader.setText(activityReference.getString(R.string.fast_delivery_in_24_hours));
                tvDetail.setText(activityReference.getString(R.string.we_bring_your_fresh_clothes_the_very_next_day));
                break;
            case "3":
                linerLayoutBg.setBackgroundDrawable(activityReference.getResources().getDrawable(R.drawable.bg3));
                tvHeader.setText(activityReference.getString(R.string.delivery_service));
                tvDetail.setText(activityReference.getString(R.string.we_collect_your_dirty_clothes_from_your_doorsteps));
                btnNext.setText(activityReference.getString(R.string.done));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_skip, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                activityReference.emptyBackStack();
                activityReference.initFragments(new LoginFragment());
                break;
            case R.id.btn_next:
                switch (key) {
                    case "1":
                        viewPager.setCurrentItem(1);
                        break;
                    case "2":
                        viewPager.setCurrentItem(2);
                        break;
                    case "3":
                        activityReference.emptyBackStack();
                        activityReference.initFragments(new LoginFragment());
                        break;
                }
                break;
        }
    }
}
