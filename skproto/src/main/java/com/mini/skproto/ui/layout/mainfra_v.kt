package com.mini.skproto.ui.layout

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import com.mini.skproto.R
import com.mini.skproto.base.layout.LoadinfoListener
import com.mini.skproto.base.layout.custome_listview
import com.mini.skproto.until.getBitmap
import com.mini.skproto.view.CircleView
import org.jetbrains.anko.*


class mainfra_v : _LinearLayout {
    private var fr_main_left: ImageView? = null
    private var fr_main_ed: EditText? = null
    private var self_mess_lin: FrameLayout? = null
    private var fr_main_right: ImageView? = null
    private var myself_mess_stat: CircleView? = null
    private var fr_main_clicks: ImageView? = null
    private var c_listview: custome_listview? = null
    private var listener: LoadinfoListener? = null
    public var isshow1: Boolean = false
    public var isshow2: Boolean = false

    constructor(context: Context, listener: LoadinfoListener) : super(context) {
        this.listener = listener
        initview()
    }
    
    init {
        c_listview!!.initEmptView("没有数据哦", R.mipmap.fail_goods)
        c_listview!!.initXrView(2, 2, true)
        c_listview!!.setIsResh(false, true)
        c_listview!!.setLoadInfoListener(listener)
    }

    fun setListValue(data: List<Map<String, Any>>?, status: Int) {
        c_listview!!.setListValue(data!!, status)
    }
    
    fun setIsShow1(isshow1:Boolean){
        this.isshow1=isshow1
        showEmpt()
    }
    fun setIsShow2(isshow2:Boolean){
        this.isshow2=isshow2
        showEmpt()
    }

    fun showEmpt() {
        if (isshow1 || isshow2) {
            c_listview!!.empt_views!!.setVisibility(View.GONE)
            c_listview!!.list_recycler!!.setVisibility(View.VISIBLE)
        } else {
            c_listview!!.empt_views!!.setVisibility(View.VISIBLE)
            c_listview!!.list_recycler!!.setVisibility(View.GONE)
        }
    }

    private fun initview() {
        lparams(width = matchParent, height = matchParent)
        backgroundColor = resources.getColor(R.color.main_color)
        orientation = VERTICAL
        frameLayout {
            linearLayout {
                orientation = VERTICAL
                relativeLayout {
                    backgroundResource = R.mipmap.icon_daohang
                    fr_main_left = imageView {
                        image = getBitmap(R.mipmap.white_nannv, context)
                        visibility = View.GONE
                    }.lparams(width = dip(30), height = dip(30)) {
                        centerVertically()
                        leftMargin = dip(15)
                    }
                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL
                        isClickable = true
                        isFocusable = true
                        backgroundResource = R.drawable.main_title_ed_bg
                        imageView {
                            image = getBitmap(R.mipmap.icon_suosou, context)
                        }.lparams(width = dip(18), height = dip(18))
                        fr_main_ed = editText {
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER_VERTICAL
                            hint = "生鲜水产"
                            textColor = Color.parseColor("#4a4a4a")
                            hintTextColor = Color.parseColor("#989898")
                            textSize = px2sp(16)
                        }.lparams(width = matchParent, height = matchParent) {
                            leftMargin = dip(15)
                        }
                    }.lparams(width = dip(400), height = dip(35)) {
                        leftMargin = dip(15)
                        leftPadding = dip(10)
                        rightPadding = dip(10)
                        centerVertically()
                    }
                    self_mess_lin = frameLayout {
                        fr_main_right = imageView {
                            image = getBitmap(R.mipmap.icon_xinxi, context)
                        }.lparams(width = dip(30), height = dip(30)) {
                            leftMargin = dip(5)
                            topMargin = dip(5)
                        }
                        myself_mess_stat = CircleView(context) {
                            visibility = View.INVISIBLE
                        }.lparams(width = dip(8), height = dip(8)) {
                            gravity = Gravity.RIGHT
                        }
                    }.lparams(width = dip(35), height = dip(35)) {
                        rightMargin = dip(15)
                        centerVertically()
                        alignParentRight()
                    }
                }.lparams(width = matchParent, height = dip(65))
                linearLayout {
                    orientation = VERTICAL
                    c_listview = custome_listview(context) {}
                }.lparams(width = matchParent, height = matchParent)
            }.lparams(width = matchParent, height = matchParent)
            relativeLayout {
                backgroundColor = Color.TRANSPARENT
                fr_main_clicks = imageView {
                    image = getBitmap(R.mipmap.compelete_receive, context)
                }.lparams(width = dip(77), height = dip(77)) {
                    alignParentRight()
                    alignParentBottom()
                    rightMargin = dip(30)
                    bottomMargin = dip(60)
                }
            }.lparams(width = matchParent, height = matchParent)
        }.lparams(width = matchParent, height = matchParent)
    }

    fun setHead(banner: List<Map<String, Any>>, head1: List<Map<String, Any>>, head2: List<Map<String, Any>>, head3: List<Map<String, Any>>) {}


}

