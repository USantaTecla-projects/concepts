package com.example.backend.util;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;

public class GetAuthToken {

    public static String getAuthCookie(){
        RequestSpecification loginReq = RestAssured.given();

        JSONObject loginReqParams = new JSONObject();
        loginReqParams.put("username", "teacher");
        loginReqParams.put("password", "1234");

        loginReq.header("Content-Type", "application/json");
        loginReq.body(loginReqParams.toJSONString());

        Response loginRes = loginReq.post("https://localhost:8443/auth/login");

        return loginRes.getCookie("AuthToken");
    }

}
