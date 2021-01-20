package com.jyn.java_kotlin.Reflection;

public class Test {
    private int integer;
    public String string;

    public Test() {
        System.out.println("== 调用了 public Test()");
    }

    public Test(String string) {
        this.string = string;
        System.out.println("== 调用了 public Test(string)");
    }

    private Test(int integer, String string) {
        this.integer = integer;
        this.string = string;
        System.out.println("== 调用了 private Test(integer , string) ; " +
                "integer :" + integer + " ; string :" + string);
    }

    public int getInteger() {
        System.out.println("== 调用了 public getInteger()");
        return integer;
    }

    public void setInteger(int integer) {
        System.out.println("== 调用了 public setInteger(integer) ; " + "integer :" + integer);
        this.integer = integer;
    }

    private String getString() {
        System.out.println("== 调用了 private getString()");
        return string;
    }

    private void setString(String string) {
        System.out.println("== 调用了 private setString(string) ; " + "string :" + string);
        this.string = string;
    }
}
