package com.bang.transpor1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bang.transpor1.R;
import com.bang.transpor1.bean.AirCondition;
import com.bang.transpor1.bean.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12457 on 2018/10/27.
 */

public class LifeIndexListAdapter extends RecyclerView.Adapter<LifeIndexListAdapter.ListHolder> {

    String[] zhishulist = new String[]{"紫外线指数", "感冒指数", "穿衣指数", "运动指数", "空气污染扩散指数"};
    //紫外线指数：根据光照强度值显示。
    String[] ultravioletIndexs = new String[]{"弱", "中等", "强"};
    String[] ultravioletIndexTips = new String[]{"辐射较弱，涂擦SPF12~15、PA+护肤品", "涂擦 SPF 大于15、PA+防晒护肤品", "尽量减少外出，需要涂抹高倍数防晒霜"};
    //感冒指数：根据温度值显示。
    String[] tempIndexs = new String[]{"易发", "少发"};
    String[] tempIndexTips = new String[]{"温度低，风较大，较易发生感冒，注意防护", "无明显降温，感冒机率"};
    //穿衣指数：根据温度值显
    String[] clothingIndexs = new String[]{"冷", "舒适", "热 "};
    String[] clothingIndexTips = new String[]{"建议穿长袖衬、衫、单裤等服装 ", "建议穿短袖衬衫、单裤等服装", "适合穿 T 恤、短薄外套等夏季服装 "};
    //运动指数：根据二氧化碳值显示。
    String[] sportIndexs = new String[]{"适宜", "中", "稍不易 "};
    String[] sportIndexTips = new String[]{"气候适宜，推荐您进行户外运动 ", "建易感人群应适当减少室外活动", "空气氧气含量低，请在 室内进行休闲运动"};
    //空气污染扩散指数：根据 PM2.5 值显示。 。
    String[] airIndexs = new String[]{"优", "良", "污染"};
    String[] airIndexTips = new String[]{"空气质量非常好，非常适合户外活动，趁机出去多呼吸新鲜空气", "易感人群应适当减少室外活动", "空气质量差，不适合户外活动"};

    //指数集合
    List<String> indexList = new ArrayList<>();  //中等
    //提示介绍集合
    List<String> tipList = new ArrayList<>();
    //指数集合
    List<Integer> indexNumList = new ArrayList<>();   //123

    int[] icons = new int[]{R.drawable.ziwaixian, R.drawable.ganmao, R.drawable.chuanyi, R.drawable.yundong, R.drawable.kongqiwuran};
    Context context;

    public LifeIndexListAdapter(Context context, AirCondition airCondition) {
        this.context = context;
        indexList.clear();
        tipList.clear();
        indexNumList.clear();
        if (airCondition == null) {
            return;
        } else {
            indexNumList.add(airCondition.getUltravioletIntensity());
            if (airCondition.getUltravioletIntensity() > 0 || airCondition.getUltravioletIntensity() < 1000) {
                indexList.add(ultravioletIndexs[0]);
                tipList.add(ultravioletIndexTips[0]);
            } else if (airCondition.getUltravioletIntensity() >= 1000 || airCondition.getUltravioletIntensity() <= 3000) {
                indexList.add(ultravioletIndexs[1]);
                tipList.add(ultravioletIndexTips[1]);
            } else if (airCondition.getUltravioletIntensity() > 3000) {
                indexList.add(ultravioletIndexs[2]);
                tipList.add(ultravioletIndexTips[2]);
            }

            indexNumList.add(airCondition.getTemperature());
            if (airCondition.getTemperature() < 8) {
                indexList.add(tempIndexs[0]);
                tipList.add(tempIndexTips[0]);
            } else {
                indexList.add(tempIndexs[1]);
                tipList.add(tempIndexTips[1]);
            }

            indexNumList.add(airCondition.getTemperature());
            if (airCondition.getTemperature() < 12) {
                indexList.add(clothingIndexs[0]);
                tipList.add(clothingIndexTips[0]);
            } else if (airCondition.getTemperature() >= 12 || airCondition.getTemperature() <= 21) {
                indexList.add(clothingIndexs[1]);
                tipList.add(clothingIndexTips[1]);
            } else if (airCondition.getTemperature() > 21) {
                indexList.add(clothingIndexs[2]);
                tipList.add(clothingIndexTips[2]);
            }

            indexNumList.add(airCondition.getCo2());
            if (airCondition.getCo2() > 0 || airCondition.getCo2() < 3000) {
                indexList.add(sportIndexs[0]);
                tipList.add(sportIndexTips[0]);
            } else if (airCondition.getCo2() >= 3000 || airCondition.getCo2() <= 6000) {
                indexList.add(sportIndexs[1]);
                tipList.add(sportIndexTips[1]);
            } else if (airCondition.getCo2() > 6000) {
                indexList.add(sportIndexs[2]);
                tipList.add(sportIndexTips[2]);
            }

            indexNumList.add(airCondition.getPm());
            if (airCondition.getPm() > 0 || airCondition.getPm() < 30) {
                indexList.add(airIndexs[0]);
                tipList.add(airIndexTips[0]);
            } else if (airCondition.getPm() >= 30 || airCondition.getPm() <= 100) {
                indexList.add(airIndexs[1]);
                tipList.add(airIndexTips[1]);
            } else if (airCondition.getPm() > 3000) {
                indexList.add(airIndexs[2]);
                tipList.add(airIndexTips[2]);
            }
        }
    }

    @Override
    public LifeIndexListAdapter.ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_lifeindex_item_layout, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(LifeIndexListAdapter.ListHolder holder, int position) {
        //payloads是从notifyItemChanged(int, Object)中，或从notifyItemRangeChanged(int, int, Object)中传进来的Object集合
        //如果payloads不为空并且viewHolder已经绑定了旧数据了，那么adapter会使用payloads参数进行布局刷新
        //如果payloads为空，adapter就会重新绑定数据，也就是刷新整个item
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return zhishulist.length;
    }


    public class ListHolder extends RecyclerView.ViewHolder {
        ImageView iv_lifeIndex;
        TextView tv_lifeIndex_zhishu;
        TextView tv_lifeIndex_dengji;
        TextView tv_lifeIndex_miaoshu;

        public ListHolder(View itemView) {
            super(itemView);
            iv_lifeIndex = itemView.findViewById(R.id.iv_lifeIndex);
            tv_lifeIndex_zhishu = itemView.findViewById(R.id.tv_lifeIndex_zhishu);
            tv_lifeIndex_dengji = itemView.findViewById(R.id.tv_lifeIndex_dengji);
            tv_lifeIndex_miaoshu = itemView.findViewById(R.id.tv_lifeIndex_miaoshu);
        }

        public void setData(int position) {
//            iv_lifeIndex.setImageDrawable(context.getResources().getDrawable(icons[position % 10]));
            iv_lifeIndex.setImageDrawable(context.getResources().getDrawable(icons[position]));
            tv_lifeIndex_zhishu.setText(zhishulist[position] + "");
            tv_lifeIndex_dengji.setText(indexList.get(position) + "(" + indexNumList.get(position) + ")");
            tv_lifeIndex_miaoshu.setText(tipList.get(position) + "");
        }

    }
}
