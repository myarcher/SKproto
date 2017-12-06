package com.mini.skproto.base

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.mini.skproto.AppAppliction
import com.mini.skproto.AppManager
import com.mini.skproto.bean.RequestBuild
import com.mini.skproto.net.LoadListener
import com.mini.skproto.net.custom.HTTPS
import com.mini.skproto.net.custom.SHttpListener
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.ListCompositeDisposable
import retrofit2.Call

abstract class SActivity : AppCompatActivity(){
    /**
     * 防止网路请求内存泄漏
     */
    private val listCompositeDisposable = ListCompositeDisposable()
    /**
     * layout转换器
     */
    var mInflater: LayoutInflater?=null;

    /**
     * 全局应用的环境
     */
    var abApplication: AppAppliction? = null
    /**
     * layout的实际父控件
     */
    var ab_base: RelativeLayout? = null
  

     var mView: View?=null;//返回layout的xml
   

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.get().addActivity(this)
        mInflater = layoutInflater
        ab_base = RelativeLayout(this)
        ab_base!!.setBackgroundColor(Color.rgb(255, 255, 255))
        abApplication = AppAppliction.applictions
        mView=getView();
        if (mView!=null) {
            ab_base!!.removeAllViews()
            ab_base!!.addView(mView, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
            setContentView(ab_base, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
        }
        initData(savedInstanceState)
    }

    abstract fun getView(): View?

    abstract fun initData(savedInstanceState: Bundle?) //初始化数据



    override fun finish() {
        super.finish()
        // overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    /**
     * 移除注解，和请求
     */
    @CallSuper
    override fun onDestroy() {
        AppManager.get().finishActivity(this)
       
        clear()
        super.onDestroy()
    }

    /**
     * 添加请求
     * @param disposable
     */
    fun addDisposable(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            listCompositeDisposable.add(disposable)
        }
    }

    /**
     * 清除网络请求
     */
    protected fun clear() {
        if (!listCompositeDisposable.isDisposed) {
            listCompositeDisposable.clear()
        }
    }

    
    /**
     * 自定义接口的实现方法
     */
    fun callBack(code: Long, stat: Long, value1: Any, value2: Any) {

    }

    /**
     * 权限的结果处理
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * 返回键的处理
     * @param keyCode
     * @param event
     * @return
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) { //按下的如果是BACK，同时没有重复
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
    

   open  fun <T> getService(service: Class<T>): T {
        return abApplication!!.mBClient!!.create(service)
    }
    
    fun Https(builder:RequestBuild) {
        addDisposable(HTTPS(builder))
    }
    
    fun Https(url: String, call: Call<String>,httpListener:SHttpListener,load:LoadListener) {
        var build:RequestBuild=RequestBuild.get()
                .setCall(call)
                .setHttpListener(httpListener)
                .setLoadListener(load)
                .setUrl(url)
        return Https(build)
    }

}
