package com.mini.skproto.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.mini.skproto.AppAppliction
import com.mini.skproto.bean.ToastBean
import com.mini.skproto.net.HTTPS
import com.mini.skproto.net.showToast
import com.mini.skproto.service.BService
import com.mini.skproto.toast.ToastCallBackLister
import com.mini.skproto.toast.Types
import com.mini.skproto.base.layout.LoadinfoListener
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.ListCompositeDisposable
import retrofit2.Call


/**
 * 所有fragmeng的基类，处理和activity基类类似 ，懒加载 （参考sacyivity）
 *
 * @author
 * @version 1.0
 * @date 2017/5/2
 */

abstract class SFragment : Fragment, BaseHttpListener, ToastCallBackLister, LoadinfoListener {
    // 当前Fragment 是否初始化
    public var isViewInit = false
    // 当前Fragment 是否可见
    public var isVisibles = false
    // 是否加载过数据
    protected var isLoadData = false
    //xml转换器（个人定义）
    var mInflater: LayoutInflater? = null
    //fragment对应的 activity
    var mActivity: SActivity? = null
    //全局上下文环境
    var abApplication: AppAppliction? = null
    //布局layout的根布局
    var ab_base: RelativeLayout? = null
    //fragment 的页面请求防止内存泄漏
    private val listDisposable = ListCompositeDisposable()

    var mView: View? = null
    // 构造函数，一般使用带参数的

    constructor() {
        abApplication = AppAppliction.applictions
    }

    constructor(baseUi: SActivity) {
        this.mActivity = baseUi
        abApplication = AppAppliction.applictions
    }


    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mActivity = activity as SActivity?
    }

   open fun initData(savedInstanceState: Bundle?) {

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibles = isVisibleToUser
        preLoadData(false)
    }

    protected abstract fun loadData() //数据请求

    fun preLoadData(forceLoad: Boolean) {//预加载
        //只有在页面可见，view已经初始化好，没有加载过数据，或者强制刷新的时候在加载数据
        if (isViewInit && isVisible && (!isLoadData || forceLoad)) {
            loadData()
            isLoadData = true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.isViewInit = true//view已经初始化好
        initData(savedInstanceState)
        // 防止一开始加载的时候未 调用 preLoadData 方法， 因为setUserVisibleHint 比 onActivityCreated 触发 前
        if (userVisibleHint) {
            preLoadData(false)
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mInflater = inflater
        if (ab_base != null) {
            val parent = ab_base!!.parent as ViewGroup
            parent?.removeView(ab_base)
        } else {
            ab_base = RelativeLayout(mActivity)
            ab_base!!.setBackgroundColor(Color.rgb(255, 255, 255))
            mView = getViews()
            if (mView != null) {
                ab_base!!.removeAllViews()
                ab_base!!.addView(mView, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
            }
        }
        return ab_base
    }

    abstract fun getViews(): View?

   


    @CallSuper
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onPause() {
        clear()
        super.onPause()
    }

    @CallSuper
    override fun onDestroy() {

        super.onDestroy()
    }


    fun addDisposable(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            listDisposable.add(disposable)
        }
    }


    protected fun clear() {
        if (!listDisposable.isDisposed) {
            listDisposable.clear()
        }
    }

    fun get(): BService {
        return abApplication!!.mBClient!!.create(BService::class.java)
    }

    override fun onSuccess(indexs: Int, mess: String, data: Any) {
        super.onSuccess(indexs, mess, data)
    }

    override fun onStarts(context: Context) {
        super.onStarts(context)
    }

    override fun onFinishs(context: Context, isshow: Boolean) {
        super.onFinishs(context, isshow)
    }


    override fun showMess(mess: String, flag: Int, types: Types, obj: Any?) {
        showToast(mActivity!!, mess, flag, types, obj)
    }

    override fun beginClick(bean: ToastBean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun forwordclick(bean: ToastBean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadinfo(index: Int) {
        
    }

    fun Https(indexs: Int, call: Call<String>, isShow: Boolean, isCancel: Boolean, backshow: Boolean) {
        addDisposable(HTTPS(mActivity!!, indexs, call, isShow, isCancel, backshow))
    }

    fun Https(indexs: Int, call: Call<String>,backshow: Boolean) {
        return Https(indexs, call, false, false, backshow)
    }

    fun Http(indexs: Int, call: Call<String>,backshow: Boolean) {
        return Https(indexs, call, true, false, backshow)
    }
}
