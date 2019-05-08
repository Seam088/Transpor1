package com.bang.transpor1.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 12457 on 2018/10/26.
 */

public class AirCondition {

    /**
     * co2 : 3090
     * 紫外线强度 : 675
     * pm2.5 : 182
     * 温度 : 25
     * RESULT : S
     * 相对湿度 : 39
     * ERRMSG : 成功
     */

    private int co2;
    @SerializedName("紫外线强度")
    private int ultravioletIntensity;
    @SerializedName("pm2.5")
    private int pm; // FIXME check this code
    @SerializedName("温度")
    private int temperature;
    private String RESULT;
    @SerializedName("相对湿度")
    private int humidity;
    private String ERRMSG;

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int getUltravioletIntensity() {
        return ultravioletIntensity;
    }

    public void setUltravioletIntensity(int ultravioletIntensity) {
        this.ultravioletIntensity = ultravioletIntensity;
    }

    public int getPm() {
        return pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getERRMSG() {
        return ERRMSG;
    }

    public void setERRMSG(String ERRMSG) {
        this.ERRMSG = ERRMSG;
    }


}
