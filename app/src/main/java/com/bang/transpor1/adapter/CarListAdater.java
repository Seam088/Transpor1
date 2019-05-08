package com.bang.transpor1.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bang.transpor1.R;
import com.bang.transpor1.bean.Car;
import com.bang.transpor1.util.ToastUtils;
import com.bang.transpor1.weight.PayDialogActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CarListAdater extends BaseAdapter {
    private ArrayList<Car.DataBean> carList;
    private Context context;
    private Activity activity;
    private LayoutInflater inflater;
    public LinkedHashMap<Integer, Car.DataBean> cbMap = new LinkedHashMap<>();
    //控制CheckBox选中情况
    private static LinkedHashMap<Integer, Boolean> isSelected;

    public CarListAdater(ArrayList<Car.DataBean> carList, Context context ,Activity activity) {
        this.carList = carList;
        this.context = context;
        this.activity = activity;
        isSelected = new LinkedHashMap<Integer, Boolean>();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CarListHolder carListHolder;
        inflater = LayoutInflater.from(context);
        if (convertView == null) {
            carListHolder = new CarListHolder();
            convertView = inflater.inflate(R.layout.lv_item_accountmanage, null);
            carListHolder.tv_carPlate = convertView.findViewById(R.id.tv_carPlate);
            carListHolder.tv_account_carName = convertView.findViewById(R.id.tv_account_carName);
            carListHolder.tv_amount = convertView.findViewById(R.id.tv_amount);
            carListHolder.cb_account = convertView.findViewById(R.id.cb_account);
            carListHolder.iv_account_carlog = convertView.findViewById(R.id.iv_account_carlog);
            carListHolder.btn_account_recharge = convertView.findViewById(R.id.btn_account_recharge);
            convertView.setTag(carListHolder);
        } else {
            carListHolder = (CarListHolder) convertView.getTag();
        }
        Integer[] imageUrl = new Integer[]{R.drawable.baoma, R.drawable.zhonghua, R.drawable.benci, R.drawable.mazida, R.drawable.sikeda};

        carListHolder.tv_carPlate.setText(carList.get(position).getCarno() + "");
        carListHolder.tv_account_carName.setText(carList.get(position).getWho() + "");
        carListHolder.tv_amount.setText("余额:" + carList.get(position).getAmount() + "元");

        carListHolder.iv_account_carlog.setImageResource(imageUrl[position]);
        carListHolder.btn_account_recharge.setTag(position);
        //给Button添加单击事件 添加Button之后ListView将失去焦点 需要的直接把Button的焦点去掉
        carListHolder.btn_account_recharge.setOnClickListener(new MyListener(position));
        // 根据isSelected来设置checkbox的选中状况
        carListHolder.cb_account.setChecked(getIsSelected().get(position));
        carListHolder.cb_account.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isSelected.get(position)) {
                    isSelected.put(position, false);
                    cbMap.remove(position);
                } else {
                    isSelected.put(position, true);
                    cbMap.put(position, carList.get(position));
                }
                if(cbMap.size() > 4){
                    carListHolder.cb_account.toggle();
                    ToastUtils.showToast(context,"最多选择四个");
                    cbMap.remove(position);
                    isSelected.put(position, false);
                }
            }
        });
        return convertView;
    }

    private class MyListener implements View.OnClickListener {
        int inPosition;

        public MyListener(int inPosition) {
            this.inPosition = inPosition;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            String carno = carList.get(inPosition).getCarno();
            Intent intent = new Intent(context, PayDialogActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("CarNo", carno);
            context.startActivity(intent);
            Toast.makeText(context, carList.get(inPosition).getCarno(), Toast.LENGTH_SHORT).show();
        }
    }

    public class CarListHolder {
        CheckBox cb_account;
        ImageView iv_account_carlog;
        Button btn_account_recharge;
        TextView tv_carPlate, tv_account_carName, tv_amount;
    }

    public static LinkedHashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    //供activity获取选中的数据
    public List<Car.DataBean> getSelectedData(){
        if(cbMap.size() > 0){
            List<Car.DataBean> mSelectedData = new ArrayList<>();
            for (Integer key : cbMap.keySet()) {
                mSelectedData.add(carList.get(key));
            }
            return mSelectedData;
        }else{
            return null;
        }
    }

}
