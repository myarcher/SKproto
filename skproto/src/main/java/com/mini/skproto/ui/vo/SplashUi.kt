package com.mini.skproto.ui.vo

import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.View
import com.mini.skproto.base.SActivity
import com.mini.skproto.ui.layout.splash_v
import org.jetbrains.anko.startActivity

class SplashUi : SActivity() {
    val total: Long = 5 * 1000
    val one: Long = 1000

    override fun initData(savedInstanceState: Bundle?) {
        mDownTimer.start()
    }

    override fun getView(): View? {
        return splash_v(this);
    }

    internal var mDownTimer: CountDownTimer = object : CountDownTimer(total, one) {
        //计时器，倒数
        override fun onTick(mill: Long) {
        }

        override fun onFinish() {
            toWings()//倒计时结束
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
            mDownTimer.cancel()//结束倒计时
            finish()
        }
        return false
    }

    internal fun toWings() {
        mDownTimer.cancel()//在倒计时期间点击按钮，结束倒计时
        startActivity<MainUi>()
        finish()
    }
}
