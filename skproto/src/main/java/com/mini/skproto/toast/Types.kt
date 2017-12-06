package com.mini.skproto.toast

/**
 *
 *
 * @author
 * @date    2017/10/12
 * @version 1.0
 */
enum class Types {
    OK(1000, 0), ERREY(1500, 1), NOTI(1500, 2), GO(0, 3);

   constructor(time: Int, indexs: Int){
        this.indexs = indexs
        this.time = time
    }

    fun getTime(): Int {
        return time
    }

    fun setTime(time: Int) {
        this.time = time
    }

    private var time: Int = 0
    private var indexs: Int = 0

    fun getIndexs(): Int {
        return indexs
    }

    fun setIndexs(indexs: Int) {
        this.indexs = indexs
    }
}