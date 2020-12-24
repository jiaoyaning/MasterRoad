package com.jyn.masterroad;

/*
 * in 表示数据只能由客户端流向服务端，
 * out 表示数据只能由服务端流向客户端
 * inout 则表示数据可在服务端与客户端之间双向流通。
 * 此外，如果AIDL方法接口的参数值类型是：基本数据类型、String、CharSequence或者其他AIDL文件定义的方法接口，
 * 那么这些参数值的定向 Tag 默认是且只能是 in，所以除了这些类型外，其他参数值都需要明确标注使用哪种定向Tag
 */
interface AidlTestInterface {
    String getTest();
    void setTest(String test);

    AidlTestBean testFun(in AidlTestBean aidlTestBean1,in AidlTestBean aidlTestBean2);
}
