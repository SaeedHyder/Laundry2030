package com.ingic.mylaundry.ui;


import com.mukesh.countrypicker.CountryPicker;

public class CountryPickerDialog extends CountryPicker {
    CountryPicker picker;
    OnDestroyListener listener;

    public CountryPickerDialog(String title, OnDestroyListener listener){
        this.picker = newInstance(title);
        this.listener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listener.onDestroy();
    }

    public interface OnDestroyListener{
        void onDestroy();
    }
}
