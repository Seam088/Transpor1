package com.bang.transpor1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bang.transpor1.R;
import com.bang.transpor1.bean.Pubcar;
import com.bang.transpor1.request.BaseRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CarFragment extends Fragment {
    Context context;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_car, container, false);

        getdate();
        return view;
    }


    public void getdate() {


        new Thread(new Runnable() {
            @Override
            public void run() {
/*
                Map map = new HashMap();
                map.put("CarId",1);
                Gson gson = new Gson();
                String data =  gson.toJson(map);
                String str = BaseRequest.postRequest(data,"http://hh1.me:5001/pubcar");
                Pubcar pubCar = gson.fromJson(str,Pubcar.class);
*/

//                System.out.println("----BaseRequest---"+pubCar.getData().get(0).toString());

            }
        }).start();
    }
}
