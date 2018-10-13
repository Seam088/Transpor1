package com.bang.transpor1.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12457 on 2018/10/10.
 */

public class Car {

    /**
     * RESULT : S
     * data : [{"stop":false,"carno":"辽A10001","carplate":"宝马","$min":0,"amount":100,"speedmax":1000,"who":"张三","speedmin":0,"$max":10000,"speed":100},{"stop":false,"carno":"辽A10002","carplate":"中华","$min":0,"amount":99,"speedmax":1000,"who":"李四","speedmin":0,"$max":10000,"speed":100},{"stop":false,"carno":"辽A10003","carplate":"奔驰","$min":0,"amount":103,"speedmax":1000,"who":"王五","speedmin":0,"$max":10000,"speed":100},{"stop":false,"carno":"辽A10004","carplate":"马自达","$min":0,"amount":12,"speedmax":1000,"who":"赵六","speedmin":0,"$max":10000,"speed":100},{"stop":false,"carno":"辽A10005","carplate":"斯柯达","$min":0,"amount":0,"speedmax":1000,"who":"上官六","speedmin":0,"$max":10000,"speed":100}]
     * ERRMSG : 成功
     */

    private String RESULT;
    private String ERRMSG;
    private ArrayList<DataBean> data;

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getERRMSG() {
        return ERRMSG;
    }

    public void setERRMSG(String ERRMSG) {
        this.ERRMSG = ERRMSG;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * stop : false
         * carno : 辽A10001
         * carplate : 宝马
         * $min : 0
         * amount : 100
         * speedmax : 1000
         * who : 张三
         * speedmin : 0
         * $max : 10000
         * speed : 100
         */

        private boolean stop;
        private String carno;
        private String carplate;
        private int amount;
        private int speedmax;
        private String who;
        private int speedmin;
        @SerializedName("$min")
        private int min;
        @SerializedName("$max")
        private int max;
        private int speed;

        public boolean isStop() {
            return stop;
        }

        public void setStop(boolean stop) {
            this.stop = stop;
        }

        public String getCarno() {
            return carno;
        }

        public void setCarno(String carno) {
            this.carno = carno;
        }

        public String getCarplate() {
            return carplate;
        }

        public void setCarplate(String carplate) {
            this.carplate = carplate;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getSpeedmax() {
            return speedmax;
        }

        public void setSpeedmax(int speedmax) {
            this.speedmax = speedmax;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public int getSpeedmin() {
            return speedmin;
        }

        public void setSpeedmin(int speedmin) {
            this.speedmin = speedmin;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "stop=" + stop +
                    ", carno='" + carno + '\'' +
                    ", carplate='" + carplate + '\'' +
                    ", amount=" + amount +
                    ", speedmax=" + speedmax +
                    ", who='" + who + '\'' +
                    ", speedmin=" + speedmin +
                    ", min=" + min +
                    ", max=" + max +
                    ", speed=" + speed +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Car{" +
                "RESULT='" + RESULT + '\'' +
                ", ERRMSG='" + ERRMSG + '\'' +
                ", data=" + data +
                '}';
    }
}
