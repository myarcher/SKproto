package com.mini.skproto.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.util.AttributeSet
import android.widget.TextView

class CircleView :TextView {
    private val mBgPaint = Paint()
    private val mBgPaints = Paint()
    private var isTrue = false
    private var w: Int = 0
    internal var pfd = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int,function: () -> Unit) : super(context, attrs, defStyle) {}

    constructor(context: Context, attrs: AttributeSet,function: () -> Unit) : super(context, attrs) {
        mBgPaint.color = Color.parseColor("#f12f2f")
        mBgPaint.isAntiAlias = true
        mBgPaints.color = Color.parseColor("#2c8dff")
        mBgPaints.isAntiAlias = true
        mBgPaints.style = Paint.Style.STROKE//设置空心
        w = 1
        mBgPaints.strokeWidth = 1f

    }

    constructor(context: Context,function: () -> Unit) : super(context) {
        mBgPaint.color = Color.parseColor("#f12f2f")
        mBgPaint.isAntiAlias = true

        mBgPaints.color = Color.parseColor("#2c8dff")
        mBgPaints.isAntiAlias = true
        mBgPaints.style = Paint.Style.STROKE//设置空心
        w = 1
        mBgPaints.strokeWidth = 1f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measuredWidth = measuredWidth
        val measuredHeight = measuredHeight
        val max = Math.max(measuredWidth, measuredHeight)
        setMeasuredDimension(max, max)
    }

    override fun setBackgroundColor(color: Int) {
        mBgPaint.color = color
        invalidate()
    }

    fun setWid(w: Int) {
        this.w = w
        mBgPaints.strokeWidth = w.toFloat()
        invalidate()
    }

    fun setBackgroundColor(color: Int, istrue: Boolean) {
        mBgPaint.color = color
        this.isTrue = istrue
        invalidate()
    }

    /**
     * 设置通知个数显示
     *
     * @param text
     */
    fun setNotifiText(text: String) {
        setText(text + "")
    }

    override fun draw(canvas: Canvas) {
        canvas.drawFilter = pfd//给Canvas加上抗锯齿标志
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (Math.max(width, height) / 2).toFloat(), mBgPaint)
        if (isTrue) {
            val ra = Math.max(width, height) / 2 - w
            canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), ra.toFloat(), mBgPaints)
        }
        super.draw(canvas)
    }
}
