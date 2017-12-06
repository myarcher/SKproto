package com.mini.skproto.ui.layout

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.mini.skproto.R
import com.mini.skproto.until.getBitmap
import com.mini.skproto.view.BannerLayout
import org.jetbrains.anko.*

/**
 *
 *
 * @author
 * @date    2017/10/17
 * @version 1.0
 */
class mainfra_head_v : _LinearLayout {
    public var main_four_head_title_img: ImageView? = null
    public var main_four_head_banner: BannerLayout? = null

    constructor(context: Context) : super(context) {
        initview()
    }

    init {

    }

    private fun initview() {
        lparams(width = matchParent, height = matchParent)
        backgroundColor = resources.getColor(R.color.main_color)
        orientation = VERTICAL
        main_four_head_banner = BannerLayout(context){
            visibility = View.VISIBLE
            autoPlayDuration = 5000
            indicatorMargin=dip(8)
            indicatorPosition=BannerLayout.Position.centerBottom
            indicatorShape=BannerLayout.Shape.oval
            indicatorSpace=dip(5)
            scrollDuration=1100
            selectedIndicatorColor=resources.getColor(R.color.white_color)
            selectedIndicatorHeight=dip(8)
            selectedIndicatorWidth=dip(8)
            unSelectedIndicatorColor=resources.getColor(R.color.gray_color)
            unSelectedIndicatorHeight=dip(8)
            unSelectedIndicatorWidth=dip(8)
        }.lparams(width = dip(480), height = dip(240))
        main_four_head_title_img = imageView {
            backgroundColor = Color.WHITE
        }.lparams(width = matchParent, height = dip(177)) {
            topPadding = dip(15)
        }
        linearLayout {
            orientation = VERTICAL
            backgroundColor = Color.WHITE
            gravity = Gravity.CENTER
            imageView {
                image = getBitmap(R.mipmap.main_four_title, context)
            }.lparams(width = dip(288), height = dip(28)) {
                gravity = Gravity.CENTER
            }
        }.lparams(width = matchParent, height = dip(64)) {
            topMargin = dip(20)
        }

        linearLayout {
            orientation = VERTICAL
            view {
                backgroundColor = Color.parseColor("#e4e4e4")
            }.lparams(width = matchParent, height = 1)
            view {
                backgroundColor = Color.WHITE
            }.lparams(width = matchParent, height = dip(10))
        }.lparams(width = matchParent, height = wrapContent)
    }

}

/*
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/main_color"
    android:orientation="vertical">

    <com.yyydjk.library.BannerLayout
        android:id="@+id/main_four_head_banner"
        android:layout_width="@dimen/dimen_479px"
        android:layout_height="@dimen/dimen_239px"
        android:visibility="visible"
        app:autoPlayDuration="5000"
        app:indicatorMargin="@dimen/dimen_8px"
        app:indicatorPosition="centerBottom"
        app:indicatorShape="oval"
        app:indicatorSpace="@dimen/dimen_5px"
        app:scrollDuration="1100"
        app:selectedIndicatorColor="@color/white_color"
        app:selectedIndicatorHeight="@dimen/dimen_8px"
        app:selectedIndicatorWidth="@dimen/dimen_8px"
        app:unSelectedIndicatorColor="@color/gray_color"
        app:unSelectedIndicatorHeight="@dimen/dimen_8px"
        app:unSelectedIndicatorWidth="@dimen/dimen_8px"/>

    <ImageView
        android:id="@+id/main_four_head_title_img"
        android:layout_width="match_parent"
        android:layout_height="@dimen/dimen_177px"
        android:background="@color/white_color"
        android:paddingTop="@dimen/dimen_15px"/>

    <include layout="@layout/main_four_head_two"/>

    <include layout="@layout/main_four_head_three"/>

    <include layout="@layout/main_four_head_four"/>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="@dimen/dimen_64px"
        android:layout_marginTop="@dimen/dimen_20px"
        android:background="@color/white_color"
        android:gravity="center"
        android:orientation="vertical">

        <ImageView
            android:layout_width="@dimen/dimen_288px"
            android:layout_height="@dimen/dimen_28px"
            android:layout_gravity="center"
            android:src="@mipmap/main_four_title"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">
        <View
            android:layout_width="match_parent"
            android:layout_height="1px"
            android:background="#e4e4e4"/>

        <View
            android:layout_width="match_parent"
            android:layout_height="@dimen/dimen_10px"
            android:background="@color/white_color"
            />
    </LinearLayout>
</LinearLayout>

 */
