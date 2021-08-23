package com.jyn.masterroad.view.draw.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.apkfuns.logutils.LogUtils
import com.jyn.masterroad.view.draw.px

/*
 * HenCoder Android 开发进阶：自定义 View 1-3 文字的绘制
 * https://juejin.cn/post/6844903488460177416
 */
class DrawTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    val text = "一蓑烟雨任平生"

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 15f.px
    }

    override fun onDraw(canvas: Canvas) {
        drawText(canvas)        //文字绘制
        layout(canvas)          //文本布局
        measureText(canvas)     //文字测量
    }

    /**
     * 共有三个文字绘制方法
     *
     *  drawText()
     *  drawTextRun()
     *  drawTextOnPath()
     *
     * 注意：drawText系列方法不支持换行，\n会被绘制成空格
     */
    private fun drawText(canvas: Canvas) {
        /*
         * drawText()
         *
         *  text 文字内容，
         *  x 和 y 是文字的坐标。
         *
         * 注意：这个坐标并不是文字的左上角，而是一个与左下角比较接近的位置。
         */
        canvas.drawText(text, 0f, 15f.px, textPaint)
        canvas.drawText("drawText()", 200f.px, 15f.px, textPaint)


        /*
         * drawTextRun()
         *  1.可以根据上下文绘制，对汉字和英语意义不大
         *  2.可以从右向左绘制
         *
         *  text：要绘制的文字
         *  start：从那个字开始绘制
         *  end：绘制到哪个字结束
         *  contextStart：上下文的起始位置。contextStart 需要小于等于 start
         *  contextEnd：上下文的结束位置。contextEnd 需要大于等于 end
         *  x：文字左边的坐标
         *  y：文字的基线坐标
         *  isRtl：是否是 RTL（Right-To-Left，从右向左）
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            canvas.drawTextRun(
                text, 0, text.length, 0, text.length,
                0f, 40f.px, true, textPaint
            )
            canvas.drawText("drawTextRun()", 200f.px, 40f.px, textPaint)
        }


        /*
         * drawTextOnPath() 根据path来绘制
         *
         *
         *   hOffset 水平偏移量
         *   vOffset 竖直偏移量
         *      利用它们可以调整文字的位置。例如你设置 hOffset 为 5， vOffset 为 10，文字就会右移 5 像素和下移 10 像素。
         *
         *
         * 注意：drawTextOnPath() 所使用的 Path ，拐弯处全用圆角，别用尖角
         */
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
        val path = Path().apply {
            moveTo(0f, 65f.px)
            lineTo(70f.px, 65f.px)
            lineTo(120f.px, 100f.px)
            lineTo(170f.px, 70f.px)
        }
        canvas.drawPath(path, paint)
        canvas.drawTextOnPath(text + text, path, 0f, 0f, textPaint)
        canvas.drawText("drawTextOnPath()", 200f.px, 65f.px, textPaint)
    }

    /**
     * StaticLayout     文本显示之后不会发生改变
     * DynamicLayout    文本内容会被编辑
     * BoringLayout     当你确保你的文本只有一行，且所有的字符均是从左到右显示的
     */
    private fun layout(canvas: Canvas) {
        /*
         * StaticLayout
         *
         * source           文本内容。
         * bufstart,bufend  开始位置和结束位置。
         * paint            文本画笔对象。
         * outerwidth       布局宽度，超出宽度换行显示。
         * align            对齐方式，默认是Alignment.ALIGN_LEFT。
         * textDir          文本显示方向。
         * spacingmult      行间距倍数，默认是1。
         * spacingadd       行距增加值，默认是0。
         * includepad       文本顶部和底部是否留白。
         * ellipsize        文本省略方式，有 START、MIDDLE、 END、MARQUEE 四种省略方式（其实还有一个 END_SMALL，但是 Google 并未开放出来）。
         * ellipsizedWidth  省略宽度。
         * maxLines         最大行数。
         */
    }


    /**
     * 文本测量
     *
     * getFontSpacing() 获取字间距
     * getTextBounds()  获取文本的显示范围
     * measureText()    测量文字的宽度
     * getTextWidths()  获取字符串中每个字符的宽度
     */
    private fun measureText(canvas: Canvas) {
        /**
         * getFontSpacing() 获取字间距
         */
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 15f.px

        }
        canvas.drawText("第一行", 10f, 140f.px, paint)
        canvas.drawText("第二行    getFontSpacing()", 10f, 140f.px + paint.fontSpacing, paint)


        /*
         * getTextBounds()  获取文本的显示范围
         *
         *  text       要测量的文字，
         *  start end  文字的起始和结束位置
         *  bounds     存储文字显示范围的对象，测算完成之后会把结果写进 bounds中。
         */
        val bounds = Rect()
        val textBounds = "获取文字的显示范围     getTextBounds()"
        canvas.drawText(textBounds, 10f, 190f.px, paint)
        paint.getTextBounds(textBounds, 0, textBounds.length, bounds)
        bounds.left += 10
        bounds.right += 10
        bounds.top += 190f.px.toInt()
        bounds.bottom += 190f.px.toInt()
        paint.style = Paint.Style.STROKE
        paint.color = Color.RED
        canvas.drawRect(bounds, paint)

        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL


        /*
         * measureText()    测量文字的宽度
         */
        val textMeasure = "获取文字的宽度      measureText()"
        canvas.drawText(textMeasure, 10f, 220f.px, paint)
        val textWidth = paint.measureText(textMeasure)
        paint.color = Color.RED
        canvas.drawLine(10f, 220f.px, textWidth + 10f, 220f.px, paint)
        paint.color = Color.BLACK

        /**
         * breakText()      在给出宽度上限的前提下测量文字的宽度
         *
         *  text                是要测量的文字
         *  measureForwards     表示文字的测量方向,true表示由左往右测量
         *  maxWidth            是给出的宽度上限
         *  measuredWidth       用于接受数据(测量完成后会把截取的文字宽度赋值给 measuredWidth[0])
         */
        val textBreak = "获取文字的宽度        breakText()"
        canvas.drawText(textBreak, 10f, 250f.px, paint)
        val measuredWidth = FloatArray(textBreak.length)
        paint.breakText(textBreak, 0, text.length, true, 70f.px, measuredWidth)
        paint.color = Color.RED
        canvas.drawLine(10f + 70f.px, 235f.px, 10f + 70f.px, 250f.px, paint)    //画出分割线
        canvas.drawLine(10f, 250f.px, measuredWidth[0] + 10f, 250f.px, paint)   //画出breakText后的宽度


        /*
         * getTextWidths()
         * 获取字符串中每个字符的宽度，并把结果填入参数 widths
         */
        paint.color = Color.BLACK
        val textWidths = "获取字符串中每个字符的宽度     getTextWidths()"
        val widths = FloatArray(textWidths.length)
        canvas.drawText(textWidths, 10f, 280f.px, paint)
        paint.getTextWidths(textWidths, widths)
        paint.color = Color.RED
        var start = 10f
        widths.forEachIndexed { index, fl ->
            val end = start + fl
            if (index % 2f == 0f) {
                canvas.drawLine(start, 280f.px, end, 280f.px, paint)
            }
            start = end
        }

        /**
         * getRunAdvance()  光标相关
         *
         *  text 是要测量的文字
         *  start end 是文字的起始和结束坐标
         *  contextStart contextEnd 是上下文的起始和结束坐标
         *  isRtl 是文字方向
         *  advance 是给出的位置的像素值
         *
         *  对应的字符偏移量将作为返回值返回。
         */
        val runAdvanceText = "光标计算 测绘位置        getRunAdvance() \uD83C\uDDE8\uD83C\uDDF3"
        paint.color = Color.BLACK
        canvas.drawText(runAdvanceText, 10f, 310f.px, paint)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val advance = paint.getRunAdvance(
                runAdvanceText,
                0,
                runAdvanceText.length,
                0,
                runAdvanceText.length,
                false,
                runAdvanceText.length - 4
            )
            paint.color = Color.RED
            canvas.drawLine(advance + 10, 295f.px, advance + 10, 310f.px + 10, paint)
        }
    }
}