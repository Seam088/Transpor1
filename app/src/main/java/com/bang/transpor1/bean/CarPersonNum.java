package com.bang.transpor1.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 12457 on 2018/11/1.
 */

public class CarPersonNum implements Serializable {


    /**
     * RESULT : S
     * data : [{"personNum":119,"carId":1},{"personNum":57,"carId":2},{"personNum":15,"carId":3},{"personNum":13,"carId":4},{"personNum":66,"carId":5},{"personNum":69,"carId":6},{"personNum":22,"carId":7},{"personNum":26,"carId":8},{"personNum":21,"carId":9},{"personNum":84,"carId":10},{"personNum":48,"carId":11},{"personNum":32,"carId":12},{"personNum":86,"carId":13},{"personNum":80,"carId":14},{"personNum":63,"carId":15}]
     * ERRMSG : 成功
     */

    private String RESULT;
    private String ERRMSG;
    @SerializedName("data")
    private List<DataBean> dataList;

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

    public List<DataBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataBean {
        /**
         * personNum : 119
         * carId : 1
         */

        private int personNum;
        private int carId;

        public int getPersonNum() {
            return personNum;
        }

        public void setPersonNum(int personNum) {
            this.personNum = personNum;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }
    }

    @Override
    public String toString() {
        return "CarPersonNum{" +
                "RESULT='" + RESULT + '\'' +
                ", ERRMSG='" + ERRMSG + '\'' +
                ", dataList=" + dataList +
                '}';
    }
}
