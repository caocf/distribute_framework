package com.jueyue.onepiece.test.entity;

public class BaiduWeatherEntity {

    private String error;
    private String date;
    private String status;
    public String getDate() {
        return date;
    }

    public String getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
