package com.ingic.mylaundry.helpers;

/**
 * Created by developer007.ingic on 12/20/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.ingic.mylaundry.Interface.GooglePlaceDataInterface;
import com.ingic.mylaundry.activities.MainActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;


public class GooglePlaceHelper {

    public static final int REQUEST_CODE_AUTOCOMPLETE = 6666;
    public static final int PLACE_PICKER = 0;
    public static final int PLACE_AUTOCOMPLETE = 1;
    private static final String TAG = "Google Place";
    private int apiType;
    private GooglePlaceDataInterface googlePlaceDataInterface;
    private Context context;
    private Fragment fragment;

    public GooglePlaceHelper(MainActivity context, int apiType, GooglePlaceDataInterface googlePlaceDataInterface, Fragment fragment) {
        this.context = context;
        this.apiType = apiType;
        this.googlePlaceDataInterface = googlePlaceDataInterface;
        this.fragment = fragment;
    }

    public static GoogleAddressModel getAddress(Context context, double LATITUDE, double LONGITUDE) {

        GoogleAddressModel googleAddressModel = new GoogleAddressModel("", "", "", "", "", "");

        //Set address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {


                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String streetName = addresses.get(0).getFeatureName(); // Only if available else return NULL


                googleAddressModel = new GoogleAddressModel(address, city, state, country, postalCode, streetName);

                Log.d(TAG, "getAddress:  address -" + address);
                Log.d(TAG, "getAddress:  city -" + city);
                Log.d(TAG, "getAddress:  country -" + country);
                Log.d(TAG, "getAddress:  state -" + state);
                Log.d(TAG, "getAddress:  postalCode -" + postalCode);
                Log.d(TAG, "getAddress:  knownName" + streetName);


                String countryCode = addresses.get(0).getCountryCode();
                Log.d(TAG, "getAddress:  countryCode" + countryCode);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleAddressModel;
    }

    public void openAutocompleteActivity() {
        try {


            // The autocomplete activity requires Google Play ServicesWrapper to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent;
            if (apiType == PLACE_PICKER) {
                intent = new PlacePicker.IntentBuilder()
                        .build((MainActivity) context);
            } else {
                intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build((MainActivity) context);

            }


            ((MainActivity) context).startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
//            fragment.startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play ServicesWrapper is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog((MainActivity) context, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play ServicesWrapper is not available and the problem is not easily
            // resolvable.
            String message = "Google Play ServicesWrapper is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e(TAG, message);
            Utils.showToast(context, message);
        }
    }

    /**
     * Override fragment's onActivityResult and pass its parameters here.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GooglePlaceHelper.REQUEST_CODE_AUTOCOMPLETE && data != null) {

            if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                if (PlacePicker.getStatus(context, data) == null) {
                    return;
                }
                Status status = PlaceAutocomplete.getStatus(context, data);
                Log.e(TAG, "Error: Status = " + status.toString());
                googlePlaceDataInterface.onError(status.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            } else {


                if (PlacePicker.getPlace(context, data) == null) {
                    return;
                }


                Place place = PlacePicker.getPlace(context, data);

                String locationName = place.getName().toString();
                Double latitude = place.getLatLng().latitude;
                Double longitude = place.getLatLng().longitude;


                Log.d(TAG, "onActivityResult MAP: locationName = " + locationName);
                Log.d(TAG, "onActivityResult MAP: latitude = " + latitude);
                Log.d(TAG, "onActivityResult MAP: longitude = " + longitude);

//                Log.d(TAG, "onActivityResult MAP: Locale = " +    place.getLocale().toString());
//                Log.d(TAG, "onActivityResult MAP: Country = " +    place.getLocale().getCountry());
//                Log.d(TAG, "onActivityResult MAP: Display Country = " + place.getLocale().getDisplayCountry());
//                Log.d(TAG, "onActivityResult MAP: Display Name = " + place.getLocale().getDisplayName());

                getCurrentAddress(latitude, longitude, locationName);


            }

        }
    }

    private void getCurrentAddress(double lat, double lng, String locationName) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.getDefault());
            addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                String city = addresses.get(0).getLocality();

                String country = addresses.get(0).getCountryName();


                googlePlaceDataInterface.onPlaceActivityResult(lat, lng, locationName, city, country);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class GoogleAddressModel {
        private String address;
        private String city;
        private String state;
        private String country;
        private String postalCode;
        private String streetName;


        public GoogleAddressModel(String address, String city, String state, String country, String postalCode, String streetName) {
            this.address = address;
            this.city = city;
            this.state = state;
            this.country = country;
            this.postalCode = postalCode;
            this.streetName = streetName;
        }


        public String getAddress() {
            return address == null ? "" : address;
        }

        public String getCity() {
            return city == null ? "" : city;
        }

        public String getState() {
            return state == null ? "" : state;
        }

        public String getCountry() {
            return country == null ? "" : country;
        }

        public String getPostalCode() {
            return postalCode == null ? "" : postalCode;
        }

        public String getStreetName() {
            return streetName == null ? "" : streetName;
        }
    }
}

