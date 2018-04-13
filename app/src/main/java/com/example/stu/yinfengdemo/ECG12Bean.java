package com.example.stu.yinfengdemo;

/**
 * Created by yf on 2018/3/13.
 * 12道心电
 */

public class ECG12Bean {
    private String code_code;
    private String digits;
    private Double[] ecgArr;
    private double max;
    private double min;

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public Double[] getEcgArr() {
        return ecgArr;
    }

    public void setEcgArr(Double[] ecgArr) {
        this.ecgArr = ecgArr;
    }


    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits;
    }

    public String getCode_code() {
        return code_code;
    }

    public void setCode_code(String code_code) {
        this.code_code = code_code;
    }

//    class value {
//        private String digits;
//    }


}
