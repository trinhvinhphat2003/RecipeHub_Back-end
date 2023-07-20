package com.example.RecipeHub.configs;

public class ZaloPayConfiguration {
    // key1, key2, app id will be provided when register merchant in zalo pay
    public static final String APP_ID = "2554";
    public static final String KEY1 = "sdngKKJmqEMzvh5QQcdD2A9XBSKUNaYn";
    public static final String KEY2 = "trMrHtvjo6myautxDUiAcYsVtaeQ8nhf";
    public static final String CREATE_ORDER_URL = "https://sandbox.zalopay.com.vn/v001/tpe/createorder";
    public static final String GET_ORDER_STATUS_URL = "https://sandbox.zalopay.com.vn/v001/tpe/getstatusbyapptransid";
    public static final String REDIRECT_URL = "http://localhost:8080/api/v1/zalo-pay/callback";
    public static final String IPN_URL = "Iyz2habzyr7AG8SgvoBCbKwKi3UzlLi3";
    public static final String GET_BANKS_URL = "https://sbgateway.zalopay.vn/api/getlistmerchantbanks";
}
