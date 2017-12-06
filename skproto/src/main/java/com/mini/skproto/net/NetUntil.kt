package com.mini.skproto.net.custom

import android.content.Context
import com.mini.skproto.base.*
import com.mini.skproto.bean.RequestBuild
import com.mini.skproto.bean.ToastBean
import com.mini.skproto.net.LoadListener
import com.mini.skproto.toast.MyDialogs
import com.mini.skproto.toast.ToastCallBackLister
import com.mini.skproto.toast.Types
import com.mini.skproto.until.verifyNetwork
import io.reactivex.disposables.Disposable
import retrofit2.Call

/**
 *
 *
 * @author
 * @date    2017/10/16
 * @version 1.0
 */


fun SActivity.HTTPS(build: RequestBuild): Disposable? {
    return HTTP(build)
}

fun SFragment.HTTPS(build: RequestBuild): Disposable? {
    return HTTP(build)
}

fun HTTP(build: RequestBuild):
        Disposable? {
    if (verifyNetwork()) {
        return SFlowable(build).build()
    } else {
        if (build!=null&&build.httplistener!=null) {
            build.httplistener!!.onFails(build, Exception("网络错误"))
        }
        return null
    }
}





