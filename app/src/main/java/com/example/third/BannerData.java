package com.example.third;

import java.util.List;

public class BannerData {
    List<DetailData> data;
    public int errorCode;
    public String errorMsg;
    static class DetailData{
        /*"       {\n" +
                "             \"category\":\"源码\",\n " +
                "             \"icon\":\"\",\n" +
                "             \"id\":22,\n" +
                "             \"link\": \"https://www.androidos.net.cn/sourcecode\",\n" +
                "             \"name\":\"androidos\",\n" +
                "             \"order\":11,\n" +
                "             \"visible\":1,\n" +
                "       },\n"*/
        String category;
        String icon;
        int id;
        String link;
        String name;
        int order;
        int visible;
    }

}
