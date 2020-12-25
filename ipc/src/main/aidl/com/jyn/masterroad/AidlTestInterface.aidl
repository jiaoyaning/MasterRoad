package com.jyn.masterroad;
import com.jyn.masterroad.AidlTestBean;
/*
 * in 表示数据只能由客户端流向服务端，
 * out 表示数据只能由服务端流向客户端
 * inout 则表示数据可在服务端与客户端之间双向流通。
 * 此外，如果AIDL方法接口的参数值类型是：基本数据类型、String、CharSequence或者其他AIDL文件定义的方法接口，
 * 那么这些参数值的定向 Tag 默认是且只能是 in，所以除了这些类型外，其他参数值都需要明确标注使用哪种定向Tag
 * (需要限制方向在我们真正需要的情况内，而不是全都写成 inout，因为编组数据开销很大。)
 *
 * 客户端要想使用 AIDL 接口，首先需要在自己的工程内保存一份 .aidl 文件，路径、名字都需要保持一致，否则无法正常使用。
 * 如果是在同一个 App 里那么无需操作，但是如果是另外一个 App，则需要。可以拷贝一份，也可以依赖 aar。
 * 把 AIDL 相关的东西放到一个模块，做成 aar 让服务端和客户端工程都依赖是一个不错的选择。
 */
interface AidlTestInterface {
    String getTest();
    void setTest(String test);

    AidlTestBean testFun(in AidlTestBean aidlTestBean1,in AidlTestBean aidlTestBean2);

    void setInTest(in AidlTestBean inTest); //基本数据类型默认切必须是in，两端不能同步修改
    void setOutTest(out AidlTestBean outTest); //自定义类型必须制定Tag，bean类不能被客户端更改，只能由服务端更改
    void setInOutTest(inout AidlTestBean inoutTest); //服务端修改后，自动同步到客户端
    AidlTestBean getTestBean();

    /*
     * oneway 修饰的方法不能有返回值；
     * oneway 修饰的方法不能有 out 类型的参数；
     * oneway 只对不同进程的调用有用，本地进程无效，依然是同步调用；
     *
     * 客户端调用该方法立马就返回的，不会等服务端处理的结果，那么最好不修改参数也不返回数据。
     */
    oneway void onewayTest(in AidlTestBean aidlTestBean);
}
