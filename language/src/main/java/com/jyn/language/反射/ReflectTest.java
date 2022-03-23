package com.jyn.language.反射;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*
 * Java知识点—反射
 * https://mp.weixin.qq.com/s/a6CPTxlaN6-o46lpb0vY6w
 *
 * Java反射进阶—聊聊反射的几个问题 （final关键字）
 * https://mp.weixin.qq.com/s/oKiIAwVyPjmUGt6M6jyk6w
 *
 * 深入分析Java方法反射的实现原理
 * https://mp.weixin.qq.com/s/45mfublTt4mh9EkQCuZ4qA
 *
 * Java基础之反射（非常重要）
 * https://mp.weixin.qq.com/s/oWsx2hmA9burob8zjNm43w
 *
 * 虽然反射很好用，增加了程序的灵活性，但是也有他的缺点：
 * 性能问题。由于用到动态类型（运行时才检查类型），所以反射的效率比较低。但是对程序的影响比较小，除非对性能要求比较高。所以需要在两者之间平衡。
 * 不够安全。由于可以执行一些私有的属性和方法，所以可能会带来安全问题。
 * 不易读写。当然这一点也有解决方案，比如jOOR库，但是不适用于Android定义为final的字段。
 *
 * private修饰的方法可以通过反射访问，那么private意义何在？
 *  1. private并不是解决安全问题的，如果想让解决代码的安全问题，请用别的办法。
 *  2. private的意义是OOP（面向对象编程）的封装概念。
 *
 * ReflectASM
 * https://github.com/EsotericSoftware/reflectasm
 * 通过代码生成来提供高性能的反射处理，自动为 get/set 字段提供访问类，访问类使用字节码操作而不是 Java 的反射技术，因此非常快。
 * 他的原理是通过ASM库，生成了一个新的类，然后相当于直接调用新的类方法，从而完成反射的功能。
 * 缺点：无法访问私有方式和私有变量
 */
public class ReflectTest {

