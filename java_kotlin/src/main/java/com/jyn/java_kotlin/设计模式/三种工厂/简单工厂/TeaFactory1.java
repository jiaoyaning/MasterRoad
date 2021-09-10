package com.jyn.java_kotlin.设计模式.三种工厂.简单工厂;

public class TeaFactory1 {
    private static CocoTea createCocoTea(String name, String price) {
        return new CocoTea(name, price);
    }

    private static PearlTea createPearlTea(String name, String price) {
        return new PearlTea(name, price);
    }

    private static PuddingTea createPuddingTea(String name, String price) {
        return new PuddingTea(name, price);
    }

    public static Tea create(String name, String price) {
        if ("Coco".equals(name)) {
            return createCocoTea(name, price);
        } else if ("Pearl".equals(name)) {
            return createPearlTea(name, price);
        } else if ("Pudding".equals(name)) {
            return createPuddingTea(name, price);
        }
        return null;
    }
}
