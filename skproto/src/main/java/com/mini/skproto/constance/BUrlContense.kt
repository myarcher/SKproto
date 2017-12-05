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

    val BASE_URL = "http://120.77.247.228/dongsd/"
    val BASE_IMG_URL = "http://120.77.247.228/dongsd"

    val UNAUTHORIZED = 401
    val FORBIDDEN = 403
    val NOT_FOUND = 404
    val REQUEST_TIMEOUT = 408
    val INTERNAL_SERVER_ERROR = 500
    val BAD_GATEWAY = 502
    val SERVICE_UNAVAILABLE = 503
    val GATEWAY_TIMEOUT = 504

    val kefu_tel = "400-101-5218"
    val APP_ID = "371"
    //  public static  final String APP_ID="453";
    /**
     * 未知错误
     */
    val UNKNOWN = 1000
    /**
     * 解析错误
     */
    val PARSE_ERROR = 1001
    /**
     * 网络错误
     */
    val NETWORD_ERROR = 1002
    /**
     * 协议出错
     */
    val HTTP_ERROR = 1003
}
