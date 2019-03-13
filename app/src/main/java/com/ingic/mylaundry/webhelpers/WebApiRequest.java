package com.ingic.mylaundry.webhelpers;

import android.app.ProgressDialog;
import android.support.v4.content.ContextCompat;
import android.webkit.MimeTypeMap;

import com.ingic.mylaundry.R;
import com.ingic.mylaundry.activities.MainActivity;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.fragments.LoginFragment;
import com.ingic.mylaundry.helpers.NetworkUtils;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.models.Address;
import com.ingic.mylaundry.models.AddressWraper;
import com.ingic.mylaundry.models.AvailableTimeSlotWrapper;
import com.ingic.mylaundry.models.CouponsDetail;
import com.ingic.mylaundry.models.Feedback;
import com.ingic.mylaundry.models.GetContact;
import com.ingic.mylaundry.models.NotificationWrapper;
import com.ingic.mylaundry.models.OrderPostWraper;
import com.ingic.mylaundry.models.OrderSubWraper;
import com.ingic.mylaundry.models.OrderWrapper;
import com.ingic.mylaundry.models.ServicesWrapper;
import com.ingic.mylaundry.models.TermWrapper;
import com.ingic.mylaundry.models.UpdateAddressWrapper;
import com.ingic.mylaundry.models.User;
import com.ingic.mylaundry.models.UserWrapper;
import com.ingic.mylaundry.webServices.WebServiceFactory;
import com.ingic.mylaundry.webServices.webservice;
import com.ingic.mylaundry.webhelpers.models.Api_Array_Response;
import com.ingic.mylaundry.webhelpers.models.Api_Response;
import com.ingic.telerApp.SuccessTransationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebApiRequest {

    private static webservice apiService;
    private static ProgressDialog mDialog;
    private static MainActivity currentActivity;
    private static SuccessTransationActivity successTransationActivity;
    private static WebApiRequest ourInstance = new WebApiRequest();

    private WebApiRequest() {
    }

    public static WebApiRequest getInstance(MainActivity activity, boolean isShow) {
        apiService = WebServiceFactory.getInstance(activity);
        currentActivity = activity;

//        if (isShow) {
//
//            if(mDialog != null && !mDialog.isShowing()){
//                mDialog = new ProgressDialog(currentActivity);
//                mDialog.setMessage(activity.getString(R.string.loading));
//                mDialog.setTitle(activity.getString(R.string.please_wait));
//                mDialog.setCancelable(false);
//            }
//
//            if (!currentActivity.isFinishing()) {
//                if(mDialog != null && !mDialog.isShowing())
//                    mDialog.show();
//
//            }
//        }

        return ourInstance;
    }

    public static WebApiRequest getInstance(SuccessTransationActivity activity, boolean isShow) {
        apiService = WebServiceFactory.getInstance(activity);
        successTransationActivity = activity;
        return ourInstance;
    }

    private void userBlocked(String message) {
        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container), message, ContextCompat.getColor(currentActivity, R.color.color_button));
        currentActivity.prefHelper.removeLoginPreference();
        currentActivity.onLoadingFinished();
        currentActivity.emptyBackStack();
        currentActivity.initFragments(new LoginFragment());
    }

    // url = file path or whatever suitable URL you want.
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public RequestBody getStringRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    public RequestBody getImageRequestBody(File value) {
        //return RequestBody.create(MediaType.parse(getMimeType(value.getAbsolutePath())), value);
        return RequestBody.create(MediaType.parse("image/*"), value);
    }

    public void UserRegister(String name, String email, String password, String password_confirmation, String deviceToken, String phone, final APIRequestDataCallBack apiRequestDataCallBack) {

        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("email", email);
            jsonObject.put("password_confirmation", password_confirmation);
            jsonObject.put("password", password);
            jsonObject.put("device_type", AppConstant.DEVICE_TYPE);
            jsonObject.put("device_token", deviceToken);
            jsonObject.put("phone", phone);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        Call<Api_Response<UserWrapper>> call = apiService.userRegister(body);
        call.enqueue(new Callback<Api_Response<UserWrapper>>() {
//            private Call<Api_Response<UserWrapper>> pCall;
//            private Response<Api_Response<UserWrapper>> response;

            @Override
            public void onResponse(Call<Api_Response<UserWrapper>> pCall, Response<Api_Response<UserWrapper>> response) {
//                this.pCall = pCall;
//                this.response = response;

                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }

                if (response != null && response.body() != null) {
                    if (response.body().getCode() == 200) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response<UserWrapper>> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void SignIn(String email, String password, String device_type, String device_token, final APIRequestDataCallBack apiRequestDataCallBack) {

        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("device_type", device_type);
            jsonObject.put("device_token", device_token);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        Call<Api_Response<UserWrapper>> call = apiService.SignIn(body);
        call.enqueue(new Callback<Api_Response<UserWrapper>>() {
            @Override
            public void onResponse(Call<Api_Response<UserWrapper>> pCall, Response<Api_Response<UserWrapper>> response) {

                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }

                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<Api_Response<UserWrapper>> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void GetServices(final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }


        Call<Api_Response<ServicesWrapper>> call = apiService.getServices();

        call.enqueue(new Callback<Api_Response<ServicesWrapper>>() {
            @Override
            public void onResponse(Call<Api_Response<ServicesWrapper>> pCall, Response<Api_Response<ServicesWrapper>> response) {

                if (response != null && response.body() != null) {


                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
//                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
//                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_chatHeader));
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }


                } else {
                    apiRequestDataCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<Api_Response<ServicesWrapper>> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
    }

    public void saveInstruction(int user_id, String other, int is_fold, int at_my_door, int call_me_before_pickup, int call_me_before_delivery, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }

        Call<Api_Response<UserWrapper>> call = apiService.updateInstruction(
                user_id,
                other,
                is_fold,
                at_my_door,
                call_me_before_pickup,
                call_me_before_delivery
        );
        call.enqueue(new Callback<Api_Response<UserWrapper>>() {
            @Override
            public void onResponse(Call<Api_Response<UserWrapper>> pCall, Response<Api_Response<UserWrapper>> response) {

                if (response != null && response.body() != null) {
                    if (response.body().getUserBlocked() == 0) {
                        if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                            apiRequestDataCallBack.onSuccess(response.body());
                        } else {
                            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                    response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                            apiRequestDataCallBack.onError();
                        }
                    } else {
                        userBlocked(response.body().getMessage());
                    }
                } else {
                    apiRequestDataCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<Api_Response<UserWrapper>> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
    }

    public void EmailPasswordCode(String email, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }

        Call<Api_Response> call = apiService.EmailPasswordCode((email));
        call.enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> pCall, Response<Api_Response> response) {

                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<Api_Response> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
//                Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
//                        currentActivity.getString(R.string.something_went_wrong), ContextCompat.getColor(currentActivity, R.color.color_purple));
                apiRequestDataCallBack.onError();
            }
        });
    }

    public void VerifyPasswordCode(String verification_code, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }

        Call<Api_Response> call = apiService.VerifyPasswordCode((verification_code));
        call.enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> pCall, Response<Api_Response> response) {

                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<Api_Response> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
    }

    public void UpdateForgotPassword(String email, String password, String password_confirmation, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }

        Call<Api_Response> call = apiService.UpdateForgotPassword(email, password, password_confirmation);
        call.enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> pCall, Response<Api_Response> response) {

                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<Api_Response> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
    }

    public void UpdatedProfile(String id, String name, String email, String phone, File image, String address, final APIRequestDataCallBack apiRequestDataCallBack) {


        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }

        MultipartBody.Part body;
        File file;
        if (image != null) {
            //if (!Utils.isEmptyOrNull(image)) {
            body = MultipartBody.Part.createFormData("image", image.getName(), getImageRequestBody(image));
        } else
            body = null;

        Call<Api_Response<UserWrapper>> call = apiService.UpdatedProfile(
                getStringRequestBody(id),
                getStringRequestBody(name),
                getStringRequestBody(phone),
                body,
                getStringRequestBody(address)


        );

        call.enqueue(new Callback<Api_Response<UserWrapper>>() {
            @Override
            public void onResponse(Call<Api_Response<UserWrapper>> pCall, Response<Api_Response<UserWrapper>> response) {

                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }

                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }


            }

            @Override
            public void onFailure(Call<Api_Response<UserWrapper>> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
    }

    public void AddAddress(int user_id, double longitude, double latitude, String location, String address, String type,
                           final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }


        Call<Api_Response<AddressWraper>> call = apiService.addAddress(user_id, longitude, latitude, location, address, type);
        call.enqueue(new Callback<Api_Response<AddressWraper>>() {
            @Override
            public void onResponse(Call<Api_Response<AddressWraper>> pCall, Response<Api_Response<AddressWraper>> response) {

                if (response != null && response.body() != null) {
                    if (response.body().getUserBlocked() == 0) {
                        if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                            apiRequestDataCallBack.onSuccess(response.body());
                            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                    response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        } else {
                            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                    response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                            apiRequestDataCallBack.onError();
                        }
                    } else {
                        userBlocked(response.body().getMessage());
                    }
                } else {
                    apiRequestDataCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<Api_Response<AddressWraper>> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
    }

    public void UpdateAddress(int user_id, double longitude, double latitude, String location, String address, int id, String type, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }


        Call<Api_Response<UpdateAddressWrapper>> call = apiService.updateAddress(user_id, longitude, latitude, location, address, id, type);
        call.enqueue(new Callback<Api_Response<UpdateAddressWrapper>>() {
            @Override
            public void onResponse(Call<Api_Response<UpdateAddressWrapper>> pCall, Response<Api_Response<UpdateAddressWrapper>> response) {

                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<Api_Response<UpdateAddressWrapper>> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
    }

    public void UpdatePassword(String emailId, String password, String confirmPassword, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.updatePassword(emailId, password, confirmPassword).enqueue(new Callback<Api_Response<User>>() {
            @Override
            public void onResponse(Call<Api_Response<User>> call, Response<Api_Response<User>> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response<User>> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void AvailableTimeSlot(String pickup_date, String date, String instant, String slot_type, String slot, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.availableTimeSlot(date, instant, slot, pickup_date, slot_type).enqueue(new Callback<Api_Response<AvailableTimeSlotWrapper>>() {
            @Override
            public void onResponse(Call<Api_Response<AvailableTimeSlotWrapper>> call, Response<Api_Response<AvailableTimeSlotWrapper>> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getUserBlocked() == 0) {
                        if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                            apiRequestDataCallBack.onSuccess(response.body());
                        } else {
                            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                    response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                            apiRequestDataCallBack.onError();
                        }

                    } else {
                        userBlocked(response.body().getMessage());
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response<AvailableTimeSlotWrapper>> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void TermAndCondition(String type, final APIRequestDataCallBack apiRequestDataCallBack) {

        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }

        Call<Api_Response<TermWrapper>> call = apiService.getTermsAndCondition(type);
        call.enqueue(new Callback<Api_Response<TermWrapper>>() {
            @Override
            public void onResponse(Call<Api_Response<TermWrapper>> pCall, Response<Api_Response<TermWrapper>> response) {

                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
//                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
//                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_chatHeader));
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<Api_Response<TermWrapper>> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
    }

    public void GetAddresses(int user_id, int offset, int limit, final APIRequestDataCallBack apiRequestDataCallBack) {

        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }

        Call<Api_Response<Address>> call = apiService.getAddress(user_id, offset, limit);
        call.enqueue(new Callback<Api_Response<Address>>() {
            @Override
            public void onResponse(Call<Api_Response<Address>> pCall, Response<Api_Response<Address>> response) {

                if (response != null && response.body() != null) {
                    if (response.body().getUserBlocked() == 0) {
                        if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                            apiRequestDataCallBack.onSuccess(response.body());
//                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
//                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_chatHeader));
                        } else {
                            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                    response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                            apiRequestDataCallBack.onError();
                        }
                    } else {
                        userBlocked(response.body().getMessage());
                    }
                } else {
                    apiRequestDataCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<Api_Response<Address>> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
    }

    public void ResentCode(String email, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.getResentCode(email).enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> call, Response<Api_Response> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void ResentForgotCode(String email, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.getResentForgotCode(email).enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> call, Response<Api_Response> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void FeedBack(int user_id, String feedback, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.feedback(user_id, feedback).enqueue(new Callback<Api_Response<Feedback>>() {
            @Override
            public void onResponse(Call<Api_Response<Feedback>> call, Response<Api_Response<Feedback>> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response<Feedback>> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
    }

    public void OrderHistory(int user_id, String status, int offset, int limit, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.myorders(user_id, status, offset, limit).enqueue(new Callback<Api_Response<OrderSubWraper>>() {
            @Override
            public void onResponse(Call<Api_Response<OrderSubWraper>> call, Response<Api_Response<OrderSubWraper>> response) {
                if (response != null && response.body() != null) {

                        if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                            apiRequestDataCallBack.onSuccess(response.body());
                        } else {
                            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                    response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                            apiRequestDataCallBack.onError();
                        }

                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response<OrderSubWraper>> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void SaveOrder(OrderPostWraper order_body, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(successTransationActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
//            Utils.showSnackBar(successTransationActivity, currentActivity.findViewById(R.id.main_container),
//                    currentActivity.getResources().getString(R.string.no_network_available),
//                    ContextCompat.getColor(currentActivity, R.color.color_button));

            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.Order(order_body).enqueue(new Callback<Api_Response<OrderWrapper>>() {
            @Override
            public void onResponse(Call<Api_Response<OrderWrapper>> call, Response<Api_Response<OrderWrapper>> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getUserBlocked() == 0) {
                        if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                            apiRequestDataCallBack.onSuccess(response.body());
                        } else {
                            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                    response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                            apiRequestDataCallBack.onError();
                        }
                    } else {
                        userBlocked(response.body().getMessage());
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response<OrderWrapper>> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void SaveOrderFragment(OrderPostWraper order_body, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.Order(order_body).enqueue(new Callback<Api_Response<OrderWrapper>>() {
            @Override
            public void onResponse(Call<Api_Response<OrderWrapper>> call, Response<Api_Response<OrderWrapper>> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response<OrderWrapper>> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void resetPassword(String id, String old_password, String newPassword, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.resetPassword(id, old_password, newPassword).enqueue(new Callback<Api_Response<User>>() {
            @Override
            public void onResponse(Call<Api_Response<User>> call, Response<Api_Response<User>> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response<User>> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void ReportAnIssues(int user_id, String title, int missing_product, int late_order, int payment_issue, int something_else, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.report_an_issue(user_id, title, missing_product, late_order, payment_issue, something_else).enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> call, Response<Api_Response> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getUserBlocked() == 0) {
                        if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                            apiRequestDataCallBack.onSuccess(response.body());
                        } else {
                            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                    response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                            apiRequestDataCallBack.onError();
                        }
                    } else {
                        userBlocked(response.body().getMessage());
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void OrderDetail(int order_id, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.order_detail(order_id).enqueue(new Callback<Api_Response<OrderWrapper>>() {
            @Override
            public void onResponse(Call<Api_Response<OrderWrapper>> call, Response<Api_Response<OrderWrapper>> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getUserBlocked() == 0) {
                        if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                            apiRequestDataCallBack.onSuccess(response.body());
                        } else {
                            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                    response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                            apiRequestDataCallBack.onError();
                        }
                    } else {
                        userBlocked(response.body().getMessage());
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response<OrderWrapper>> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public interface APIRequestDataCallBack {
        void onSuccess(Api_Response response);

        void onError();

        void onNoNetwork();
    }

    public interface APIArrayRequestDataCallBack {
        void onSuccess(Api_Array_Response response);

        void onError();

        void onNoNetwork();
    }

    public void resendOTP(String number, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.resendOtp(number).enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> call, Response<Api_Response> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void verifyOTP(String number, String code, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.verifyOtp(number, code).enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> call, Response<Api_Response> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void verifyRegCode(String email, int code, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.verifyRegCode(email, code).enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> call, Response<Api_Response> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void logout(String device_token, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.logout(device_token).enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> call, Response<Api_Response> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void getNotification(int user_id, int offset, int limit, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }

        Callback<Api_Response<NotificationWrapper>> req = new Callback<Api_Response<NotificationWrapper>>() {
            @Override
            public void onResponse(Call<Api_Response<NotificationWrapper>> call, Response<Api_Response<NotificationWrapper>> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getUserBlocked()==0) {
                        if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                            apiRequestDataCallBack.onSuccess(response.body());
                        } else {
                            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                    response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                            apiRequestDataCallBack.onError();
                        }
                    }else
                    {
                        userBlocked(response.body().getMessage());
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response<NotificationWrapper>> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        };

        apiService.getNotification(user_id, offset, limit).enqueue(req);


    }

    public Call<Api_Response> notificationStatus(int user_id, int notification_status, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return null;
        }
        Call<Api_Response> req = apiService.notificationStatus(user_id, notification_status);


        req.enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> call, Response<Api_Response> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
        return req;

    }

    public void getCoupons(String code, int user_id, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.getCoupons(code, user_id).enqueue(new Callback<Api_Response<CouponsDetail>>() {
            @Override
            public void onResponse(Call<Api_Response<CouponsDetail>> call, Response<Api_Response<CouponsDetail>> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response<CouponsDetail>> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void cancelOrder(int id, int user_id, int order_current_status, int order_status, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.cancelOrder(id, user_id, order_current_status, order_status).enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> call, Response<Api_Response> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void getContact(int phone, String email, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.getContact(phone, email).enqueue(new Callback<Api_Response<GetContact>>() {
            @Override
            public void onResponse(Call<Api_Response<GetContact>> call, Response<Api_Response<GetContact>> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response<GetContact>> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void deleteAddress(int id, int user_id, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.deleteAddress(id, user_id).enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> call, Response<Api_Response> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void instanceOrder(final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }
        apiService.instance_order().enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> call, Response<Api_Response> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getUserBlocked() == 0) {
                        if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                            apiRequestDataCallBack.onSuccess(response.body());
                        } else {
                            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                    response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                            apiRequestDataCallBack.onError();
                        }
                    } else {
                        userBlocked(response.body().getMessage());
                    }
                } else {
                    apiRequestDataCallBack.onError();

                }
            }

            @Override
            public void onFailure(Call<Api_Response> call, Throwable t) {
                t.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });

    }

    public void deleteNotification(int user_id, int notification_id, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }

        Call<Api_Response> call = apiService.deleteNotification(user_id, notification_id);
        call.enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> pCall, Response<Api_Response> response) {

                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
//                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
//                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_chatHeader));
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<Api_Response> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
    }

    public void readNotification(int user_id, int notification_id, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return;
        }

        Call<Api_Response> call = apiService.readNotification(user_id, notification_id);
        call.enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> pCall, Response<Api_Response> response) {

                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
//                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
//                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_chatHeader));
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<Api_Response> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
    }

    public Call<Api_Response> countNotification(int user_id, final APIRequestDataCallBack apiRequestDataCallBack) {
        if (!NetworkUtils.isNetworkAvailable(currentActivity)) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                    currentActivity.getResources().getString(R.string.no_network_available),
                    ContextCompat.getColor(currentActivity, R.color.color_button));
            apiRequestDataCallBack.onNoNetwork();
            return null;
        }

        Call<Api_Response> call = apiService.getCountNotification(user_id);
        call.enqueue(new Callback<Api_Response>() {
            @Override
            public void onResponse(Call<Api_Response> pCall, Response<Api_Response> response) {

                if (response != null && response.body() != null) {
                    if (response.body().getCode().equals(AppConstant.ServerAPICalls.SUCCESS_CODE)) {
                        apiRequestDataCallBack.onSuccess(response.body());
//                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
//                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_chatHeader));
                    } else {
                        Utils.showSnackBar(currentActivity, currentActivity.findViewById(R.id.main_container),
                                response.body().getMessage(), ContextCompat.getColor(currentActivity, R.color.color_button));
                        apiRequestDataCallBack.onError();
                    }
                } else {
                    apiRequestDataCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<Api_Response> pCall, Throwable throwable) {
                throwable.printStackTrace();
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                apiRequestDataCallBack.onError();
            }
        });
        return call;
    }
}
