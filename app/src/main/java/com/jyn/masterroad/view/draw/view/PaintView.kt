package com.jyn.masterroad.view.draw.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jyn.masterroad.R
import com.jyn.masterroad.view.draw.dp

/*
 * HenCoder Android 开发进阶: 自定义 View 1-2 Paint 详解
 * https://juejin.cn/post/6844903487570968584
 *
 *  Paint 官方API： https://developer.android.com/reference/android/graphics/Paint.html
 */
class PaintView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val bitmap = getBitmap(R.mipmap.icon_master_road2)

    /**
     * 画笔的风格 Style
     *      Paint.Style.FILL    实心
     *      Paint.Style.STROKE  空心
     *      Paint.Style.FILL_AND_STROKE   同时实心和空心，该参数在某些场合会带来不可预期的显示效果。
     *
     * Paint.setStrokeWidth(float width)   //设置宽度
     *
     * Paint.setStrokeCap(Paint.Cap cap)   //设置线条端点形状的方法，端点有圆头 (ROUND)、平头 (BUTT) 和方头 (SQUARE) 三种
     *      Paint.Cap.BUTT   平头(默认)
     *      Paint.Cap.ROUND  圆头
     *      Paint.Cap.SQUARE 方形
     *
     * Paint.setStrokeJoin(Paint.Join join) //设置拐角的形状。有三个值可以选择：MITER 尖角、 BEVEL 平角和 ROUND 圆角。
     *      Paint.Join.MITER   尖角(默认)
     *      Paint.Join.BEVEL   平角
     *      Paint.Join.ROUND   圆角
     * Paint.setStrokeMiter(float miter)    //对于 setStrokeJoin() 的一个补充，它用于设置 MITER 型拐角的延长线的最大值。
     *
     *
     * Paint.setColor(int color)
     * Paint.setARGB(int a, int r, int g, int b)
     * 前两个设置颜色的API太简单了，直接略过
     */
    override fun onDraw(canvas: Canvas) {
        shader(canvas)      //着色器
        shadowLayer(canvas) //阴影绘制
        maskFilter(canvas)  //遮罩绘制
        getXXXPath(canvas)  //获取绘制的 Path
    }

    /*
     * Paint.setShader(Shader shader) 着色器，具体参数是Shader的子类
     *      LinearGradient 线性渐变
     *      RadialGradient 辐射渐变
     *      SweepGradient
     *      BitmapShader
     *      ComposeShader
     *
     * 注意：在设置了 Shader 的情况下， Paint.setColor/ARGB() 所设置的颜色就不再起作用。
     */
    private fun shader(canvas: Canvas) {
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
         *      CLAMP 拉伸  基于绘制的第一个完成的bitmap，X轴方向基本bitmap右边最后一个像素拉伸，Y轴方向基于bitmap下方最后一个像                         素拉伸
         *      REPEAT 重复 基于绘制的第一个完成的bitmap，X轴Y轴方向重复的绘制bitmap
         *      MIRROR 镜像 基于绘制的第一个完成的bitmap,横向不断翻转重复，纵向不断翻转重复
         *
         * BitmapShader的绘制原理是从视图原点开始绘制第一个Bitmap,然后在按设置的模式先绘制Y轴，然后根据Y轴的绘制，绘制X轴这样去绘制超过Bitmap的部分。
         * 按这样的原理绘制的图形此时并不会显示，只有当canvas调用draw绘制后，才显示canvas绘制的那部分。
         */
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

    /*
     * Paint.setShadowLayer() 绘制内容下面加一层阴影
     *    radius      是阴影的模糊范围
     *    dx dy       是阴影的偏移量
     *    shadowColor 是阴影的颜色
     *
     * clearShadowLayer() 清除阴影
     *
     * 注意
     *    1. 在硬件加速开启的情况下， setShadowLayer() 只支持文字的绘制，
     *       文字之外的绘制必须关闭硬件加速才能正常绘制阴影。
     *    2. 如果 shadowColor 是半透明的，阴影的透明度就使用 shadowColor 自己的透明度；
     *       而如果 shadowColor 是不透明的，阴影的透明度就使用 paint 的透明度。
     */
    private fun shadowLayer(canvas: Canvas) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 20f.dp
        }
        paint.setShadowLayer(3f, 0f, 0f, Color.RED)
        canvas.drawText("加阴影", 0f, 300f, paint)
    }

    /**
     * Paint.setMaskFilter()  遮罩
     * 上一个方法 setShadowLayer() 是设置的在绘制层下方的附加效果；而这个 MaskFilter 和它相反，设置的是在绘制层上方的附加效果。
     *
     *      BlurMaskFilter      模糊效果
     *      EmbossMaskFilter    浮雕效果
     *
     * 注意: maskFilter不支持硬件加速
     */
    private fun maskFilter(canvas: Canvas) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        /**
         * BlurMaskFilter   模糊效果
         *
         * radius  模糊范围
         * style   模糊的类型
         *      NORMAL  内外都模糊绘制
         *      SOLID   内部正常绘制，外部模糊
         *      INNER   内部模糊，外部不绘制
         *      OUTER   内部不绘制，外部模糊（什么鬼？）
         */
        paint.maskFilter = BlurMaskFilter(50f, BlurMaskFilter.Blur.NORMAL)
        canvas.drawBitmap(bitmap, 100f, 400f, paint)
        paint.maskFilter = BlurMaskFilter(50f, BlurMaskFilter.Blur.SOLID)
        canvas.drawBitmap(bitmap, 500f, 400f, paint)
        paint.maskFilter = BlurMaskFilter(50f, BlurMaskFilter.Blur.INNER)
        canvas.drawBitmap(bitmap, 100f, 700f, paint)
        paint.maskFilter = BlurMaskFilter(50f, BlurMaskFilter.Blur.OUTER)
        canvas.drawBitmap(bitmap, 500f, 700f, paint)
    }


    /**
     * Paint.getFillPath(Path src, Path dst)  获取实际 Path ，然后把结果保存在 dst 里。
     *      src：原 Path
     *      dst：实际 Path
     */
    private fun getXXXPath(canvas: Canvas) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 7f.dp
            textSize = 15f.dp
        }

        val srcPath = Path().apply {
            moveTo(10f, 1000f)
            lineTo(110f, 1100f)
            lineTo(160f, 1000f)
        }
        canvas.drawPath(srcPath, paint)

        val dstPath = Path()
        paint.getFillPath(srcPath, dstPath) //获取实际路径

        dstPath.offset(300f, 0f)

        canvas.drawPath(dstPath, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
        })
    }

    /**
     * setColorFilter(ColorFilter colorFilter) 为绘制设置颜色过滤，ColorFilter 并不直接使用，而是使用它的三个子类
     *      LightingColorFilter
     *      PorterDuffColorFilter
     *      ColorMatrixColorFilter
     */

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