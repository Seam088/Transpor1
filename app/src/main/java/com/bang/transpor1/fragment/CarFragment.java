package com.bang.transpor1.fragment;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bang.transpor1.R;
import com.bang.transpor1.bean.Pubcar;
import com.bang.transpor1.request.BaseRequest;
import com.bang.transpor1.weight.DrawBoard;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.http.Url;


public class CarFragment extends Fragment implements View.OnTouchListener, View.OnClickListener{
    Context context;
    View view;
    private TextView tv_car;
    private ImageView iv_car;
    private GestureOverlayView gestureOverlayView;
    private DrawBoard drawBoard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_car, container, false);
        initView();
        return view;
    }

    private void initView() {
        gestureOverlayView = view.findViewById(R.id.gov_1);
        Button btn_clear = view.findViewById(R.id.btn_clear);
        Button btn_save = view.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
//        drawBoard = view.findViewById(R.id.db);
//        drawBoard.onTouchEvent()
//        WebView mWebView = view.findViewById(R.id.web);
//        mWebView.getSettings().setDisplayZoomControls(false);
//        mWebView.loadUrl("file:///android_asset/animation.html");


        /*tv_car = view.findViewById(R.id.tv_car);
        iv_car = view.findViewById(R.id.iv_car);
        Button button = view.findViewById(R.id.btn_car);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getdate();
            }
        });*/
    }

    public Bitmap saveImage(){
        gestureOverlayView.setDrawingCacheEnabled(true);
        return Bitmap.createBitmap(gestureOverlayView.getDrawingCache());
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        drawBoard.onTouchEvent(event);
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                Bitmap bitmap =  saveImage();
                break;
            case R.id.btn_clear:
//                drawBoard.clear();
                gestureOverlayView.clear(true);
                break;

        }
    }
}