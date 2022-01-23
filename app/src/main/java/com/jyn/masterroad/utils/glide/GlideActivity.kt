package com.jyn.masterroad.utils.glide

import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityGlideBinding

/*
 * 面试官：简历上最好不要写Glide，不是问源码那么简单
 * https://juejin.cn/post/6844903986412126216
 *
 * 【带着问题学】Glide做了哪些优化?
 * https://juejin.cn/post/6970683481127043085
 *
 * Android NDK —— GIFLIB 优化 Glide GIF 的加载
 * https://sharrychoo.github.io/blog/android-source/graphic-choreographer
 *
 * Glide源码解析 优秀
 * https://www.jianshu.com/nb/45157164
 */

@Route(path = RoutePath.Glide.path)
class GlideActivity : BaseActivity<ActivityGlideBinding>(R.layout.activity_glide) {
    companion object {
        const val url1 = "https://img2.baidu.com/it/u=327269120,3878801149&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
    }

    /**
     * Glide：
     *      多种图片格式的缓存，适用于更多的内容表现形式（如Gif、WebP、缩略图、Video）
     *      生命周期集成（根据Activity或者Fragment的生命周期管理图片加载请求）
     *      高效处理Bitmap（bitmap的复用和主动回收，减少系统回收压力）
     *      高效的缓存策略，灵活（Picasso只会缓存原始尺寸的图片，Glide缓存的是多种规格），加载速度快且内存开销小（默认Bitmap格式的不同，使得内存开销是Picasso的一半）
     *
     * Fresco：
     *      最大的优势在于5.0以下(最低2.3)的bitmap加载。在5.0以下系统，Fresco将图片放到一个特别的内存区域(Ashmem区)
     *      大大减少OOM（在更底层的Native层对OOM进行处理，图片将不再占用App的内存）
     *      适用于需要高性能加载大量图片的场景
     *      缺点就是包太大，依赖很多，使用复杂，要命的是还要在布局使用 SimpleDraweeView 控件加载图片
     *
     *      Fresco 针对不同版本的系统用了不同的解码器，为什么呢？
     *      因为解码器最令人担心的问题，在于频繁解码会耗费大量的内存，这些内存频繁分配和回收，会造成GC卡顿。所以，Fresco做了很多针对解码器的优化
     *
     *  Glide 和 Fresco 都默认使用 HttpURLConnection
     *
     *
     *  Glide4.0之前,Glide默认使用RGB565格式，比较省内存
     *  Glide4.0之后，默认格式已经变成了ARGB_8888格式了,这一优势也就不存在了。
     *
     *  Glide.with(this)绑定了Activity的生命周期。在Activity内新建了一个无UI的Fragment，
     *  这个Fragment持有一个Lifecycle，通过Lifecycle在Fragment关键生命周期通知RequestManager进行相关从操作。
     *  在生命周期onStart时继续加载，onStop时暂停加载，onDestory时停止加载任务和清除操作。
     */

    override fun initData() {
        Glide.with(this).load(url1).into(binding.ivGlide01)
        binding.ivFresco01.setImageURI(url1)
    }
}