    public static void main(String[] args) throws Exception {
        System.out.println("\n");

        //region 获取class的三种方式

        // 1、根据类路径获取类对象
        try {
            Class<?> clzForName = Class.forName("com.jyn.language.反射.Test");
            System.out.println("Class.forName方法得到的 class :" + clzForName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 2.实例对象的getClass()方法
        Test test = new Test();
        Class<? extends Test> clzGetClass = test.getClass();
        System.out.println("getClass()方法得到的 class :" + clzGetClass);

        // 3.直接获取
        Class<Test> clz = Test.class;
        System.out.println("Test.class方法得到的 class :" + clz);

        //endregion

        //反射获取构造方法
        System.out.println("反射获取构造方法 ===\n");
        constructor(clz);

        //获取成员变量 & 获取方法
        System.out.println("获取成员变量 & 获取方法 ===\n");
        instance(clz);

        //反射获取注解
        System.out.println("反射获取注解 ===\n");
        annotation(clz);

        //反射对于 final 关键字测试
        System.out.println("反射对于 final 关键字测试 ===\n");
        finalTest(clz);

        //反射对于 static 关键字测试
        System.out.println("反射对于 static 关键字测试 ===\n");
        staticTest(clz);

        //reflectASM 测试
        System.out.println("reflectASM 测试 ===\n");
        reflectASMTest(clz);

        System.out.println("\n");
    }

    // 反射获取构造方法 & 初始化对象
    private static void constructor(Class<Test> clz) throws Exception {
        //这个方法只能调用无参构造函数，也就是 Class 对象的 newInstance 方法不能传入参数
        clz.newInstance();

        //获取所有构造函数（不包括私有构造方法）
        Constructor<?>[] constructors1 = clz.getConstructors();

        //获取所有构造函数（包括私有构造方法）
        Constructor<?>[] constructors2 = clz.getDeclaredConstructors();

        //获取无参构造函数
        Constructor<Test> constructor = clz.getConstructor();

        //获取参数为String的构造函数
        Constructor<Test> constructor1 = clz.getConstructor(String.class);
        constructor1.newInstance("反射获取构造方法 Constructor创建一个对象 constructor : " + constructor1.toGenericString());

        //获取参数为 (int , String) 的私有构造函数
        Constructor<Test> constructor2 = clz.getDeclaredConstructor(int.class, String.class);
        constructor2.setAccessible(true);
        constructor2.newInstance(1, "反射获取构造方法 Constructor创建一个对象 constructor : " + constructor2.toGenericString());
    }

    // 获取成员变量 & 获取方法
    private static void instance(Class<Test> clz) throws Exception {
        Test test = clz.newInstance();
        /*
         * 获取类的属性（包括私有属性）
         */
        Field field = clz.getField("string");  //只能获取public
        field.set(test, "获取属性 field 反射修改后的string");

        Field declaredField = clz.getDeclaredField("integer");  //能获取所有类型
        declaredField.setAccessible(true);
        declaredField.set(test, 100);

        System.out.println("获取属性 field.set() 方法测试 :" + test.toString());

        /*
         * 获取类的方法
         * getMethod() public方法
         * getDeclaredMethod() private方法
         */
        Method method = clz.getDeclaredMethod("getString");
        method.setAccessible(true);
        System.out.println("获取方法 getString() 方法测试");
        method.invoke(test);

        Method method1 = clz.getDeclaredMethod("setString", String.class);
        method1.setAccessible(true);
        System.out.println("获取方法 setString() 方法测试");
        method1.invoke(test, "获取方法 后调用 setString() 的string形参");
    }

    // 反射获取注解
    private static void annotation(Class<Test> clz) throws Exception {
        Test test = clz.newInstance();
        Field[] fields = clz.getFields(); //获取所有的成员变量
        for (Field field : fields) {
            //判断有没有设置 @TestAnnotation
            boolean isSetTestAnnotation = field.isAnnotationPresent(TestAnnotation.class);
            if (isSetTestAnnotation) {
                System.out.println("该 变量 设置@TestAnnotation : " + field.getName());
                TestAnnotation annotation = field.getAnnotation(TestAnnotation.class);
                System.out.println("该 变量 设置@TestAnnotation  annotation.value : " + annotation.value());
                field.setAccessible(true);
                field.set(test, annotation.value());
            }
        }

        Method[] methods = clz.getMethods();
        for (Method method : methods) {
            boolean isSetAnnotation2 = method.isAnnotationPresent(TestAnnotation.class);
            if (isSetAnnotation2) {
                System.out.println("该 方法 设置@TestAnnotation : " + method.getName());
                TestAnnotation annotation = method.getAnnotation(TestAnnotation.class);
                System.out.println("该 方法 设置@TestAnnotation  annotation.value : " + annotation.value());
                method.setAccessible(true);
            }
        }

        System.out.println(test.toString());
    }

    // 反射对于 final 关键字测试
    private static void finalTest(Class<Test> clz) throws Exception {
        Test test = clz.newInstance();

        /*
         * 为什么不成功？
         * JVM的内联优化：内联函数，编译器将指定的函数体插入并取代每一处调用该函数的地方（上下文）
         * ，从而节省了每次调用函数带来的额外时间开支。
         *
         *「反射是可以修改final变量的，但是如果是基本数据类型或者String类型的时候
         * ，无法通过对象获取修改后的值，因为JVM对其进行了内联优化。」
         */
        Field field = clz.getField("finalStringTest");
        field.setAccessible(true); //final 也需要设置
        field.set(test, "反射final测试 这是反射修改后的值");
        System.out.println(test.getFinalStringTest());  //未成功 内联优化导致，具体看该方法注释
        System.out.println(test.finalStringTest);       //未成功 内联优化导致，编译后等于 : System.out.println("finalStringTest 默认值")
        System.out.println(field.get(test));            //成功 通过反射直接获取成员变量值

        //通过对对象的测试，可发现反射确实可以修改final所修饰的成员变量
        System.out.println(test.finalInner);
        Field field2 = clz.getField("finalInner");
        field2.setAccessible(true);
        field2.set(test, new Test.Inner());
        System.out.println(test.finalInner);            //前后地址变化，可见已被修改
    }

    // 反射对于 static 关键字测试
    private static void staticTest(Class<Test> clz) throws Exception {

        /*
         * 静态变量是在类的实例化之前就进行了初始化（类的初始化阶段），
         * 所以静态变量是跟着类本身走的，跟具体的对象无关，所以我们获取变量就不需要传入对象，直接传入null即可。
         *
         * Field.get(null) 可以获取静态变量。
         * Field.set(null,object) 可以修改静态变量。
         */

        Field field = clz.getField("staticStringTest");
        field.set(null, "反射static测试 这是反射修改后的值");
        System.out.println(Test.staticStringTest);
        System.out.println(field.get(null)); //直接通过null也可获取到static关键字
    }

    //reflectASM 使用
    private static void reflectASMTest(Class<Test> clz) {
        ConstructorAccess<Test> testConstructorAccess = ConstructorAccess.get(clz);
        Test test = testConstructorAccess.newInstance();

        MethodAccess methodAccess = MethodAccess.get(clz);
        int reflectInt = (int) methodAccess.invoke(test, "getInteger");
        System.out.println("通过reflectASM 调用getInteger方法 :" + reflectInt);

        FieldAccess fieldAccess = FieldAccess.get(clz);
        fieldAccess.set(test, "string", "reflectASM 获取属性 fieldAccess 反射修改后的string");
        System.out.println(test.toString());
    }
}
