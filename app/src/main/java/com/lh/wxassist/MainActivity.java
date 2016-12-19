package com.lh.wxassist;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    ComponentName mPlcManagerCn;
    DevicePolicyManager mPolicyManager;
    @InjectView(R.id.btn_am_service_action)
    Button btnAmServiceAction;
    @InjectView(R.id.btn_am_device_admin)
    Button btnAmDeviceAdmin;
    @InjectView(R.id.activity_main)
    LinearLayout activityMain;
    private String TAG = "AutoRS";
    // 连接对象
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("Service", "连接成功！");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("Service", "断开连接！");
        }
    };
    private String serviceName = "com.lh.wxassist.AutoReplyService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        btnAmServiceAction.setText(isServiceWork(this, serviceName) ? "停止自动回复" : "开启自动回复");

        //获取设备管理服务
        mPolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        //AdminReceiver 继承自 DeviceAdminReceiver

        mPlcManagerCn = new ComponentName(this, AdminReceiver.class);
//
        btnAmDeviceAdmin.setText(mPolicyManager.isAdminActive(mPlcManagerCn) ? "已经是设备管理器" : "设置为设备管理器");
    }

    /**
     * 激活设备管理器
     */
    private void activeAdminManager(ComponentName cn) {

        // 启动设备管理(隐式Intent) - 在AndroidManifest.xml中设定相应过滤器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

        //权限列表
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);

        //描述(additional explanation)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.device_des));

        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("INFO", "requestCode : " + requestCode + "   resultCode:" + resultCode);
    }

    public boolean isServiceWork(Context context, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(200);
        if (myList.size() < 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    @OnClick({R.id.btn_am_service_action, R.id.btn_am_device_admin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_am_service_action:
                Intent intent = new Intent(MainActivity.this, AutoReplyService.class);

                if (!isServiceWork(MainActivity.this, serviceName)) {
                    startService(intent);
                } else {
                    Log.e("--"," stopService(intent);");
                    stopService(intent);

                }
                btnAmServiceAction.setText(isServiceWork(this, serviceName) ? "停止自动回复" : "开启自动回复");
                break;
            case R.id.btn_am_device_admin:
                if (mPolicyManager.isAdminActive(mPlcManagerCn)) {
                    Toast.makeText(this, "已经取得设备管理", Toast.LENGTH_SHORT).show();
                } else {
                    activeAdminManager(mPlcManagerCn);
                }
                break;
        }
    }
}
