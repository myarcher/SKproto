package com.mini.skproto.net.custom

import android.content.Context
import android.os.Handler
import android.os.Message
import com.mini.skproto.bean.RequestBuild
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
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
    private var build:RequestBuild? = null
    constructor(build:RequestBuild) {
        this.build = build
        if (build != null&& build.loader != null) {
            build.loader!!.onStarts(build)
        }
    }

    fun build(): Disposable? {
        
        var flowable: Flowable<String> = Flowable.create(FlowableOnSubscribe<String> { e ->

            e.setCancellable {
                if (build!!.call!!.isCanceled!!) {
                    build!!.call!!.cancel()
                }
            }
            build!!.call!!.enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    e.onComplete()
                    if (build!!.loader!! != null) {
                        build!!.loader!!.onFinishs(build!!)
                    }
                    if(build!!.httplistener!=null){
                        build!!.httplistener!!.onFails(build!!,Exception("请求服务器错误"))
                    }
                }

                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    e.onComplete()

                    if (build!!.loader!! != null) {
                        build!!.loader!!.onFinishs(build!!)
                    }
                    if(build!!.httplistener!=null){
                        if(response!!.isSuccessful) {
                            build!!.httplistener!!.onNexts(build!!, response.body()!!)
                        }else{
                            build!!.httplistener!!.onFails(build!!,Exception("请求服务器错误"))
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
    

}