package com.mini.skproto.xrecyclerview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.mini.skproto.R
import com.mini.skproto.xrecyclerview.progressindicator.AVLoadingIndicatorView
import com.mini.skproto.xrecyclerview.view.Listview_Head

import org.jetbrains.anko.dip

import java.util.Date

class ArrowRefreshHeader : LinearLayout, BaseRefreshHeader {

    private var mContainer: Listview_Head? = null
    private var mArrowImageView: ImageView? = null
    private var mProgressBar: SimpleViewSwitcher? = null
    private var mStatusTextView: TextView? = null
    // 显示进度
    // 显示箭头图片
    var state = BaseRefreshHeader.STATE_NORMAL
        set(state) {
            if (state == this.state)
                return

            if (state == BaseRefreshHeader.STATE_REFRESHING) {
                mArrowImageView!!.clearAnimation()
                mArrowImageView!!.visibility = View.INVISIBLE
                mProgressBar!!.visibility = View.VISIBLE
                smoothScrollTo(mMeasuredHeight)
            } else if (state == BaseRefreshHeader.STATE_DONE) {
                mArrowImageView!!.visibility = View.INVISIBLE
                mProgressBar!!.visibility = View.INVISIBLE
            } else {
                mArrowImageView!!.visibility = View.VISIBLE
                mProgressBar!!.visibility = View.INVISIBLE
            }

            when (state) {
                BaseRefreshHeader.STATE_NORMAL -> {
                    if (this.state == BaseRefreshHeader.STATE_RELEASE_TO_REFRESH) {
                        mArrowImageView!!.startAnimation(mRotateDownAnim)
                    }
                    if (this.state == BaseRefreshHeader.STATE_REFRESHING) {
                        mArrowImageView!!.clearAnimation()
                    }
                    mStatusTextView!!.setText(R.string.listview_header_hint_normal)
                }
                BaseRefreshHeader.STATE_RELEASE_TO_REFRESH -> if (this.state != BaseRefreshHeader.STATE_RELEASE_TO_REFRESH) {
                    mArrowImageView!!.clearAnimation()
                    mArrowImageView!!.startAnimation(mRotateUpAnim)
                    mStatusTextView!!.setText(R.string.listview_header_hint_release)
                }
                BaseRefreshHeader.STATE_REFRESHING -> mStatusTextView!!.setText(R.string.refreshing)
                BaseRefreshHeader.STATE_DONE -> mStatusTextView!!.setText(R.string.refresh_done)
            }

            field = state
        }

    private var mHeaderTimeView: TextView? = null

    private var mRotateUpAnim: Animation? = null
    private var mRotateDownAnim: Animation? = null
    private var heights = 60

    var mMeasuredHeight: Int = 0

    var visibleHeight: Int
        get() {
            val lp = mContainer!!.layoutParams as LinearLayout.LayoutParams
            return lp.height
        }
        set(height) {
            var height = height
            if (height < 0) {
                height = 0
            }
            val lp = mContainer!!.layoutParams as LinearLayout.LayoutParams
            lp.height = height
            mContainer!!.layoutParams = lp
        }

    constructor(context: Context) : super(context) {
        initView()
    }

