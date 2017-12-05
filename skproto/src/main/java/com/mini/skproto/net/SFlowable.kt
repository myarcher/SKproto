package com.mini.skproto.net

import android.content.Context
import android.os.Handler
import android.os.Message
import com.mini.skproto.constance.BUrlContense
import com.mini.skproto.toast.ToastCallBackLister
import com.mini.skproto.toast.Types
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Cancellable
import io.reactivex.internal.schedulers.IoScheduler
import org.reactivestreams.Publisher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
 *
 * @author
 * @date    2017/10/12
 * @version 1.0
 */
class SFlowable {
    private var context: Context? = null
    private var isshow: Boolean = false
    private var slistener: SHttpListener? = null

    constructor(context: Context, isshow: Boolean,slistener: SHttpListener) {
        this.context = context
        this.isshow = isshow
        this.slistener=slistener
        if (context != null && isshow && slistener != null) {
            slistener!!.onStarts(context)
        }
    }

    fun build(call: Call<String>, indexs: Int, backshow: Boolean): Disposable? {
        var flowable: Flowable<String> = Flowable.create(FlowableOnSubscribe<String> { e ->

            e.setCancellable {
                if (call.isCanceled!!) {
                    call.cancel()
                }
            }
            call.enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    e.onComplete()
                    if (slistener != null) {
                        slistener!!.onFinishs(context!!, isshow)
                        val message = Message()
                        message.what = indexs
                        message.obj=backshow
                        mHandler.sendMessage(message)//保证信息处理在主线程中处理
                    }
                }

                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    e.onComplete()
                    if (slistener != null) {
                        slistener!!.onFinishs(context!!, isshow)
                        if (response!!.isSuccessful()) {
                            slistener!!.onNexts(indexs, response.body()!!, backshow)
                        } else {
                            val message = Message()
                            message.what = indexs
                            message.obj=backshow
                            mHandler.sendMessage(message)//保证信息处理在主线程中处理
                        }
                    }
                }
            })

        }, BackpressureStrategy.BUFFER).compose(background())

        return flowable.subscribe()
    }

    fun <T> background(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.subscribeOn(IoScheduler())
                    .observeOn(AndroidSchedulers.mainThread()).onErrorResumeNext(Flowable.empty())
        }
    }
    
    internal var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            slistener!!.onFails(msg.what, Exception(""), msg.obj as Boolean)
        }
    }

}