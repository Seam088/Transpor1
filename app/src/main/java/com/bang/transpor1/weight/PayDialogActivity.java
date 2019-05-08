package com.bang.transpor1.weight;

import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bang.transpor1.R;
import com.bang.transpor1.adapter.PayDialogAdapter;
import com.bang.transpor1.bean.Car;
import com.bang.transpor1.bean.CarNoEvent;
import com.bang.transpor1.bean.ClickEvent;
import com.bang.transpor1.bean.ItemModel;
import com.bang.transpor1.request.BaseRequest;
import com.bang.transpor1.util.ToastUtils;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayDialogActivity extends AppCompatActivity implements OnClickListener {
    public static final String GET_MESSAGE_SUCCESS = "get_message_success";
    private RecyclerView recyclerView;
    private PayDialogAdapter adapter;
    private TextView tvPay;
    private TextView tv_chongzhiNum, tv_chongzhiName;
    private ArrayList<ItemModel> list;
    private String price;
    private ArrayList<String> carNoList;
    private ImageView iv_paydialog_back;
    private String carNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        setContentView(R.layout.activity_pay_dialog);
        recyclerView = findViewById(R.id.recylerview);
        tvPay = findViewById(R.id.tvPay);   //充值按钮

        tv_chongzhiNum = findViewById(R.id.tv_chongzhiNum);
        tv_chongzhiName = findViewById(R.id.tv_chongzhiName);
        iv_paydialog_back = findViewById(R.id.iv_paydialog_back);
        iv_paydialog_back.setOnClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter = new PayDialogAdapter(this));

        adapter.replaceAll(getData());

        EventBus.getDefault().register(this);  //注册事件

        carNoList = getIntent().getStringArrayListExtra("CarNoList");
        carNo = getIntent().getStringExtra("CarNo");
        String str = "";
        if (carNoList != null && carNoList.size() > 0) {
            for (int i = 0; i < carNoList.size(); i++) {
                if ((i + 1) % 2 == 0) {
                    str += carNoList.get(i) + " \n";
                } else {
                    str += carNoList.get(i) + " ";
                }
            }
        } else if (carNo != "") {
            str = carNo;
        }
        tv_chongzhiName.setText(str + "");
        tvPay.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ClickEvent event) {
        switch (event.type) {
            case TV_SEND_MSG:
                price = event.data.toString();
                int parseInt = Integer.parseInt(price);
                tv_chongzhiNum.setText(parseInt + "元");
                break;
            case ET_SEND_MSG:
                price = event.data.toString();
                if (price != null) {
                    int priceInt = Integer.parseInt(price);
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


    private void payData(final String name, final int mm) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = BaseRequest.postRequest(parse(name, mm), "http://hh1.me:5001/charge");
                Car car = new Gson().fromJson(str, Car.class);
                ToastUtils.showThreadToast(PayDialogActivity.this, PayDialogActivity.this, "S".equals(car.getRESULT()) ? "充值成功" : "充值失败");
//                getCarData();  //添加后刷新数据
            }
        }).start();
    }

    private String parse(String name, int mm) {
        String carNos = "";
        for (int i = 0; i < carNoList.size(); i++) {
            carNos += carNoList.get(i) + "&";
        }
        if (carNos.length() != 0) {
            carNos = carNos.substring(0, carNos.length() - 1);
        }
        Map map = new HashMap();
        map.put("who", name);
        map.put("amount", mm);
        map.put("carno", carNos);
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPay:
                if (price == null) {
                    ToastUtils.showToast(PayDialogActivity.this, "请选择充值额度，再充值！！！");
                } else {
                    int price1 = Integer.parseInt(price);
                    if (carNoList != null && carNoList.size() > 0) {
                        System.out.println("==>" + price1 + "    ===>" + carNoList.size());
                    } else if (carNo != "") {
                        carNoList = new ArrayList<>();
                        carNoList.clear();
                        carNoList.add(carNo);
                    }
                    payData("张三", price1);
                    finish();
                }
                break;
            case R.id.iv_paydialog_back:
                finish();
                break;
        }
    }
}
