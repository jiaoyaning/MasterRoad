package com.jyn.masterroad.view.draw.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave
import com.jyn.masterroad.R
import com.jyn.masterroad.view.draw.dp

/*
 * HenCoder Android 开发进阶：自定义 View 1-4 Canvas 对绘制的辅助
 * https://juejin.cn/post/6844903489789755406
 */
class CanvasView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 15f.dp
    }
    private val bitmap = getBitmap(R.mipmap.icon_master_road2)

    override fun onDraw(canvas: Canvas) {
        /**
         * 裁切
         * 会导致毛边
         *
         * clipRect()
         * clipPath() path方向并无作用
         */
        clip(canvas)

        /**
         * Canvas 二维变换
         *
         * translate()  平移
         * rotate()     旋转
         * scale()      缩放
         * skew()       错切
         */
        canvasConversion(canvas)
        matrix(canvas)

        /**
         * Camera   三维变换(旋转、平移、移动相机)
         *
         * 注意：是以(0,0)为变换基点的，所以大部分情况下需要先绘制内容移动到轴心
         */
        camera(canvas)
    }

    /**
     * canvas.saveLayer()       离屏缓存，针对的是每一次绘制
     *
     * view.setLayerType()      开启View级别的离屏缓存
     *      LAYER_TYPE_NONE         关闭离屏缓存
     *      LAYER_TYPE_SOFTWARE     开启软件实现的离屏缓存
     *      LAYER_TYPE_HARDWARE     开启硬件实现的离屏缓存
     */

    /**
     * 裁切
     * 会导致毛边
     *
     * clipRect()
     * clipPath() path方向并无作用
     */
    private fun clip(canvas: Canvas) {
        canvas.drawText("clip裁切：clipRect() & clipPath()", 50f, 70f, textPaint)
        //原图
        canvas.drawBitmap(bitmap, 50f, 100f, paint)

        //裁切
        canvas.save()
        canvas.clipRect(350f, 150f, 550f, 250f)
        canvas.drawBitmap(bitmap, 350f, 100f, paint)
        canvas.restore()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.save()
            canvas.clipOutRect(650f, 150f, 850f, 250f)
            canvas.drawBitmap(bitmap, 650f, 100f, paint)
            canvas.restore()
        }


        //原图
        canvas.drawBitmap(bitmap, 50f, 350f, paint)
        //裁切 顺时针
        val cwPath = Path().apply {
            moveTo(450f, 450f)
            addOval(350f, 350f, 550f, 550f, Path.Direction.CW)
        }
        canvas.save()
        canvas.clipPath(cwPath)
        canvas.drawBitmap(bitmap, 350f, 350f, paint)
        canvas.restore()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //裁切 逆时针
            val ccwPath = Path().apply {
                moveTo(750f, 450f)
                addOval(650f, 350f, 850f, 550f, Path.Direction.CW)
            }
            canvas.save()
            canvas.clipOutPath(ccwPath)
            canvas.drawBitmap(bitmap, 650f, 350f, paint)
            canvas.restore()
        }
    }


    /**
     * Canvas 二维变换
     *
     * translate()  平移
     * rotate()     旋转
     * scale()      缩放
     * skew()       错切
     */
    private fun canvasConversion(canvas: Canvas) {
        canvas.drawText(
                "Canvas 二维变换：translate() & rotate() & scale() & skew()",
                50f,
                620f,
                textPaint
        )

        /**
         * translate 平移
         */
        canvas.save()
        canvas.translate(50f, 650f)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        canvas.restore()


        /**
         * rotate   旋转
         */
        canvas.save()
        canvas.rotate(45f, 400f, 750f)
        canvas.drawBitmap(bitmap, 300f, 650f, paint)
        canvas.restore()


        /**
         * scale    缩放
         */
        canvas.save()
        canvas.scale(0.8f, 1.2f, 650f, 750f)
        canvas.drawBitmap(bitmap, 550f, 650f, paint)
        canvas.restore()


        /**
         * skew     错切
         */
        canvas.save()
        canvas.skew(0f, 0.1f)
        canvas.drawBitmap(bitmap, 800f, 650f * (1 - 0.1f), paint)
        canvas.restore()
    }


    /**
     * Matrix   变换
     *
     * 1. 创建 Matrix 对象
     * 2. 调用 Matrix 的 pre/postTranslate/Rotate/Scale/Skew() 方法来设置几何变换；
     * 3. 使用 Canvas.setMatrix(matrix) 或 Canvas.concat(matrix) 来把几何变换应用到 Canvas。
     *      Canvas.setMatrix(matrix)：
     *          用 Matrix 直接替换 Canvas 当前的变换矩阵，即抛弃 Canvas 当前的变换，改用 Matrix 的变换
     *          注：不同的手机系统中 setMatrix(matrix) 的行为可能不一致，尽量用 concat(matrix)
     *      Canvas.concat(matrix)：
     *          用 Canvas 当前的变换矩阵和 Matrix 相乘，即基于 Canvas 当前的变换，叠加上 Matrix 中的变换
     */
    private fun matrix(canvas: Canvas) {

        /**
         * setPolyToPoly()  多点映射
         *  把指定的点移动到给出的位置，从而发生形变。例如：(0, 0) -> (100, 100) 表示把 (0, 0) 位置的像素移动到 (100, 100) 的位置，
         *  这个是单点的映射，单点映射可以实现平移。而多点的映射，就可以让绘制内容任意地扭曲。
         *
         *  src , dst 源点集合和目标点集；
         *  srcIndex , dstIndex 第一个点的偏移；
         *  pointCount 采集的点的个数（个数不能大于 4，因为大于 4 个点就无法计算变换）
         */
        canvas.drawText("Matrix 矩阵变换：setPolyToPoly()", 50f, 920f, textPaint)

        canvas.drawBitmap(bitmap, 50f, 950f, paint)

//        canvas.drawBitmap(bitmap, 350f, 950f, paint)
        canvas.save()
        val matrix = Matrix()
        //左上，右上，左下，右下
        val pointsSrc = floatArrayOf(
                350f, 950f,     //左上
                550f, 950f,     //右上
                350f, 1150f,    //左下
                550f, 1150f     //右下
        )
        val pointsDst = floatArrayOf(
                350f - 10, 950f + 10,   //左上
                550f + 10, 950f - 10,   //右上
                350f - 20, 1150 + 10f,  //左下
                550f + 10, 1150 + 50f   //右下
        )
        matrix.setPolyToPoly(pointsSrc, 0, pointsDst, 0, 4)
        canvas.concat(matrix)
        canvas.drawBitmap(bitmap, 350f, 950f, paint)
        canvas.restore()
    }

    /**
     * Camera   三维变换(旋转、平移、移动相机)
     *
     * 注意：是以(0,0)为变换基点的，所以大部分情况下需要先绘制内容移动到轴心
     */
    private val camera = Camera()
    private fun camera(canvas: Canvas) {
        canvas.drawText("Camera   三维变换", 50f, 1220f, textPaint)

        /**
         * 中心旋转45度
         */
        canvas.save()
        camera.save()                // 保存 Camera 的状态
        camera.rotateX(45f)      // 旋转 Camera 的三维空间
        canvas.translate(150f, 1350f)  // 旋转之后把投影移动回来
        camera.applyToCanvas(canvas) // 把旋转投影到 Canvas
        canvas.translate(-150f, -1350f) // 旋转之前把绘制内容移动到轴心（原点）
        canvas.drawBitmap(bitmap, 50f, 1250f, paint)
        camera.restore()             //恢复 Camera 的状态
        canvas.restore()


        /**
         * 只下半部分旋转45度
         */
        //上半部分
        canvas.save()
        canvas.clipRect(350f, 1250f, 550f, 1350f)
        canvas.drawBitmap(bitmap, 350f, 1250f, paint)
        canvas.restore()
        //下半部分
        canvas.save()
        camera.save()
        camera.rotateX(45f)      // 旋转 Camera 的三维空间
        canvas.translate(450f, 1350f)  // 旋转之后把投影移动回来
        camera.applyToCanvas(canvas) // 把旋转投影到 Canvas
        canvas.translate(-450f, -1350f) // 旋转之前把绘制内容移动到轴心（原点）
        canvas.clipRect(350f, 1350f, 550f, 1450f)
        canvas.drawBitmap(bitmap, 350f, 1250f, paint)
        camera.restore()
        canvas.restore()

        /**
         * 斜着切35度
         */
        //上半部分
        canvas.save()
        canvas.translate(750f, 1350f)
        canvas.rotate(-35f)
        canvas.clipRect(-200f, -200f, 200f, 0f)
        canvas.rotate(35f)
        canvas.translate(-750f, -1350f)
        canvas.drawBitmap(bitmap, 650f, 1250f, paint)
        canvas.restore()

        //下半部分
        camera.save()
        camera.rotateX(45f)      // 旋转 Camera 的三维空间
        camera.setLocation(0f, 0f, -6 * resources.displayMetrics.density)

        canvas.withSave {
            translate(750f, 1350f)
            rotate(-35f)
            camera.applyToCanvas(canvas) // 把旋转投影到 Canvas
            clipRect(-200f, 0f, 200f, 200f)
            rotate(35f)
            translate(-750f, -1350f)
            drawBitmap(bitmap, 650f, 1250f, paint)
        }

        camera.restore()
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