package com.ingic.telerApp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.DockActivity;
import com.ingic.mylaundry.helpers.PreferenceHelper;
import com.ingic.mylaundry.helpers.Singleton;
import com.ingic.mylaundry.models.Order;
import com.ingic.mylaundry.models.OrderPostWraper;
import com.ingic.mylaundry.models.ScheduleOrderModel;
import com.telr.mobile.sdk.activty.WebviewActivity;
import com.telr.mobile.sdk.entity.request.payment.Address;
import com.telr.mobile.sdk.entity.request.payment.App;
import com.telr.mobile.sdk.entity.request.payment.Billing;
import com.telr.mobile.sdk.entity.request.payment.MobileRequest;
import com.telr.mobile.sdk.entity.request.payment.Name;
import com.telr.mobile.sdk.entity.request.payment.Tran;

import java.math.BigInteger;
import java.util.Random;

@Keep
public class TelerActivity extends DockActivity {

    private String amount = "3"; // Just for testing
    public static final String KEY = "pLDV3-hfsk#nZ82Z"; // TODO: Insert your Key here
    public static final String STORE_ID = "21187";    // TODO: Insert your Store ID here
    public static final String EMAIL = "test@test.com";     // TODO: Insert the customer email here
    public static final boolean isSecurityEnabled = false;      // Mark false to test on simulator, True to test on actual device and Production
    private Fragment fr;
    private boolean show = true;
    OrderPostWraper orderPostWraper;
    ScheduleOrderModel scheduleOrderModel;
    Double totalAmount;

    @Override
    public int getDockFrameLayoutId() {
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teler);

        orderPostWraper = (OrderPostWraper) getIntent().getExtras().get("obj");
        scheduleOrderModel = (ScheduleOrderModel) getIntent().getExtras().get("objSchedule");
//        Singleton.getInstance().setOrderPostWraper(orderPostWraper);
//        Singleton.getInstance().setScheduleOrderModel(scheduleOrderModel);

//        Intent successIntent = new Intent(this, SuccessTransationActivity.class);
//
//        startActivity(successIntent);
//        finish();



        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("telr", Context.MODE_PRIVATE);
        String ref = sharedPref.getString("ref", null);
//        Button b = findViewById(R.id.cardButton);
//        if (ref != null) {
//            b.setVisibility(View.VISIBLE);
//        } else {
//            b.setVisibility(View.INVISIBLE);
//        }

        Intent intent = new Intent(TelerActivity.this, WebviewActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        EditText editText = (EditText) findViewById(R.id.text_amount);
        amount = editText.getText().toString();

        intent.putExtra(WebviewActivity.EXTRA_MESSAGE, getMobileRequest());
        intent.putExtra(WebviewActivity.SUCCESS_ACTIVTY_CLASS_NAME, "com.ingic.telerApp.SuccessTransationActivity");
        intent.putExtra(WebviewActivity.FAILED_ACTIVTY_CLASS_NAME, "com.ingic.telerApp.FailedTransation");
        intent.putExtra(WebviewActivity.IS_SECURITY_ENABLED, isSecurityEnabled);
        startActivity(intent);
        finish();
    }
    public void sendMessage(View view) {
//        Intent intent = new Intent(TelerActivity.this, WebviewActivity.class);
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        EditText editText = (EditText) findViewById(R.id.text_amount);
//        amount = editText.getText().toString();
//
//        intent.putExtra(WebviewActivity.EXTRA_MESSAGE, getMobileRequest());
//        intent.putExtra(WebviewActivity.SUCCESS_ACTIVTY_CLASS_NAME, "com.ingic.telerApp.SuccessTransationActivity");
//        intent.putExtra(WebviewActivity.FAILED_ACTIVTY_CLASS_NAME, "com.ingic.telerApp.FailedTransation");
//        intent.putExtra(WebviewActivity.IS_SECURITY_ENABLED, isSecurityEnabled);
//        startActivity(intent);
    }

