package com.bang.transpor1.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 12457 on 2018/10/30.
 */

public class Chargelog implements Serializable {

    /**
     * RESULT : S
     * data : [{"amount":600,"charge":500,"who":"师俊","time":"2018-10-17 20:54:41","carno":"辽A10001"}]
     * ERRMSG : 成功
     */

    private String RESULT;
    private String ERRMSG;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * amount : 600
         * charge : 500
         * who : 师俊
         * time : 2018-10-17 20:54:41
         * carno : 辽A10001
         */

        private int amount;
        private int charge;
        private String who;
        private String time;
        private String carno;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCarno() {
            return carno;
        }

        public void setCarno(String carno) {
            this.carno = carno;
        }
    }
}
