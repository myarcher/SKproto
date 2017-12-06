package com.mini.skproto.statusview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import org.jetbrains.anko._RelativeLayout

/**
 * 状态视图
 * 类描述：  一个方便在多种状态切换的view
 */
class MultipleStatusView  constructor(context: Context,funtion:()->Unit) : _RelativeLayout(context) {

    //private View mEmptyView;
    private var mErrorView: View? = null
    private var mLoadingView: View? = null
    private var mNoNetworkView: View? = null
    private var mContentView: View? = null
    
    /**
     * 获取当前状态
     */
    var viewStatus: Int = 0
    
    private var mInflater: LayoutInflater? = null
    private var mOnRetryClickListener: View.OnClickListener? = null
    private val mLayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)


    fun sView(mErrorView: View, mLoadingView: View, mNoNetworkView: View, mContentView: View): MultipleStatusView {
        this.mErrorView = mErrorView
        this.mLoadingView = mLoadingView
        this.mNoNetworkView = mNoNetworkView
        this.mContentView = mContentView
        return this
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        mInflater = LayoutInflater.from(context)
        showContent()
    }

    /**
     * 设置重试点击事件
     *
     * @param onRetryClickListener 重试点击事件
     */
    fun setOnRetryClickListener(onRetryClickListener: View.OnClickListener) {
        this.mOnRetryClickListener = onRetryClickListener
    }

    /**
     * 显示空视图
     */
    /* public final void showEmpty() {
        mViewStatus = STATUS_EMPTY;
        if (null!= mEmptyView) {
            //   if(null != mOnRetryClickListener && null != mEmptyRetryView){
            //     mEmptyRetryView.setOnClickListener(mOnRetryClickListener);
            //  }
            addView(mEmptyView, 0, mLayoutParams);
        }
        showViewByStatus(mViewStatus);
    }*/

    /**
     * 显示错误视图
     */
    fun showError() {
        viewStatus = STATUS_ERROR
        if (null != mErrorView) {
            if (null != mOnRetryClickListener) {
                mErrorView!!.setOnClickListener(mOnRetryClickListener)
            }
            addView(mErrorView, 0, mLayoutParams)
        }
        showViewByStatus(viewStatus)
    }

    /**
     * 显示加载中视图
     */
    fun showLoading() {
        viewStatus = STATUS_LOADING
        if (null != mLoadingView) {
            addView(mLoadingView, 0, mLayoutParams)
        }
        showViewByStatus(viewStatus)
    }

    /**
     * 显示无网络视图
     */
    fun showNoNetwork() {
        viewStatus = STATUS_NO_NETWORK
        if (null != mNoNetworkView) {
            if (null != mOnRetryClickListener) {
                mNoNetworkView!!.setOnClickListener(mOnRetryClickListener)
            }
            addView(mNoNetworkView, 0, mLayoutParams)
        }
        showViewByStatus(viewStatus)
    }

    /**
     * 显示内容视图
     */
    fun showContent() {
        viewStatus = STATUS_CONTENT
        if (null != mContentView) {
            addView(mContentView, 0, mLayoutParams)
        }
        showViewByStatus(viewStatus)
    }

    private fun showViewByStatus(viewStatus: Int) {
        if (null != mLoadingView) {
            // mLoadingView.setVisibility(viewStatus == STATUS_LOADING ? View.VISIBLE : View.GONE);
            if (viewStatus == STATUS_LOADING) {
                mLoadingView!!.visibility = View.VISIBLE
            } else {
                mLoadingView!!.visibility = View.GONE
            }
        }
        // if (null != mEmptyView) {
        //     mEmptyView.setVisibility(viewStatus == STATUS_EMPTY ? View.VISIBLE : View.GONE);
        // }
        if (null != mErrorView) {
            mErrorView!!.visibility = if (viewStatus == STATUS_ERROR) View.VISIBLE else View.GONE
        }
        if (null != mNoNetworkView) {
            mNoNetworkView!!.visibility = if (viewStatus == STATUS_NO_NETWORK) View.VISIBLE else View.GONE
        }
        if (null != mContentView) {
            mContentView!!.visibility = if (viewStatus == STATUS_CONTENT) View.VISIBLE else View.GONE

        }
    }

    companion object {
        val STATUS_CONTENT = 0x00
        val STATUS_LOADING = 0x01
        val STATUS_EMPTY = 0x02
        val STATUS_ERROR = 0x03
        val STATUS_NO_NETWORK = 0x04

        private val NULL_RESOURCE_ID = -1
    }

}
