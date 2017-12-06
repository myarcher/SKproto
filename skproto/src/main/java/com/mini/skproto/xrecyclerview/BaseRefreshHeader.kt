package com.mini.skproto.xrecyclerview

/**
 * Created by jianghejie on 15/11/22.
 */
internal interface BaseRefreshHeader {

    fun onMove(delta: Float)

    fun releaseAction(): Boolean

    fun refreshComplete()

    companion object {
        public open val STATE_NORMAL = 0
        public open val STATE_RELEASE_TO_REFRESH = 1
        public open val STATE_REFRESHING = 2
        public open val STATE_DONE = 3
    }

}