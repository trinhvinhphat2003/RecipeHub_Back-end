package com.example.RecipeHub.services.impl;

import com.example.RecipeHub.client.dtos.response.CallbackResponse;
import com.example.RecipeHub.client.dtos.response.ZaloPayResponse;
import com.example.RecipeHub.configs.ZaloPayConfiguration;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.services.ZaloPayService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.DatatypeConverter;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.zalopay.crypto.HMACUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Service
public class ZaloPayServiceImpl implements ZaloPayService {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Mac HmacSHA256;

    public ZaloPayServiceImpl() throws Exception {
        HmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec key = new SecretKeySpec(ZaloPayConfiguration.KEY2.getBytes("UTF-8"), "HmacSHA256");
        HmacSHA256.init(key);
    }

    @Override
    public ZaloPayResponse createPayment() throws Exception {
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

        // convert response body to response object
        ZaloPayResponse response = convertResponseBodyToObject(res, ZaloPayResponse.class);
        return response;
    }

    public void checkCallback(String jsonStr){
        // at first, i wanna use a callback response, but it's seem like it's not necessary to decode it;
        CallbackResponse response;
        JSONObject result = new JSONObject();
        try {
            JSONObject cbdata = new JSONObject(jsonStr);
            String dataStr = cbdata.getString("data");
            String reqMac = cbdata.getString("mac");

            byte[] hashBytes = HmacSHA256.doFinal(dataStr.getBytes());
            String mac = DatatypeConverter.printHexBinary(hashBytes).toLowerCase();

            // check whether it's a valid callback or not
            if (!reqMac.equals(mac)) {

            } else {
                // thanh toán thành công
                // merchant cập nhật trạng thái cho đơn hàng
                JSONObject data = new JSONObject(dataStr);
                logger.info("update order's status = success where apptransid = " + data.getString("apptransid"));

                result.put("returncode", 1);
                result.put("returnmessage", "success");
            }
        } catch (Exception ex) {
            result.put("returncode", 0); // ZaloPay server sẽ callback lại (tối đa 3 lần)
            result.put("returnmessage", ex.getMessage());
        }

        // thông báo kết quả cho ZaloPay server
//        return result.toString();
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
    }

    private <T> T convertResponseBodyToObject(CloseableHttpResponse res, Class<T> valueType) throws Exception{
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }
        ObjectMapper mapper = new ObjectMapper();
        T responseObject = mapper.readValue(resultJsonStr.toString(), valueType);
        return responseObject;
    }

    private String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }

    private Map<String, Object> getOrderRequestData(){
        final Map embeddata = new HashMap(){{
            put("redirecturl", ZaloPayConfiguration.REDIRECT_URL);
//            put("bankgroup", "ATM");
            put("promotioninfo","{\"campaigncode\":\"yeah\"}",;
            put("merchantinfo","embeddata123");
        }};
        final Map item = new HashMap();
        item.put("name", "RecipeHub premium");
        item.put("price", 100000);
        item.put("time", 1);
        item.put("timeUnit", "month");
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("appid", ZaloPayConfiguration.APP_ID);
        orderInfo.put("apptransid", getCurrentTimeString("yyMMdd") +"_"+ UUID.randomUUID());
        orderInfo.put("apptime", System.currentTimeMillis()); // miliseconds
//        orderInfo.put("appuser", user.getUserId());
        orderInfo.put("appuser", "testUser");
        orderInfo.put("amount", 500000);
        orderInfo.put("description", "RecipeHub premium");
        orderInfo.put("bankcode", "");
        orderInfo.put("item", new JSONObject(item).toString());
        orderInfo.put("embeddata", new JSONObject(embeddata).toString());

        String data = orderInfo.get("appid") +"|"+ orderInfo.get("apptransid") +"|"+ orderInfo.get("appuser") +"|"+ orderInfo.get("amount")
                +"|"+ orderInfo.get("apptime") +"|"+ orderInfo.get("embeddata") +"|"+ orderInfo.get("item");
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, ZaloPayConfiguration.KEY1, data);
        orderInfo.put("mac", mac);
        return orderInfo;
    }
}
