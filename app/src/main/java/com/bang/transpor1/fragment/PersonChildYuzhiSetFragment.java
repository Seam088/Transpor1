package com.bang.transpor1.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bang.transpor1.MainActivity;
import com.bang.transpor1.R;
import com.bang.transpor1.adapter.CarListAdater;
import com.bang.transpor1.adapter.PubcalListAdater;
import com.bang.transpor1.bean.Car;
import com.bang.transpor1.request.BaseRequest;
import com.bang.transpor1.util.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonChildYuzhiSetFragment extends Fragment implements View.OnClickListener {


    private Activity activity;
    private Context context;
    private View view;
    private TextView tv_yuzhi;
    private EditText et_yuzhi;
    private List<Car.DataBean> carList;   //所有车辆的信息

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = getContext();
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_person_child_yuzhi_set, container, false);
        initView();  //初始化视图
        getData();  //获取网络数据carList
        return view;
    }

    private void initView() {
        tv_yuzhi = view.findViewById(R.id.tv_yuzhi);
        et_yuzhi = view.findViewById(R.id.et_yuzhi);
        Button btn_yuzhi_set = view.findViewById(R.id.btn_yuzhi_set);
        btn_yuzhi_set.setOnClickListener(this);

        tv_yuzhi.setText("当前小车账户余额告警阈值未设置！");
    }

    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = BaseRequest.getRequest("http://hh1.me:5001/carlist");
                final Car car = new Gson().fromJson(str, Car.class);
                if (car == null) {
                    System.out.println("网络获取数据失败！！！");
                } else {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            List<Integer> amountList = new ArrayList<>();  //所有车辆的余额
                            if (car != null) {
                                //所有车辆的信息
                                carList = car.getData();
                                PubcalListAdater pubcalListAdater = new PubcalListAdater(context, 1);
                                pubcalListAdater.notifyDataSetChanged();
                                for (int i = 0; i < car.getData().size(); i++) {
                                    amountList.add(car.getData().get(i).getAmount());
                                    System.out.println(amountList.get(i));
                                }
                            }
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yuzhi_set:
                String yuzhiStr = et_yuzhi.getText().toString().trim();
                int yuzhi = Integer.parseInt(yuzhiStr);
                tv_yuzhi.setText("当前小车账户余额告警阈值为 " + yuzhi + " 元");
                if (carList != null) {
                    for (int i = 0; i < carList.size(); i++) {
                        if (carList.get(i).getAmount() < yuzhi) {
//                        ToastUtils.showToast(context,"车牌号:"+carList.get(i).getCarno()+"\n余额："+carList.get(i).getAmount()+"元\n阈值："+yuzhi+"元");
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("message", 10);//传递参数，然后根据参数进行判断需要跳转的fragment界面
                            PendingIntent pi = PendingIntent.getActivity(activity, 0, intent, 0);
                            NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                            @SuppressLint("WrongConstant")
                            Notification notification = new Notification.Builder(context)
                                    .setContentTitle("账户余额不足")
                                    .setContentText("车牌号:" + carList.get(i).getCarno() + "\n 余额：" + carList.get(i).getAmount() + "元 \n 阈值：" + yuzhi + "元")
                                    .setWhen(System.currentTimeMillis())
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentIntent(pi)
                                    .setAutoCancel(true)  //点击消失
                                  /*  .setVibrate(new long[]{0, 1000, 1000, 1000}) //使震动 数组表示 静止0秒，震动1秒 静止1秒 震动1秒
                                    .setLights(Color.GREEN, 1000, 1000)//设置呼吸灯 参数：颜色  亮起时长 暗去时长*/
                                    .setDefaults(NotificationCompat.DEFAULT_ALL)//设置默认
                                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                    .setPriority(NotificationCompat.PRIORITY_MAX)//设置重要程度： PRIORITY_DEFAULT （表示默认）PRIORITY_MIN(表示最低) PRIORITY_LOW （较低）PRIORITY_HIGH （较高）PRIORITY_MAX（最高）
                                    .build();
                            notificationManager.notify(i, notification);
                            new NotificationCompat.Builder(context);
                        }
                    }
                }
                break;
        }
    }


}
