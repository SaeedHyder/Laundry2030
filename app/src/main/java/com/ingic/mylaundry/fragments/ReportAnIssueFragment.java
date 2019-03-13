package com.ingic.mylaundry.fragments;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

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

public class ReportAnIssueFragment extends BaseFragment {

    @BindView(R.id.et_email)
    TextView etEmail;
    @BindView(R.id.radio_missingProducts)
    RadioButton radioMissingProducts;
    @BindView(R.id.radio_lateOrder)
    RadioButton radioLateOrder;
    @BindView(R.id.radio_payment)
    RadioButton radioPayment;
    @BindView(R.id.somethingElse)
    RadioButton somethingElse;
    @BindView(R.id.et_issue)
    EditText etIssue;
    @BindView(R.id.btn_done)
    Button btnDone;
    Unbinder unbinder;
    int missingProduct = 1;
    int lateOrder = 0;
    int paymentIssues = 0;
    int somethingelse = 0;
    @BindView(R.id.tv_reportAnIssueTitle)
    TextView tvReportAnIssueTitle;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_report_an_issue, container, false);
        unbinder = ButterKnife.bind(this, view);
        setFonts();
        if (preferenceHelper.getUser() != null && preferenceHelper.getUser().getEmail() != null)
            etEmail.setText(preferenceHelper.getUser().getEmail());
        return view;
    }

    public void setFonts() {
        etEmail.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvReportAnIssueTitle.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        radioMissingProducts.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        radioLateOrder.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        radioPayment.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        somethingElse.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        etIssue.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        btnDone.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        titleBar.setTitle(activityReference.getString(R.string.report_issue));
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void reportAnIssue() {
        loadingStarted();

        WebApiRequest.getInstance(activityReference, true).
                ReportAnIssues(preferenceHelper.getUser().getId(),
                        etIssue.getText().toString(), missingProduct, lateOrder, paymentIssues, somethingelse,
                        new WebApiRequest.APIRequestDataCallBack() {

                            @Override
                            public void onSuccess(Api_Response response) {

                                Utils.showToast(activityReference, getString(R.string.report_submitted));
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
        if (!Utils.isEmptyOrNull(etIssue.getText().toString())) {
            reportAnIssue();
        } else
            Utils.showSnackBar(activityReference, view, getString(R.string.please_write_some_issue), ContextCompat.getColor(activityReference, R.color.color_button));
    }

    @OnClick({R.id.radio_missingProducts, R.id.radio_lateOrder, R.id.radio_payment, R.id.somethingElse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_missingProducts:
                setValues(1, 0, 0, 0);
                break;
            case R.id.radio_lateOrder:
                setValues(0, 1, 0, 0);
                break;
            case R.id.radio_payment:
                setValues(0, 0, 1, 0);
                break;
            case R.id.somethingElse:
                setValues(0, 0, 0, 1);
                break;
        }
    }

    void setValues(int val1, int val2, int val3, int val4) {
        missingProduct = val1;
        lateOrder = val2;
        paymentIssues = val3;
        somethingelse = val4;
    }
}
