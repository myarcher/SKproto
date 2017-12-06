package com.mini.skproto.toast

import android.content.Context
import com.mini.skproto.base.SActivity
import com.mini.skproto.base.SFragment
import com.mini.skproto.bean.ToastBean

/**
 *
 *
 * @author
 * @date    2017/12/6
 * @version 1.0
 */
fun SFragment.showToast(mContext: Context, mess: String, flag: Int, types: Types, obj: Any?,toastLister: ToastCallBackLister): MyDialogs {
    return showToast(mContext, isDetached, mess, flag, types, obj, toastLister)
}

fun SActivity.showToast(mContext: Context, mess: String, flag: Int, types: Types, obj: Any?,toastLister: ToastCallBackLister): MyDialogs {
    return showToast(mContext, isFinishing, mess, flag, types, obj, toastLister)
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
