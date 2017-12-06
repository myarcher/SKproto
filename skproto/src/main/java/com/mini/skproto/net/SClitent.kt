package com.mini.skproto.net.custom

import com.mini.skproto.AppAppliction
import com.mini.skproto.constance.BUrlContense
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object SClitent {
    fun get(): SClitent {
        return this
    }

    private val mRetrofit: Retrofit

    init {
        mRetrofit = createRetrofit()
    }

    /**
     * 创建相应的服务接口
     */
    fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }


    private fun createRetrofit(): Retrofit {
        //初始化OkHttp
        val cache = Cache(File(AppAppliction.applictions!!.getCacheDir(), "HttpCache"), (1024 * 1024 * 100).toLong())
        val builder = OkHttpClient.Builder()//.cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)    //设置连接超时 9s
                .readTimeout(20, TimeUnit.SECONDS)      //设置读取超时 10s
                .writeTimeout(20, TimeUnit.SECONDS).retryOnConnectionFailure(false)
        //.sslSocketFactory(sslSocketFactory(launcherTrust), launcherTrust);

        if (AppAppliction.applictions!!.isDebug) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        // 返回 Retrofit 对象
        return Retrofit.Builder().baseUrl(BUrlContense.BASE_URL).client(builder.build()) // 传入请求客户端
                .addConverterFactory(ScalarsConverterFactory.create()) // 添加Gson转换工厂
                //.addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换工厂
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加RxJava2调用适配工厂
                .build()
    }
}
