package com.sword.app.jsoup;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestResponse {

    private String empcode;
    private String requestUrl;
    private String result;
    private String startTime;
    private String endTime;

    public TestResponse(String empcode, String requestUrl, String result, String startTime, String endTime) {
        this.empcode = empcode;
        this.requestUrl = requestUrl;
        this.result = result;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TestResponse(String empcode, String requestUrl, String result) {
        this.empcode = empcode;
        this.requestUrl = requestUrl;
        this.result = result;
    }

    public TestResponse(String empcode, String requestUrl) {
        this.empcode = empcode;
        this.requestUrl = requestUrl;
    }

}
