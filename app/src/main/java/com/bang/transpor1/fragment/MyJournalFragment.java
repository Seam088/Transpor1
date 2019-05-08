package com.bang.transpor1.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bang.transpor1.R;
import com.bang.transpor1.util.MyDBUtil;
import com.bang.transpor1.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyJournalFragment extends Fragment {


    private View view;
    private String result1;

    public MyJournalFragment() {
        // Required empty public constructor
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    ToastUtils.showToast(getContext(), "11"+result1);
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_journal, container, false);
        initView();
        initData();
        return view;
    }

    private void initView() {

    }

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        JSONObject js = new JSONObject();
        try {
            js.put("action", "get");
            js.put("object", "traffic");
            js.put("station", 1);
            map.put("url", "http://dev1.cn:8888");
            map.put("jsonobject", js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyDBUtil.getDataByPostInterface(map, new MyDBUtil.SuccessCallback() {
            @Override
            public void onSuccess(final String result) {
                result1 = result;
                ToastUtils.showToast(getContext(), "11"+result1);
//                handler.sendEmptyMessage(0);
            }
        }, new MyDBUtil.FailCallback() {
            @Override
            public void onFail() {
                ToastUtils.showThreadToast(getActivity(), getContext(), "数据获取失败");
            }
        });

    }


}
