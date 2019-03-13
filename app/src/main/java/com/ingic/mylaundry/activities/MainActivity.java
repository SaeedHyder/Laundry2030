package com.ingic.mylaundry.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.ingic.mylaundry.Interface.MediaTypePicker;
import com.ingic.mylaundry.Interface.OnActivityResultInterface;
import com.ingic.mylaundry.R;
import com.ingic.mylaundry.constant.AppConstant;
import com.ingic.mylaundry.customViews.TitleBar;
import com.ingic.mylaundry.firebase.GcmDataObject;
import com.ingic.mylaundry.fragments.HomeFragment;
import com.ingic.mylaundry.fragments.NotificationFragment;
import com.ingic.mylaundry.fragments.OrderDetailFragment;
import com.ingic.mylaundry.fragments.OrderSummaryFragment;
import com.ingic.mylaundry.fragments.WelcomeFragment;
import com.ingic.mylaundry.fragments.baseClass.BaseFragment;
import com.ingic.mylaundry.helpers.GooglePlaceHelper;
import com.ingic.mylaundry.helpers.Utils;
import com.ingic.mylaundry.listener.LoadingListener;
import com.ingic.mylaundry.models.ScheduleOrderModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.utils.Orientation;
import id.zelory.compressor.Compressor;

public class MainActivity extends DockActivity implements LoadingListener {

    public boolean loading;
    Context context;
    @BindView(R.id.mTitleBar)
    public TitleBar mTitleBar;
    @BindView(R.id.mainFrameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.main_container)
    LinearLayout mainContainer;
    @BindView(R.id.progressBarContainer)
    FrameLayout progressBarContainer;
    OnActivityResultInterface onActivityResultInterface;
    MediaTypePicker mediaPickerListener;
    ArrayList<String> photoPaths;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    String actionType;
    String orderId;
//    public Realm realm ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        prefHelper.putScheduleOrder(new ScheduleOrderModel()); //Reset Schedule Order Model in Preference
        setAndBindTitleBar();


//        Configuration newConfig = new Configuration();
//        newConfig.locale = Locale.ENGLISH;
//        onConfigurationChanged(newConfig);

        //Clear Cart when app Kill
        prefHelper.putCartData(null);


//        realm = Realm.getDefaultInstance();
        context = this;
        //  if (prefHelper.getUser() != null)
        getGCMNotification();

    }


    private void getGCMNotification() {
        Bundle b = getIntent().getExtras();
        String actionType = "";
        String orderId = "";
        if (b != null && (b.getString("gcmObject") != null || (b.getString("ref_id") != null && b.getString("action_type") != null))) {
            if (b.getString("gcmObject") != null) {
                GcmDataObject data = new GsonBuilder().create().fromJson(b.getString("gcmObject"), GcmDataObject.class);
                actionType = data.getAction_type();
                orderId = data.getAction_id();
            } else if (b.getString("ref_id") != null && b.getString("action_type") != null) {
                actionType = b.getString("action_type");
                orderId = b.getString("ref_id");
            }
            if (actionType != null) {
                if (actionType.equals("order_status")) {
                    initFragments(new HomeFragment());
                    OrderDetailFragment detailFragment = new OrderDetailFragment();
                    detailFragment.setOrderId(orderId);
                    detailFragment.setComingFromNoti(true);
                    initFragments(detailFragment);
                } else if (actionType.equals("general")) {
                    initFragments(new HomeFragment());
                    initFragments(new NotificationFragment());
                } else {
                    initFragments(new HomeFragment());
                    initFragments(new NotificationFragment());
                }
            }
        } else {
            if (!prefHelper.getLoginStatus()) {
                initFragments(new WelcomeFragment());
            } else {
                popBackStackTillEntry(0);
                initFragments(new HomeFragment());
                prefHelper.putCartData(null);


            }
        }
    }


    @Override
    public void onBackPressed() {
        if (!loading) {
            super.onBackPressed();
        } else {
            Utils.showToast(this, getString(R.string.please_wait_data_loading));
        }
    }

    public void initFragments(BaseFragment frag) {
        Utils.hideKeyboard(this);
        addDockableSupportFragment(frag, frag.getClass().getName());
    }

    public void initFragments(BaseFragment frag, boolean addtoBackstack) {
        addDockableSupportFragment(frag, addtoBackstack);

    }

    public void addFragments(BaseFragment frag) {
        Utils.hideKeyboard(this);
        addDockableSupportFragmentReplace(frag, frag.getClass().getName());
    }

    @Override
    public int getDockFrameLayoutId() {
        return R.id.mainFrameLayout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GooglePlaceHelper.REQUEST_CODE_AUTOCOMPLETE:
                onActivityResultInterface.onActivityResultInterface(requestCode, resultCode, data);
                break;

            case FilePickerConst.REQUEST_CODE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    photoPaths = new ArrayList<>();
                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                    new AsyncTaskRunner().execute(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                }
                break;

            default:
                break;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private class AsyncTaskRunner extends AsyncTask<ArrayList<String>, ArrayList<File>, ArrayList<File>> {

        ProgressDialog progressDialog;

        @Override
        protected ArrayList<File> doInBackground(ArrayList<String>... params) {

            ArrayList<File> compressedAndVideoImageFileList = new ArrayList<>();

            for (int index = 0; index < params[0].size(); index++) {

                File file = new File(params[0].get(index));

                if (file.toString().endsWith(".jpg") ||
                        file.toString().endsWith(".jpeg") ||
                        file.toString().endsWith(".png") ||
                        file.toString().endsWith(".gif")) {
                    try {
                        File compressedImageFile = new Compressor(MainActivity.this).compressToFile(file, "compressed_" + file.getName());
                        compressedAndVideoImageFileList.add(compressedImageFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    compressedAndVideoImageFileList.add(file);
                }
            }
            return compressedAndVideoImageFileList;
        }

        @Override
        protected void onPostExecute(ArrayList<File> result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            mediaPickerListener.onPhotoClicked(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    context.getString(R.string.app_name),
                    context.getString(R.string.compressing_image_please_wait));

            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
        }


    }

    public void setOnActivityResultInterface(OnActivityResultInterface activityResultInterface) {
        this.onActivityResultInterface = activityResultInterface;
    }

    @Override
    public void onLoadingStarted() {

        if (progressBarContainer != null) {
            progressBarContainer.setVisibility(View.VISIBLE);
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }
            loading = true;

        }
    }

    @Override
    public void onLoadingFinished() {
        progressBarContainer.setVisibility(View.GONE);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        loading = false;
    }

    public void openImagePicker(final MediaTypePicker listener) {

        TedPermission.with(this)
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        mediaPickerListener = listener;
                        FilePickerBuilder.getInstance()
                                .setMaxCount(AppConstant.SELECT_IMAGE_COUNT)
                                //.setSelectedFiles(photoPaths)
                                .setActivityTheme(R.style.FilePickeTheme)
                                .enableVideoPicker(false)
                                .enableCameraSupport(true)
                                .showGifs(false)
                                .enableSelectAll(false)
                                .showFolderView(false)
                                .enableImagePicker(true)
                                .withOrientation(Orientation.UNSPECIFIED)
                                .pickPhoto(MainActivity.this);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> arrayList) {
                        Utils.showToast(context, context.getString(R.string.permission_denied));
                    }
                }).check();

    }

    private void setAndBindTitleBar() {
        mTitleBar.setVisibility(View.GONE);
    }

    public TitleBar getTitleBar() {
        return mTitleBar;
    }
    public void showImplementLater() {
        Utils.showToastInCenter(this, getString(R.string.will_be_implemented));

    }


}