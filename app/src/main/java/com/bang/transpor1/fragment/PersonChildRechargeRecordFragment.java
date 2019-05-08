package com.bang.transpor1.fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bang.transpor1.R;
import com.bang.transpor1.adapter.CarListAdater;
import com.bang.transpor1.bean.Chargelog;
import com.bang.transpor1.bean.MyInfo;
import com.bang.transpor1.request.BaseRequest;
import com.bang.transpor1.util.ToastUtils;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonChildRechargeRecordFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View view;
    private TextView tv_childrechargerecord_name, tv_childrechargerecord_totalPay;
    private ListView lv_childrechargerecord;
    private Context context;
    private Activity activity;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = getContext();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person_child_recharge_record, container, false);
        initView();
        getData();
        return view;
    }

    private void initView() {
        refreshLayout = view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setBackgroundResource(R.color.touming);

        tv_childrechargerecord_name = view.findViewById(R.id.tv_childrechargerecord_name);
        tv_childrechargerecord_totalPay = view.findViewById(R.id.tv_childrechargerecord_totalPay);

        lv_childrechargerecord = view.findViewById(R.id.lv_childrechargerecord);
    }

    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = BaseRequest.getRequest("http://hh1.me:5001/getchargelog");
                String str1 = BaseRequest.getRequest("http://hh1.me:5001/getmyinfo");
                Gson gson = new Gson();
                final Chargelog chargelog = gson.fromJson(str, Chargelog.class);
                final MyInfo myInfo = gson.fromJson(str1, MyInfo.class);
                if (chargelog == null && myInfo == null){
                    System.out.println("无数据~~~~~~");
                    return;
                }else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<Chargelog.DataBean> beanList = chargelog.getData();
                            int totalPay = 0;  //充值总数

                            for (int i = 0; i < beanList.size(); i++) {
                                totalPay += beanList.get(i).getCharge();
                            }

                            lv_childrechargerecord.setAdapter(new MyListAdapter(beanList));
                            tv_childrechargerecord_name.setText("" + myInfo.getName());
                            tv_childrechargerecord_totalPay.setText("" + totalPay + "元");
                        }
                    });
                }

            }
        }).start();
    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
                getData();
                ToastUtils.showToast(context,"充值记录刷新成功");
            }
        },3000);
    }

    private class MyListAdapter extends BaseAdapter {
        List<Chargelog.DataBean> beanList = null;
        public MyListAdapter(List<Chargelog.DataBean> beanList) {
            this.beanList = beanList;
        }

        @Override
        public int getCount() {
            return beanList.size();
        }

        @Override
        public Object getItem(int position) {
            return beanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListHolder listHolder = null;
            LayoutInflater inflater = LayoutInflater.from(context);
            if (listHolder == null) {
                listHolder = new ListHolder();
                convertView = inflater.inflate(R.layout.lv_item_child_rechargerecord, null);
                listHolder.tv_lv_childrechargerecord_time = convertView.findViewById(R.id.tv_lv_childrechargerecord_time);
                listHolder.tv_lv_childrechargerecord_timeWeek = convertView.findViewById(R.id.tv_lv_childrechargerecord_timeWeek);
                listHolder.tv_lv_childrechargerecord_rechargeName = convertView.findViewById(R.id.tv_lv_childrechargerecord_rechargeName);
                listHolder.tv_lv_childrechargerecord_carNO = convertView.findViewById(R.id.tv_lv_childrechargerecord_carNO);
                listHolder.tv_lv_childrechargerecord_rechargeNum = convertView.findViewById(R.id.tv_lv_childrechargerecord_rechargeNum);
                listHolder.tv_lv_childrechargerecord_balanceNum = convertView.findViewById(R.id.tv_lv_childrechargerecord_balanceNum);
                listHolder.tv_lv_childrechargerecord_rechargeTime = convertView.findViewById(R.id.tv_lv_childrechargerecord_rechargeTime);
                convertView.setTag(listHolder);
            } else {
                convertView.getTag();
            }

            String timeStr = beanList.get(position).getTime().substring(0,10);
            String week = getStringOfDateWeek(timeStr);
            listHolder.tv_lv_childrechargerecord_time.setText(""+timeStr);
            listHolder.tv_lv_childrechargerecord_timeWeek.setText(""+week);
            listHolder.tv_lv_childrechargerecord_rechargeName.setText("充值人 : " + beanList.get(position).getWho());
            listHolder.tv_lv_childrechargerecord_carNO.setText("车牌号 : " + beanList.get(position).getCarno());
            listHolder.tv_lv_childrechargerecord_rechargeNum.setText("充值 : " + beanList.get(position).getCharge()+"元");
            listHolder.tv_lv_childrechargerecord_balanceNum.setText("余额 : " + beanList.get(position).getAmount()+"元");
            listHolder.tv_lv_childrechargerecord_rechargeTime.setText("充值时间 : " + beanList.get(position).getTime());
            return convertView;
        }

        class ListHolder {
            TextView tv_lv_childrechargerecord_time;
            TextView tv_lv_childrechargerecord_timeWeek;
            TextView tv_lv_childrechargerecord_rechargeName;
            TextView tv_lv_childrechargerecord_carNO;
            TextView tv_lv_childrechargerecord_rechargeNum;
            TextView tv_lv_childrechargerecord_balanceNum;
            TextView tv_lv_childrechargerecord_rechargeTime;
        }
    }

    //String转化为Date类型，从而获取时间
    public String getStringOfDateWeek(String timeStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义日期格式
        Date date = null;
        try {
            date = format.parse(timeStr);// 将字符串转换为日期
        } catch (ParseException e) {
            System.out.println("输入的日期格式不合理！");
        }
        SimpleDateFormat formatWeek = new SimpleDateFormat("EEEE");// 星期几  定义日期格式
        String week = formatWeek.format(date);
        return week;
    }

}
