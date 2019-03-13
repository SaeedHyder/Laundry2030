package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FeedbackSuggestionFragment extends BaseFragment {


    @BindView(R.id.btn_done)
    Button btnDone;
    Unbinder unbinder;
    @BindView(R.id.et_feedback)
    EditText etFeedback;
    View view;
    TitleBar titleBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feedback_suggestion, container, false);
        unbinder = ButterKnife.bind(this, view);
        setFonts();
        titleBar = activityReference.getTitleBar();
        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.setTitle(activityReference.getResources().getString(R.string.feedback_suggestion));
        titleBar.showHeader(true);
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
    }

    public void setFonts() {
        etFeedback.setTypeface(TextUtility.setPoppinsLight(activityReference));
        btnDone.setTypeface(TextUtility.setPoppinsRegular(activityReference));
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

    private void updateFeedback() {
        loadingStarted();
        WebApiRequest.getInstance(activityReference, true).FeedBack(
                preferenceHelper.getUser().getId(),
                etFeedback.getText().toString(),
                new WebApiRequest.APIRequestDataCallBack() {
            @Override
            public void onSuccess(Api_Response response) {
                Utils.showToast(activityReference, getString(R.string.msg_sent));
                activityReference.onPageBack();
                loadingFinished();
            }

            @Override
            public void onError() {
                loadingFinished();
            }

            @Override
            public void onNoNetwork() {
                loadingFinished();
            }
        });
    }

    @OnClick(R.id.btn_done)
    public void onViewClicked() {
        if (!Utils.isEmptyOrNull(etFeedback.getText().toString()))
            updateFeedback();
        else
            Utils.showSnackBar(activityReference, view, getString(R.string.enter_detail_first), ContextCompat.getColor(activityReference, R.color.color_button));
    }
}
