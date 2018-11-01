package com.jackson.processcommunication;

import android.Manifest;
import android.app.Activity;


import android.content.pm.PackageManager;

import android.support.v4.app.ActivityCompat;

import android.util.Log;



import java.util.ArrayList;


/**
 * Created by qiurui on 2016/11/30.
 */
public class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getSimpleName();
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 0;
    public static final int CODE_RECORD_AUDIO = 2;
    public static final int CODE_TAKE_CAMERA = 1;
    public static final int CODE_MULTI_PERMISSION = 100;
    public static final int CODE_COARSE_LOCATION = 4;
    public static final int RCODE_UM_SHARE = 123;  //友盟分享权限申请码
    public static final int H5_PERMISSION_CAMERA = 124;  //友盟分享权限申请码

    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_READ_SMS = Manifest.permission.READ_SMS;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_MOUNT_UNMOUNT_FILESYSTEMS=Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS;
    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_READ_LOGS = Manifest.permission.READ_LOGS;
    public static final String PERMISSION_SET_DEBUG_APP = Manifest.permission.SET_DEBUG_APP;
    public static final String PERMISSION_SYSTEM_ALERT_WINDOW = Manifest.permission.SYSTEM_ALERT_WINDOW;
    public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_WRITE_APN_SETTINGS = Manifest.permission.WRITE_APN_SETTINGS;





    public static final String[] shareRequestPermissions = {
            PERMISSION_WRITE_EXTERNAL_STORAGE,
            PERMISSION_READ_EXTERNAL_STORAGE

    };




}