package com.mini.skproto

import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.support.annotation.RequiresApi
import com.mini.skproto.base.SActivity
import java.util.*

/**
 *
 *
 * @author
 * @date    2017/10/11
 * @version 1.0
 */
class AppManager {
    /**
     * 确保单例
     */
    companion object {
        private var activityStack: Stack<SActivity>? = null//使用栈的方式管理activity
        private var instance: AppManager? = null
        fun get(): AppManager {
            if (instance == null) {
                instance = AppManager()
            }
            return instance as AppManager 
        }
    }

    private constructor() {
        if (activityStack == null) {
            activityStack = Stack<SActivity>()
        }
    }

    /**
     * 添加activity
     * @param activity 要添加的activity
     */
    fun addActivity(activity: SActivity) {
        if (activityStack == null) {
            activityStack = Stack<SActivity>()
        }
        activityStack!!.add(activity)
    }

    /**
     * 获取栈顶部的activity
     * @return  顶部activity
     */
    fun currentActivity(): SActivity {
        return activityStack!!.lastElement()
    }

    /**
     * 销毁栈顶的activity
     */
    fun finishActivity() {
        var activity: SActivity? = activityStack!!.lastElement()
        if (activity != null) {
            activity!!.finish()
            activity = null
        }
    }

    /**
     * 移除栈顶的activity
     */
    fun finishActivity(activity: SActivity?) {
        var activity = activity
        if (activity != null) {
            activityStack!!.remove(activity)
            activity!!.finish()
            activity = null
        }
    }

    /**
     * 移除制定的activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack!!) {
            if (activity::class.java.equals(cls)) {
                this.finishActivity(activity)
            }
        }
    }


    /**
     * 移除所有的activity
     */
    fun finishAllActivity() {
        val size = activityStack!!.size
        for (i in 0 until size) {
            if (null != activityStack!![i]) {
                activityStack!![i].finish()
            }
        }
        activityStack!!.clear()
    }


    /**
     * 退出应用（注意这个在6.0版本上，是需要动态权限的）
     * @param context 上下环境
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    fun AppExit(context: Context) {
        try {
            this.finishAllActivity()
            val activityMgr = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityMgr.restartPackage(context.packageName)
            //	System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    /**
     * 退出应用的提示框
     * @param context 上下文环境
     */
    fun showExitDialog(context: Activity) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("你确定退出应用吗?")
        builder.setNegativeButton("取消", null)
        builder.setPositiveButton("确定") { dialog, which ->
            this@AppManager.AppExit(context)
            context.finish()
        }
        val alert = builder.create()
        alert.show()
    }


}
