package com.mini.skproto.base.layout

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import com.mini.skproto.R
import com.mini.skproto.until.getBitmap
import org.jetbrains.anko.*

/**
 *
 *
 * @author
 * @date    2017/10/13
 * @version 1.0
 */ 
class network_view:_LinearLayout{
    constructor(context:Context):super(context){
        initview()
    }

    private fun initview() {
        backgroundColor=Color.WHITE
        orientation= VERTICAL
        gravity=Gravity.CENTER
        lparams(width= matchParent,height = matchParent)
        imageView{
            image= getBitmap(R.mipmap.no_net,context)
        }.lparams(width= dip(131),height = dip(131))
        textView{
            textColor=resources.getColor(R.color.black_color)
            textSize=px2sp(18)
            text="没有网络"
        }.lparams(width= wrapContent,height = wrapContent){
            topMargin=dip(30)
        }
    }
}
