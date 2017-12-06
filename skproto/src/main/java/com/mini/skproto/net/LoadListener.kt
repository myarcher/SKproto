package com.mini.skproto.net

import android.content.Context
import com.mini.skproto.bean.RequestBuild

/**
 *
 *
 * @author
 * @date    2017/12/6
 * @version 1.0
 */ 
interface LoadListener{
    fun onStarts(build: RequestBuild)
    fun onFinishs(build:RequestBuild)
}