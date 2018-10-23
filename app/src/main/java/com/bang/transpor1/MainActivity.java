package com.bang.transpor1;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bang.transpor1.fragment.AccountManageFragment;
import com.bang.transpor1.fragment.BusFragment;
import com.bang.transpor1.fragment.CarFragment;
import com.bang.transpor1.fragment.ExitLogonFragment;
import com.bang.transpor1.weight.NewMessageNotification;
import com.bang.transpor1.weight.PayDialog;
import com.slidingmenu.lib.SlidingMenu;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SlidingMenu slidingMenu;
    private TextView tv_title;
    private FrameLayout frameLayout;
    private FragmentTransaction fragmentTransaction;
    private ImageButton imgbtn_leftMenu;
    private static Boolean isQuit = false;
    private Timer timer = new Timer();
    private android.widget.Toast Toast;
    private long firstTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        showSliding();
    }

    private void initView() {
        imgbtn_leftMenu = findViewById(R.id.imgbtn_leftMenu);
        imgbtn_leftMenu.setOnClickListener(this);
        tv_title = findViewById(R.id.tv_title);
        frameLayout = findViewById(R.id.framelayout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framelayout,new CarFragment());
        fragmentTransaction.commit();
    }

    private void showSliding() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);  //从左滑出
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.slidingmenu_layout);  //设置侧滑栏的布局文件
        slidingMenu.setBehindOffsetRes(R.dimen.dimens);


        final String[] titles = new String[]{"账户管理","我的交通","我的日志","公交查询","我的消息","我的租车","我的路况","退出登录"};
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.item,R.id.text,titles);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frameLayout.removeAllViews();
                switch (position){
                    case 0:  //账户管理
                        fragmentTransaction.replace(R.id.framelayout,new AccountManageFragment());
                        break;
                    case 1:  //我的交通
                        break;
                    case 2:  //我的日志
                        break;
                    case 3:  //公交查询
                        fragmentTransaction.replace(R.id.framelayout,new BusFragment());
                        break;
                    case 4: //我的消息
                        break;
                    case 5:  //我的租车
                        break;
                    case 6:  //我的路况
                        break;
                    case 7:  //退出登录
                        fragmentTransaction.replace(R.id.framelayout,new ExitLogonFragment());
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
        switch (v.getId()){
            case R.id.imgbtn_leftMenu:
                slidingMenu.toggle();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if(secondTime-firstTime>2000){
            Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
            firstTime=secondTime;
        }else{
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
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }

}
