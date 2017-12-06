package com.mini.skproto

import android.app.Application
import com.mini.skproto.constance.BaseContanse
import com.mini.skproto.net.custom.SClitent
import com.mini.skproto.until.ShareUntil

/**
 * @author
 * @version 1.0
 * @date 2017/4/19
 */

 public open class AppAppliction : Application() {
    var isDebug: Boolean = false//是否调试
    var mBClient: SClitent?=null
    var preShare:ShareUntil?=null
    override fun onCreate() {
        super.onCreate()
        applictions = this
        isDebug = true
        mBClient = SClitent.get()
        preShare=ShareUntil.get(BaseContanse.SHARE_NAME, this)
    }
    
    companion object {
        var applictions: AppAppliction?=null//全局单例
    }

}
