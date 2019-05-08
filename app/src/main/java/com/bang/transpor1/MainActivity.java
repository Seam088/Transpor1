package com.bang.transpor1;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bang.transpor1.fragment.AccountManageFragment;
import com.bang.transpor1.fragment.BusFragment;
import com.bang.transpor1.fragment.CarFragment;
import com.bang.transpor1.fragment.ExitLogonFragment;
import com.bang.transpor1.fragment.LeftHelperFragment;
import com.bang.transpor1.fragment.MyJournalFragment;
import com.bang.transpor1.fragment.PersonalFragment;
import com.bang.transpor1.util.NetworkUtils;
import com.bang.transpor1.util.ToastUtils;
import com.bang.transpor1.weight.CommonDialog;
import com.slidingmenu.lib.SlidingMenu;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SlidingMenu slidingMenu;
    private TextView tv_title;
    private FrameLayout frameLayout;
    private FragmentTransaction fragmentTransaction;
    private ImageButton imgbtn_leftMenu;
    private static Boolean isQuit = false;
    private Timer timer = new Timer();
    private android.widget.Toast Toast;
    private long firstTime = 0;
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    private static Dialog mWifiDialog = null;   //没有网络是的wifiDialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
        initView();
        showSliding();
    }

    private void initView() {
        imgbtn_leftMenu = findViewById(R.id.imgbtn_leftMenu);
        imgbtn_leftMenu.setOnClickListener(this);
        tv_title = findViewById(R.id.tv_title);

        Button iv_menu = findViewById(R.id.iv_menu);
        registerForContextMenu(iv_menu);

        frameLayout = findViewById(R.id.framelayout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framelayout, new CarFragment());
        fragmentTransaction.commit();  //提交事务
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int message = getIntent().getIntExtra("message", 0);
        if (message == 0) {
            frameLayout.removeAllViews();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.framelayout, new AccountManageFragment());
            fragmentTransaction.commit();  //提交事务
        }
    }

    private void showSliding() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);  //从左滑出
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.slidingmenu_layout);  //设置侧滑栏的布局文件
        slidingMenu.setBehindOffsetRes(R.dimen.dimens);

        final String[] titles = new String[]{"账户管理", "我的交通", "我的日志", "公交查询", "个人中心", "生活助手", "我的路况", "退出登录"};
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item, R.id.text, titles);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frameLayout.removeAllViews();
                registerReceiver(networkChangeReceiver, intentFilter);
                switch (position) {
                    case 0:  //账户管理
                        fragmentTransaction.replace(R.id.framelayout, new AccountManageFragment());
                        break;
                    case 1:  //我的交通
                        break;
                    case 2:  //我的日志
                        fragmentTransaction.replace(R.id.framelayout, new MyJournalFragment());
                        break;
                    case 3:  //公交查询
                        fragmentTransaction.replace(R.id.framelayout, new BusFragment());
                        break;
                    case 4: //个人中心
                        fragmentTransaction.replace(R.id.framelayout, new PersonalFragment());
//                        NetworkUtils.GoSetting(MainActivity.this);
                        break;
                    case 5:  //生活助手
                        fragmentTransaction.replace(R.id.framelayout, new LeftHelperFragment());
                        break;
                    case 6:  //我的路况
                        break;
                    case 7:  //退出登录
//                        fragmentTransaction.replace(R.id.framelayout, new ExitLogonFragment());
                        exitLogin();
                        break;
                }
                tv_title.setText(titles[position]);
                slidingMenu.toggle();
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtn_leftMenu:
                slidingMenu.toggle();
                break;
        }
    }

    @Override
    public void onBackPressed() {

/*        // 点击返回键关闭滑动菜单
        if (slidingMenu.isMenuShowing()) {
            slidingMenu.showContent();
        } else {
            super.onBackPressed();
        }*/

        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        /*if (isQuit == false) {
            isQuit = true;
            Toast.makeText(getBaseContext(), "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();

            timer.schedule( new TimerTask() {
                @Override
                public void run() {
                    isQuit = false;
                }
            },2000);
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }


    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);   //专门管理网络连接的
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            //networkInfo.isAvailable()  判断是否有网络
            if (networkInfo != null && networkInfo.isAvailable()) {
//                Toast.makeText(MainActivity.this, "network is available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "网络不给力，请检查网络设置", Toast.LENGTH_SHORT).show();
                isNetWorkDialog(MainActivity.this);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                ToastUtils.showToast(this, "add");
                break;
            case R.id.remove_item:
                ToastUtils.showToast(this, "remove");
                break;
        }
        return true;
    }


    /*
    *    设置网络Dialog
    * */
    private void isNetWorkDialog(final Context context) {
        final CommonDialog dialog = new CommonDialog(MainActivity.this);
        dialog.setMessage("当前无网络")
                .setImageResId(R.drawable.no_network_32)
//                .setTitle("系统提示")
                .setSingle(false)
                .setPositive("设置")
                .setNegtive("取消")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
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

                    @Override
                    public void onNegtiveClick() {
                        dialog.dismiss();
                    }
                }).show();
    }

    /*
    *   退出登录Dialog
    * */
    private void exitLogin() {
//        Dialog.
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_exitlogon, null);

        if (view.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            viewGroup.removeView(view);
        }
        /*final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("退出登录")
                .setNegativeButton("取消退出", null)
                .setPositiveButton("退出登录", null)
                .show();
        alertDialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });*/
        final CommonDialog commonDialog = new CommonDialog(MainActivity.this);
        commonDialog.setMessage("是否退出登录")
                .setPositive("退出登录")
                .setNegtive("取消")
                .setSingle(false)
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        commonDialog.dismiss();
                    }

                    @Override
                    public void onNegtiveClick() {
                        commonDialog.dismiss();
                    }
                }).show();
    }

}
