package com.mini.skproto.bean

import com.mini.skproto.AppAppliction
import com.mini.skproto.net.LoadListener
import com.mini.skproto.net.custom.SHttpListener
import retrofit2.Call

/**
 *
 *
 * @author
 * @date    2017/12/6
 * @version 1.0
 */
class RequestBuild {
    public var call: Call<String>? = null;
    public var httplistener: SHttpListener? = null
    public var loader: LoadListener? = null
    public var url: String? = null

    constructor(call: Call<String>?, httplistener: SHttpListener?, loader: LoadListener?, url: String?) {
        this.call = call
        this.httplistener = httplistener
        this.loader = loader
        this.url = url
    }

   private constructor() {
    }

    public fun setCall(call: Call<String>?): RequestBuild {
        this.call = call
        return this
    }

    public fun setHttpListener(httplistener: SHttpListener?): RequestBuild {
        this.httplistener = httplistener
        return this
    }

    public fun setLoadListener(loader: LoadListener?): RequestBuild {
        this.loader = loader
        return this
    }

    public fun setUrl(url: String?): RequestBuild {
        this.url = url
        return this
    }

    companion object {
        fun get(): RequestBuild {
            return RequestBuild()
        }
    }
}