package com.bang.transpor1.fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bang.transpor1.R;
import com.bang.transpor1.adapter.LifeIndexListAdapter;
import com.bang.transpor1.bean.AirCondition;
import com.bang.transpor1.bean.Weather;
import com.bang.transpor1.request.BaseRequest;
import com.bang.transpor1.util.DischartsUtil;
import com.bang.transpor1.util.PureNetUtil;
import com.bang.transpor1.util.TempchartsUtil;
import com.bang.transpor1.util.ToastUtils;
import com.google.gson.Gson;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYSeries;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeftHelperFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    Context context;
    Activity activity;
    private View view;
    private GraphicalView graphicalView;
    private LinearLayout linearLayout;
    private List<Double> list;
    private XYSeries xySeries;
    private TempchartsUtil tempchartsUtil;
    private TextView tvTemp, tvMaxminTemp, tvWeek;
    private List<Integer> minTempList;
    private List<Integer> maxTempList;
    private List<String> weekList;
    private int indexWeekDay;  //今天市本周第几天
    private String[] arr;
    private SwipeRefreshLayout refresh_left_helper;
    private RecyclerView rv_lifeIndex;

    public LeftHelperFragment() {
        // Required empty public constructor
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

            }
        }
    };

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_left_helper, container, false);
        initView();
        getAirConditionData();
        getWeatherData();
        initData();
/*        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
//                initData();
//                tempchartsUtil.updateChart(maxTempList,minTempList,graphicalView);
            }
        }, 3000, 3000);*/
        return view;
    }

    private void initData() {
        minTempList = new ArrayList<>();
        maxTempList = new ArrayList<>();
        weekList = new ArrayList<>();
        arr = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String[] week1 = {"昨天", "今天", "明天"};
        Calendar calendar = Calendar.getInstance();  //获取当前时间
        indexWeekDay = calendar.get(calendar.DAY_OF_WEEK) - 1;  //今天市本周第几天
        minTempList.clear();
        maxTempList.clear();
        weekList.clear();
        for (int i = 0; i < 7; i++) {
/*            int maxTemp = (int) (Math.random() * 30);
            int minTemp = (int) (Math.random() * 20);
            minTempList.add(minTemp);
            maxTempList.add(maxTemp);*/
            if (i < 3) {
                weekList.add(week1[i]);
            }
        }
        int week4 = (indexWeekDay + 2) <= 6 ? indexWeekDay + 2 : (indexWeekDay + 2) % 7;
        int week5 = (indexWeekDay + 3) <= 6 ? indexWeekDay + 3 : (indexWeekDay + 3) % 7;
        int week6 = (indexWeekDay + 4) <= 6 ? indexWeekDay + 4 : (indexWeekDay + 4) % 7;
        int week7 = (indexWeekDay + 5) <= 6 ? indexWeekDay + 5 : (indexWeekDay + 5) % 7;
        weekList.add(arr[week4]);
        weekList.add(arr[week5]);
        weekList.add(arr[week6]);
        weekList.add(arr[week7]);
    }

    private void initView() {
        refresh_left_helper = view.findViewById(R.id.refresh_left_helper);
        refresh_left_helper.setOnRefreshListener(this);
        refresh_left_helper.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refresh_left_helper.setProgressBackgroundColor(R.color.refresh_bg);
        tvTemp = view.findViewById(R.id.tv_lh_temp);
        tvMaxminTemp = view.findViewById(R.id.tv_lh_maxmin_temp);
        tvWeek = view.findViewById(R.id.tv_lh_week);
        linearLayout = view.findViewById(R.id.ll_linechar);

        rv_lifeIndex = view.findViewById(R.id.rv_lifeIndex);
        LinearLayoutManager ms = new LinearLayoutManager(context);  // LinearLayoutMannager 是一个布局排列 ， 管理的接口,子类都都需要按照接口的规范来实现。
        ms.setOrientation(LinearLayout.HORIZONTAL); // 设置 recyclerview 布局方式为横向布局
        rv_lifeIndex.setLayoutManager(ms);  //给RecyClerView 添加设置好的布局样式
    }

    /*
    * 获取生活指数数据
    * */
    public void getAirConditionData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://hh1.me:5001/getAirCondition";
                String str = BaseRequest.getRequest(path);
                final AirCondition airCondition = new Gson().fromJson(str, AirCondition.class);
                if (activity == null) {
                    return;
                } else {
                    if (airCondition == null) {
                        return;
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final LifeIndexListAdapter lifeIndexListAdapter = new LifeIndexListAdapter(context, airCondition);  //初始化适配器
                                rv_lifeIndex.setAdapter(lifeIndexListAdapter);  // 对 recyclerview 添加数据内容
                                tvWeek.setText("星期" + arr[indexWeekDay].substring(1));
                            }
                        });
                    }
                }
            }
        }).start();
    }

    /*
    * 获取天气预报数据以及未来几天的数据
    * */
    public void getWeatherData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> weatherMap = new HashMap<>();
                weatherMap.put("cityname","株洲");    //	城市名或城市ID，如："苏州"，需要utf8 urlencode
                weatherMap.put("dtype","json");   //返回数据格式：json或xml,默认json
                weatherMap.put("format","2");       //	未来7天预报(future)两种返回格式，1或2，默认1

                String str =  PureNetUtil.get("http://v.juhe.cn/weather/index?cityname="+"株洲"+"&format=2&key=06b623bcdb664317593a938c38e61f5e");
                final Weather weather = new Gson().fromJson(str, Weather.class);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTemp.setText(weather.getResult().getSk().getTemp() + "℃");
                        List<Weather.ResultBean.FutureBean> maxMinTemp = weather.getResult().getFuture();
                        List<String> maxMinTempList = new ArrayList<>();
                        List<int[]> maxMinTempList1 = new ArrayList<>();
                        minTempList.clear();
                        maxTempList.clear();
                        for (int i = 0; i < maxMinTemp.size(); i++) {
                            if (i == 0){
                                maxMinTempList.add(weather.getResult().getToday().getTemperature());
                            }else {
                                maxMinTempList.add(maxMinTemp.get(i-1).getTemperature());
                            }
                            maxMinTempList1.add(AnalyticWeatherData(maxMinTempList.get(i)));
                            minTempList.add(maxMinTempList1.get(i)[0]);
                            maxTempList.add(maxMinTempList1.get(i)[1]);
                        }
                        tvMaxminTemp.setText("今天" + weather.getResult().getFuture().get(0).getTemperature());  // 13℃~16℃
                        setChartTest();  //设置画图
                    }
                });
//                ToastUtils.showThreadToast(activity, context, str+"");
            }
        }).start();
    }

    /*
    *  解析数据
    *  如：13℃~16℃   ==》 13 16
    * */
    private int[] AnalyticWeatherData(String temperature){
        String[]  strings = temperature.split("~");
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].substring(0, strings[i].length()-1);
//            System.out.println(strings[i]);
        }
        int minTemp = Integer.parseInt(strings[0]);
        int maxTemp = Integer.parseInt(strings[1]);
        return new int[]{minTemp, maxTemp};
    }

    private void setChartTest() {
        tempchartsUtil = new TempchartsUtil();
        graphicalView = ChartFactory.getLineChartView(context, tempchartsUtil.setDataSet(maxTempList, minTempList, weekList), tempchartsUtil.getRenderer());
        linearLayout.addView(graphicalView);
    }

    @Override
    public void onRefresh() {
        refresh_left_helper.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh_left_helper.setRefreshing(false);
                initData();
                tempchartsUtil.updateChart(maxTempList, minTempList, graphicalView);
            }
        }, 2000);
    }
}
