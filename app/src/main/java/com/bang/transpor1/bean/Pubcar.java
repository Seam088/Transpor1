package com.bang.transpor1.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12457 on 2018/9/19.
 */

public class Pubcar implements Serializable{

    /**
     * data : [{"pass":false,"direction":false,"bus":1,"distance":4.848428083333147,"persons":5},{"pass":false,"direction":false,"bus":2,"distance":3.848428083333147,"persons":24}]
     * RESULT : S
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

    @Override
    public String toString() {
        return "Pubcar{" +
                "RESULT='" + RESULT + '\'' +
                ", ERRMSG='" + ERRMSG + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * pass : false
         * direction : false
         * bus : 1
         * distance : 4.848428083333147
         * persons : 5
         */
        private boolean pass;
        private boolean direction;
        private int bus;
        private double distance;
        private int persons;

        public boolean isPass() {
            return pass;
        }

        public void setPass(boolean pass) {
            this.pass = pass;
        }

        public boolean isDirection() {
            return direction;
        }

        public void setDirection(boolean direction) {
            this.direction = direction;
        }

        public int getBus() {
            return bus;
        }

        public void setBus(int bus) {
            this.bus = bus;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getPersons() {
            return persons;
        }

        public void setPersons(int persons) {
            this.persons = persons;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "pass=" + pass +
                    ", direction=" + direction +
                    ", bus=" + bus +
                    ", distance=" + distance +
                    ", persons=" + persons +
                    '}';
        }
    }
}
