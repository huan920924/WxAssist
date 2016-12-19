package com.lh.wxassist;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * -----------------------------------------------------------------------------------Author Info---
 * Company Name:          xjyy.
 * Author:                Liu Huan.
 * Email:                 771383629@qq.com.
 * Date:                  16-12-12 上午11:32.
 * -----------------------------------------------------------------------------------Message-------
 * If the following code to run properly, it is coding by Liu Huan.
 * otherwise I don't know.
 * -----------------------------------------------------------------------------------Class Info----
 * ClassName:             com.lh.wxassist.
 * -----------------------------------------------------------------------------------Describe------
 * Function:
 * -----------------------------------------------------------------------------------Modify--------
 * 16-12-12 上午11:32     Modified By liuhuan.
 * -----------------------------------------------------------------------------------End-----------
 */
public class AdminReceiver extends DeviceAdminReceiver {

    @Override
    public DevicePolicyManager getManager(Context context) {
        return super.getManager(context);
    }

    @Override
    public ComponentName getWho(Context context) {
        return super.getWho(context);
    }

    /**
     * 禁用
     */
    @Override
    public void onDisabled(Context context, Intent intent) {

        Toast.makeText(context, "禁用设备管理", Toast.LENGTH_SHORT).show();

        super.onDisabled(context, intent);
    }
    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return super.onDisableRequested(context, intent);
    }

    /**
     * 激活
     */
    @Override
    public void onEnabled(Context context, Intent intent) {

        Toast.makeText(context, "启动设备管理", Toast.LENGTH_SHORT).show();

        super.onEnabled(context, intent);
    }
    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        super.onPasswordChanged(context, intent);
    }
    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        super.onPasswordFailed(context, intent);
    }
    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        super.onPasswordSucceeded(context, intent);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);


    }
    @Override
    public IBinder peekService(Context myContext, Intent service) {
        Log.d("AdminReceiver ,","------" + "peekService" + "------");
        return super.peekService(myContext, service);
    }

}
