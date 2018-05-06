package com.example.demo;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Controller
@SpringBootApplication
public class DemoApplication {
    private static final String TOKEN = "weixin";
    private static final String ACCESS_TOKEN_BASE = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private static final String APPID = "?appid=wx8e82b325d4a63e6e";
    private static final String SECRET = "&secret=b84c87cd5a9d9a3f860987d4c53c7e32";
    private static final String GRANT_TYPE = "&grant_type=authorization_code";

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping(value = "/")
    @ResponseBody
    String home(@RequestParam("code") String code,
                @RequestParam("state") String state) {
        System.out.println("GET :/ code = " + code + "; state = " + state);
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder urlBuff = new StringBuilder();
        urlBuff.append(ACCESS_TOKEN_BASE);
        urlBuff.append(APPID);
        urlBuff.append(SECRET);
        urlBuff.append("&code=");
        urlBuff.append(code);
        urlBuff.append(GRANT_TYPE);
        System.out.println(urlBuff.toString());
        String result = restTemplate.getForObject(urlBuff.toString(), String.class);
        System.out.println(result);
        JSONObject jsonResponse = new JSONObject(result);
        if(!jsonResponse.has("access_token")){
            return "";
        }
        System.out.println("access_token is :" + jsonResponse.getString("access_token"));
        return result;
    }

    @RequestMapping(value = "/certify")
    @ResponseBody
    public String certifyWechart(@RequestParam("signature") String signature,
                                 @RequestParam("timestamp") String timestamp,
                                 @RequestParam("nonce") String nonce,
                                 @RequestParam("echostr") String echostr) {
        StringBuilder buff = new StringBuilder();
        String[] vec = {TOKEN, timestamp, nonce};
        Arrays.sort(vec);

        for (String ele : vec) {
            buff.append(ele);
        }

        String buffString = buff.toString();
        String hashValue = DigestUtils.sha1Hex(buffString);
        String result = hashValue.equals(signature) ? echostr : hashValue;
        return result;
    }
}
