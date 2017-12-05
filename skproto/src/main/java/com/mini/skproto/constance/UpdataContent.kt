package com.mini.skproto.constance

/**
 * @author
 * @version 1.0
 * @date 2017/6/11
 */

class UpdataContent private constructor() {

    var main = 0
    var shops = 0
    var mess = 0
    var self = 0
    var selfs = 0
    var sex = 0
    var sex1 = 0
    var shop = 0
    var self2 = 0
    var after1 = 0
    var after2 = 0
    var showDialog = 0

    companion object {
        private var ins: UpdataContent? = null


        fun instance(): UpdataContent {
            if (ins == null) {
                ins = UpdataContent()
            }
            return ins!!
        }
    }
}
