package com.bang.transpor1.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bang.transpor1.R;
import com.bang.transpor1.bean.CarPersonNum;
import com.bang.transpor1.bean.ConstantValue;
import com.bang.transpor1.bean.Pubcar;
import com.bang.transpor1.request.BaseRequest;
import com.bang.transpor1.adapter.PubcalListAdater;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private final static int GETBUSDIALOGDATA = 0;
    private View view;
    private ArrayList<Pubcar> pubcarList;
    private Activity activity;
    private Context context;
    private int carId;
    private ListView lv_bus;
    LinkedList<Pubcar> pubcarLinkedList;
    private PubcalListAdater pubcalListAdater;
    private Timer timer;
    private List<CarPersonNum.DataBean> carPersonNumList;
    private View busDialogView;
    private MyDialogAdapter myDialogAdapter;
    private TextView tv_chengzaiNum;
    private LayoutInflater inflater;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GETBUSDIALOGDATA:
                    if (myDialogAdapter == null) {
                        myDialogAdapter = new MyDialogAdapter();
                        listView.setAdapter(myDialogAdapter);
                    } else {
                        myDialogAdapter.notifyDataSetChanged();
                    }
                    int busPersonNumTotal = getBusPersonNumTotal();
                    tv_chengzaiNum.setText("承载能力: " + busPersonNumTotal + "人");
                    TextView tv_BusPersonSum = busDialogView.findViewById(R.id.tv_bus_carryingNum_total);
                    tv_BusPersonSum.setText(busPersonNumTotal + "");
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private Timer timer_dialog;
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bus, container, false);
        initView();   //初始化视图
        getBusData(); //从网络中获取公交车json数据
        getCarPersonNum();  //获取公交汽车承载数据
        return view;
    }

    private void initView() {
        refreshLayout = view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setProgressBackgroundColor(R.color.refresh_bg);

        tv_chengzaiNum = view.findViewById(R.id.tv_ChengzaiNum);

        lv_bus = view.findViewById(R.id.lv_bus);
        pubcalListAdater = new PubcalListAdater(getContext(), carId);  //实例化适配器
        lv_bus.setAdapter(pubcalListAdater);  //设置适配器

        Button btn_Xiangqing = view.findViewById(R.id.btn_Xiangqing);
        btn_Xiangqing.setOnClickListener(this);  //添加监听事件

        LayoutInflater layoutInflater = getLayoutInflater();
        busDialogView = layoutInflater.inflate(R.layout.dialog_bus_detail, null);

        listView = busDialogView.findViewById(R.id.lv_bus_dialog);
    }

    public void getBusData() {
        pubcarLinkedList = new LinkedList<>();
        new LoadBusDataAsync().execute();
       /* timer = new Timer();
         timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new LoadBusDataAsync().execute();
//                new LoadBusDataAsync().onProgressUpdate();
            }
        }, 0, 3000);*/
    }

    //获取公交车承载人数
    public void getCarPersonNum() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = BaseRequest.getRequest("http://hh1.me:5001/getCarPersonNum");
                final CarPersonNum carPersonNum = new Gson().fromJson(str, CarPersonNum.class);
                carPersonNumList = new ArrayList<>();
                getBusPersonNumTotal(); //获取公交车乘客总数
                if (activity == null) {
                    return;
                } else {
                    if (carPersonNum == null || "F".equals(carPersonNum.getRESULT())) {
                        return;
                    } else {
                        carPersonNumList = carPersonNum.getDataList();
                        handler.sendEmptyMessage(GETBUSDIALOGDATA);
                    }
                }
            }
        }).start();
    }

    //获取公交车乘客总数
    private Integer getBusPersonNumTotal() {
        int busPersonNumTotal = 0;
        for (int i = 0; i < carPersonNumList.size(); i++) {
            busPersonNumTotal += carPersonNumList.get(i).getPersonNum();
        }
        return busPersonNumTotal;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Xiangqing:
                showBusPersonNumDialog();   //显示公交车载客Dialog
                break;
        }
    }

    //显示公交车载客Dialogjmxiao'xue
    private void showBusPersonNumDialog() {
        if (activity == null) {
            return;
        } else {
            if (busDialogView.getParent() != null) {
                ViewGroup vg = (ViewGroup) busDialogView.getParent();
                vg.removeView(busDialogView);
            }
            final AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle("公交车载客查询")
                    .setView(busDialogView)
                    .setNegativeButton("返回", null)
                    .show();
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (timer_dialog != null) {
                        timer_dialog.cancel();
                        timer_dialog = null;
                    }
                }
            });

            getCarPersonNum();  //获取公交汽车承载数据
            timer_dialog = new Timer();
            timer_dialog.schedule(new TimerTask() {
                @Override
                public void run() {
                    getCarPersonNum();  //获取公交汽车承载数据
                }
            }, 0, 3000);
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
                getBusData(); //从网络中获取公交车json数据
                getCarPersonNum();  //获取公交汽车承载数据
            }
        }, 3000);
    }

    private class LoadBusDataAsync extends AsyncTask<Void, Pubcar, Void> {
        private Pubcar pubcar;

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 4; i++) {
                Map map = new HashMap<>();
                map.put("CarId", i);
                Gson gson = new Gson();
                String data = gson.toJson(map);
                String result = BaseRequest.postRequest1(data, "http://hh1.me:5001/pubcar");
                if (result != null) {
                    pubcar = gson.fromJson(result, Pubcar.class);
                    onProgressUpdate(pubcar);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Pubcar... values) {
            super.onProgressUpdate(values);
            if (values == null) {
                return;
            }
            final Pubcar pubcar = values[0];
            if (activity == null || pubcar == null) {
                Log.e("Bang", "Activity为空!数据为空！！" + "\n ");
                return;
            } else {
                Log.e("Bang1", pubcar.getRESULT() + "");
                if (ConstantValue.FAIL.equals(pubcar.getRESULT())) {
                    Log.e("Bang", "数据调用失败！" + "\n ");
                    return;
                }
                Log.e("Bang", carId + "");
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pubcalListAdater.addList(pubcar);
                    }
                });
            }

        }

    }


    private class MyDialogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return carPersonNumList.size();
        }

        @Override
        public Object getItem(int i) {
            return carPersonNumList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            BusListHolder busListHolder = null;
            inflater = LayoutInflater.from(context);
            if (view == null) {
                view = inflater.inflate(R.layout.item_lv_dialog_bus, null);
                busListHolder = new BusListHolder(view);
                view.setTag(busListHolder);
            } else {
                busListHolder = (BusListHolder) view.getTag();
            }
            busListHolder.setData(i);
            return view;
        }

        private class BusListHolder {
            TextView tv_busDialogId;
            TextView tv_busDialogCarId;
            TextView tv_busDialogpersonNum;

            public BusListHolder(View viewHolder) {
                tv_busDialogId = viewHolder.findViewById(R.id.tv_busDialogId);
                tv_busDialogCarId = viewHolder.findViewById(R.id.tv_busDialogCarId);
                tv_busDialogpersonNum = viewHolder.findViewById(R.id.tv_busDialogpersonNum);
            }

            public void setData(int position) {
                tv_busDialogId.setText(position + "");
                tv_busDialogCarId.setText(carPersonNumList.get(position).getCarId() + "");
                tv_busDialogpersonNum.setText(carPersonNumList.get(position).getPersonNum() + "");
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        } else {
            return;
        }
    }



}
