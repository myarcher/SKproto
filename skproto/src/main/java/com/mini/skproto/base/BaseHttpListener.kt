package com.mini.skproto.base

import android.content.Context
import android.util.Log
import com.mini.skproto.AppAppliction
import com.mini.skproto.AppManager
import com.mini.skproto.constance.UpdataContent
import com.mini.skproto.net.SHttpListener
import com.mini.skproto.parse.CommonJSONParser
import com.mini.skproto.toast.Types

/**
 *
 *
 * @author
 * @date    2017/10/16
 * @version 1.0
 */
interface BaseHttpListener : SHttpListener {
    
    override fun onNexts(indexs: Int, json: String, backshows: Boolean) {
        Log.i("json", "json>>>" + json)
        val map = CommonJSONParser().parse(json)//自自己写的万能网络json解析
        if (map != null) {//如果解析时有异常，map会为null
            var status: Int = map.get("status") as Int
            var message: String = map.get("message") as String
            if (status == 1) {
                onSuccess(indexs, message, map!!.get("data")!!)//正常数据的处理调用
            } else {
                dealPulics(message, status)
            }
        } else {
            if (backshows) {//如果请求时有加载弹出框，回调时也显示
                showMess("解析数据错误",-1,Types.ERREY,null)
            }
        }
    }

    open fun onSuccess(indexs: Int, mess: String, data: Any) {}

    override fun onFails(indexs: Int, t: Exception, isTrue: Boolean) {
        var message = "连接服务器错误"
        showMess(message,-1, Types.ERREY,null)
    }

    override fun onStarts(context: Context) {
    }

    override fun onFinishs(context: Context, isshow: Boolean) {

    }

    public fun dealPulics(message: String, status: Int) {
        if (status == 0) {
            showMess(message,-1,Types.ERREY,null)//提示信息
        } else if (status == -1) {
            showMess(message + "",-1,Types.NOTI,null)//提示信息
        } else if (status == -2) {//-2表示token不对，此时用户重新登录
            exit()
            val activity = AppManager.get().currentActivity()
            // activity.startActivity(Intent(activity, LoginUi::class.java))
        } else if (status == -3) {
            val activity = AppManager.get().currentActivity()
            // val ins = Intent(activity, FailureUi::class.java)
            // ins.putExtra("mess", message)
            // activity.startActivity(ins)
        } else if (status == -4) {
            showMess(message,-1,Types.ERREY,null)//提示信息
        }
    }

    private fun exit() {
        // Units.saveInfo(null)//设置默认数据
        AppAppliction.applictions!!.preShare!!.sets("divice", "")//divice 为""
        AppAppliction.applictions!!.preShare!!.sets("phone", "")
        AppAppliction.applictions!!.preShare!!.sets("pass", "")//设置登陆的手机号和密码为""
        UpdataContent.instance().self = 1//刷新个人中心
        UpdataContent.instance().shop = 1//刷新购物车
    }

    //调用activity里面的弹出提示框的方法
    fun showMess(mess: String,lag:Int, type: Types,obj:Any?) {
       
    }
}