package com.mini.skproto.base

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.mini.skproto.base.layout.BindItemListener

import java.util.ArrayList


/**
 * Created by wanglei on 2016/11/29.
 */

class CustomAdapter : RecyclerView.Adapter<ViHolder> {
    var mList: MutableList<Map<String, Any>>? = ArrayList()
    var mContext: SActivity
    var mView: View? = null
    var list: MutableList<Map<String, Any>>?
        get() {
            if (mList == null) {
                mList = ArrayList()
            }
            return mList
        }
        set(mList) {
            if (mList != null) {
                this.mList = mList
                notifyDataSetChanged()
            }
        }

    constructor(context: SActivity, mView: View) : super() {
        this.mContext = context
        this.mView = mView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViHolder {
        return ViHolder.createViewHolder(mContext, mView!!)
    }

    override fun onBindViewHolder(holder: ViHolder, position: Int) {
        val data = mList!![position]
        try {
            var bind: BindItemListener = holder.convertView as BindItemListener
            bind.bindItemHolder(holder, data, position, this)
        } catch (e: Exception) {
            throw RuntimeException("需要实现BindItemListener接口")
        }
    }


    fun addList(mList: List<Map<String, Any>>?) {
        if (mList != null) {
            this.mList!!.addAll(mList)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        if (mList != null) {
            mList!!.clear()
        }
    }

    fun setItem(po: Int, item: Map<String, Any>) {
        if (mList != null) {
            mList!![po] = item
        }
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return if (mList == null) {
            0
        } else mList!!.size
    }


}
