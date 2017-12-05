package com.mini.skproto.base.layout

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
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
class empt_view : _LinearLayout {
    public var empt_img:ImageView?=null
    public var empt_tv:TextView?=null
    constructor(context: Context) : super(context) {
        initview()
    }

    private fun initview() {
        gravity = Gravity.CENTER
        orientation = VERTICAL
        visibility= View.GONE
        lparams(width = matchParent, height = matchParent)
        backgroundColor = Color.WHITE
        empt_img=imageView{
            image= getBitmap(R.mipmap.default_img4,context)
        }.lparams(width=dip(153),height = dip(153))
        empt_tv=textView{
            textColor=Color.parseColor("#9A9A9A")
            textSize=px2sp(22)
            text="~~没有数据哦~~"
        }.lparams(width = wrapContent, height = wrapContent){
            topMargin=dip(42)
        }
    }
    
}

/*
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    android:id="@+id/empt_view"
    android:gravity="center"
    android:visibility="invisible"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/white"
    android:orientation="vertical">

    <ImageView
        android:id="@+id/empt_img"
        android:layout_width="@dimen/dimen_153px"
        android:layout_height="@dimen/dimen_133px"
        android:src="@mipmap/default_img4"/>

    <TextView
        android:id="@+id/empt_tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/dimen_42px"
        android:text="购物车是空的，赶快来选购吧"
        android:textColor="#9A9A9A"
        android:textSize="@dimen/dimen_22px"/>
    <Button
        android:id="@+id/empt_bnt"
        android:text="随便逛逛"
        android:gravity="center"
        android:textColor="@android:color/white"
        android:textSize="@dimen/dimen_18px"
        android:background="@mipmap/yellow_bg"
        android:layout_marginLeft="@dimen/dimen_48px"
        android:layout_marginRight="@dimen/dimen_48px"
        android:layout_marginTop="@dimen/dimen_105px"
        android:layout_width="match_parent"
        android:layout_height="@dimen/dimen_56px"/>
</LinearLayout>
 */