package com.example.third;

import java.util.List;

public class BannerData {
    List<DetailData> data;
    int errorCode;
    String errorMsg;
    static class DetailData{
        String category;
        String icon;
        int id;
        String link;
        String name;
        int order;
        int visible;
    }

}
