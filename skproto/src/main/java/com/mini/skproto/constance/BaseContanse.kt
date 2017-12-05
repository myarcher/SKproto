package com.mini.skproto.constance

/**
 * @author
 * @version 1.0
 * @date 2017/4/19
 */

object BaseContanse {
    val SHARE_NAME = "shoppings"
    val MYTOAST_NO = -2
    val MYTOAST_ER = -1
    val PASS_MA = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$"
    val LOGIN = 1

    var email = ""

    var phone = ""
    var address_ma = "请选择"

    val POST = 1
    val GET = 2
    val UP = 3
    val DOWN = 4
    val POST_TWO = 5

    var SAVE_PATH = ""
    val JPEG_FILE_PREFIX = "IMG_"
    val JPEG_FILE_SUFFIX = ".jpg"

    val RESULT_PICK_IMAGE = 1
    val RESULT_TAKE_IMAGE = 2

}
