package com.example.RecipeHub.client.dtos.request;

import vn.zalopay.crypto.HMACUtil;

import java.util.HashMap;
import java.util.Map;

public class ZaloPayRequest {
    private int appId;
    private String appUser;
    private long appTime;
    private long amount;
    private String appTransId;
    private String embedData;
    private String item;
    private String mac;
    private String bankCode;
    private String description;
//    phone	String	50		Số điện thoại của người dùng	0934568239
////    email	String	100		Email của người dùng	example@gmail.com
////    address	String	1024		Địa chỉ của người dùng	TPHCM
////    subappid	String

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }

    public long getAppTime() {
        return appTime;
    }

    public void setAppTime(long appTime) {
        this.appTime = appTime;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getAppTransId() {
        return appTransId;
    }

    public void setAppTransId(String appTransId) {
        this.appTransId = appTransId;
    }

    public String getEmbedData() {
        return embedData;
    }

    public void setEmbedData(String embedData) {
        this.embedData = embedData;
    }

    public String getMac() {
        if(this.mac == null){
            String data = this.appId +"|"+ this.appTransId +"|"+ this.appUser +"|"+ this.amount +"|"+ this.appTime +"|"+ this.embedData +"|"+ this.item;
            mac = HMACUtil.HMacBase64Encode(HMACUtil.HMACSHA256, "", data);
            this.mac = mac;
        }
        return mac;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
