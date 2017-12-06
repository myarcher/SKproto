package com.mini.skproto.ui.layout

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import com.mini.skproto.R
import com.mini.skproto.until.getBitmap
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.style

/**
 *
 *
 * @author
 * @date    2017/10/11
 * @version 1.0
 */
class splash_v(context: Context) : _LinearLayout(context) {
    init {
        lparams(width = matchParent, height = matchParent)
        backgroundColor = resources.getColor(R.color.gray_color)
        orientation = VERTICAL
        frameLayout {
            imageView {
                backgroundDrawable = getBitmap(R.mipmap.start, context)
            }.lparams(width = matchParent, height = matchParent)
            
            relativeLayout {
                textView{
                    visibility= View.GONE
                    id=R.integer.splash_tv
                     text="2"
                    gravity=Gravity.CENTER
                    textColor=Color.parseColor("#4a4a4a")
                    textSize=px2sp(18)
                    backgroundColor=resources.getColor(R.color.gray_color)
                }.lparams(width=dip(45),height = dip(45)){
                    alignParentBottom()
                    alignParentRight()
                    bottomMargin=dip(35)
                    rightMargin=dip(35)
                }
            }.lparams(width = matchParent, height = matchParent)
        }.lparams( width = matchParent, height = matchParent)
    }
}