    public void showCards(View view) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        Button b = findViewById(R.id.cardButton);

//        if (show) {
//            ft.replace(R.id.cardFrame, new CardFragment());
//            b.setText("HIDE");
//            show = false;
//        } else {
//            fr = fm.findFragmentById(R.id.cardFrame);
//            if (fr != null) {
//                ft.remove(fr);
//            }
//            b.setText(getResources().getString(R.string.showDetails));
//            show = true;
//        }
//        ft.commit();
    }

    public void sendMessage2(View view) {
        Intent intent = new Intent(TelerActivity.this, WebviewActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        EditText editText = (EditText) findViewById(R.id.text_amount);
        amount = editText.getText().toString();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("telr", Context.MODE_PRIVATE);
        String ref = sharedPref.getString("ref", null);
        intent.putExtra(WebviewActivity.EXTRA_MESSAGE, getMobileRequestWithContAuth(ref));
        intent.putExtra(WebviewActivity.SUCCESS_ACTIVTY_CLASS_NAME, "com.ingic.telerApp.SuccessTransationActivity");
        intent.putExtra(WebviewActivity.FAILED_ACTIVTY_CLASS_NAME, "com.ingic.telerApp.FailedTransation");
        intent.putExtra(WebviewActivity.IS_SECURITY_ENABLED, isSecurityEnabled);
        startActivity(intent);
    }


    // This example used for the first payment, or with new card details.
    private MobileRequest getMobileRequest() {
        MobileRequest mobile = new MobileRequest();
        mobile.setStore(STORE_ID);                       // Store ID
        mobile.setKey(KEY);                              // Authentication Key : The Authentication Key will be supplied by Telr as part of the Mobile API setup process after you request that this integration type is enabled for your account. This should not be stored permanently within the App.
        App app = new App();
        app.setId(String.valueOf(prefHelper.getUser().getId()));                          // Application installation ID
        app.setName("Laundry 2030");                    // Application name
        app.setUser("123456");                           // Application user ID : Your reference for the customer/user that is running the App. This should relate to their account within your systems.
        app.setVersion("0.0.1");                         // Application version
        app.setSdk("123");
        mobile.setApp(app);
        Tran tran = new Tran();
        tran.setTest("1");                              // Test mode : Test mode of zero indicates a live transaction. If this is set to any other value the transaction will be treated as a test.
        tran.setType("auth");                           /* Transaction type
                                                            'auth'   : Seek authorisation from the card issuer for the amount specified. If authorised, the funds will be reserved but will not be debited until such time as a corresponding capture command is made. This is sometimes known as pre-authorisation.
                                                            'sale'   : Immediate purchase request. This has the same effect as would be had by performing an auth transaction followed by a capture transaction for the full amount. No additional capture stage is required.
                                                            'verify' : Confirm that the card details given are valid. No funds are reserved or taken from the card.
                                                        */
        tran.setClazz("paypage");                       // Transaction class only 'paypage' is allowed on mobile, which means 'use the hosted payment page to capture and process the card details'
        tran.setCartid(String.valueOf(new BigInteger(128, new Random()))); //// Transaction cart ID : An example use of the cart ID field would be your own transaction or order reference.
        tran.setDescription("Laundry 2030");         // Transaction description
        tran.setCurrency("AED");                        // Transaction currency : Currency must be sent as a 3 character ISO code. A list of currency codes can be found at the end of this document. For voids or refunds, this must match the currency of the original transaction.
        tran.setAmount(String.valueOf(orderPostWraper.getAmount()));                         // Transaction amount : The transaction amount must be sent in major units, for example 9 dollars 50 cents must be sent as 9.50 not 950. There must be no currency symbol, and no thousands separators. Thedecimal part must be separated using a dot.
        //tran.setRef(???);                           // (Optinal) Previous transaction reference : The previous transaction reference is required for any continuous authority transaction. It must contain the reference that was supplied in the response for the original transaction.
        tran.setLangauge("en");                        // (Optinal) default is en -> English
        mobile.setTran(tran);
        Billing billing = new Billing();
        Address address = new Address();
        address.setCity("");                       // City : the minimum required details for a transaction to be processed
        address.setCountry("");                       // Country : Country must be sent as a 2 character ISO code. A list of country codes can be found at the end of this document. the minimum required details for a transaction to be processed
        address.setRegion("Dubai");                     // Region
        address.setLine1("Dubai");                 // Street address – line 1: the minimum required details for a transaction to be processed
        //address.setLine2("SIT G=Towe");               // (Optinal)
        //address.setLine3("SIT G=Towe");               // (Optinal)
        //address.setZip("SIT G=Towe");                 // (Optinal)
        billing.setAddress(address);
        Name name = new Name();
        name.setFirst(prefHelper.getUser().getName());                          // Forename : the minimum required details for a transaction to be processed
        name.setLast("");                          // Surname : the minimum required details for a transaction to be processed
        name.setTitle("");                           // Title
        billing.setName(name);
        billing.setEmail(prefHelper.getUser().getEmail());                 // TODO: Insert your email here : the minimum required details for a transaction to be processed.
        billing.setPhone(prefHelper.getUser().getPhone());                // Phone number, required if enabled in your merchant dashboard.
        mobile.setBilling(billing);
        return mobile;
    }

    /* This example used for continuous authority after using the first request, it used for recurring payment without asking the user to fill again the card details  */
    private MobileRequest getMobileRequestWithContAuth(String ref) {
        MobileRequest mobile = new MobileRequest();
        mobile.setStore(STORE_ID);                       // Store ID
        mobile.setKey(KEY);                              // Authentication Key : The Authentication Key will be supplied by Telr as part of the Mobile API setup process after you request that this integration type is enabled for your account. This should not be stored permanently within the App.
        App app = new App();
        app.setId(String.valueOf(prefHelper.getUser().getId()));                          // Application installation ID
        app.setName("Laundry 2030");                    // Application name
        app.setUser("123456");                           // Application user ID : Your reference for the customer/user that is running the App. This should relate to their account within your systems.
        app.setVersion("0.0.1");                         // Application version
        app.setSdk("123");
        mobile.setApp(app);
        Tran tran = new Tran();
        tran.setTest("0");                              // Test mode : Test mode of zero indicates a live transaction. If this is set to any other value the transaction will be treated as a test.
        tran.setType("sale");                           /* Transaction type
                                                            'auth'   : Seek authorisation from the card issuer for the amount specified. If authorised, the funds will be reserved but will not be debited until such time as a corresponding capture command is made. This is sometimes known as pre-authorisation.
                                                            'sale'   : Immediate purchase request. This has the same effect as would be had by performing an auth transaction followed by a capture transaction for the full amount. No additional capture stage is required.
                                                            'verify' : Confirm that the card details given are valid. No funds are reserved or taken from the card.
                                                        */
        tran.setClazz("cont");
        tran.setCartid(String.valueOf(new BigInteger(128, new Random()))); //// Transaction cart ID : An example use of the cart ID field would be your own transaction or order reference.
        tran.setDescription("Test Mobile API");         // Transaction description
        tran.setCurrency("AED");                        // Transaction currency : Currency must be sent as a 3 character ISO code. A list of currency codes can be found at the end of this document. For voids or refunds, this must match the currency of the original transaction.
        tran.setAmount(String.valueOf(orderPostWraper.getAmount()));                         // Transaction amount : The transaction amount must be sent in major units, for example 9 dollars 50 cents must be sent as 9.50 not 950. There must be no currency symbol, and no thousands separators. Thedecimal part must be separated using a dot.
        tran.setRef(ref);                        // TODO Previous transaction reference : The previous transaction reference is required for any continuous authority transaction. It must contain the reference that was supplied in the response for the original transaction.
        mobile.setTran(tran);
        return mobile;
    }
}
