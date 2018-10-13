package com.bang.transpor1.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bang.transpor1.R;
import com.bang.transpor1.bean.ConstantValue;
import com.bang.transpor1.bean.Pubcar;
import com.bang.transpor1.request.BaseRequest;
import com.bang.transpor1.adapter.PubcalListAdater;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusFragment extends Fragment {
    private View view;
    private ArrayList<Pubcar> pubcarList;
    private Activity activity;
    private int carId;
    private ListView lv_bus;
    LinkedList<Pubcar> pubcarLinkedList;
    private PubcalListAdater pubcalListAdater;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
        context = getContext();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bus, container, false);
        initView();
        getBusData();
        return view;
    }

    private void initView() {
        lv_bus = view.findViewById(R.id.lv_bus);
        pubcalListAdater = new PubcalListAdater(getContext(), carId);
        lv_bus.setAdapter(pubcalListAdater);
    }

    public void getBusData() {
        pubcarLinkedList = new LinkedList<>();
        new LoadBusDataAsync().execute();
    }

    private class LoadBusDataAsync extends AsyncTask<Void, Pubcar, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 4; i++) {
                Map map = new HashMap<>();
                map.put("CarId", i);
                Gson gson = new Gson();
                String data = gson.toJson(map);
                String result = BaseRequest.postRequest1(data, "http://hh1.me:5001/pubcar");
                if (result != null) {
                    Pubcar pubcar = gson.fromJson(result, Pubcar.class);
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


}
