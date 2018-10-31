package com.sword.app;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TestJsoup {

    public static void main(String[] args) throws Exception {
        Map<String, String> datas = Maps.newHashMap();
        String url = "http://192.168.1.189:8888/manage/index.html";
        Connection.Response response = Jsoup.connect(url)
                .data(datas)
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .execute();
        Map<String, String> cookies = response.cookies();
        System.out.println("==============INDEX===COMPLETE===========");
        url = "http://192.168.1.189:8888/manage/login.html";
        response = Jsoup.connect(url)
                .data(TestJsoup.loginInfo())
                .header("X-CSRF-TOKEN", StringUtils.isBlank(cookies.get("_csrf")) ? "" : cookies.get("_csrf"))
                .cookies(cookies)
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .execute();
        System.out.println("==============LOGIN===COMPLETE===========");
        final String url1 = "http://192.168.1.189:8888/manage/reportTool/reportScheme/selectReportData" +
                ".html?reportid=23&rview=0&order=asc&page=1&search_EQ_IMPORTID=&" +
                "search_GTE_a.FRIST_CALLOUTTIME=2018-10-31+00%3A00%3A00&" +
                "search_LTE_a.FRIST_CALLOUTTIME=2018-10-31+23%3A59%3A00&isBTSearch=true&_=1540975544694";

        List<String> urls = Lists.newArrayList();
        for(int i = 0; i < 10000; i++) {
            urls.add(url1);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        AtomicInteger success = new AtomicInteger();
        AtomicInteger fail = new AtomicInteger();
        AtomicInteger index = new AtomicInteger();
        CompletableFuture[] cfs = urls.stream().map(a -> CompletableFuture.supplyAsync(() -> {
            System.out.println("=============BEGIN==============" + index.getAndIncrement());
            try {
                Jsoup.connect(url1)
                        .data(TestJsoup.loginInfo())
                        .header("X-CSRF-TOKEN", StringUtils.isBlank(cookies.get("_csrf")) ? "" : cookies.get("_csrf"))
                        .cookies(cookies)
                        .method(Connection.Method.POST)
                        .ignoreContentType(true)
                        .timeout(20000)
                        .execute().body();
                success.getAndIncrement();
                System.out.println("===================SUCCESS=================");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            fail.getAndIncrement();
            return false;
        }, executorService)).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(cfs).join();
        System.out.println("===========================");
        System.out.println("SCCUESS IS " + success.get());
        System.out.println("FAIL IS " + fail.get());
    }

    public static Map<String, String> loginInfo() {
        Map<String, String> data = Maps.newHashMap();
        data.put("cid", "3000");
        data.put("username", "3000_sa");
        data.put("password", "accp123");
        data.put("captcha","");
        data.put("targetUrl","");
        data.put("extPhone", "");
        data.put("app", "0");
        return data;
    }

}
