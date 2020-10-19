package com.holy.simpleutils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    //权限动态申请

    public static void requestAllPower(Activity ctx) {
        if (ContextCompat.checkSelfPermission(ctx,  Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
         || ContextCompat.checkSelfPermission(ctx,  Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ctx,  Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ctx,  Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ctx,  Manifest.permission.MODIFY_AUDIO_SETTINGS)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ctx,  Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，返回 true。
            //if (ActivityCompat.shouldShowRequestPermissionRationale(ctx,  Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //} else {
                ActivityCompat.requestPermissions(ctx, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, "android.permission.MODIFY_AUDIO_SETTINGS",
                        "android.permission.RECORD_AUDIO", "android.permission.INTERNET"}, 1);
           // }
        }
    }
}
