package com.bang.testt;

import android.view.View;

/**
 * Created by 12457 on 2018/10/11.
 */

public class ClickEvent {
    public enum Type {
        //et发送消息
        ET_SEND_MSG,
        //tv发送消息
        TV_SEND_MSG,
    }

    public Type type;
//    public View view;
    public Object data;

    public ClickEvent(Type type, Object data) {
        this.type = type;
//        this.view = view;
        this.data = data;
    }
}
