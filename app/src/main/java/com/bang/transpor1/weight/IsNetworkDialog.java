package com.bang.transpor1.weight;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.bang.transpor1.R;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by 12457 on 2018/11/7.
 */

public class IsNetworkDialog {
    public static void showNetWorkDlg(final Context context, Dialog mWifiDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (mWifiDialog == null) {
            mWifiDialog = builder.setIcon(R.drawable.no_network_32)
                    .setTitle("网络设置")
                    .setMessage("当前无网络")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 跳转到系统的网络设置界面
                            Intent intent = null;
                            // 先判断当前系统版本
                            if (android.os.Build.VERSION.SDK_INT > 10) {  // 3.0以上
                                intent = new Intent(Settings
                                        .ACTION_SETTINGS);
                            } else {
                                intent = new Intent();
                                intent.setClassName("com.android.settings",
                                        Settings.ACTION_SETTINGS);
                            }
                            if ((context instanceof Application)) {
                                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                            }
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton("知道了", null)
                    .create();
            // 设置为系统的Dialog，这样使用Application的时候不会 报错
            Window window = mWifiDialog.getWindow();
            window.setGravity(Gravity.CENTER); //Dialog在屏幕中间弹出来
            WindowManager.LayoutParams layoutParams = window.getAttributes();//获得布局属性
            layoutParams.width = 300; //设置Dialog的宽
            layoutParams.height = 200; //设置Dialog的高
            window.setWindowAnimations(R.style.MyDialogTranslate);
//            window.setBackgroundDrawableResource(R.color.transparent_70);   //设置Dialog背景颜色
            window.setBackgroundDrawableResource(R.drawable.dialog_bg_toumin);
//            window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        mWifiDialog.show();
    }
}
