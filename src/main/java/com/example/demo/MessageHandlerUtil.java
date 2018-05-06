package com.example.demo;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHandlerUtil {
    public static Map<String, String> parseXml(String content) throws Exception {
        Map<String, String> map = new HashMap();
        Document document = DocumentHelper.parseText(content);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();

        for (Element e : elementList) {
            System.out.println(e.getName() + "|" + e.getText());
            map.put(e.getName(), e.getText());
        }

        return map;
    }

    public static String buildTextMessage(Map<String, String> map, String content) {
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        return String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[text]]></MsgType>" +
                        "<Content><![CDATA[%s]]></Content>" + "</xml>",
                fromUserName, toUserName, getUtcTime(), content);
    }

    private static String getUtcTime() {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");
        String nowTime = df.format(dt);
        long dd = 0L;

        try {
            dd = df.parse(nowTime).getTime();
        } catch (Exception e) {
            System.out.println("Generate time error!!!");
        }

        return String.valueOf(dd);
    }
}
