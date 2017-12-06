package com.mini.skproto.net.custom

import android.app.Dialog
import android.content.Context
import com.mini.skproto.bean.RequestBuild

/**
 * 成功回调处理
 * Created by WZG on 2016/7/16.
 */
interface SHttpListener {
    fun onNexts(build: RequestBuild,json:String)
    fun onFails(build: RequestBuild, exception:Exception)
    
}
