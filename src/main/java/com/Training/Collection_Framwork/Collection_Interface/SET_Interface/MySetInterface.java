package com.Training.Collection_Framwork.Collection_Interface.SET_Interface;


import java.util.*;

public class MySetInterface {
    public static void main(String[] args) {
        Set<String> s = new HashSet<String>();
        s.add("ew er");
        s.add("krihsd");
        s.add("krihsd");
        System.out.println(s);
        s.iterator();
        
        s.remove("ew er");
        System.out.println(s);
       // s.clear();
        System.out.println(s);
        for (String a : s)
               s.add(a);
               System.out.println(s.size() + " distinct words: " + s);
    }
}
