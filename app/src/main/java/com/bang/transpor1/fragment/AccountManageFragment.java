package com.bang.transpor1.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bang.transpor1.R;
import com.bang.transpor1.adapter.CarListAdater;
import com.bang.transpor1.bean.Car;
import com.bang.transpor1.bean.CarNoEvent;
import com.bang.transpor1.request.BaseRequest;
import com.bang.transpor1.util.ToastUtils;
import com.bang.transpor1.weight.PayDialogActivity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountManageFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,SwipeRefreshLayout.OnRefreshListener {
    private Activity activity;
    private Context context;
    private View view;
    private ListView lv_account;
    private CheckBox chekbox_all;
    private TextView tv_total_carprice;
    private TextView tv_total_carnumber;
    private TextView tv_go_to_recharge;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<Car.DataBean> carList;
    private Gson gson;
    private boolean checlAll;
    private int checkNum = 0; // 记录选中的条目数量
    private String defaul = "";//默认的全部不勾选
    private String selectall = "";//全部勾选
    private final String TAG = "DialogDate";
    private final int REQUEST_CODE = 0X2;
    private List<Car.DataBean> selectedData;
    private CarListAdater carListAdater;
    private ArrayList<String> carNoList;   //记录充值车牌号集合
    private List<Car.DataBean> checkCarList = new ArrayList<>();   //checkbox选中的数据集合
    private boolean iscjeboxAll;

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
        view = inflater.inflate(R.layout.fragment_account_manage, container, false);
        initView();
        initData();
        getCarData();
        return view;
    }

    private void initView() {
        refreshLayout = view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setProgressBackgroundColor(R.color.refresh_bg);

        lv_account = view.findViewById(R.id.lv_account);
        chekbox_all = view.findViewById(R.id.chekbox_all);
        //￥0.00
        tv_total_carprice = view.findViewById(R.id.total_carprice);
        //共有车辆:0辆
        tv_total_carnumber = view.findViewById(R.id.total_carnumber);
        chekbox_all = view.findViewById(R.id.chekbox_all);
        chekbox_all.setOnCheckedChangeListener(this);

        tv_go_to_recharge = view.findViewById(R.id.tv_go_to_recharge);
        tv_go_to_recharge.setOnClickListener(this);

    }

    private void initData() {

    }

    /*
    * 获取car的所有信息
    * */
    public void getCarData() {
        carList = new ArrayList();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = BaseRequest.getRequest("http://hh1.me:5001/carlist");
//                Log.e("Bang",str+"");
                gson = new Gson();
                final Car car = gson.fromJson(str, Car.class);
                if (car == null) {
                    System.out.println("当前没取到数据!");
                    return;
                }else {
                    carList = car.getData();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                        Log.e("Bang1", carList.toString() + "");
                            carListAdater = new CarListAdater(carList, context, activity);
                            lv_account.setAdapter(carListAdater);
                        }
                    });
                }
            }
        }).start();
    }


    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_go_to_recharge:
                if (!iscjeboxAll && carListAdater.getSelectedData() != null) {
                    checkCarList = carListAdater.getSelectedData();
                    carNoList = new ArrayList<>();
                    if (checkCarList != null || checkCarList.size() > 0) {
                        for (int i = 0; i < checkCarList.size(); i++) {
                            carNoList.add(checkCarList.get(i).getCarno());
//                            Log.e("Bang", carNoList.get(i));
                        }
                    }
                }else {
                    carNoList = new ArrayList<>();
                    for (int i = 0; i < checkCarList.size(); i++) {
                        carNoList.add(checkCarList.get(i).getCarno());
                        Log.e("Bang", carNoList.get(i));
                    }
                }
                if (carNoList != null && carNoList.size() > 0) {
//                EventBus.getDefault().post(new CarNoEvent(CarNoEvent.Type.TV_CARNO_SEND_MSG,carNoList));
                    Intent intent = new Intent(context, PayDialogActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putStringArrayListExtra("CarNoList", carNoList);
                    context.startActivity(intent);
                }else {
                    ToastUtils.showToast(context,"请至少选择一个用户");
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.chekbox_all:
                checkCarList.clear();
                iscjeboxAll = isChecked;
                if (isChecked) {
                    // 遍历list的长度，将MyAdapter中的map值全部设为true
                    for (int i = 0; i < carList.size(); i++) {
                        CarListAdater.getIsSelected().put(i, true);
                    }
                    // 数量设为list的长度
                    checkNum = carList.size();
                    checkCarList = carList;
                    // 刷新listview显示
                    dataChanged();
                } else {
                    // 遍历list的长度，将MyAdapter中的map值全部设为false
                    for (int i = 0; i < carList.size(); i++) {
                        CarListAdater.getIsSelected().put(i, false);
                    }
                    // 数量设为list的长度
                    checkNum = 0;
                    // 刷新listview显示
                    dataChanged();
                }
                break;
        }
    }

    // 刷新listview显示
    private void dataChanged() {
        // 通知listView刷新
        carListAdater.notifyDataSetChanged();
        // TextView显示最新的选中数目
//        tv_total_carnumber.setText("共有车辆:" + checkNum + "辆");  //这个功能还不完善，保存后再打开没把这个保存进去，会算错。
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("===========>"+"onResum");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("===========>"+"onStart");
        getCarData();
    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
                getCarData();
            }
        },3000);
    }
    
}