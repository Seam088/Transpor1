package com.bang.transpor1.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.bang.transpor1.R;
import com.bang.transpor1.adapter.CarListAdater;
import com.bang.transpor1.bean.Car;
import com.bang.transpor1.request.BaseRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountManageFragment extends Fragment {
    private Activity activity;
    private Context context;
    private View view;
    private ListView lv_account;
    private CheckBox chekbox_all;
    private TextView tv_total_carprice;
    private TextView tv_total_carnumber;
    private ArrayList<Car.DataBean> carList = new ArrayList();
    ;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
            }
        }
    };
    private Gson gson;

    public AccountManageFragment() {
        // Required empty public constructor
    }

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
        getData();
        return view;
    }

    private void initView() {
        lv_account = view.findViewById(R.id.lv_account);
        chekbox_all = view.findViewById(R.id.chekbox_all);
        //￥0.00
        tv_total_carprice = view.findViewById(R.id.total_carprice);
        //共有车辆:0辆
        tv_total_carnumber = view.findViewById(R.id.total_carnumber);
    }

    private void initData() {

    }

    /*
    * 获取car的所有信息
    * */
    public void getData() {
        new Thread(new Runnable() {

            private Car car;

            @Override
            public void run() {
                String str = BaseRequest.getRequest("http://hh1.me:5001/carlist");
//                Log.e("Bang",str+"");
                gson = new Gson();
                car = new Car();
                car = gson.fromJson(str, Car.class);
                carList = car.getData();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Log.e("Bang1", carList.toString() + "");
                        lv_account.setAdapter(new CarListAdater(carList, context));
                        lv_account.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
                                CarListAdater.CarListItem viewHolder = (CarListAdater.CarListItem) view.getTag();
                                viewHolder.getCb_account().toggle();// 把CheckBox的选中状态改为当前状态的反,gridview确保是单一选中
                                CarListAdater.getIsSelected().put(position, viewHolder.getCb_account().isChecked());//将CheckBox的选中状况记录下来
                            }
                        });
                    }
                });
            }
        }).start();
    }

}