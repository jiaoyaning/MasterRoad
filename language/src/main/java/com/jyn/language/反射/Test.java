package com.jyn.language.反射;

public class Test {
    private int integer;
    @TestAnnotation
    public String string;

    //测试反射能不能修改final
    public final String finalStringTest = "finalStringTest final 修饰 默认值";
    public final Inner finalInner = new Inner();

    //反射 对于 static
    public static String staticStringTest = "staticStringTest static 修饰 默认值";

    public Test() {
        System.out.println("== 调用了 public Test() 构造方法");
    }

    public Test(String string) {
        this.string = string;
        System.out.println("== 调用了 public Test(string) 构造方法 ; string :" + string);
    }

    private Test(int integer, String string) {
        this.integer = integer;
        this.string = string;
        System.out.println("== 调用了 private Test(integer , string) 构造方法 ; " +
                "integer :" + integer + " ; string :" + string);
    }

    public int getInteger() {
        System.out.println("== 调用了 public getInteger() 共有方法");
        return integer;
    }

    public void setInteger(int integer) {
        System.out.println("== 调用了 public setInteger(integer) 共有方法 ; " + "integer :" + integer);
        this.integer = integer;
    }

    private String getString() {
        System.out.println("== 调用了 private getString() 私有方法");
        return string;
    }

    @TestAnnotation(value = "注解value所设置的值")
    private void setString(String string) {
        System.out.println("== 调用了 private setString(string) 私有方法 ; " + "string :" + string);
        this.string = string;
    }

    @Override
    public String toString() {
        return "Test{" +
                "integer=" + integer +
                ", string='" + string + '\'' +
                '}';
    }

    /*
     * 内联优化后，等于 :
     * public String getFinalStringTest() {
     *      return "finalStringTest 默认值";
     * }
     */
    public String getFinalStringTest() {
        return finalStringTest;
    }

    static class Inner {

    }
}
