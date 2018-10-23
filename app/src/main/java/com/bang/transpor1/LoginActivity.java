package com.bang.transpor1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bang.transpor1.bean.ConstantValue;
import com.bang.transpor1.request.PostUtil;
import com.bang.transpor1.util.SharePreUtil;
import com.bang.transpor1.weight.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;


import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{

    private EditText et_username,et_pwd;
    private CheckBox cb_remPwd;
    private CheckedTextView ctv;
    private String username,password;
    private SharedPreferences sp;
    private Boolean isRemPsd;
    private ImageButton ib_pwd_gone;
    private Button btn_login;
    private Button btn_reg;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initView() {
        et_username = findViewById(R.id.et_login_username);
        et_pwd = findViewById(R.id.et_login_password);
        cb_remPwd = findViewById(R.id.cb_rem_pwd);
        ctv = findViewById(R.id.cb_Forget_pwd);
        cb_remPwd.setOnCheckedChangeListener(this);
        ib_pwd_gone = findViewById(R.id.ib_pwd_gone);
        btn_login = findViewById(R.id.btn_login);
        btn_reg = findViewById(R.id.btn_reg);
        btn_login.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
        ib_pwd_gone.setOnClickListener(this);

        //控制登录用户民图标大小
        Drawable drawable_username = getResources().getDrawable(R.drawable.username); // username
        drawable_username.setBounds(0,0,35,35);
        Drawable drawable1_password = getResources().getDrawable(R.drawable.password);
        drawable1_password.setBounds(0,0,35,35);
        Drawable drawable1_password_gone = getResources().getDrawable(R.drawable.password_gone);
        drawable1_password.setBounds(0,0,35,35);
        et_username.setCompoundDrawables(drawable_username,null,null,null);
        et_pwd.setCompoundDrawables(drawable1_password,null,drawable1_password_gone,null);
    }

    private void initData() {
        //判断是否记住密码
        isRemPsd = SharePreUtil.getBoolean(getApplicationContext(), ConstantValue.ISREMPWD,false);
        if (isRemPsd) {
            cb_remPwd.setChecked(isRemPsd);//勾选记住密码
            //把密码和账号输入到输入框中
            et_username.setText("" + getUsername());
            et_pwd.setText("" + getPsd() );
        } else {
            cb_remPwd.setChecked(isRemPsd);//勾选记住密码
            et_username.setText("" +getUsername());   //把用户账号放到输入账号的输入框中
        }
    }

    public String getEtAccount(){
        return et_username.getText().toString().trim();
    }

    public String getEtpassword(){
        return et_pwd.getText().toString().trim();
    }

    /**
     * 获得保存在本地的用户名
     */
    public String getUsername() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        return SharePreUtil.getString(getApplicationContext(),ConstantValue.USERNAME,"");
    }

    /**
     * 获得保存在本地的密码
     */
    public String getPsd() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        return SharePreUtil.getString(getApplicationContext(),ConstantValue.PASSWORD,"");
    }

    /**
    * CheckBox的监听方法 （记住密码）
    **/
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        isChecked = SharePreUtil.getBoolean(getApplicationContext(),ConstantValue.ISREMPWD,false);
        if (isChecked){
            SharePreUtil.saveBoolean(getApplicationContext(),ConstantValue.ISREMPWD,isChecked);
        }else {
            SharePreUtil.saveBoolean(getApplicationContext(),ConstantValue.ISREMPWD,isChecked);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                SharePreUtil.saveString(getApplicationContext(),ConstantValue.USERNAME,getEtAccount());
                //判断是否保存密码
                if (isRemPsd){
                    SharePreUtil.saveString(getApplicationContext(),ConstantValue.PASSWORD,getEtpassword());  //把checkBox选中，传到sp
//                    Toast.makeText(getApplicationContext(),"password=>"+getPsd(),Toast.LENGTH_SHORT).show();
                }else{
                    SharePreUtil.clearString(getApplicationContext(),ConstantValue.PASSWORD);  //清除sp保存的数据密码
                }
                 login();  //登录
                break;
            case R.id.ib_pwd_gone:  //密码是否可见
                if (ib_pwd_gone.isSelected()){
                    ib_pwd_gone.setSelected(false);
                    et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //密码不可见
                    ib_pwd_gone.setBackgroundResource(R.drawable.login_eye_c);
                }else {
                    ib_pwd_gone.setSelected(true);
                    //密码可见
                    ib_pwd_gone.setBackgroundResource(R.drawable.login_eye_o);
                    et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case  R.id.btn_reg:
//                Toast.makeText(getApplicationContext(),"reg",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,RegActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void login(){
        //先做一些基本的判断，比如输入的用户命为空，密码为空，网络不可用多大情况，都不需要去链接服务器了，而是直接返回提示错误
        if (getEtAccount().isEmpty()){
            showToast("你输入的账号为空！");
        }
        if (getEtpassword().isEmpty()){
            showToast("你输入的密码为空！");
        }
        //登录一般都是请求服务器来判断密码是否正确，要请求网络，要子线程
        showLoading();//显示加载框
        Thread loginRunable = new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                super.run();
                setLoginBtnClickable(false);//点击登录后，设置登录按钮不可点击状态
                String str = PostUtil.getLogin(getEtAccount(),getEtpassword());
//                System.out.println("======>"+str);
                JSONObject jsonObject = null;
                String result = "";
                try {
                    jsonObject = new JSONObject(str);
                    result = jsonObject.getString("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println("----->"+result);

                //睡眠三秒
                try {
                    Thread.sleep(3*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(getEtAccount() + "<====>"+getEtpassword() );
                //判断账号和密码
                if ("ok".equals(result)) {
                    showToast("登录成功");
//                    loadCheckBoxState();//记录下当前用户记住密码和自动登录的状态;
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();//关闭页面
                } else {
                    showToast("输入的登录账号或密码不正确");
                }

                setLoginBtnClickable(true);  //这里解放登录按钮，设置为可以点击
                hideLoading();//隐藏加载框
            }
        };
        loginRunable.start();
    }

    /**
     * 是否可以点击登录按钮
     */
    public void setLoginBtnClickable(boolean clickable) {
        btn_login.setClickable(clickable);
    }

    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 显示加载的进度款
     */
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, getString(R.string.loading), false);
        }
        mLoadingDialog.show();
    }


    /**
     * 隐藏加载的进度框
     */
    public void hideLoading() {
        if (mLoadingDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.hide();
                }
            });
        }
    }

    /**
     * 监听回退键
     */
    @Override
    public void onBackPressed() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.cancel();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    /**
     * 页面销毁前回调的方法
     */
    protected void onDestroy() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;
        }
        super.onDestroy();
    }
}