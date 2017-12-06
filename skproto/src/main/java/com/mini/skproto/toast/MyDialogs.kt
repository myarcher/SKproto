package com.mini.skproto.toast

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Handler
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.mini.skproto.R

import com.mini.skproto.bean.ToastBean
import com.mini.skproto.constance.BaseContanse
import org.jetbrains.anko.dip

import java.util.Timer
import java.util.TimerTask

/**
 * @author
 * @version 1.0
 * @date 2017/7/20
 */

class MyDialogs(private val context: Context, private val bean: ToastBean, private val backLister: ToastCallBackLister?) {
    private var mydialogs_tv: TextView? = null
    private val inflater: LayoutInflater
    private var dialog: Dialog? = null


    val isShow: Boolean
        get() = if (dialog == null) {
            false
        } else dialog!!.isShowing

    init {
        inflater = LayoutInflater.from(context)
        init()
    }

    fun init() {
        dialog = Dialog(context, R.style.EvaluateDialogStyle)
        val view = mydialogs_v(context)
        mydialogs_tv = view.mydialogs_tv
        mydialogs_tv!!.text = bean.title
        dialog!!.setContentView(view)
        val window = dialog!!.window
        val params = window!!.attributes
        val w = context.dip(320)
        context.resources.getDimension(R.dimen.dimen_320px).toInt()
        params.width = w
        params.y = w / 2
        window.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        window.attributes = params
    }

    fun setCanceListener(listener: DialogInterface.OnCancelListener) {
        dialog!!.setOnCancelListener(listener)
    }

    fun setCancelable(cancel: Boolean) {
        dialog!!.setCancelable(cancel)
    }


    fun show() {
        backLister?.beginClick(bean)
        beginClick(bean)
        
        dialog!!.show()
        if (bean.type!!.getIndexs() != 3) {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    dissmiss()
                    forwordclick(bean)
                }
            }, bean.type!!.getTime().toLong())
        }
    }

    fun dissmiss() {
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }
    }

    fun beginClick(bean: ToastBean) {
        if (bean.getFlags() !== BaseContanse.MYTOAST_ER) {//默认情况-1是进行回调处理，只是显示，下面判断相同
            val message = Message()
            message.what = 1
            message.obj = bean
            mHandler.sendMessage(message)
        }
    }

    fun forwordclick(bean: ToastBean) {
        if (bean.getFlags() !== BaseContanse.MYTOAST_ER) {
            val message = Message()
            message.what = 2
            message.obj = bean
            mHandler.sendMessage(message)
        }
    }


    internal val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 1) {
                backLister?.forwordclick(bean)
            } else if (msg.what == 2) {
                backLister?.forwordclick(bean)
            }
        }
    }
}
