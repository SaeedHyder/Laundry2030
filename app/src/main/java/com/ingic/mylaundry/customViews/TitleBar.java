package com.ingic.mylaundry.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.helpers.BasePreferenceHelper;

public class TitleBar extends RelativeLayout {

    private RelativeLayout contTitleBar, relativeCount, relativeNotiCount;
    private TextView txtTitle, txtCartCount, txtNotiCount, txtArabic;
    private ImageView backBtn, notiBtn, cartBtn, editProfileBtn, logoImage, imageView_add;
    private Context context;

    public TitleBar(Context context) {
        super(context);
        this.context = context;
        initLayout(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
        if (attrs != null)
            initAttrs(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLayout(context);
        if (attrs != null)
            initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
    }

    private void initLayout(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.titlebar, this);

        bindViews();

    }

    public void resetViews() {

        contTitleBar.setVisibility(GONE);
        notiBtn.setVisibility(GONE);
        txtTitle.setVisibility(GONE);
//        txtCartCount.setVisibility(GONE);
        relativeCount.setVisibility(GONE);
        relativeNotiCount.setVisibility(GONE);
        editProfileBtn.setVisibility(GONE);
        backBtn.setVisibility(GONE);
        logoImage.setVisibility(GONE);
        txtArabic.setVisibility(GONE);
        imageView_add.setVisibility(GONE);

        backBtn.setOnClickListener(null);
        notiBtn.setOnClickListener(null);
        cartBtn.setOnClickListener(null);
        editProfileBtn.setOnClickListener(null);
        imageView_add.setOnClickListener(null);
        txtCartCount.setOnClickListener(null);
        txtArabic.setOnClickListener(null);
        txtNotiCount.setOnClickListener(null);
    }

    private void bindViews() {

        contTitleBar = this.findViewById(R.id.header_layout);
        relativeCount = this.findViewById(R.id.relativeCount);
        relativeNotiCount = this.findViewById(R.id.relativeNotiCount);
        txtTitle = this.findViewById(R.id.mTitleText);
        txtCartCount = this.findViewById(R.id.txtCartCount);
        txtNotiCount = this.findViewById(R.id.txtNotiCount);
        txtArabic = this.findViewById(R.id.txtArabic);
        backBtn = this.findViewById(R.id.backBtn);
        logoImage = this.findViewById(R.id.logoImage);
        notiBtn = this.findViewById(R.id.imageView_bell);
        cartBtn = this.findViewById(R.id.imageView_cart);
        editProfileBtn = this.findViewById(R.id.imageView_edit);
        imageView_add = this.findViewById(R.id.imageView_add);

    }

    public void setTitle(String title) {
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(title);
        txtTitle.setTypeface(TextUtility.setPoppinsRegular(getContext()));
    }

    public void setCartCount(int count) {
        if (count > 0) {
            txtCartCount.setVisibility(VISIBLE);
            txtCartCount.setText(count + "");
        }else {
            txtCartCount.setVisibility(GONE);
        }
    }

    public void setNotificationCount(int count) {
        if (count > 0) {
            txtNotiCount.setVisibility(VISIBLE);
            txtNotiCount.setText(count + "");
        } else {
            txtNotiCount.setVisibility(GONE);
        }
    }

//    public void setSearchText(String text){
//        editText_search.setText(text);
//    }
//
//    public EditText getSearchBar() {
//        return editText_search;
//    }

    public void showHeader(boolean isShow) {
        if (isShow) {
            this.setVisibility(View.VISIBLE);
            contTitleBar.setVisibility(View.VISIBLE);

        } else {
            this.setVisibility(View.GONE);
            contTitleBar.setVisibility(View.GONE);

        }
    }

//    public EditText showSearchHeader(boolean isShow) {
//        if (isShow) {
//            this.setVisibility(View.VISIBLE);
//
//            searchHeader_layout.setVisibility(View.VISIBLE);
//            return editText_search;
//        } else {
//            this.setVisibility(View.GONE);
//
//            searchHeader_layout.setVisibility(View.GONE);
//        }
//        return editText_search;
//    }

    public void showHomeLogo() {
        logoImage.setVisibility(VISIBLE);

    }

    public void hideHomeLogo() {
        logoImage.setVisibility(GONE);

    }

//    public void showCount() {
//        txtCartCount.setVisibility(VISIBLE);
//    }

//    public void hideCount() {
//        txtCartCount.setVisibility(GONE);
//    }

    public void showNotificationButtonAndBindClickListener(OnClickListener onClickListener) {
        relativeNotiCount.setVisibility(VISIBLE);
        notiBtn.setVisibility(View.VISIBLE);
        notiBtn.setOnClickListener(onClickListener);
    }

    public void showAddButtonAndBindClickListener(OnClickListener onClickListener) {
        imageView_add.setVisibility(View.VISIBLE);
        imageView_add.setOnClickListener(onClickListener);
    }

    public TextView showArabicTextBindClickListener(final MainActivity mainActivity) {
        if (mainActivity.prefHelper.getBooleanPrefrence(BasePreferenceHelper.LANGUAGE))
            txtArabic.setText(R.string.english);
        else
            txtArabic.setText(R.string.arabic);
        
        txtArabic.setVisibility(VISIBLE);
        txtArabic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtArabic.getText().equals(mainActivity.getString(R.string.english))) {
                    mainActivity.prefHelper.setBooleanPrefrence(BasePreferenceHelper.LANGUAGE, false);
                    txtArabic.setText(R.string.arabic);
                    mainActivity.setLocale("en");
                } else {
                    mainActivity.prefHelper.setBooleanPrefrence(BasePreferenceHelper.LANGUAGE, true);
                    txtArabic.setText(R.string.english);
                    mainActivity.setLocale("ar");
                }
            }
        });
        return txtArabic;
    }

    public void setLanguage(String lang) {
        txtArabic.setText(lang);
    }

    //    public void getLanguage()
//    {
//        txtArabic.getText();
//    }
    public void showCartButtonAndBindClickListener(OnClickListener onClickListener) {
        relativeCount.setVisibility(View.VISIBLE);
        cartBtn.setOnClickListener(onClickListener);
    }

    public void showEditProfileButtonAndBindClickListener(OnClickListener onClickListener) {
        editProfileBtn.setVisibility(View.VISIBLE);
        editProfileBtn.setOnClickListener(onClickListener);
    }

    public void showBackButtonAndBindClickListener(OnClickListener onClickListener) {
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(onClickListener);
    }
}
