package com.gll.stock.execute;

import com.alibaba.fastjson.JSON;
import com.gll.stock.model.StockDataWrapper;
import com.gll.stock.response.StockDataResponseData;
import com.gll.stock.response.StockResponse;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

/**
 * @author gll
 * @datetime 2020-08-23
 **/
public class StockDataCaller {



    public static StockDataWrapper queryStock(String code) {
        String json = callJson(code);
        StockResponse object = JSON.parseObject(json, StockResponse.class);
        StockDataResponseData stockWrapper = object.getData();
        stockWrapper.transferData();
        return stockWrapper;
    }

    private static String callJson(String code) {
        String url = "https://api.doctorxiong.club/v1/fund/detail?code=" + code;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            String resp = new String(call.execute().body().bytes());
            return resp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
