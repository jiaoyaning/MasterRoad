package com.jyn.language.Java学习.Java新特性;

/**
 * Created by jiaoyaning on 2020/12/16.
 */
class Example {
    private String name = "空name";
    private String age = "空age";

    Example() {

    }

    Example(String name) {
        this.name = name;
    }

    public Example(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String create() {
        return "create name";
    }

    @Override
    public String toString() {
        return "Example{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    interface InterfaceExample {
        Example create();
    }

    interface InterfaceExample2 {
        Example create(String name);
    }
}
