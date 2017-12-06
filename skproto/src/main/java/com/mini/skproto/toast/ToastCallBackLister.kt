package com.mini.skproto.toast

import com.mini.skproto.bean.ToastBean

/**
 *
 *
 * @author
 * @date    2017/10/16
 * @version 1.0
 */
interface ToastCallBackLister {
     fun beginClick(bean: ToastBean)
     fun forwordclick(bean: ToastBean)
}