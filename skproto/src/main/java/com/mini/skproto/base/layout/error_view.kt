package com.mini.skproto.base.layout

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
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
class error_view:_LinearLayout{
    public var error_img:ImageView?=null
    public var error_tv:TextView?=null
    constructor(context: Context):super(context){
        initview()
    }

    private fun initview() {
        backgroundColor=Color.WHITE
        orientation= VERTICAL
        gravity=Gravity.CENTER
        lparams(width= matchParent,height = matchParent)
        error_img=imageView{
            image= getBitmap(R.drawable.ic_prompt_alert_warn,context)
        }.lparams(width=dip(131),height = dip(131))
        error_tv=textView{
            text="连接服务器失败"
            textColor=resources.getColor(R.color.black_color)
            textSize=px2sp(18)
        }.lparams(width= wrapContent,height = wrapContent){
            topMargin=dip(30)
        }
    }

}
