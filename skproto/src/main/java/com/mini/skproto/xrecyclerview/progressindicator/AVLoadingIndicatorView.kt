package com.mini.skproto.xrecyclerview.progressindicator

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.support.annotation.IntDef
import android.util.AttributeSet
import android.view.View
import com.mini.skproto.R
import com.mini.skproto.xrecyclerview.progressindicator.indicator.BallSpinFadeLoaderIndicator
import com.mini.skproto.xrecyclerview.progressindicator.indicator.BaseIndicatorController


class AVLoadingIndicatorView : View {

    //attrs
    internal var mIndicatorId: Int = 0
    internal var mIndicatorColor: Int = 0

    internal var mPaint: Paint?=null

    internal var mIndicatorController: BaseIndicatorController?=null

    private var mHasAnimation: Boolean = false


    constructor(context: Context) : super(context) {
        inits()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inits()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inits()
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        inits()
    }

    private fun inits() {
        mIndicatorId = BallSpinFadeLoader
        mIndicatorColor =Color.WHITE
        mPaint = Paint()
        mPaint!!.color = mIndicatorColor
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.isAntiAlias = true
        applyIndicator()
    }

    fun setIndicatorId(indicatorId: Int) {
        mIndicatorId = indicatorId
        applyIndicator()
    }

    fun setIndicatorColor(color: Int) {
        mIndicatorColor = color
        mPaint!!.color = mIndicatorColor
        this.invalidate()
    }

    private fun applyIndicator() {
        when (mIndicatorId) {
            BallSpinFadeLoader -> mIndicatorController = BallSpinFadeLoaderIndicator()
        }
        mIndicatorController!!.setTargets(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measureDimension(dp2px(DEFAULT_SIZE), widthMeasureSpec)
        val height = measureDimension(dp2px(DEFAULT_SIZE), heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun measureDimension(defaultSize: Int, measureSpec: Int): Int {
        var result = defaultSize
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)
        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize)
        } else {
            result = defaultSize
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawIndicator(canvas)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (!mHasAnimation) {
            mHasAnimation = true
            applyAnimation()
        }
    }

    override fun setVisibility(v: Int) {
        if (visibility != v) {
            super.setVisibility(v)
            if (v == View.GONE || v == View.INVISIBLE) {
                mIndicatorController!!.setAnimationStatus(BaseIndicatorController.AnimStatus.END)
            } else {
                mIndicatorController!!.setAnimationStatus(BaseIndicatorController.AnimStatus.START)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mIndicatorController!!.setAnimationStatus(BaseIndicatorController.AnimStatus.CANCEL)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mIndicatorController!!.setAnimationStatus(BaseIndicatorController.AnimStatus.START)
    }

    internal fun drawIndicator(canvas: Canvas) {
        mIndicatorController!!.draw(canvas, mPaint!!)
    }

    internal fun applyAnimation() {
        mIndicatorController!!.initAnimation()
    }

    private fun dp2px(dpValue: Int): Int {
        return context.resources.displayMetrics.density.toInt() * dpValue
    }

    companion object {
        val BallSpinFadeLoader = 22

        //Sizes (with defaults in DP)
        val DEFAULT_SIZE = 30
    }


}
