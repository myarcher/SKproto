package com.mini.skproto.constance

/**
 * @author
 * @version 1.0
 * @date 2017/5/2
 */

object BUrlContense {
    //public static final String BASE_JOKE_URL = "http://120.77.247.228/brand/";//基础接口请求地址
    // public static final String BASE_IMG_URL = "http://120.77.247.228/brand";//基础接口请求地址
    //public static final String BASE_JOKE_URL = "http://store.dreameb.com/";//基础接口请求地址
    // public static final String BASE_IMG_URL = "http://store.dreameb.com";//基础接口请求地址

   open var BASE_URL = "http://120.77.247.228/dongsd/"
    open var BASE_IMG_URL = "http://120.77.247.228/dongsd"

    open var UNAUTHORIZED = 401
    open var FORBIDDEN = 403
    open var NOT_FOUND = 404
    open var REQUEST_TIMEOUT = 408
    open var INTERNAL_SERVER_ERROR = 500
    open var BAD_GATEWAY = 502
    open var SERVICE_UNAVAILABLE = 503
    open var GATEWAY_TIMEOUT = 504

    open var kefu_tel = "400-101-5218"
    open var APP_ID = "371"
    //  public static  final String APP_ID="453";
    /**
     * 未知错误
     */
    open var UNKNOWN = 1000
    /**
     * 解析错误
     */
    open var PARSE_ERROR = 1001
    /**
     * 网络错误
     */
    open var NETWORD_ERROR = 1002
    /**
     * 协议出错
     */
    open var HTTP_ERROR = 1003
}
