package com.bang.transpor1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_1;
    private Button btn_2;
    private EditText et_1;
    private EditText et_2;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        et_1 = findViewById(R.id.et_1);
        et_2 = findViewById(R.id.et_2);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        //获取SharedPreferences对象
    }

    @Override
    public void onClick(View v) {
        sp = getSharedPreferences("firstStart", Context.MODE_PRIVATE);
        switch (v.getId()){
            case R.id.btn_1:
                //获取sp.edit对象
                SharedPreferences.Editor editor = sp.edit();
                //通过editor对象写入数据
                String str = et_1.getText().toString().trim();
                Toast.makeText(WelcomeActivity.this,"==>"+str,Toast.LENGTH_SHORT).show();
                editor.putString("Value",str);
                //提交数据到XML文件中
                editor.commit();
                break;
            case R.id.btn_2:
                String value = sp.getString("Value","null");
                Toast.makeText(WelcomeActivity.this,"==>"+value , Toast.LENGTH_SHORT).show();
                et_2.setText(value+"");
                break;
        }
    }
}