    /**
     * @param context
     * @param attrs
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    private fun initView() {
        // 初始情况，设置下拉刷新view高度为0
        heights =dip(60)
        mContainer = Listview_Head(context)
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(0, 0, 0, 0)
        this.layoutParams = lp
        this.setPadding(0, 0, 0, 0)

        addView(mContainer, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0))
        gravity = Gravity.BOTTOM

        mArrowImageView = mContainer!!.listview_header_arrow
        mStatusTextView = mContainer!!.refresh_status_textview

        //init the progress view
        mProgressBar =mContainer!!.listview_header_progressbar// findViewById(R.id.listview_header_progressbar) as SimpleViewSwitcher
        val progressView = AVLoadingIndicatorView(context)
        progressView.setIndicatorColor(-0x4a4a4b)
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader)
        mProgressBar!!.setView(progressView)


        mRotateUpAnim = RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mRotateUpAnim!!.duration = ROTATE_ANIM_DURATION.toLong()
        mRotateUpAnim!!.fillAfter = true
        mRotateDownAnim = RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mRotateDownAnim!!.duration = ROTATE_ANIM_DURATION.toLong()
        mRotateDownAnim!!.fillAfter = true

        mHeaderTimeView = mContainer!!.last_refresh_time//findViewById(R.id.last_refresh_time) as TextView
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mMeasuredHeight = measuredHeight
    }

    fun setProgressStyle(style: Int) {
        if (style == ProgressStyle.SysProgress) {
            mProgressBar!!.setView(ProgressBar(context, null, android.R.attr.progressBarStyle))
        } else {
            val progressView = AVLoadingIndicatorView(this.context)
            progressView.setIndicatorColor(Color.WHITE)
            progressView.setIndicatorId(style)
            mProgressBar!!.setView(progressView)
        }
    }

    fun setArrowImageView(resid: Int) {
        mArrowImageView!!.setImageResource(resid)
    }

    override fun refreshComplete() {
        mHeaderTimeView!!.text = friendlyTime(Date())
        state = BaseRefreshHeader.STATE_DONE
        Handler().postDelayed({ reset() }, 200)
    }

    override fun onMove(delta: Float) {
        if (visibleHeight > 0 || delta > 0) {

            if (state <= BaseRefreshHeader.STATE_RELEASE_TO_REFRESH) { // 未处于刷新状态，更新箭头
                if (visibleHeight > mMeasuredHeight) {
                    state = BaseRefreshHeader.STATE_RELEASE_TO_REFRESH
                } else {
                    state = BaseRefreshHeader.STATE_NORMAL
                }
                visibleHeight = delta.toInt() + visibleHeight
            }
        }
    }

    override fun releaseAction(): Boolean {
        var isOnRefresh = false
        val height = visibleHeight
        if (height == 0)
        // not visible.
            isOnRefresh = false

        if (visibleHeight > mMeasuredHeight && state < BaseRefreshHeader.STATE_REFRESHING) {
            state = BaseRefreshHeader.STATE_REFRESHING
            isOnRefresh = true
        }
        // refreshing and header isn't shown fully. do nothing.
        if (state == BaseRefreshHeader.STATE_REFRESHING && height <= mMeasuredHeight) {
            //return;
        }
        if (state != BaseRefreshHeader.STATE_REFRESHING) {
            smoothScrollTo(0)
        }

        if (state == BaseRefreshHeader.STATE_REFRESHING) {
            val destHeight = mMeasuredHeight
            smoothScrollTo(destHeight)
        }

        return isOnRefresh
    }

    fun reset() {
        smoothScrollTo(0)
        Handler().postDelayed({ state = BaseRefreshHeader.STATE_NORMAL }, 500)
    }

    private fun smoothScrollTo(destHeight: Int) {
        val animator = ValueAnimator.ofInt(visibleHeight, destHeight)
        animator.setDuration(300).start()
        animator.addUpdateListener { animation -> visibleHeight = animation.animatedValue as Int }
        animator.start()
    }

    companion object {
        private val ROTATE_ANIM_DURATION = 180

        fun friendlyTime(time: Date): String {
            //获取time距离当前的秒数
            val ct = ((System.currentTimeMillis() - time.time) / 1000).toInt()

            if (ct == 0) {
                return "刚刚"
            }

            if (ct > 0 && ct < 60) {
                return ct.toString() + "秒前"
            }

            if (ct >= 60 && ct < 3600) {
                return Math.max(ct / 60, 1).toString() + "分钟前"
            }
            if (ct >= 3600 && ct < 86400)
                return (ct / 3600).toString() + "小时前"
            if (ct >= 86400 && ct < 2592000) { //86400 * 30
                val day = ct / 86400
                return day.toString() + "天前"
            }
            return if (ct >= 2592000 && ct < 31104000) { //86400 * 30
                (ct / 2592000).toString() + "月前"
            } else (ct / 31104000).toString() + "年前"
        }
    }

}