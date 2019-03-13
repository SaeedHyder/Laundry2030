package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyCartFragment extends BaseFragment {

    @BindView(R.id.btn_startLaundry)
    Button btnStartLaundry;
    Unbinder unbinder;
    @BindView(R.id.txtCartTitle)
    TextView txtCartTitle;
    private TitleBar titleBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        unbinder = ButterKnife.bind(this, view);
        setFonts();
        titleBar = activityReference.getTitleBar();

        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        this.titleBar = titleBar;
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.setTitle(activityReference.getResources().getString(R.string.my_cart));
        titleBar.showHeader(true);
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    public void setFonts() {
        txtCartTitle.setTypeface(TextUtility.setPoppinsSemiBold(activityReference));
        btnStartLaundry.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.setTitle(activityReference.getString(R.string.services));
        titleBar.showCartButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferenceHelper.getCartData() != null && preferenceHelper.getCartData().getItems().size() > 0) {
                    CartFragment cartFragment = new CartFragment();
                    cartFragment.fromService(true);
                    activityReference.addFragments(cartFragment);
                } else {
                    activityReference.addFragments(new MyCartFragment());
                }
            }
        });
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
        unbinder.unbind();
    }

    @OnClick(R.id.btn_startLaundry)
    public void onViewClicked() {

        activityReference.initFragments(new ServiceFragment(), false);
        // activityReference.onPageBack();

    }
}
