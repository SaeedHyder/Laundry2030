package com.ingic.mylaundry.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.mylaundry.helpers.DialogFactory;
import com.ingic.mylaundry.helpers.JsonHelpers;
import com.ingic.mylaundry.models.GetContact;
import com.ingic.mylaundry.webhelpers.WebApiRequest;
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ContactUsFragment extends BaseFragment {

    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_infoEmail)
    TextView tvInfoEmail;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_reportAnIssue)
    TextView tvReportAnIssue;
    Unbinder unbinder;
    int phone;
    String email;
    String number;
    String Email;
    @BindView(R.id.tv_contactusTitle)
    TextView tvContactusTitle;
    TitleBar titleBar;
    String[] values;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        getContact();
        setFonts();
        guestUser();
        tvContact.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvContact.setSelected(true);
        titleBar = activityReference.getTitleBar();
        arabicContent();
        return view;
    }

    public void setFonts() {
        tvContactusTitle.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvContact.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvInfoEmail.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvFeedback.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvReportAnIssue.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.setTitle(activityReference.getResources().getString(R.string.contact_us));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_feedback, R.id.tv_reportAnIssue, R.id.tv_contact, R.id.tv_infoEmail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_feedback:
                activityReference.initFragments(new FeedbackSuggestionFragment());
                break;
            case R.id.tv_reportAnIssue:
                activityReference.initFragments(new ReportAnIssueFragment());
                break;
            case R.id.tv_contact:
                if (number != null) {
//                    call(number);


                    DialogFactory.listDialog(activityReference, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            call(values[i]);


                        }
                    }, "Contact list", values);

                }

                break;
            case R.id.tv_infoEmail:
                if (Email != null) {
                    email();
                }
                break;
        }
    }

    private void call(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    public void getContact() {
        loadingStarted();
        WebApiRequest.getInstance(activityReference, true).getContact(

                phone,
                email,

                new WebApiRequest.APIRequestDataCallBack() {
                    @Override
                    public void onSuccess(final Api_Response response) {

                        loadingFinished();
                        GetContact getContact = (GetContact) JsonHelpers.convertToModelClass(response.getResult(), GetContact.class);

                        number = getContact.getPhone();


                        if (number != null) {
                            if (number.contains(",")) {
                                values = number.split(",");
                                if (values.length > 1) {
                                    tvContact.setText(number.toString());

                                } else {
                                    tvContact.setText(values[0].toString());

                                }
                            } else {
                                tvContact.setText(number);
                            }
                        } else {
                            tvContact.setText("Phone Number");

                        }


                        //  if(tvContact!=null)
                        //   tvContact.setText(number);
                        Email = getContact.getEmail().trim();
                        if (tvInfoEmail != null)
                            tvInfoEmail.setText(Email);

                    }

                    @Override
                    public void onError() {
                        loadingFinished();
                        //  hideView();
                    }

                    @Override
                    public void onNoNetwork() {
                        loadingFinished();

                        //   hideView();
                    }
                }
        );
    }

    private void email() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", Email, null));
        startActivity(Intent.createChooser(emailIntent, "Email"));


    }

    private void guestUser() {

        if (preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.GUEST_USER)) {
            tvFeedback.setOnClickListener(guestListner());
            tvReportAnIssue.setOnClickListener(guestListner());
        }
    }

    private void arabicContent() {
        if (preferenceHelper != null)
            if (preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.LANGUAGE)) {
                tvInfoEmail.setGravity(Gravity.RIGHT);
            } else {
                tvInfoEmail.setGravity(Gravity.LEFT);
            }
    }

}
