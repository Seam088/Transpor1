package com.bang.transpor1.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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
import com.bang.transpor1.util.ToastUtils;
import com.bang.transpor1.weight.PayDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CarListAdater extends BaseAdapter {
    ArrayList<Car.DataBean> carList;
    Context context;
    private LayoutInflater inflater;
    public LinkedHashMap<Integer, Car.DataBean> cbMap = new LinkedHashMap<>();
    //控制CheckBox选中情况
    private static LinkedHashMap<Integer, Boolean> isSelected;
    private OnItemClickListener onItemClickListener;
    private int inPosition;
    private List<Integer> poList = new ArrayList<>();
    private List<String> carNoList = new ArrayList<>() ;   //记录充值车牌号集合

    public CarListAdater(ArrayList<Car.DataBean> carList, Context context) {
        this.carList = carList;
        this.context = context;
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
        carListHolder.tv_carPlate.setText(carList.get(position).getCarno() + "");
        carListHolder.tv_account_carName.setText(carList.get(position).getWho() + "");
        carListHolder.tv_amount.setText("余额:" + carList.get(position).getAmount() + "元");
        Integer[] imageUrl = new Integer[]{R.drawable.baoma, R.drawable.zhonghua, R.drawable.benchi, R.drawable.mazida, R.drawable.mazida};
        /*String[] carName = new String[]{"宝马", "中华", "奔驰", "马自达","斯柯达"};
       for (int i = 0; i < carList.size(); i++) {
            if (carName[i] == carList.get(position).getCarplate()) {
                carListItem.iv_account_carlog.setImageResource(imageUrl[i]);
            }
        }*/
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
            onItemClickListener.onItemClick(v, inPosition);
//              showRechargeInfo(inPosition);
//            Toast.makeText(context, carList.get(inPosition).getCarplate(), Toast.LENGTH_SHORT).show();
        }

    }

    public class CarListHolder {
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

    public static LinkedHashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }
    public static void setIsSelected(LinkedHashMap<Integer,Boolean> isSelected) {
        CarListAdater.isSelected = isSelected;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
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

    //充值按钮事件
    private void showRechargeInfo(int inPosition) {
        Toast.makeText(context, "ss", Toast.LENGTH_SHORT).show();
//        PayDialog payDialog = new PayDialog(context,inPosition);
//        FragmentManager fragmentManager = getFra
        /*
        FragmentTransaction ft = ;
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        mdf.show(ft, "df");*/
        /*new AlertDialog.Builder(context)
                .setTitle("车辆账户充值")
                .setMessage("ss"+carList.get(inPosition).getWho())
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
                .show();*/
    }

}
