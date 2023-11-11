package com.example.cognitive_diagnosis_app;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permission {
    private String[] permission={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private List<String> permissionList=new ArrayList<>();
    public int REQUEST_CODE=1000;

    public void checkPermission(Activity activity){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            for(int i=0;i<permission.length;i++){
                if (ContextCompat.checkSelfPermission(activity,permission[i])!= PackageManager.PERMISSION_GRANTED){
                    permissionList.add(permission[i]);
                }
            }
            if (permissionList.size()>0){
                requestPermission(activity);

            }
        }
    }
    private void requestPermission(Activity activity){
        ActivityCompat.requestPermissions(activity,permissionList.toArray(new String[permissionList.size()]),REQUEST_CODE);
    }

}
