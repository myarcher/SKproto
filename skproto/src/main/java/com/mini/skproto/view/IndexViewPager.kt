package com.mini.skproto.view

import android.content.Context
import android.view.MotionEvent
import org.jetbrains.anko.support.v4._ViewPager

class IndexViewPager(context: Context, function: () -> Unit) : _ViewPager(context) {
    private var isCanScroll = false


    fun setScanScroll(isCanScroll: Boolean) {
        this.isCanScroll = isCanScroll
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        // TODO Auto-generated method stub
        return if (isCanScroll) {
            super.onTouchEvent(arg0)
        } else {
            false
        }

    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        // TODO Auto-generated method stub
        return if (isCanScroll) {
            super.onInterceptTouchEvent(arg0)
        } else {
            false
        }

    }

}
