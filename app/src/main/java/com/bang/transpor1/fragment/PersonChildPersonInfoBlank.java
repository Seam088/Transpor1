package com.bang.transpor1.fragment;


import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bang.transpor1.R;
import com.bang.transpor1.bean.MyInfo;
import com.bang.transpor1.request.BaseRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonChildPersonInfoBlank extends Fragment {


    private View view;
    private TextView tv_personalinfo_name, tv_personalinfo_sex, tv_personalinfo_tel, tv_personalinfo_sfz, tv_personalinfo_regtime;
    private Context context;
    private Activity activity;
    private GridView gv_personalinfo_car;

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
        view = inflater.inflate(R.layout.fragment_person_child_person_info_blank, container, false);

        initView();
        getData();   //从网络获取数据
        return view;
    }

    private void initView() {
        tv_personalinfo_name = view.findViewById(R.id.tv_personalinfo_name);
        tv_personalinfo_sex = view.findViewById(R.id.tv_personalinfo_sex);
        tv_personalinfo_tel = view.findViewById(R.id.tv_personalinfo_tel);
        tv_personalinfo_sfz = view.findViewById(R.id.tv_personalinfo_sfz);
        tv_personalinfo_regtime = view.findViewById(R.id.tv_personalinfo_regtime);

        gv_personalinfo_car = view.findViewById(R.id.gv_personalinfo_car);
    }

    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = BaseRequest.getRequest("http://hh1.me:5001/getmyinfo");
                Gson gson = new Gson();
                final MyInfo myInfo = gson.fromJson(str, MyInfo.class);
                if (activity == null) {
                    return;
                } else {
                    if (myInfo == null || "F".equals(myInfo.getRESULT())) {
                        Log.e("Person","数据获取失败");
                        return;
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_personalinfo_name.setText("姓名 : " + myInfo.getName());
                                tv_personalinfo_sex.setText("性别 : " + myInfo.getSex());
                                tv_personalinfo_tel.setText("电话 : " + myInfo.getPhoneTel());
                                tv_personalinfo_sfz.setText("身份证 : " + myInfo.getSfzNum());
                                tv_personalinfo_regtime.setText("注册时间 : " + myInfo.getRegTime());
                                List<String> nameCarList = myInfo.getNameCarList();
                                //初始化数据
                                List<Map<String, Object>> data = new ArrayList<>();
                                Integer[] images = new Integer[]{R.drawable.baoma, R.drawable.zhonghua, R.drawable.benci, R.drawable.mazida, R.drawable.sikeda};
                                for (int i = 0; i < nameCarList.size(); i++) {
                                    Map<String, Object> map = new HashMap<>();
                                    //如果只需要显示图片，可以不用这一行，需要同时将from和to中的相关内容删去
                                    map.put("ItemImage", images[i]);
                                    map.put("ItemText", nameCarList.get(i));
                                    data.add(map);
                                }
                                String[] from = {"ItemImage", "ItemText"};
                                int[] to = {R.id.iv_gv_car, R.id.tv_gv_carname};
                                SimpleAdapter simpleAdapter = new SimpleAdapter(context, data, R.layout.gv_item_gv_personalinfo, from, to);
                                gv_personalinfo_car.setAdapter(simpleAdapter);
                            }
                        });
                    }
                }

            }
        }).start();
    }

}
