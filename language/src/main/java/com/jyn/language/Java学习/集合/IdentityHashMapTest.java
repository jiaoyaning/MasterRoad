package com.jyn.language.Java学习.集合;

import java.util.HashMap;
import java.util.IdentityHashMap;

/*
 * 你真的了解IdentityHashMap与HashMap区别吗？
 * https://blog.csdn.net/zzg1229059735/article/details/78991200
 */
public class IdentityHashMapTest {
    /**
     * 判断是否为同一Entry？
     *
     * HashMap
     *  if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
     *
     * IdentityHashMap
     *  if (item == k)
     */
    public static void main(String[] args) {
        identityTest();
    }

    private static void identityTest() {
        IdentityHashMap<Integer, String> identityHashMap = new IdentityHashMap<>();
        HashMap<Integer, String> hashMap = new HashMap<>();

        Integer integer0 = new Integer(0);
        Integer integer1 = new Integer(0);
        Integer integer2 = new Integer(0);

        identityHashMap.put(integer0, "0");
        identityHashMap.put(integer1, "1");
        identityHashMap.put(integer2, "2");

        hashMap.put(integer0, "0");
        hashMap.put(integer1, "1");
        hashMap.put(integer2, "2");

        System.out.println("identityHashMap size:" + identityHashMap.size());
        System.out.println("hashMap size:" + hashMap.size());
    }
}
