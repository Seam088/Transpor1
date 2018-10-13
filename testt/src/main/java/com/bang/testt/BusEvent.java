package com.bang.testt;

/**
 * Created by 12457 on 2018/10/11.
 */

public class BusEvent {
    public enum Type {
        /**
         * 登陆成功
         */
        LOGIN_SUCCESS,
        /**
         * 登陆失败
         */
        LOGIN_FAILURE,
    }

    public Type type;
    public String tag;
    public Object data;

    public BusEvent(Type type, String tag, Object data) {
        this.type = type;
        this.tag = tag;
        this.data = data;
    }
}
