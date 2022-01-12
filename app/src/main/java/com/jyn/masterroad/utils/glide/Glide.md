# 缓存策略

DiskCacheStrategy.NONE //不缓存任何图片   
DiskCacheStrategy.ALL //缓存原始图片（默认）   
DiskCacheStrategy.SOURCE //只缓存原始图片   
DiskCacheStrategy.RESULT //只缓存转换后的图片

### 内存缓存 LruCache

### 磁盘缓存 DiskLruCache

# 防止OOM 策略

## 1. 软引用 (注意：不是弱引用)

## 2. onLowMemory

## 3. Bitmap优化

[图形图像处理 - 我们所不知道的 Bitmap](https://www.jianshu.com/p/e430b95010c7)

> 1. 采用 RGB_565编码
> 2. 根据尺寸压缩Bitmap

