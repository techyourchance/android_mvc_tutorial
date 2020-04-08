package com.techyourchance.android_mvc_tutorial.common.permissions;

import android.content.pm.PackageManager;

import com.techyourchance.android_mvc_tutorial.common.BaseObservable;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

@UiThread
public class PermissionsHelper extends BaseObservable<PermissionsHelper.Listener> {

    public interface Listener {
        void onPermissionGranted(MyPermission permission);
        void onPermissionDenied(MyPermission permission);
        void onPermissionDeniedAndDontAskAgain(MyPermission permission);
        void onPermissionRequestCancelled();
    }

    public static final int REQUEST_CODE = 1001;

    private final AppCompatActivity mActivity;

    public PermissionsHelper(AppCompatActivity activity) {
        mActivity = activity;
    }

    public boolean hasPermission(MyPermission permission) {
        return ContextCompat.checkSelfPermission(mActivity, permission.getCode()) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(MyPermission permission) {
        if (hasPermission(permission)) {
            throw new RuntimeException("mustn't ask for already granted permission");
        }
        if (isDeniedAndDontAskAgain(permission)) {
            notifyDeniedAndDontAskAgain(permission);
            return;
        }
        ActivityCompat.requestPermissions(mActivity, new String[] {permission.getCode()}, REQUEST_CODE);
    }

    private boolean isDeniedAndDontAskAgain(MyPermission permission) {
        return !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission.getCode());
    }

    public void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
        if (permissions.length == 0) {
            notifyCancelled();
            return;
        }

        MyPermission permission = permissionFromCode(permissions[0]);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            notifyGranted(permission);
        } else {
            if (isDeniedAndDontAskAgain(permission)) {
                notifyDeniedAndDontAskAgain(permission);
            } else {
                notifyDenied(permission);
            }
        }
    }

    private MyPermission permissionFromCode(String permissionCode) {
        for (MyPermission permission : MyPermission.values()) {
            if (permission.getCode().equals(permissionCode)) {
                return permission;
            }
        }
        throw new RuntimeException("unsupported permission: " + permissionCode);
    }

    private void notifyGranted(MyPermission permission) {
        for (Listener listener : getListeners()) {
            listener.onPermissionGranted(permission);
        }
    }

    private void notifyDenied(MyPermission permission) {
        for (Listener listener : getListeners()) {
            listener.onPermissionDenied(permission);
        }
    }

    private void notifyDeniedAndDontAskAgain(MyPermission permission) {
        for (Listener listener : getListeners()) {
            listener.onPermissionDeniedAndDontAskAgain(permission);
        }
    }

    private void notifyCancelled() {
        for (Listener listener : getListeners()) {
            listener.onPermissionRequestCancelled();
        }
    }
}
