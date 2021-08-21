package com.jyn.masterroad.view.draw.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jyn.masterroad.R

/*
 * HenCoder Android 开发进阶: 自定义 View 1-2 Paint 详解
 * https://juejin.cn/post/6844903487570968584
 *
 *  Paint 官方API： https://developer.android.com/reference/android/graphics/Paint.html
 */
class PaintView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas) {
        colorTest(canvas)
        effectTest(canvas)
        textTest(canvas)
        initTest(canvas)
    }

    /**
     * 一. 颜色
     *  1. Paint.setColor(int color)
     *  2. Paint.setARGB(int a, int r, int g, int b)
     *  前两个设置颜色的API太简单了，直接略过
     *
     *  3. setShader(Shader shader) 着色器，具体参数是Shader的子类
     *      LinearGradient 线性渐变
     *      RadialGradient 辐射渐变
     *      SweepGradient
     *      BitmapShader
     *      ComposeShader
     *
     *  注意：在设置了 Shader 的情况下， Paint.setColor/ARGB() 所设置的颜色就不再起作用。
     */
    private fun colorTest(canvas: Canvas) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        /**
         * LinearGradient 线性渐变
         *
         * x0 y0 x1 y1   渐变的两个端点的位置
         * color0 color1 两个端点分别的颜色值
         * TileMode      端点范围之外的着色规则
         *      CLAMP  用边缘色彩填充多余空间
         *      MIRROR 重复原图像来填充多余空间
         *      REPEAT 重复使用镜像模式的图像来填充多余空间
         */
        val linearGradient = LinearGradient(
            0f, 0f, 200f, 200f,
            Color.parseColor("#00FFFF"), Color.parseColor("#FF00FF"),
            Shader.TileMode.CLAMP
        )
        paint.shader = linearGradient
        canvas.drawRect(0f, 0f, 200f, 200f, paint)
        paint.reset()

        /**
         * RadialGradient 辐射渐变
         *
         * centerX      辐射中心的坐标
         * radius       辐射半径
         * centerColor  辐射中心的颜色
         * edgeColor    辐射边缘的颜色
         * tileMode     辐射范围之外的着色模式。
         */
        val radialGradient = RadialGradient(
            350f, 100f, 100f,
            Color.parseColor("#00FFFF"), Color.parseColor("#FF00FF"),
            Shader.TileMode.MIRROR //这里需要把半径调小才能看出效果
        )
        paint.shader = radialGradient
        canvas.drawCircle(350f, 100f, 100f, paint)
        paint.reset()

        /**
         * SweepGradient 扫描渐变
         *
         * cx cy ：扫描的中心
         * color0 color1 两个端点分别的颜色值
         */
        val sweepGradient = SweepGradient(
            600f, 100f,
            Color.parseColor("#00FFFF"), Color.parseColor("#FF00FF")
        )
        paint.shader = sweepGradient
        canvas.drawCircle(600f, 100f, 100f, paint)
        paint.reset()

        /**
         * BitmapShader
         *
         * bitmap：用来做模板的 Bitmap 对象
         * tileX：横向的 TileMode
         * tileY：纵向的 TileMode
         *
         * BitmapShader的绘制原理是从视图原点开始绘制第一个Bitmap,然后在按设置的模式先绘制Y轴，然后根据Y轴的绘制，绘制X轴这样去绘制超过Bitmap的部分。
         * 按这样的原理绘制的图形此时并不会显示，只有当canvas调用draw绘制后，才显示canvas绘制的那部分。
         */
        val bitmap = getBitmap(R.mipmap.icon_master_road2)
//        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.icon_master_road2)
        val bitmapShader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        paint.shader = bitmapShader
        canvas.drawRect(750f, 0f, 950f, 200f, paint)
        paint.reset()


        /**
         * ComposeShader 混合着色器
         * 所谓混合，就是把两个 Shader 一起使用
         */
    }

    // 2. 效果
    private fun effectTest(canvas: Canvas) {
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    // 3. Text相关
    private fun textTest(canvas: Canvas) {
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    // 4. 初始化
    private fun initTest(canvas: Canvas) {
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    }


    private fun getBitmap(id: Int): Bitmap {
        val options = BitmapFactory.Options()
        /**
         * options.inJustDecodeBounds = true 表示只读图片，不加载到内存中，就不会给图片分配内存空间，但是可以获取到图片的大小等属性;
         * 设置为false, 就是要加载这个图片.
         */
        options.inJustDecodeBounds = true //只读尺寸
        BitmapFactory.decodeResource(resources, id, options)
        options.inJustDecodeBounds = false //根据尺寸读取原图
        options.inDensity = options.outWidth
        options.inTargetDensity = 200
        return BitmapFactory.decodeResource(resources, id, options)
    }
}