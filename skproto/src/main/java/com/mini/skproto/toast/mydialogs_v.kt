package com.mini.skproto.toast

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.TextView
import com.mini.skproto.R
import org.jetbrains.anko.*

/**
 *
 *
 * @author
 * @date    2017/10/16
 * @version 1.0
 */ 

class mydialogs_v:_LinearLayout{
    public var mydialogs_tv:TextView?=null
    constructor(context:Context):super(context){
        initview()
    }

    private fun initview() {
        lparams(width=dip(320),height = wrapContent){
            bottomMargin=dip(100)
            gravity=Gravity.BOTTOM;Gravity.CENTER_HORIZONTAL
        }
        gravity=Gravity.CENTER
        orientation= VERTICAL
        backgroundResource= R.drawable.mydialogs_bg
        minimumHeight=dip(96)
        mydialogs_tv=textView{
            textColor=Color.WHITE
            text=""
            textSize=px2sp(20)
          
        }.lparams(width= wrapContent,height = wrapContent)
    }
}
