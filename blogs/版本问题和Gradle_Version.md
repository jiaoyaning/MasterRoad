# 版本适配

[Android版本适配](https://juejin.cn/post/6898176468661059597)

## 6.0

### 运行时权限动态申请

---

## 7.0

### FileProvider

[透过FileProvider再看ContentProvider](https://mp.weixin.qq.com/s/xdeFJx5FLEZbfhARWSzx_g)

## 8.0

### 通知渠道适配

### 广播限制-无法再注册静态广播

### APP 安装权限

## 9.0

### 默认Https，Http请求失败

## 10.0

### 分区存储 - 沙盒

## 11.0

### 必须使用V2签名
### 检测网络是否属于5G

应用只能看到本应用专有的目录（通过 Context.getExternalFilesDir() 访问）以及特定类型的媒体。

### 定位权限

新增权限 ACCESS_BACKGROUND_LOCATION 在使用中（仅限前台）或始终（前台和后台）


---

# Gradle-Version

[一文带你读懂compileSdkVersion、minSdkVersion与targetSdkVersion](https://lilei.pro/2020/03/16/Android-SDK-versions/)

## compileSdkVersion - 开发版本

## minSdkVersion = 最低版本

## targetSdkVersion - APP目标版本

> 通知系统，向下兼容
> 比如我们的 APP 是基于5.0开发的，但是在安装在6.0的手机上时，显然是没有适配的。
>
> AOSP里有很多
> ```
>   getApplicationInfo().targetSdkVersion < Buid.XXXX样式的代码，就是用来判断targetSdkVersion的
> ``` 







