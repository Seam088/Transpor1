package com.bang.testt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String GET_MESSAGE_SUCCESS = "get_message_success";
    private RecyclerView recyclerView;
    private DemoAdapter adapter;
    private TextView tvPay;
    private TextView tv_chongzhiNum, tv_chongzhiName;
    private ArrayList<ItemModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        tvPay = (TextView) findViewById(R.id.tvPay);   //充值按钮
        tv_chongzhiNum = findViewById(R.id.tv_chongzhiNum);
        tv_chongzhiName = findViewById(R.id.tv_chongzhiName);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter = new DemoAdapter());
        adapter.replaceAll(getData());
        EventBus.getDefault().register(this);  //注册事件
//        ClickEvent clickEvent = new ClickEvent(ClickEvent.Type.SEND_MSG,recyclerView,getData().get(0).data);


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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPay:
                break;
        }
    }
}
