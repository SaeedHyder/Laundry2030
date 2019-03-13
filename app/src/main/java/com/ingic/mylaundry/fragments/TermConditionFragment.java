package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.TermWrapper;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TermConditionFragment extends BaseFragment {

    @BindView(R.id.tv_term_Condition)
    TextView tvTermCondition;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_term_condition, container, false);
        unbinder = ButterKnife.bind(this, view);
        getTermsAndCondition(AppConstant.TERMS);
        return view;
    }
    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.setTitle(activityReference.getResources().getString(R.string.terms_conditions));
        titleBar.showHeader(true);
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
    }

    public void getTermsAndCondition( String type) {
        loadingStarted();
        WebApiRequest.getInstance(activityReference, true).TermAndCondition(
                type,
                //  offset,
                //searchBar.getText().toString(), size, from,

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {
                        loadingFinished();
                        TermWrapper termWrapper = (TermWrapper) JsonHelpers.convertToModelClass(response.getResult(), TermWrapper.class);

                        if (!Utils.isEmptyOrNull(termWrapper.getCrm().getBody())) {

                            tvTermCondition.setText(Html.fromHtml(termWrapper.getCrm().getBody()));
                        }

                    }

                    @Override
                    public void onError() {
                        loadingFinished();

                    }

                    @Override
                    public void onNoNetwork() {
                        loadingFinished();

                    }
                }
        );
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
