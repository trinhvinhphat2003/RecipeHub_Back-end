package com.example.RecipeHub.services.impl;

import com.example.RecipeHub.configs.ZaloPayConfiguration;
import com.example.RecipeHub.services.ZaloPayService;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import vn.zalopay.crypto.HMACUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ZaloPayServiceImpl implements ZaloPayService {
    @Override
    public void createPayment() throws IOException, URISyntaxException {
        Map<String, Object> orderInfo = getOrderRequestData();

        // fetch data from zalo pay server, http post
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(ZaloPayConfiguration.CREATE_ORDER_URL);
        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : orderInfo.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }
        post.setEntity(new UrlEncodedFormEntity(params));
        for(Header header: post.getHeaders()){
            System.out.println(header.getName()+": "+header.getValue());
        }
        CloseableHttpResponse res = client.execute(post);

        // convert data to json
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }

        JSONObject result = new JSONObject(resultJsonStr.toString());
    }

    private String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }

    private Map<String, Object> getOrderRequestData(){
        final Map embeddata = new HashMap(){{
            put("merchantinfo", "embeddata123");
            put("redirecturl", ZaloPayConfiguration.REDIRECT_URL);
        }};
        final Map item = new HashMap(){{
            put("name", "RecipeHub premium");
            put("price", 100000);
            put("time", 1);
            put("timeUnit", "month");
        }};
        Map<String, Object> orderInfo = new HashMap<>(){{
            put("appid", ZaloPayConfiguration.APP_ID);
            put("apptransid", getCurrentTimeString("yyMMdd") +"_"+ UUID.randomUUID());
            put("apptime", System.currentTimeMillis()); // miliseconds
            put("appuser", "demo");
            put("amount", 500000);
            put("description", "RecipeHub premium");
            put("bankcode", "zalopayapp");
            put("item", new JSONObject(item).toString());
            put("embeddata", new JSONObject(embeddata).toString());
        }};

        String data = orderInfo.get("appid") +"|"+ orderInfo.get("apptransid") +"|"+ orderInfo.get("appuser") +"|"+ orderInfo.get("amount")
                +"|"+ orderInfo.get("apptime") +"|"+ orderInfo.get("embeddata") +"|"+ orderInfo.get("item");
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, ZaloPayConfiguration.KEY1, data);
        orderInfo.put("mac", mac);
        return orderInfo;
    }

    public void getOrderStatus() throws Exception{
        String apptransid = "";
        String data = ZaloPayConfiguration.APP_ID +"|"+ apptransid  +"|"+ ZaloPayConfiguration.KEY1; // appid|apptransid|key1
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, ZaloPayConfiguration.KEY1, data);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("appid", ZaloPayConfiguration.APP_ID));
        params.add(new BasicNameValuePair("apptransid", apptransid));
        params.add(new BasicNameValuePair("mac", mac));

        URIBuilder uri = new URIBuilder(ZaloPayConfiguration.GET_ORDER_STATUS_URL);
        uri.addParameters(params);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri.build());

        CloseableHttpResponse res = client.execute(get);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }

        JSONObject result = new JSONObject(resultJsonStr.toString());
        for (String key : result.keySet()) {
            System.out.format("%s = %s\n", key, result.get(key));
        }
    }
}
