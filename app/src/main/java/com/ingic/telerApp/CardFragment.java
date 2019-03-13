package com.ingic.telerApp;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ingic.mylaundry.R;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class CardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.card_fragment,container,false);
        SharedPreferences sharedPref =  getActivity().getApplicationContext().getSharedPreferences("telr", Context.MODE_PRIVATE);
        if(sharedPref.getString("ref", null) == null){
            //setTransactionDetails("04003004555", "4567");
        }else {
            TextView tv = view.findViewById(R.id.tvCard);
            Map<String, String> map = getTransactionDetails();
            tv.setText("**** **** **** " + map.get("last4"));
        }
        return view;
    }

    private Map<String,String> getTransactionDetails(){
        Map<String, String> map = new HashMap<>();
        SharedPreferences sharedPref =  getActivity().getApplicationContext().getSharedPreferences("telr", Context.MODE_PRIVATE);
        map.put("ref",sharedPref.getString("ref",null));
        map.put("last4",sharedPref.getString("last4",null));
        return map;
    }

    public void sendMessage2(View view){
        ((TelerActivity)getActivity()).sendMessage2(null);
    }

}
