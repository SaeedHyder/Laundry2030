package com.ingic.mylaundry.fragments;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.customViews.TextUtility;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;
import com.ingic.mylaundry.helpers.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class MyProfileFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.image_camera)
    ImageView imageCamera;
    @BindView(R.id.tv_fullName)
    TextView tvFullName;
    @BindView(R.id.tv_emailAddress)
    TextView tvEmailAddress;
    @BindView(R.id.tv_Address)
    TextView tvAddress;
    @BindView(R.id.tv_mobileNumber)
    TextView tvMobileNumber;

    @Override
    public void setTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.resetViews();
        titleBar.showHeader(true);
        setFonts();
        titleBar.showEditProfileButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityReference.initFragments(new EditProfileFragment());
            }
        });
        titleBar.setTitle(activityReference.getString(R.string.my_profile));
        titleBar.showBackButtonAndBindClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomBackPressed();
            }
        });
    }

    public void setFonts() {
        tvFullName.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvEmailAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvAddress.setTypeface(TextUtility.setPoppinsRegular(activityReference));
        tvMobileNumber.setTypeface(TextUtility.setPoppinsRegular(activityReference));
    }

    @Override
    public void onCustomBackPressed() {
        activityReference.onPageBack();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        fillDetail();
        arabicContent();

        if (preferenceHelper.getUser() != null) {
            tvEmailAddress.setText(preferenceHelper.getUser().getEmail());

            Utils.setImageWithGlide(activityReference, profileImage, AppConstant.ServerAPICalls.BASE_URL + AppConstant.ServerAPICalls.API + "resize/" + preferenceHelper.getUser().getImage());
            tvFullName.setText(preferenceHelper.getUser().getName());

            if (preferenceHelper.getUser().getPhone() == null) {
                tvMobileNumber.setText("");
            } else {

                if (preferenceHelper.getUser().getPhone().contains("-")) {
                    String[] separated = preferenceHelper.getUser().getPhone().split("-");
                    String Code = separated[0];
                    String Number = separated[1];

                   // String phone =preferenceHelper.getUser().getPhone().trim().split("-")[1];
                    tvMobileNumber.setText(Code+"  "+Number.substring(0,3)+"-"+Number.substring(3,6)+"-"+Number.substring(6,Number.length()));

                  //  tvMobileNumber.setText(Code + Number);
                } else {
                    tvMobileNumber.setText(preferenceHelper.getUser().getPhone());
                }

            }

            if (preferenceHelper.getUser().getAddress() != null)
                tvAddress.setText(preferenceHelper.getUser().getAddress().replaceAll("\\|", ""));
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void fillDetail() {
        tvFullName.setText(preferenceHelper.getUser().getName());
        tvEmailAddress.setText(preferenceHelper.getUser().getEmail());
        tvAddress.setText(preferenceHelper.getUser().getAddress());
        String phone =preferenceHelper.getUser().getPhone().trim().split("-")[1];
        tvMobileNumber.setText(phone.substring(0,3)+"-"+phone.substring(3,6)+"-"+phone.substring(6,phone.length()));

    }

    public String format(String phone){
        String result[] = new String[]{};
        int lastSplit=4,split=3;
        result[0]=phone.substring(phone.length()-lastSplit,phone.length());
        result[1]=phone.substring(phone.length()-lastSplit-split,phone.length()-lastSplit);
        result[2]=phone.substring(phone.length()-lastSplit-2*split,phone.length()-lastSplit-split);
        result[3]=phone.substring(0,phone.length()-lastSplit-2*split);
        return result[3]+"-"+result[2]+"-"+result[1]+"-"+result[0];
    }

    //Arabic Alignment
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void arabicContent() {

            if (preferenceHelper.getBooleanPrefrence(BasePreferenceHelper.LANGUAGE)) {
                tvFullName.setTextDirection(View.TEXT_DIRECTION_RTL);
                tvMobileNumber.setTextDirection(View.TEXT_DIRECTION_LTR);
                tvMobileNumber.setGravity(Gravity.RIGHT);
                tvEmailAddress.setTextDirection(View.TEXT_DIRECTION_RTL);
                tvAddress.setTextDirection(View.TEXT_DIRECTION_RTL);
            }

    }
}
