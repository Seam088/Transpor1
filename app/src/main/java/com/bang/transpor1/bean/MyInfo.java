package com.bang.transpor1.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 12457 on 2018/10/30.
 */

public class MyInfo implements Serializable {

    /**
     * 手机号码 : 18208120683
     * 身份证号码 : 430203199909091234
     * 名下车辆 : ["辽A10001","辽A10002","辽A10003","辽A10004","辽A10005"]
     * 姓名 : 师俊
     * ERRMSG : 成功
     * 性别 : 男
     * RESULT : S
     * 注册时间 : 1985-12-27
     */
    @SerializedName("手机号码")
    private String phoneTel;
    @SerializedName("身份证号码")
    private String sfzNum;
    @SerializedName("姓名")
    private String name;
    private String ERRMSG;
    @SerializedName("性别")
    private String sex;
    private String RESULT;
    @SerializedName("注册时间")
    private String regTime;
    @SerializedName("名下车辆")
    private List<String> nameCarList;

    public String getPhoneTel() {
        return phoneTel;
    }

    public void setPhoneTel(String phoneTel) {
        this.phoneTel = phoneTel;
    }

    public String getSfzNum() {
        return sfzNum;
    }

    public void setSfzNum(String sfzNum) {
        this.sfzNum = sfzNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getERRMSG() {
        return ERRMSG;
    }

    public void setERRMSG(String ERRMSG) {
        this.ERRMSG = ERRMSG;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public List<String> getNameCarList() {
        return nameCarList;
    }

    public void setNameCarList(List<String> nameCarList) {
        this.nameCarList = nameCarList;
    }

    @Override
    public String toString() {
        return "MyInfo{" +
                "phoneTel='" + phoneTel + '\'' +
                ", sfzNum='" + sfzNum + '\'' +
                ", name='" + name + '\'' +
                ", ERRMSG='" + ERRMSG + '\'' +
                ", sex='" + sex + '\'' +
                ", RESULT='" + RESULT + '\'' +
                ", regTime='" + regTime + '\'' +
                ", nameCarList=" + nameCarList +
                '}';
    }
}
