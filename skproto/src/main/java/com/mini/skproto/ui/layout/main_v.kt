package com.mini.skproto.ui.layout

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mini.skproto.R
import com.mini.skproto.until.getBitmap
import com.mini.skproto.view.IndexViewPager
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class main_v : _LinearLayout{

    val main_tab_tvsize: Int = 18
    val main_tab_height: Int = 65
    val main_tab_imgwh: Int = 25
    val main_tab_margin_top: Int = 5
    val main_tab_line_height: Float = 1f

    private var main_pager: IndexViewPager? = null
    private var main_line: View?= null
    private var main_tab: LinearLayout?=null
    private var ids: IntArray? = null
    private var idss: IntArray? = null
    private var ids1: Array<TextView?>;
    private var idss2: Array<ImageView?>

    constructor(context: Context) : super(context) {
        ids1 = arrayOfNulls(4)
        idss2 = arrayOfNulls(4)
        innv()
    }

    init {
        ids = intArrayOf(R.mipmap.main1, R.mipmap.fenglei1, R.mipmap.shop1, R.mipmap.me1)
        idss = intArrayOf(R.mipmap.main2, R.mipmap.fenglei2, R.mipmap.shop2, R.mipmap.me2)
    }

    fun innv() {
        lparams(width = matchParent, height = matchParent)
        backgroundColor = resources.getColor(R.color.gray_color)
        orientation = VERTICAL
        relativeLayout {
            backgroundColor = Color.WHITE
            main_tab = linearLayout {
                orientation = HORIZONTAL
                id=0x1000
                linearLayout {
                    onClick { setSelectTab(0) }
                    orientation = VERTICAL
                    gravity = Gravity.CENTER
                    idss2[0] = imageView {
                        image = getBitmap(R.mipmap.main1, context)
                    }.lparams(width = dip(main_tab_imgwh), height = dip(main_tab_imgwh))

                    ids1[0] = textView {
                        text = "首页"
                        textColor = resources.getColor(R.color.yellow_color)
                        textSize = px2sp(main_tab_tvsize)
                    }.lparams(width = wrapContent, height = wrapContent) {
                        topMargin = dip(main_tab_margin_top)
                    }
                }.lparams(width = 0, weight = 1f, height = matchParent) {}
                linearLayout {
                    onClick { setSelectTab(1) }
                    orientation = VERTICAL
                    gravity = Gravity.CENTER
                    idss2[1] = imageView {
                        image = getBitmap(R.mipmap.fenglei2, context)
                    }.lparams(width = dip(main_tab_imgwh), height = dip(main_tab_imgwh))

                    ids1[1] = textView {
                        text = "分类"
                        textColor = resources.getColor(R.color.gray_color)
                        textSize = px2sp(main_tab_tvsize)
                    }.lparams(width = wrapContent, height = wrapContent) {
                        topMargin = dip(main_tab_margin_top)
                    }
                }.lparams(width = 0, weight = 1f, height = matchParent) {}
                linearLayout {
                    onClick { setSelectTab(2) }
                    orientation = VERTICAL
                    gravity = Gravity.CENTER
                    idss2[2]=imageView {
                        image = getBitmap(R.mipmap.shop2, context)
                    }.lparams(width = dip(main_tab_imgwh), height = dip(main_tab_imgwh))

                    ids1[2]=textView {
                        text = "购物车"
                        textColor = resources.getColor(R.color.gray_color)
                        textSize = px2sp(main_tab_tvsize)
                    }.lparams(width = wrapContent, height = wrapContent) {
                        topMargin = dip(main_tab_margin_top)
                    }
                }.lparams(width = 0, weight = 1f, height = matchParent) {}
                linearLayout {
                   onClick { setSelectTab(3) }
                    orientation = VERTICAL
                    gravity = Gravity.CENTER
                    idss2[3]=imageView {
                        id = R.integer.main_tab4_img
                        image = getBitmap(R.mipmap.me2, context)
                    }.lparams(width = dip(main_tab_imgwh), height = dip(main_tab_imgwh))

                    ids1[3]=textView {
                        text = "我的"
                        textColor = resources.getColor(R.color.gray_color)
                        textSize = px2sp(main_tab_tvsize)
                    }.lparams(width = wrapContent, height = wrapContent) {
                        topMargin = dip(main_tab_margin_top)
                    }
                }.lparams(width = 0, weight = 1f, height = matchParent) {}

            }.lparams(width = matchParent, height = dip(main_tab_height)) {
                alignParentBottom()
            }
           
            main_line = view {
                id=0x1001
                backgroundColor = resources.getColor(R.color.line_color)
            }.lparams(width = matchParent, height = dip(main_tab_line_height)) {
                above(main_tab!!)
            }
            main_pager = IndexViewPager(context) {
            }.lparams(width = matchParent, height = matchParent) {
                above(main_line!!)
            }
        }.lparams(width = matchParent, height = matchParent)
    }

    fun setSelectTab(po: Int) {
        for (index in 0..3) {
            if (po == index) {
                ids1[index]!!.setTextColor(Color.parseColor("#70a4f6"))
               idss2[index]!!.setImageDrawable(getBitmap(ids!![index], context))
            } else {
               ids1[index]!!.setTextColor(Color.parseColor("#999999"))
               idss2[index]!!.setImageDrawable(getBitmap(idss!![index], context))
            }
        }
        main_pager!!.setCurrentItem(po, false)
    }
}
