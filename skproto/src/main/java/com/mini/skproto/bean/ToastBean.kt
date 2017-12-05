package com.mini.skproto.bean

import com.mini.skproto.toast.Types

import java.io.Serializable

class ToastBean : Serializable {
    var title: String? = null
    var type: Types? = null
    var flag: Int = 0
    var obj: Any? = null
    var url: String? = null
 
    constructor(title: String, type: Types, flag: Int, obj: Any) {
        this.title = title
        this.type = type
        this.flag = flag
        this.obj = obj
    }

    constructor(title: String, type:Types, flag: Int) {
        this.title = title
        this.type = type
        this.flag = flag
        this.obj = null
        this.url = null
    }

    constructor(title: String, type:Types) {
        this.title = title
        this.type = type
        this.flag = -1
        this.obj = null
        this.url = null
    }

    constructor(title: String, type:Types, url: String) {
        this.title = title
        this.type = type
        this.flag = -1
        this.obj = null
        this.url = url
    }
   open fun getFlags():Int{
        return flag
    }

}
