package com.bang.transpor1.adapter;

import android.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bang.transpor1.R;
import com.bang.transpor1.bean.ClickEvent;
import com.bang.transpor1.bean.ItemModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Bnag on 2018/10/13.
 */
public class PayDialogAdapter extends RecyclerView.Adapter<PayDialogAdapter.BaseViewHolder> {
    private ArrayList<ItemModel> dataList = new ArrayList<>();
    private int lastPressIndex = -1;

    public void replaceAll(ArrayList<ItemModel> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public PayDialogAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemModel.ONE:
                return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.one, parent, false));
            case ItemModel.TWO:
                return new TWoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.two, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(PayDialogAdapter.BaseViewHolder holder, int position) {

        holder.setData(dataList.get(position).data);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        void setData(Object data) {
        }
    }

    private class OneViewHolder extends BaseViewHolder {
        private TextView tv;

        public OneViewHolder(final View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Log.e("TAG", "OneViewHolder: "+position+"==>"+dataList.get(position).data);
                    if (lastPressIndex == position) {
                        lastPressIndex = -1;
                    } else {
                        lastPressIndex = position;
                    }
                    EventBus.getDefault().post(new ClickEvent(ClickEvent.Type.TV_SEND_MSG,dataList.get(position).data));
                    notifyDataSetChanged();
                }

            });

        }

        @Override
        void setData(Object data) {
            if (data != null) {
                int text = (int) data;
                tv.setText(text+"");
                if (getAdapterPosition() == lastPressIndex) {
                    tv.setSelected(true);
                    tv.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
                } else {
                    tv.setSelected(false);
                    tv.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.blue_500));
                }
            }
        }

    }

    private class TWoViewHolder extends BaseViewHolder {
        private EditText et;

        public TWoViewHolder(View view) {
            super(view);
            et = (EditText) view.findViewById(R.id.et);
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                //一般我们都是在这个里面进行我们文本框的输入的判断，上面两个方法用到的很少
                @Override
                public void afterTextChanged(Editable s) {
                    String etString = et.getText().toString().trim();
                    EventBus.getDefault().post(new ClickEvent(ClickEvent.Type.ET_SEND_MSG,etString));
                    Log.e("Bang2:",etString+"");
                }
            });
            Log.e("TAG", "twoViewHolder: "+et.getText().toString().trim());

//            EventBus.getDefault().post(new ClickEvent(new ClickEvent(ClickEvent.Type.SEND_MSG ,view , et.getText().toString().trim() )));
        }

        @Override
        void setData(Object data) {
            super.setData(data);
        }
    }
}
