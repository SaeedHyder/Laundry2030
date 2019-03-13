package com.ingic.mylaundry.webServices;


import com.ingic.mylaundry.constant.AppConstant;
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
import com.ingic.mylaundry.webhelpers.models.Api_Response;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by khanubaid on 12/28/2017.
 */

public interface webservice {

    // Account register
    @POST(AppConstant.ServerAPICalls.REGISTER)
    Call<Api_Response<UserWrapper>> userRegister(
            @Body RequestBody params
    );

    //Account SignIn
    @POST(AppConstant.ServerAPICalls.SIGNIN)
    Call<Api_Response<UserWrapper>> SignIn(
            @Body RequestBody params
    );

    //ServicesWrapper
    @POST(AppConstant.ServerAPICalls.SERVICES)
    Call<Api_Response<ServicesWrapper>> getServices(

    );

    //Update instraction
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.Update_instruction)
    Call<Api_Response<UserWrapper>> updateInstruction(
            @Field("user_id") int user_id,
            @Field("other") String other,
            @Field("is_fold") int is_fold,
            @Field("at_my_door") int at_my_door,
            @Field("call_me_before_pickup") int call_me_before_pickup,
            @Field("call_me_before_delivery") int call_me_before_delivery

    );


    //Email Password Code
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.EMAIL_PASSWORD_CODE)
    Call<Api_Response> EmailPasswordCode(
            @Field("email") String email);

    //Verify Password Code
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.VERIFY_PASSWORD_CODE)
    Call<Api_Response> VerifyPasswordCode(
            @Field("verification_code") String verification_code);

    //Update ForgotPassword
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.UPDATE_FORGOT_PASSWORD)
    Call<Api_Response> UpdateForgotPassword(
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation);

    // Update Profile
    @Multipart
    @POST(AppConstant.ServerAPICalls.UPDATE_PROFILE)
    Call<Api_Response<UserWrapper>> UpdatedProfile(
            @Part("user_id") RequestBody user_id,
            @Part("name") RequestBody name,
            @Part("phone") RequestBody phone,
            @Part MultipartBody.Part image,
            @Part("address") RequestBody address
    );

    //Add address
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.ADD_ADDRESS)
    Call<Api_Response<AddressWraper>> addAddress(
            @Field("user_id") Integer user_id,
            @Field("longitude") Double longitude,
            @Field("latitude") Double latitude,
            @Field("location") String location,
            @Field("address") String address,
            @Field("type") String type);


    //Change password
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.UPDATE_PASSWORD)
    Call<Api_Response<User>> updatePassword(
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String confirmPassword);

    //Available TimeSlot
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.AVAILABLE_TIME_SLOT)
    Call<Api_Response<AvailableTimeSlotWrapper>> availableTimeSlot(
            @Field("date") String date,
            @Field("instance") String instant,
            @Field("slot") String slot,
            @Field("pickup_date") String pickup_date,
            @Field("slot_type") String slot_type);

    //Term and Condition
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.TERM_AND_CONDITION)
    Call<Api_Response<TermWrapper>> getTermsAndCondition(
            @Field("type") String type);

    //Get address
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.GET_ADDRESS)
    Call<Api_Response<Address>> getAddress(
            @Field("user_id") Integer user_id,
            @Field("offset") Integer offset,
            @Field("limit") Integer limit
    );

    //Update address
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.UPDATE_ADDRESS)
    Call<Api_Response<UpdateAddressWrapper>> updateAddress(
            @Field("user_id") Integer user_id,
            @Field("longitude") Double longitude,
            @Field("latitude") Double latitude,
            @Field("location") String location,
            @Field("address") String address,
            @Field("id") Integer id,
            @Field("type") String type);

    //Change password
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.RESET_PASSWORD)
    Call<Api_Response<User>> resetPassword(
            @Field("user_id") String id,
            @Field("old_password") String old_password,
            @Field("password") String password);


    //Get Resent Pin Signup
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.RESENT_REG_CODE)
    Call<Api_Response> getResentCode(
            @Field("email") String email);

    //Get Resent Pin Forgot
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.RESENT_FORGOT_CODE)
    Call<Api_Response> getResentForgotCode(
            @Field("email") String email);

    //Feedback
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.FEEDBACK)
    Call<Api_Response<Feedback>> feedback(
            @Field("user_id") Integer user_id,
            @Field("feedback") String feedback);

    //Order
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.MY_ORDER)
    Call<Api_Response<OrderSubWraper>> myorders(
            @Field("user_id") Integer user_id,
            @Field("status") String status,
            @Field("offset") Integer offset,
            @Field("limit") Integer limit);


    //Save Order
    @POST(AppConstant.ServerAPICalls.SAVE_ORDER)
    Call<Api_Response<OrderWrapper>> Order(
            @Body OrderPostWraper order_body
    );

    //Report An Issues
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.REPORT_AN_ISSUES)
    Call<Api_Response> report_an_issue(
            @Field("user_id") Integer user_id,
            @Field("title") String title,
            @Field("missing_product") Integer missing_product,
            @Field("late_order") Integer late_order,
            @Field("payment_issue") Integer payment_issue,
            @Field("something_else") Integer something_else
    );

    //Order Detail
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.ORDER_DETAIL)
    Call<Api_Response<OrderWrapper>> order_detail(
            @Field("order_id") Integer order_id
    );

    //Resend Otp
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.VERIFY_OTP)
    Call<Api_Response> verifyOtp(
            @Field("phone") String num,
            @Field("verification_code") String code
    );

    //Verify Register Code
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.VERIFY_REG_CODE)
    Call<Api_Response> verifyRegCode(
            @Field("email") String email,
            @Field("verification_code") Integer verification_code
    );

    //Logout
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.LOGOUT)
    Call<Api_Response> logout(
            @Field("device_token") String device_token
    );

    //Get Notification
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.GET_NOTIFICATION)
    Call<Api_Response<NotificationWrapper>> getNotification(
            @Field("user_id") Integer user_id,
            @Field("offset") Integer offset,
            @Field("limit") Integer limit
    );

    //Notification Status
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.NOTIFICATION_STATUS)
    Call<Api_Response> notificationStatus(
            @Field("user_id") Integer user_id,
            @Field("notification_status") Integer notification_status);

    //Get Coupons
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.GET_COUPONS)
    Call<Api_Response<CouponsDetail>> getCoupons(
            @Field("code") String code,
            @Field("user_id") Integer user_id);

    //Cancel Order
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.CANCEL_ORDER)
    Call<Api_Response> cancelOrder(
            @Field("id") Integer code,
            @Field("user_id") Integer user_id,
            @Field("order_current_status") Integer order_current_status,
            @Field("order_status") Integer order_status);

    //Get Contact
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.GET_CONTACT)
    Call<Api_Response<GetContact>> getContact(
            @Field("phone") Integer phone,
            @Field("email") String email
    );

    //Delete Address
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.DELETE_ADDRESS)
    Call<Api_Response> deleteAddress(
            @Field("id") Integer id,
            @Field("user_id") Integer user_id
    );

    //Instance Order
//    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.INSTANCE_ORDER)
    Call<Api_Response> instance_order(
    );

    //Resend Otp
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.RESEND_OTP)
    Call<Api_Response> resendOtp(String num);

    //Delete Notification
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.DELETE_NOTIFICATION)
    Call<Api_Response> deleteNotification(
            @Field("user_id") Integer user_id,
            @Field("notification_id") Integer notification_id
    );

    //Read Notification
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.READ_NOTIFICATION)
    Call<Api_Response> readNotification(
            @Field("user_id") Integer user_id,
            @Field("notification_id") Integer notification_id
    );

    //Count Notification
    @FormUrlEncoded
    @POST(AppConstant.ServerAPICalls.COUNT_NOTIFICATION)
    Call<Api_Response> getCountNotification(
            @Field("user_id") Integer user_id

    );
}
