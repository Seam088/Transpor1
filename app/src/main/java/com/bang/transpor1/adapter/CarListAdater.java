package com.bang.transpor1.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bang.transpor1.MainActivity;
import com.bang.transpor1.R;
import com.bang.transpor1.bean.Car;
import com.bang.transpor1.bean.Pubcar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CarListAdater extends BaseAdapter {
    ArrayList<Car.DataBean> carList = new ArrayList<>();
    Context context;
    private LayoutInflater inflater;
    //控制CheckBox选中情况
    private static HashMap<Integer, Boolean> isSelected;

    public CarListAdater(ArrayList<Car.DataBean> carList, Context context) {
        this.carList = carList;
        this.context = context;
        isSelected = new HashMap<Integer, Boolean>();
        initData();
    }

    //初始化isSelected的数据
    private void initData() {
        for (int i = 0; i < carList.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    @Override
    public int getCount() {
        return carList.size();
    }

    @Override
    public Object getItem(int position) {
        return carList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CarListItem carListItem = null;
        inflater = LayoutInflater.from(context);
        if (convertView == null) {
            carListItem = new CarListItem();
            convertView = inflater.inflate(R.layout.lv_item_accountmanage, null);
            carListItem.tv_carPlate = convertView.findViewById(R.id.tv_carPlate);
            carListItem.tv_account_carName = convertView.findViewById(R.id.tv_account_carName);
            carListItem.tv_amount = convertView.findViewById(R.id.tv_amount);
            carListItem.cb_account = convertView.findViewById(R.id.cb_account);
            carListItem.iv_account_carlog = convertView.findViewById(R.id.iv_account_carlog);
            carListItem.btn_account_recharge = convertView.findViewById(R.id.btn_account_recharge);
            convertView.setTag(carListItem);
        } else {
            carListItem = (CarListItem) convertView.getTag();
        }
        carListItem.tv_carPlate.setText(carList.get(position).getCarno() + "");
        carListItem.tv_account_carName.setText(carList.get(position).getWho() + "");
        carListItem.tv_amount.setText("余额:"+carList.get(position).getAmount() + "元");
        Integer[] imageUrl = new Integer[]{R.drawable.baoma, R.drawable.zhonghua, R.drawable.benchi, R.drawable.mazida, R.drawable.mazida};
        /*String[] carName = new String[]{"宝马", "中华", "奔驰", "马自达","斯柯达"};
       for (int i = 0; i < carList.size(); i++) {
            if (carName[i] == carList.get(position).getCarplate()) {
                carListItem.iv_account_carlog.setImageResource(imageUrl[i]);
            }
        }*/
        carListItem.iv_account_carlog.setImageResource(imageUrl[position]);
        // 根据isSelected来设置checkbox的选中状况
        carListItem.cb_account.setChecked(getIsSelected().get(position));
        carListItem.btn_account_recharge.setTag(position);
        //给Button添加单击事件 添加Button之后ListView将失去焦点 需要的直接把Button的焦点去掉
        carListItem.btn_account_recharge.setOnClickListener(new MyListener(position));
        /*carListItem.btn_account_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRechargeInfo();
            }
        });*/
        return convertView;
    }
    private class MyListener implements View.OnClickListener {
        int inPosition;
        public MyListener(int inPosition){
            this.inPosition = inPosition;
        }
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            showRechargeInfo();
            Toast.makeText(context, carList.get(inPosition).getCarplate(), Toast.LENGTH_SHORT).show();
        }

    }

    public class CarListItem {
        CheckBox cb_account;
        ImageView iv_account_carlog;
        Button btn_account_recharge;
        TextView tv_carPlate, tv_account_carName, tv_amount;

        public CheckBox getCb_account() {
            return cb_account;
        }
        public Button getBtn_account_recharge() {
            return btn_account_recharge;
        }
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        CarListAdater.isSelected = isSelected;
    }

    //充值按钮事件
    private void showRechargeInfo() {

        new AlertDialog.Builder(context)
                .setTitle("车辆账户充值")
                .setMessage("111")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

}
