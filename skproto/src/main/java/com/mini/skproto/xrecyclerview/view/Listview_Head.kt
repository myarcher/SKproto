package com.mini.skproto.xrecyclerview.view

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.mini.skproto.R
import com.mini.skproto.until.getBitmap
import com.mini.skproto.xrecyclerview.SimpleViewSwitcher
import org.jetbrains.anko.*

/**
 *
 *
 * @author
 * @date    2017/10/13
 * @version 1.0
 */
class Listview_Head : _LinearLayout {
    public var listview_header_content: RelativeLayout? = null
    public var listview_header_text: LinearLayout? = null
    public var refresh_status_textview: TextView? = null
    public var last_refresh_time: TextView? = null
    public var listview_header_arrow: ImageView? = null
    public var listview_header_progressbar: SimpleViewSwitcher? = null

    constructor(context: Context) : super(context) {
        lparams(width = matchParent, height = wrapContent)
        gravity = Gravity.BOTTOM
        backgroundColor = resources.getColor(R.color.gray_color)
        listview_header_content = relativeLayout {
            listview_header_text = linearLayout {
                id=0x00
                minimumWidth = dip(100)
                gravity = Gravity.CENTER
                orientation = VERTICAL
                refresh_status_textview = textView {
                    text = resources.getText(R.string.listview_header_hint_normal)
                }.lparams(width = wrapContent, height = wrapContent)
                linearLayout {
                    visibility = View.GONE
                    textView {
                        textSize = px2sp(12)
                        text = resources.getText(R.string.listview_header_last_time)
                    }
                    last_refresh_time = textView {
                        textSize = px2sp(12)
                    }
                }.lparams(width = wrapContent, height = wrapContent) {
                    topMargin = dip(3)
                }
            }.lparams(width = wrapContent, height = wrapContent) {
                centerInParent()
            }
            listview_header_arrow = imageView {
                image = getBitmap(R.drawable.ic_pulltorefresh_arrow, context)
            }.lparams(width = wrapContent, height = wrapContent) {
                centerVertically()
                leftMargin = dip(35)
                rightMargin = dip(10)
                leftOf(listview_header_text!!)
            }
            listview_header_progressbar = SimpleViewSwitcher(context) {
                visibility = View.VISIBLE
            }.lparams(width = dip(30), height = dip(30)) {
                leftOf(listview_header_text!!)
                centerVertically()
                leftMargin = dip(40)
                rightMargin = dip(10)
            }
        }.lparams(width = matchParent, height = dip(80)) {
            topPadding = dip(10)
        }
    }
}


/*
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="fill_parent"
      android:background="@color/gray_color"
android:layout_height="wrap_content"
android:gravity="bottom" >

<RelativeLayout
android:id="@+id/listview_header_content"
android:layout_width="fill_parent"
android:layout_height="80dp"
android:paddingTop="10dip"
>

<LinearLayout
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:minWidth="100dip"
    android:layout_centerInParent="true"
    android:gravity="center"
    android:orientation="vertical"
    android:id="@+id/listview_header_text">

    <TextView
        android:id="@+id/refresh_status_textview"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/listview_header_hint_normal" />

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:visibility="gone"
        android:layout_marginTop="3dp" >

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/listview_header_last_time"
            android:textSize="12sp" />

        <TextView
            android:id="@+id/last_refresh_time"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="12sp" />
    </LinearLayout>
</LinearLayout>

<ImageView
    android:id="@+id/listview_header_arrow"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_centerVertical="true"
    android:layout_marginLeft="35dp"
    android:layout_marginRight="10dp"
    android:layout_toLeftOf="@id/listview_header_text"
    android:src="@drawable/ic_pulltorefresh_arrow" />

<com.wangteng.sigleshopping.xrecyclerview.SimpleViewSwitcher
    android:id="@+id/listview_header_progressbar"
    android:layout_width="30dip"
    android:layout_height="30dip"
    android:layout_toLeftOf="@id/listview_header_text"
    android:layout_centerVertical="true"
    android:layout_marginLeft="40dp"
    android:layout_marginRight="10dp"
    android:visibility="visible" />
</RelativeLayout>

</LinearLayout>
 */