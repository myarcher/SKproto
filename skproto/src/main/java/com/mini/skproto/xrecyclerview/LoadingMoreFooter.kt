package com.mini.skproto.xrecyclerview

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.mini.skproto.xrecyclerview.progressindicator.AVLoadingIndicatorView
import org.jetbrains.anko.dip

class LoadingMoreFooter : LinearLayout {

    private var progressCon: SimpleViewSwitcher? = null
    private var mText: TextView? = null
    private var loadingHint: String? = null
    private var noMoreHint: String? = null
    private var loadingDoneHint: String? = null

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

    fun setLoadingHint(hint: String) {
        loadingHint = hint
    }

    fun setNoMoreHint(hint: String) {
        noMoreHint = hint
    }

    fun setLoadingDoneHint(hint: String) {
        loadingDoneHint = hint
    }

    fun initView() {
        gravity = Gravity.CENTER
        layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        progressCon = SimpleViewSwitcher(context){}
        progressCon!!.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val progressView = AVLoadingIndicatorView(this.context)
        progressView.setIndicatorColor(Color.GRAY)
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader)
        progressCon!!.setView(progressView)

        addView(progressCon)

        mText = TextView(context)
        mText!!.setTextColor(Color.BLACK)
        mText!!.text = "正在加载..."
        loadingHint = "正在加载..."
        noMoreHint = "没有了"
        loadingDoneHint = "加载完成"
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(dip(10), 0, 0, 0)

        mText!!.layoutParams = layoutParams
        addView(mText)
        this.visibility = View.GONE
    }

    fun setProgressStyle(style: Int) {
        if (style == ProgressStyle.SysProgress) {
            progressCon!!.setView(ProgressBar(context, null, android.R.attr.progressBarStyle))
        } else {
            val progressView = AVLoadingIndicatorView(this.context)
            progressView.setIndicatorColor(Color.GRAY)
            progressView.setIndicatorId(style)
            progressCon!!.setView(progressView)
        }
    }

    fun setState(state: Int) {
        when (state) {
            STATE_LOADING -> {
                progressCon!!.visibility = View.VISIBLE
                mText!!.text = loadingHint
                this.visibility = View.VISIBLE
            }
            STATE_COMPLETE -> {
                mText!!.text = loadingDoneHint
                this.visibility = View.GONE
            }
            STATE_NOMORE -> {
                mText!!.text = noMoreHint
                progressCon!!.visibility = View.GONE
                this.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        val STATE_LOADING = 0
        val STATE_COMPLETE = 1
        val STATE_NOMORE = 2
    }
}
