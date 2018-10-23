package com.bang.transpor1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bang.transpor1.R;
import com.bang.transpor1.bean.Pubcar;

import java.util.ArrayList;
import java.util.LinkedList;

public class PubcalListAdater extends BaseAdapter {
    LinkedList<Pubcar> pubcars = new LinkedList<>();
    Context context;
    private LayoutInflater inflater;
    private int carId;
    private String[] platform;

    public PubcalListAdater(Context context, int carId) {
        this.context = context;
        this.carId = carId;
    }

    public void addList(Pubcar pubcar){
        pubcars.offer(pubcar);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pubcars.size();
    }

    @Override
    public Object getItem(int position) {
        return pubcars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ParentListItem parentListItem = null;
        if (convertView == null) {
            parentListItem = new ParentListItem();
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.lv_item_bus, null, false);
            parentListItem.tv_bus_title = convertView.findViewById(R.id.tv_bus_title);
            parentListItem.tv_busId_one = convertView.findViewById(R.id.tv_busId_one);
            parentListItem.tv_busId1_one = convertView.findViewById(R.id.tv_busId1_one);
            parentListItem.tv_bustime_one = convertView.findViewById(R.id.tv_bustime_one);
            parentListItem.tv_bustime1_one = convertView.findViewById(R.id.tv_bustime1_one);
            parentListItem.tv_busdistance_one = convertView.findViewById(R.id.tv_busdistance_one);
            parentListItem.tv_busdistance1_one = convertView.findViewById(R.id.tv_busdistance1_one);

            parentListItem.tv_busId_two = convertView.findViewById(R.id.tv_busId_two);
            parentListItem.tv_busId1_two = convertView.findViewById(R.id.tv_busId1_two);
            parentListItem.tv_bustime_two = convertView.findViewById(R.id.tv_bustime_two);
            parentListItem.tv_bustime1_two = convertView.findViewById(R.id.tv_bustime1_two);
            parentListItem.tv_busdistance_two = convertView.findViewById(R.id.tv_busdistance_two);
            parentListItem.tv_busdistance1_two = convertView.findViewById(R.id.tv_busdistance1_two);
            convertView.setTag(parentListItem);
        } else {
            parentListItem = (ParentListItem) convertView.getTag();
        }
    /*    if (carId == 0) {
            platform = "中医院站";
        } else if (carId == 1) {
            platform = "联想大厦站";
        }else if (carId == 2) {
            platform = "第一中学站";
        }else if (carId == 3) {
            platform = "幸福小区";
        }*/
        //站台
        platform = new String[]{"中医院站","联想大厦站","第一中学站","幸福小区"};
        String distance = String.valueOf(pubcars.get(position).getData().get(0).getDistance() * 1000);
        parentListItem.tv_bus_title.setText(platform[position] + "");
        parentListItem.tv_busId_one.setText("乘坐" + pubcars.get(position).getData().get(0).getBus() + "号车");
        parentListItem.tv_busId1_one.setText(pubcars.get(position).getData().get(0).getBus() + "号（" + pubcars.get(position).getData().get(0).getPersons() + "人）");
        parentListItem.tv_bustime_one.setText(pubcars.get(position).getData().get(0).getBus() + "号车" + "5分钟到达");
        parentListItem.tv_bustime1_one.setText("5分钟");
        parentListItem.tv_busdistance_one.setText("步行" + distance.substring(0, distance.indexOf(".") + 3) + "米，到达" + platform[position]);
        parentListItem.tv_busdistance1_one.setText(distance.substring(0, distance.indexOf(".") + 3) + "米");

        String distance1 = String.valueOf(pubcars.get(position).getData().get(1).getDistance() * 1000);
        parentListItem.tv_busId_two.setText("乘坐" + pubcars.get(position).getData().get(1).getBus() + "号车");
        parentListItem.tv_busId1_two.setText(pubcars.get(position).getData().get(1).getBus() + "号（" + pubcars.get(position).getData().get(1).getPersons() + "人）");
        parentListItem.tv_bustime_two.setText(pubcars.get(position).getData().get(1).getBus() + "号车" + "5分钟到达");
        parentListItem.tv_bustime1_two.setText("5分钟");

        parentListItem.tv_busdistance_two.setText("步行" + distance1.substring(0, distance1.indexOf(".") + 3) + "米，到达" + platform[position]);
        parentListItem.tv_busdistance1_two.setText(distance1.substring(0, distance1.indexOf(".") + 3) + "米");

        return convertView;
    }

    public class ParentListItem {
        TextView tv_bus_title, tv_busId_one, tv_busId1_one, tv_bustime_one, tv_bustime1_one, tv_busdistance_one, tv_busdistance1_one;
        TextView tv_busId_two, tv_busId1_two, tv_bustime_two, tv_bustime1_two, tv_busdistance_two, tv_busdistance1_two;
    }

}
