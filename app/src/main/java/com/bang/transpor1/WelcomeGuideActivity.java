package com.bang.transpor1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class WelcomeGuideActivity extends AppCompatActivity{

    private ViewPager vp_guide;
    private ArrayList<ImageView> imageList;
    private Button btn_guide_enter;
    private LinearLayout dot_layout;
    //引导图片资源
    private final int[] pics = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    private int currentIndex;    //记录当前选中位置
    private ImageView[] dotViews;    //小圆点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();  //初始化UI控件
        initData(); //初始化数据
        initDots(); // 小圆点
        vp_guide.setAdapter(new Mypager());
    }

    private void initView() {
        vp_guide = findViewById(R.id.vp_guide);
        btn_guide_enter = findViewById(R.id.btn_guide_enter);
        dot_layout = findViewById(R.id.dot_Layout);
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imageList.size() - 1) {
                    btn_guide_enter.setVisibility(View.VISIBLE);
                    btn_guide_enter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(WelcomeGuideActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    btn_guide_enter.setVisibility(View.GONE);
                }
                for (int i = 0; i < dotViews.length; i++) {
                    if (position == i) {
                        dotViews[i].setSelected(true);
                    } else {
                        dotViews[i].setSelected(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        imageList = new ArrayList<>();
        for (int i = 0; i < pics.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(pics[i]);
            imageList.add(imageView);
        }
    }

    private void initDots() {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(20,20);
        mParams.setMargins(10,0,10,0); //设置小圆点上下左右的间距
        dotViews = new ImageView[pics.length];
        for (int i = 0; i < imageList.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(R.drawable.dotselector);
            if (i == 0){
                imageView.setSelected(true);
            }else {
                imageView.setSelected(false);
            }
            dotViews[i] = imageView;//得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
            dot_layout.addView(imageView);//添加到布局里面显示
        }
    }

    private class Mypager extends PagerAdapter {
        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = imageList.get(position);
            container.addView(view);
            if (position == imageList.size()){
                btn_guide_enter.setVisibility(View.VISIBLE);
            }else{
                btn_guide_enter.setVisibility(View.GONE);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageList.get(position));
        }
/*
        // 当前页面被滑动时调用
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // arg0 :当前页面，及你点击滑动的页面
            // arg1:当前页面偏移的百分比
            // arg2:当前页面偏移的像素位置
        }

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int position) {

        }

        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int state) {
            // arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。

        }*/
    }
}
