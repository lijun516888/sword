package com.sword.app.jsoup;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.sword.core.utils.Dates;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Slf4j
public class AgentClient {

    private String host = "";
    private String indexUrl = "/manage/index.html";
    private String loginUrl = "/manage/login.html";
    private List<String> indexUrls;
    private String cid = "1000";
    private Map<String, String> cookies;
    private JSONObject loginResult;

    private String empCode;
    private String password;

    public AgentClient(String empCode, String password, List<String> indexUrls) {
        this.empCode = empCode;
        this.password = password;
        this.indexUrls = indexUrls;
    }

    public boolean intoSystem() {
        this.getCookies();
        if(this.cookies != null) {
            this.doLogin();
            if(loginResult != null && loginResult.getBoolean("success")) {
                this.loadingIndexRequest();
            }
        }
        return true;
    }

    private void getCookies() {
        try {
            Connection.Response response = Jsoup.connect(host + indexUrl)
                    .data(Maps.newHashMap())
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .timeout(100000)
                    .execute();
            Map<String, String> cookies = response.cookies();
            this.cookies = cookies;
        } catch (IOException e) {
            log.error("工号{}获取cookies失败,原因:{}", this.empCode, e);
        }
    }

    private void doLogin() {
        try {
            String result = Jsoup.connect(host + loginUrl)
                    .data(this.getLoginParams())
                    .header("X-CSRF-TOKEN", StringUtils.isBlank(cookies.get("_csrf")) ? "" : cookies.get("_csrf"))
                    .cookies(cookies)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(100000)
                    .execute().body();
            loginResult = JSONObject.parseObject(result);
            log.info("登陆返回信息:{}" + result);
        } catch (IOException e) {
            log.error("工号{}登陆失败,原因:{}", this.empCode, e);
        }
    }

    private Map<String, String> getLoginParams() {
        Map<String, String> data = Maps.newHashMap();
        data.put("cid", this.cid);
        data.put("username", this.cid + "_" + this.empCode);
        data.put("password", this.password);
        data.put("captcha","");
        data.put("targetUrl","");
        data.put("extPhone", this.empCode);
        data.put("app", "0");
        return data;
    }

    private void loadingIndexRequest() {
        indexUrls.parallelStream().forEach(a -> new Thread(() -> {
            TestResponse testResponse = new TestResponse(this.empCode, a);
            testResponse.setStartTime(Dates.format(new Date()));
            try {
                Connection.Response response = Jsoup.connect(host + a)
                        .data(this.getLoginParams())
                        .header("X-CSRF-TOKEN", StringUtils.isBlank(cookies.get("_csrf")) ? "" : cookies.get("_csrf"))
                        .cookies(cookies)
                        .method(Connection.Method.POST)
                        .timeout(100000)
                        .ignoreContentType(true)
                        .execute();
                testResponse.setEndTime(Dates.format(new Date()));
                testResponse.setResult(response.body());
                // log.info("工号{}加载首页数据成功,请求URL={}", this.empCode, a);
            } catch (IOException e) {
                testResponse.setEndTime(Dates.format(new Date()));
                testResponse.setResult(e.getMessage());
                // log.error("工号{}加载首页数据失败,请求URL={},错误原因:{}", this.empCode, a, e);
            }
            PressureTest.requestList.add(testResponse);
        }).start());
    }

}
