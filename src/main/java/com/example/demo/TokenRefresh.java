package com.example.demo;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TokenRefresh {
    @Scheduled(fixedRate = 7000 * 1000)
    public void getNormalAccessToken() {
        System.out.println(PublicInfo.NORMAL_ACCESS_TOKEN_URL);
        JSONObject jsonResponse;
        RestTemplate restTemplate = new RestTemplate();
        do {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            String result = restTemplate.getForObject(PublicInfo.NORMAL_ACCESS_TOKEN_URL, String.class);
            System.out.println("wechat server response : " + result);
            jsonResponse = new JSONObject(result);
        } while (!jsonResponse.has(PublicInfo.ACCESS_TOKEN_KEY));
        PublicInfo.normal_access_token = jsonResponse.getString(PublicInfo.ACCESS_TOKEN_KEY);
        System.out.println("normal access token : " + PublicInfo.normal_access_token);
    }
}
