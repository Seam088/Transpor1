package com.bang.transpor1.weight;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bang.transpor1.R;

/**
 * Created by 12457 on 2018/10/11.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class PayDialog extends DialogFragment {
    private Activity activity;
    private Context context;
    private LayoutInflater inflater;
    private Dialog dialog;
    private View view;
    private Button btn_num_100;
    private Button btn_num_50;
    private Button btn_num_20;
    private Button btn_num_10;
    private Button btn_num_custom;
    private Button btn_dialog_chongzhi;
    private EditText et_num_custom;
    private int position;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (Activity) context;
    }

/*    public static UpdateMoneyDialog getInstance(String money,int postion,String name){
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
}
