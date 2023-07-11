package com.example.RecipeHub.client.dtos.response;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class ZaloPayResponse {
    private String zptranstoken;
    private String orderurl;
    private int returncode;
    private String returnmessage;
//    "zptranstoken": "EOoYhp7fm2",
//            "orderurl": "https://qcgateway.zalopay.vn/openinapp?order=eyJ6cHRyYW5zdG9rZW4iOiJFT29ZaHA3Zm0yIiwiYXBwaWQiOjU1M30=",
//            "returncode": 1,
//            "returnmessage": ""

    public ZaloPayResponse(String zptranstoken, String orderurl, int returncode, String returnmessage) {
        this.zptranstoken = zptranstoken;
        this.orderurl = orderurl;
        this.returncode = returncode;
        this.returnmessage = returnmessage;
    }

    public String getZptranstoken() {
        return zptranstoken;
    }

    public void setZptranstoken(String zptranstoken) {
        this.zptranstoken = zptranstoken;
    }

    public String getOrderurl() {
        return orderurl;
    }

    public void setOrderurl(String orderurl) {
        this.orderurl = orderurl;
    }

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public String getReturnmessage() {
        return returnmessage;
    }

    public void setReturnmessage(String returnmessage) {
        this.returnmessage = returnmessage;
    }
}
