package com.example.demo;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;


@Controller
@SpringBootApplication
public class DemoApplication {
    static final String TOKEN = "weixin";

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping(value = "/")
    @ResponseBody
    String home() {
        System.out.println("GET :/");
        return "hello world";
    }

    @RequestMapping(value = "/certify")
    @ResponseBody
    public String sayHello(@RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("echostr") String echostr) {
        System.out.println("GET /certify");
        String[] vec = {TOKEN, timestamp, nonce};
        Arrays.sort(vec);
        String hashValue = DigestUtils.sha1Hex(nonce);
        String response = hashValue.equals(signature) ? echostr : hashValue;
        return response;
    }
}
