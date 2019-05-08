package com.bang.transpor1.bean;

/**
 * Created by 12457 on 2018/10/23.
 */

public class CarNoEvent {
    public enum Type {
        //et发送消息
        TV_CARNO_SEND_MSG
    }

    public CarNoEvent.Type type;
    //    public View view;
    public Object data;

    public CarNoEvent(CarNoEvent.Type type, Object data) {
        this.type = type;
//        this.view = view;
        this.data = data;
    }
}
