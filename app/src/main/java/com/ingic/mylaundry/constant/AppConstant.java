package com.ingic.mylaundry.constant;


public class AppConstant {

    public static final int SELECT_MAX_FILE_COUNT = 10;
    public static final int SELECT_MAX_DOC_FILE_COUNT = 10;
    public static final int SELECT_IMAGE_COUNT = 1;
    public static final int CAMERA_PIC_REQUEST = 2;
    public static final int FB_REQ_CODE = 64206;
    public static final int G_REQ_CODE = 103;
    public static final String SAVE = "save";
    public static final String TOTAL = "total";
    public static final String HOME = "home";
    public static final String OFFICE = "office";
    public static final String OTHER = "other";
    public static int SPLASH_TIMER = 3500;
    public static String ZERO = "0";
    public static String AED = "AED";
    public static final String DEVICE_TYPE = "android";
    public static final String TERMS = "terms";
    public static final String FOLD_CLOTH = "foldcloth";
    public static final String LEAVE_MY_DOOR = "leave_my_door";
    public static final String CALL_PICKUP = "call_pickup";
    public static final String CALL_DELIVERY = "call_delivery";
    public static final String INSTRUCTION = "instructions";
    public static final String DELIVERY = "delivery";
    public static final String PICKUP = "pickup";
    public static final String EDIT_PROFILE = "edit_profile";

    public class ServerAPICalls {

        public static final int SUCCESS_CODE = 200;
        public static final String BASE_URL = "http://laundry.stagingic.com/";
        public static final String API = "api/";

        //For Usage
        public static final String REGISTER = BASE_URL + API + "register";
        public static final String SIGNIN = BASE_URL + API + "login";
        public static final String SERVICES = BASE_URL + API + "get-services";
        public static final String EMAIL_PASSWORD_CODE = BASE_URL + API + "email-password-code";
        public static final String VERIFY_PASSWORD_CODE = BASE_URL + API + "verify-code";
        public static final String UPDATE_FORGOT_PASSWORD = BASE_URL + API + "update-password";
        public static final String ADD_ADDRESS = BASE_URL + API + "add-address";
        public static final String Update_instruction = BASE_URL + API + "update-instruction";
        public static final String UPDATE_PROFILE = BASE_URL + API + "update-profile";
        public static final String UPDATE_PASSWORD = BASE_URL + API + "update-password";
        public static final String RESET_PASSWORD = BASE_URL + API + "reset-password";
        public static final String AVAILABLE_TIME_SLOT = BASE_URL + API + "time-slot";
        public static final String TERM_AND_CONDITION = BASE_URL + API + "get-cms";
        public static final String GET_ADDRESS = BASE_URL + API + "get-address";
        public static final String UPDATE_ADDRESS = BASE_URL + API + "update-address";
        public static final String RESENT_REG_CODE = BASE_URL + API + "resend-reg-code";
        public static final String RESENT_FORGOT_CODE = BASE_URL + API + "resend-code";
        public static final String FEEDBACK = BASE_URL + API + "feedback";
        public static final String MY_ORDER = BASE_URL + API + "my-orders";
        public static final String SAVE_ORDER = BASE_URL + API + "save-order";
        public static final String REPORT_AN_ISSUES = BASE_URL + API + "issues";
        public static final String ORDER_DETAIL = BASE_URL + API + "order-detail";
        public static final String LOGOUT = BASE_URL + API + "logout";
        public static final String RESEND_OTP = BASE_URL + API + "resend-otp";
        public static final String VERIFY_OTP = BASE_URL + API + "verify-otp";
        public static final String VERIFY_REG_CODE = BASE_URL + API + "verify-reg-code";
        public static final String GET_NOTIFICATION = BASE_URL + API + "get-notifications";
        public static final String NOTIFICATION_STATUS = BASE_URL + API + "update-profile";
        public static final String GET_COUPONS = BASE_URL + API + "get-coupons-detail";
        public static final String CANCEL_ORDER = BASE_URL + API + "cancel-order";
        public static final String GET_CONTACT = BASE_URL + API + "get-contact";
        public static final String DELETE_ADDRESS = BASE_URL + API + "delete-address";
        public static final String INSTANCE_ORDER = BASE_URL + API + "instance-order-text";
        public static final String DELETE_NOTIFICATION = BASE_URL + API + "delete-notification";
        public static final String READ_NOTIFICATION = BASE_URL + API + "read-notification";
        public static final String COUNT_NOTIFICATION = BASE_URL + API + "count-notifications";

    }

    public static class FirebaseConfig {
        // global topic to receive app wide push notifications
        public static final String TOPIC_GLOBAL = "global";

        // broadcast receiver intent filters
        public static final String REGISTRATION_COMPLETE = "registrationComplete";
        public static final String PUSH_NOTIFICATION = "pushNotification";

        // id to handle the notification in the notification tray
        public static final int NOTIFICATION_ID = 100;
        public static final int NOTIFICATION_ID_BIG_IMAGE = 101;


    }
}
