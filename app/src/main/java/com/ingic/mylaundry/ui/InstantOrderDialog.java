package com.ingic.mylaundry.ui;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InstantOrderDialog extends DialogFragment {

    @BindView(R.id.txt_instantOrder)
    TextView txtInstantOrder;
    MainActivity mainActivity;
    Unbinder unbinder;
    String instantText;

    public static InstantOrderDialog newInstance(MainActivity mainActivity, String instantText) {
        InstantOrderDialog fragment = new InstantOrderDialog();
        fragment.mainActivity = mainActivity;
        fragment.instantText = instantText;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.DialogTimeTheme);
        setCancelable(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instant_order_dialog2, container, false);
        unbinder = ButterKnife.bind(this, view);
        txtInstantOrder.setText(instantText);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
