package com.example.demo;

public class PublicInfo {
    static final String ACCESS_TOKEN_KEY = "access_token";
    static final String REFRESH_TOKEN_KEY = "refresh_token";
    static final String OPENID_KEY = "openid";
    static final String TOKEN = "weixin";
    static final String ACCESS_TOKEN_BASE = "https://api.weixin.qq.com/sns/oauth2/access_token";
    static final String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
    static final String APPID = "?appid=wx8e82b325d4a63e6e";
    static final String SECRET = "&secret=b84c87cd5a9d9a3f860987d4c53c7e32";
    static final String GRANT_TYPE = "&grant_type=authorization_code";
    static final String NORMAL_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token" +
            "?grant_type=client_credential" +
            "&appid=wx8e82b325d4a63e6e" +
            "&secret=b84c87cd5a9d9a3f860987d4c53c7e32";
    static final String LOGIN_URL = "https://open.weixin.qq.com/connect/oauth2/authorize" +
            "?appid=wx8e82b325d4a63e6e" +
            "&redirect_uri=http%3A%2F%2Fkx9smp.natappfree.cc" +
            "&response_type=code" +
            "&scope=snsapi_userinfo" +
            "&state=123" +
            "#wechat_redirect";

    static String normal_access_token = "";
    static String access_token = "";
    static String refresh_token = "";
    static String openid = "";
}
