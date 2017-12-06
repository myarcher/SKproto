package com.mini.skproto.base.layout

import android.content.Context
import android.graphics.Color
import android.support.v4.media.session.PlaybackStateCompat
import android.view.View
import com.mini.skproto.R
import org.jetbrains.anko.*

/**
 *
 *
 * @author
 * @date    2017/10/13
 * @version 1.0
 */
class loading_view : _RelativeLayout {
    constructor(context: Context) : super(context) {
        initview()
    }
    private fun initview() {
        lparams(width = matchParent, height = matchParent)
        backgroundColor = Color.WHITE
        imageView {
            visibility = View.GONE
        }.lparams(width = dip(40), height = dip(40)) {
            centerInParent()
            gravity = CENTER_VERTICAL
        }
        progressBar {
            visibility= View.VISIBLE
            indeterminateDrawable=resources.getDrawable(R.anim.dialog_loading)
        }.lparams(width=dip(40),height = dip(40)){
            centerInParent()
            gravity= CENTER_VERTICAL
        }
    }
}
