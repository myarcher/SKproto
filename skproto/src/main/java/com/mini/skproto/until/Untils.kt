package com.mini.skproto.until

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.text.TextUtils
import android.util.Log
import com.mini.skproto.AppAppliction
import java.util.*

/**
 *
 *
 * @author
 * @date    2017/10/11
 * @version 1.0
 */
     fun getBitmap(re: Int, context: Context): BitmapDrawable {
        val opt = BitmapFactory.Options()
        opt.inPreferredConfig = Bitmap.Config.RGB_565
        opt.inPurgeable = true
        opt.inInputShareable = true
        val `is` = context.resources.openRawResource(re)
        val bm = BitmapFactory.decodeStream(`is`, null, opt)
        return BitmapDrawable(context.resources, bm)
    }

     fun verifyNetwork(): Boolean {
        val con = AppAppliction.applictions
        val connectivityManager = con!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return if (activeNetInfo != null) {
            if (activeNetInfo.isConnected) {
                true
            } else {
                false
            }
        } else {
            false
        }
    }

    //获取保存的token
     fun tokens(): String {
        return AppAppliction.applictions!!.preShare!!.gets("token", "")
    }


    //获取手机的唯一标识，这个在登陆之后会存起来，在退出登录的时候清除
    fun divices(): String {
        var divice = AppAppliction.applictions!!.preShare!!.gets("divice", "")
        if (TextUtils.isEmpty(divice)) {
            val uuid = UUID.randomUUID()
            divice = uuid.toString()
        }
        return divice
    }

