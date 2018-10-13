package com.bang.transpor1;

        import android.animation.AnimatorSet;
        import android.animation.ObjectAnimator;
        import android.content.Intent;
        import android.os.Handler;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.ImageView;

        import com.bang.transpor1.bean.ConstantValue;
        import com.bang.transpor1.util.SharePreUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView = findViewById(R.id.iv_splash);
        setAnimation(imageView);
        //延迟2秒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Boolean isFirst = SharePreUtil.getBoolean(getApplicationContext(), ConstantValue.ISFIRST, true);
                if(isFirst){
                    //进入包含了viewpager那个导航界面
                    Intent intent = new Intent(getApplicationContext(), WelcomeGuideActivity.class);
                    startActivity(intent);
                    //将isFirst改为false,并且在本地持久化
                    SharePreUtil.saveBoolean(getApplicationContext(), ConstantValue.ISFIRST, false);
                }else{
                    //进入应用程序主界面
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 5000);
    }

    //执行动画的方法
    private void setAnimation(ImageView imageView) {
        //应用图标从屏幕最上方平移到屏幕中间
        ObjectAnimator trans = ObjectAnimator.ofFloat(imageView, "translationY", 0f, 500f).setDuration(1000);
        //缩放由2倍到1倍
        ObjectAnimator scalX = ObjectAnimator.ofFloat(imageView, "scaleX", 2f, 1f).setDuration(1000);
        ObjectAnimator scalY = ObjectAnimator.ofFloat(imageView, "scaleY", 2f, 1f).setDuration(1000);
        //渐变从完全透明到完全不透明
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0.0f, 1f).setDuration(1000);
        // 旋转为旋转一圈
        ObjectAnimator rotate = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f).setDuration(1000);

        //动画组合开始执行
        AnimatorSet setAnimatior = new AnimatorSet();
        setAnimatior.play(trans).before(scalX).before(scalY).before(alpha).before(rotate);
        setAnimatior.start();
    }
}
