package com.mini.skproto.net

import android.app.Dialog
import android.content.Context

/**
 * 成功回调处理
 * Created by WZG on 2016/7/16.
 */
interface SHttpListener {
    fun onNexts(indexs: Int, t: String, backshows: Boolean)
    fun onFails(indexs: Int, t: Exception, isTrue: Boolean)
    fun onStarts(context: Context)
    fun onFinishs(context: Context, isshow: Boolean)
}
