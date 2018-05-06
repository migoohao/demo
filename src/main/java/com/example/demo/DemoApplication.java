package com.example.demo;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;


@Controller
@EnableScheduling
@SpringBootApplication
public class DemoApplication {
    private String USER_CODE = "";

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping(value = "/")
    @ResponseBody
    String home(@RequestParam("code") String code,
                @RequestParam("state") String state) {
        if (code.equals(USER_CODE)) {
            return "hello world";
        }
        USER_CODE = code;
        System.out.println("GET :/ code = " + code + "; state = " + state);
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder urlBuff = new StringBuilder();
        urlBuff.append(PublicInfo.ACCESS_TOKEN_BASE);
        urlBuff.append(PublicInfo.APPID);
        urlBuff.append(PublicInfo.SECRET);
        urlBuff.append("&code=");
        urlBuff.append(code);
        urlBuff.append(PublicInfo.GRANT_TYPE);
        System.out.println(urlBuff.toString());
        String result = restTemplate.getForObject(urlBuff.toString(), String.class);
        System.out.println(result);
        JSONObject jsonResponse = new JSONObject(result);
        if (!jsonResponse.has("access_token")) {
            return "Can't get user's access_token!";
        }
        System.out.println("access_token is :" + jsonResponse.getString("access_token"));
        PublicInfo.access_token = jsonResponse.getString(PublicInfo.ACCESS_TOKEN_KEY);
        PublicInfo.refresh_token = jsonResponse.getString(PublicInfo.REFRESH_TOKEN_KEY);
        PublicInfo.openid = jsonResponse.getString(PublicInfo.OPENID_KEY);
        System.out.println(PublicInfo.ACCESS_TOKEN_KEY + " : " + PublicInfo.access_token);
        System.out.println(PublicInfo.OPENID_KEY + " : " + PublicInfo.openid);
        return "hello world";
    }

    @GetMapping(value = "/migao")
    @ResponseBody
    public String certifyWechart(@RequestParam("signature") String signature,
                                 @RequestParam("timestamp") String timestamp,
                                 @RequestParam("nonce") String nonce,
                                 @RequestParam("echostr") String echostr) {
        System.out.println("start certify token...");
        StringBuilder buff = new StringBuilder();
        String[] vec = {PublicInfo.TOKEN, timestamp, nonce};
        Arrays.sort(vec);
        for (String ele : vec) {
            buff.append(ele);
        }
        String buffString = buff.toString();
        String hashValue = DigestUtils.sha1Hex(buffString);

        if (hashValue.equals(signature)) {
            System.out.println("certify success!!!");
            return echostr;
        }
        System.out.println("certify error!!!");
        return "";
    }

    @PostMapping(value = "/migao")
    @ResponseBody
    public String sendLoginUrl(@RequestBody String xmlContent) {
        Map<String, String> receiveInfo;
        System.out.println("receive a message.");
        try {
            receiveInfo = MessageHandlerUtil.parseXml(xmlContent);
        } catch (Exception e) {
            System.out.println("Parse xml error");
            return "";
        }
        return MessageHandlerUtil.buildTextMessage(receiveInfo, PublicInfo.LOGIN_URL);
    }
}
