package com.bang.transpor1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bang.transpor1.activity.ConsendScrollingActivity;
import com.bang.transpor1.request.PostUtil;
import com.bang.transpor1.weight.VerifyCode;

import org.json.JSONException;
import org.json.JSONObject;

public class RegActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private EditText et_reg_username, et_reg_pwd, et_reg_confirmPwd, et_reg_verificationCode;
    private CheckBox cb_agree_service;
    private CheckedTextView checkedTextView;  //同意协议
    private Button btn_reg_user;
    private boolean isCheckk = false;
    private TextView tv_reg_obtainCode;  //获取验证码文本框
    private VerifyCode verifyCode_reg;   //验证码图形

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initView();
        initData();

        Intent intent = getIntent();
        boolean isConsend = intent.getBooleanExtra("isConsend", false);
        cb_agree_service.setChecked(isConsend);
    }

    private void initView() {
        et_reg_username = findViewById(R.id.et_reg_username);
        et_reg_pwd = findViewById(R.id.et_reg_pwd);
        et_reg_confirmPwd = findViewById(R.id.et_reg_confirmPwd);
        et_reg_verificationCode = findViewById(R.id.et_reg_verificationCode);

        //获取验证码文本框
        tv_reg_obtainCode = findViewById(R.id.tv_reg_obtainCode);
        //自定义验证码图形
        verifyCode_reg = findViewById(R.id.verifuCode_reg);

        cb_agree_service = findViewById(R.id.cb_agree_service);
        checkedTextView = findViewById(R.id.ctv_userRegAgreement);
        ImageButton imgbtn_reg_back = findViewById(R.id.imgbtn_reg_back);
        btn_reg_user = findViewById(R.id.btn_reg_user);

        tv_reg_obtainCode.setOnClickListener(this);
        imgbtn_reg_back.setOnClickListener(this);
        btn_reg_user.setOnClickListener(this);
        checkedTextView.setOnClickListener(this);
        cb_agree_service.setOnCheckedChangeListener(this);

        //判断验证码编辑框是否获取焦点事件
        et_reg_verificationCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {//获得焦点
                    tv_reg_obtainCode.setVisibility(View.GONE);
                    verifyCode_reg.setVisibility(View.VISIBLE);
                } else {//失去焦点
                }
            }
        });

        //控制登录用户民图标大小
        Drawable drawable_username = getResources().getDrawable(R.drawable.username); // username用户名
        drawable_username.setBounds(0, 0, 35, 35);
        Drawable drawable1_password = getResources().getDrawable(R.drawable.password); //password密码
        drawable1_password.setBounds(0, 0, 35, 35);
        Drawable drawable1_verificationCode = getResources().getDrawable(R.drawable.verification_code);  //验证码
        drawable1_verificationCode.setBounds(0, 0, 35, 35);
        et_reg_username.setCompoundDrawables(drawable_username, null, null, null);
        et_reg_pwd.setCompoundDrawables(drawable1_password, null, null, null);
        et_reg_confirmPwd.setCompoundDrawables(drawable1_password, null, null, null);
        et_reg_verificationCode.setCompoundDrawables(drawable1_verificationCode, null, null, null);
    }


    private void initData() {
        if (!isCheckk) {  //没选中 setRegBtnClickable(false);  //注册按钮不可点击
            setRegBtnClickable(false);  //注册按钮不可点击
            showToast("查看用户注册协议");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtn_reg_back:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_reg_user:
                reg();
                break;
            case R.id.ctv_userRegAgreement:
                Intent intent1 = new Intent(RegActivity.this, ConsendScrollingActivity.class);
                startActivity(intent1);
//                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                overridePendingTransition(R.anim.listen_activity_open_in_anim,
                        R.anim.listen_activity_open_out_anim);
                break;
            case R.id.tv_reg_obtainCode:   //获取验证码
                tv_reg_obtainCode.setVisibility(View.GONE);    //获取验证码文本框隐藏
                verifyCode_reg.setVisibility(View.VISIBLE);    //自定义验证码图片 显示
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {  //没选中 setRegBtnClickable(false);  //注册按钮不可点击
            setRegBtnClickable(true);  //注册按钮不可点击
        } else {   //没选中
            setRegBtnClickable(false);  //注册按钮不可点击
            showToast("查看用户注册协议");
        }
    }

    private String getRegUsername() {
        return et_reg_username.getText().toString().trim();
    }

    private String getRegPwd() {
        return et_reg_pwd.getText().toString().trim();
    }

    private String getRegConfigPwd() {
        return et_reg_confirmPwd.getText().toString().trim();
    }


    //输入验证码的值
    private String getRegVerficaltionCode() {
        return et_reg_verificationCode.getText().toString().trim();
    }


    /**
     * 是否可以点击登录按钮
     */
    public void setRegBtnClickable(boolean clickable) {
        btn_reg_user.setClickable(clickable);
    }


    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    //先做一些基本的判断，比如输入的用户命为空，密码为空，网络不可用多大情况，都不需要去链接服务器了，而是直接返回提示错误
    private void reg() {
        if (getRegUsername().isEmpty()) {
            showToast("你输入的账号为空！");
        } else if (getRegPwd().isEmpty()) {
            showToast("你输入的密码为空！");
        } else if (getRegConfigPwd().isEmpty()) {
            showToast("你输入的确认密码为空！");
        } else if (!getRegPwd().equals(getRegConfigPwd())) {
            showToast("你输入的密码不一致，请检查密码！");
        } else if (getRegVerficaltionCode().isEmpty()) {
            showToast("你输入的验证码为空");
        } else if (!verifyCode_reg.isEqualsIgnoreCase(getRegVerficaltionCode())) {       //进行判断,忽略验证码大小写
            verifyCode_reg.refresh();   //注册后，验证码不一致，验证码图片更新
            et_reg_verificationCode.setText("");   //清空验证码
            showToast("验证码不一致");
        } else {
            //登录一般都是请求服务器来判断密码是否正确，要请求网络，要子线程
//        showLoading();//显示加载框
            Thread loginRunable = new Thread() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    super.run();
                    setRegBtnClickable(false);//点击登录后，设置登录按钮不可点击状态

                    String str = PostUtil.getReg(getRegUsername(), getRegConfigPwd());
//                System.out.println("======>"+str);
                    JSONObject jsonObject = null;
                    String result = "";
                    try {
                        jsonObject = new JSONObject(str);
                        result = jsonObject.getString("result");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("----->" + result);

                    //睡眠三秒
                    try {
                        Thread.sleep(3 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(getRegUsername() + "<====>" + getRegPwd());
                    //判断账号和密码
                    if ("ok".equals(result)) {
                        showToast("注册成功");
//                    loadCheckBoxState();//记录下当前状态;
                        startActivity(new Intent(RegActivity.this, LoginActivity.class));
                        finish();//关闭页面
                    } else {
                        showToast("输入的账号已被注册");
                    }
                    setRegBtnClickable(true);  //这里解放注册按钮，设置为可以点击
//                hideLoading();//隐藏加载框
                }
            };
            loginRunable.start();
        }
    }

}
