package com.mini.skproto.net

import android.content.Context
import com.mini.skproto.base.*
import com.mini.skproto.bean.ToastBean
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

fun SActivity.HTTPS(mContext: Context, indexs: Int, call: Call<String>, isShow: Boolean, isCancel: Boolean, backshow: Boolean):
        Disposable? {
    return HTTP(mContext, isFinishing, indexs, call, isShow, isCancel, backshow, this, this)
}

fun SFragment.HTTPS(mContext: Context, indexs: Int, call: Call<String>, isShow: Boolean, isCancel: Boolean, backshow: Boolean):
        Disposable? {
    return HTTP(mContext, isDetached, indexs, call, isShow, isCancel, backshow, this, this)
}

fun HTTP(mContext: Context, isDetached: Boolean, indexs: Int, call: Call<String>, isShow: Boolean, isCancel: Boolean, backshow: Boolean, httplistener: BaseHttpListener, toastLister: ToastCallBackLister):
        Disposable? {
    if (verifyNetwork()) {
        return SFlowable(mContext!!, isShow, httplistener).build(call, indexs, backshow)
    } else {
        if (backshow) {
            showToast(mContext, isDetached, "网络错误", -1, Types.ERREY,null,toastLister)
        }
        return null
    }
}



fun SFragment.showToast(mContext: Context, mess: String, flag: Int, types: Types, obj: Any?): MyDialogs {
    return showToast(mContext, isDetached, mess, flag, types, obj, this)
}

fun SActivity.showToast(mContext: Context, mess: String, flag: Int, types: Types, obj: Any?): MyDialogs {
    return showToast(mContext, isFinishing, mess, flag, types, obj, this)
}

fun showToast(mContext: Context, istrue: Boolean, mess: String, flag: Int, types: Types, obj: Any?, toastLister: ToastCallBackLister): MyDialogs {
    var mMyDialogs: MyDialogs? = null
    if (!istrue) {
        val bean = ToastBean(mess, types, flag, obj!!)
        if (mMyDialogs != null && mMyDialogs!!.isShow) {
            mMyDialogs!!.dissmiss()
            mMyDialogs = null
        }
        mMyDialogs = MyDialogs(mContext!!, bean, toastLister)
        mMyDialogs!!.show()
    }
    return mMyDialogs!!
}


