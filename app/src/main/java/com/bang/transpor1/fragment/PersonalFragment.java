package com.bang.transpor1.fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bang.transpor1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment implements View.OnClickListener{


    private View view;
    private TextView tv_personal_info, tv_recharge_record, tv_yuzhi_set;
    private FrameLayout frameLayout;
    private FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal, container, false);

        initView();  //初始化视图
        return view;
    }

    private void initView() {
        tv_personal_info = view.findViewById(R.id.tv_personal_info);
        tv_recharge_record = view.findViewById(R.id.tv_recharge_record);
        tv_yuzhi_set = view.findViewById(R.id.tv_yuzhi_set);
        tv_personal_info.setOnClickListener(this);
        tv_recharge_record.setOnClickListener(this);
        tv_yuzhi_set.setOnClickListener(this);
        tv_personal_info.setSelected(true);  //默认选中Z+
        frameLayout = view.findViewById(R.id.fragment_child_personal);

        FragmentManager childFragmentManager = getChildFragmentManager();
        fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_child_personal,new PersonChildPersonInfoBlank());  //加载进来默认添加个人信息碎片

        fragmentTransaction.commit();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        frameLayout.removeAllViews();
        switch (v.getId()){
            case R.id.tv_personal_info:   //个人信息
                fragmentTransaction.replace(R.id.fragment_child_personal,new PersonChildPersonInfoBlank());
                change_tv_color_Select(R.id.tv_personal_info);
                break;
            case R.id.tv_recharge_record:  //充值查询
                fragmentTransaction.replace(R.id.fragment_child_personal,new PersonChildRechargeRecordFragment());
                change_tv_color_Select(R.id.tv_recharge_record);
                break;
            case R.id.tv_yuzhi_set:   //阈值设置
                fragmentTransaction.replace(R.id.fragment_child_personal,new PersonChildYuzhiSetFragment());
                change_tv_color_Select(R.id.tv_yuzhi_set);
                break;
        }
        fragmentTransaction.commit();
    }

    //改变textView选中的颜色
    private void change_tv_color_Select(int resId){
        tv_personal_info.setSelected(false);
        tv_recharge_record.setSelected(false);
        tv_yuzhi_set.setSelected(false);
        switch (resId){
            case R.id.tv_personal_info:
                tv_personal_info.setSelected(true);
                break;
            case R.id.tv_recharge_record:
                tv_recharge_record.setSelected(true);
                break;
            case R.id.tv_yuzhi_set:
                tv_yuzhi_set.setSelected(true);
                break;
        }
    }

}
