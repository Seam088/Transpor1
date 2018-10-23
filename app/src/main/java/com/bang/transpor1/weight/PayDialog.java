package com.bang.transpor1.weight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bang.transpor1.R;
import com.bang.transpor1.adapter.PayDialogAdapter;
import com.bang.transpor1.bean.ClickEvent;
import com.bang.transpor1.bean.ItemModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by 12457 on 2018/10/11.
 */

@SuppressLint("ValidFragment")
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class PayDialog extends DialogFragment implements View.OnClickListener{

    public static final String GET_MESSAGE_SUCCESS = "get_message_success";
    private Activity activity;
    private Context context;
    private Dialog dialog;
    private View view;
    private RecyclerView recyclerView;
    private PayDialogAdapter adapter;
    private TextView tvPay;
    private TextView tv_chongzhiNum, tv_chongzhiName;
    private ArrayList<ItemModel> list;
    private int position;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (Activity) context;
    }

    @SuppressLint("ValidFragment")
    public PayDialog(Context context, int position) {
        this.context = context;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.dialog_recharge,null);

        initView();
        EventBus.getDefault().register(this);  //注册事件
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recylerview);
        tvPay = view.findViewById(R.id.tvPay);   //充值按钮
        tvPay.setOnClickListener(this);
        tv_chongzhiNum = view.findViewById(R.id.tv_chongzhiNum);
        tv_chongzhiName = view.findViewById(R.id.tv_chongzhiName);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
        recyclerView.setAdapter(adapter = new PayDialogAdapter());

        dialog = new Dialog(context,R.style.bran_online_supervise_dialog);
        //设置取消标题
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置点击外部不可以消失
        dialog.setCanceledOnTouchOutside(true);
        //设置即使点击返回键也不会退出
        setCancelable(true);
        dialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置软键盘弹出模式
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        //设置Dialog宽度匹配屏幕宽度
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置Dialog高度自适应
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);

        adapter.replaceAll(getData());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ClickEvent event) {
        switch (event.type) {
            case TV_SEND_MSG:
                String price = event.data.toString();
                int parseInt = Integer.parseInt(price);
                tv_chongzhiNum.setText(parseInt + "元");
                break;
            case ET_SEND_MSG:
                String price1 = event.data.toString();
                if (price1 != null) {
                    int priceInt = Integer.parseInt(price1);
                    tv_chongzhiNum.setText(priceInt + "元");
                }
                break;
        }
    }


    public ArrayList<ItemModel> getData() {
        list = new ArrayList<>();
        int[] countMoney = new int[]{1000, 500, 300, 200, 100, 50, 20, 10};
        for (int i = 0; i < 8; i++) {
            int count = countMoney[i];
            list.add(new ItemModel(ItemModel.ONE, count));
        }
        list.add(new ItemModel(ItemModel.TWO, null));
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPay:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}
   /*     public static UpdateMoneyDialog getInstance(String money,int postion,String name){
        UpdateMoneyDialog imgShowDialog = new UpdateMoneyDialog();
        Bundle bundle = new Bundle();
        bundle.putString("money",money);
        bundle.putInt("pos",postion);
        bundle.putString("name",name);
        //传入值，跟Fragment传值方法一样
        imgShowDialog.setArguments(bundle);
        return imgShowDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = LayoutInflater.from(context);
        dialog = new Dialog(context, R.style.bran_online_supervise_dialog);
        view = inflater.inflate(R.layout.dialog_recharge, null);
        btn_num_100 = (TextView) view.findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        etMoney = (EditText) view.findViewById(R.id.et_money);

        //设置取消标题
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置点击外部不可以消失
        dialog.setCanceledOnTouchOutside(true);
        //设置即使点击返回键也不会退出
        setCancelable(true);
        dialog.setContentView(view);

        //将值取出来
        Bundle bundle = getArguments();
        if(null != bundle){
            position = bundle.getInt("pos");
            String money = bundle.getString("money");
            etMoney.setText(money);
            String name = bundle.getString("name");
            tvName.setText(name);
        }

        tvCancel.setOnClickListener(onClickListener);
        tvConfirm.setOnClickListener(onClickListener);

        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置软键盘弹出模式
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        //设置Dialog宽度匹配屏幕宽度
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置Dialog高度自适应
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        return dialog;
    }

     *//*
         View的点击事件
     *//*
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_cancel:
                    KeyboardUtils.hideSoftInput(activity,tvCancel);
                    dialog.dismiss();
                    break;
                case R.id.tv_confirm:
                    String money = etMoney.getText().toString().trim();
                    if(!TextUtils.isEmpty(money)){
                        listener.onObtainValue(position,money);
                        KeyboardUtils.hideSoftInput(activity,tvConfirm);
                        dialog.dismiss();
                    }else{
                        listener.onObtainValue(position,"0");
                        KeyboardUtils.hideSoftInput(activity,tvConfirm);
                        dialog.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public interface OnUpdateMoneyListener{
        void onObtainValue(int position,String data);
    }
    private OnUpdateMoneyListener listener = null;
    public void setOnUpdateMoneyListener(OnUpdateMoneyListener listener){
        this.listener = listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KeyboardUtils.hideSoftInput(activity);
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }*/

