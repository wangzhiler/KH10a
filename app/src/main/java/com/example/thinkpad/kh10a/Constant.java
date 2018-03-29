package com.example.thinkpad.kh10a;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2018/2/24.
 */

public class Constant {
    public static int textcount=1;

    public static int getImagecount() {
        return imagecount;
    }

    public static void setImagecount(int imagecount) {
        Constant.imagecount = imagecount;
    }

    public static int getCachecount() {
        return cachecount;
    }

    public static void setCachecount(int cachecount) {
        Constant.cachecount = cachecount;
    }

    public static int imagecount=1;
    public static int cachecount=1;
    public static ArrayList<Map<String,String>> list=new ArrayList<Map<String,String>>();

    Constant(){
        Log.d("Constant","构造函数进来了");
        textcount=1;
        list=new ArrayList<Map<String,String>>();
    }

    public static int getTextcount() {
        return textcount;
    }

    public static void setTextcount(int textcount) {
        Constant.textcount = textcount;
    }

    public static ArrayList<Map<String, String>> getList() {
        return list;
    }

    public static void addToList(Map map) {
        list.add(map);
    }
}
