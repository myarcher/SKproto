package com.mini.skproto.ui.vo

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.mini.skproto.base.SActivity
import com.mini.skproto.base.SFragment
import com.mini.skproto.constance.BUrlContense
import com.mini.skproto.ui.layout.mainfra_v
import java.util.ArrayList

/**
 *
 *
 * @author
 * @date    2017/10/12
 * @version 1.0
 */
/*class MainFourFra : SFragment {
    private var mainfra_vs: mainfra_v? = null
    private var mainUi: MainUi? = null
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mActivity = activity as SActivity
        this.mainUi = activity as MainUi
    }

    constructor () : super() {
    }

    constructor (mainUi: MainUi) : super(mainUi) {
        this.mainUi = mainUi
    }

    override fun getViews(): View? {
        mainfra_vs = mainfra_v(mainUi!!, this)
        return mainfra_vs
    }

   override fun initData(savedInstanceState: Bundle?) {
      super.initData(savedInstanceState)
    }

    override fun loadinfo(index: Int) {
        getMainFourlist(index)
    }

    override fun loadData() {
        getMainFourInfo()
        getMainFourlist(1)
    }

    fun getMainFourInfo() {
        Http(1, get().getMainFourInfo(BUrlContense.APP_ID), false)
    }

    fun getMainFourlist(index: Int) {
        Http(2, get().getMainFourlist(BUrlContense.APP_ID, "$index"), false)
    }


    override fun onSuccess(indexs: Int, mess: String, data: Any) {
        if (indexs == 1) {
            setGoods(data)
        } else if (indexs == 2) {
            setGoodsList(data)
        }
    }

    private fun setGoodsList(dataS: Any) {
        var goodsList: List<Map<String, Any>> = ArrayList()
        try {
            goodsList = dataS as List<Map<String, Any>>
        } catch (e: Exception) {
        }
        mainfra_vs!!.setListValue(goodsList, 1)
        if (goodsList!!.size > 0) {
            mainfra_vs!!.setIsShow2(true)
        } else {
            mainfra_vs!!.setIsShow2(false)
        }
    }

    private fun setGoods(data: Any?) {
        if (data != null) {
            val info = data as Map<String, Any>?
            var banner: List<Map<String, Any>> = ArrayList()
            var head1: List<Map<String, Any>> = ArrayList()
            var head2: List<Map<String, Any>> = ArrayList()
            var head3: List<Map<String, Any>> = ArrayList()
            try {
                banner = info!!["banner"] as List<Map<String, Any>>
            } catch (e: Exception) {
            }
            try {
                head1 = info!!["contact"] as List<Map<String, Any>>
            } catch (e: Exception) {
            }
            try {
                val map2 = info!!["first_module"] as Map<String, Any>
                head2 = map2["data_list"] as List<Map<String, Any>>
            } catch (e: Exception) {
            }
            try {
                val map3 = info!!["second_module"] as Map<String, Any>
                head3 = map3["data_list"] as List<Map<String, Any>>

            } catch (e: Exception) {
            }

            mainfra_vs!!.setHead(banner, head1, head2, head3)
            if (banner.size > 0 || head1.size > 0 || head2.size > 0 || head3.size > 0) {
                mainfra_vs!!.setIsShow1(true)
            } else {
                mainfra_vs!!.setIsShow1(false)
            }
            mainfra_vs!!.setListValue(null, 1)
        }
    }

    override fun onFails(indexs: Int, t: Exception, isTrue: Boolean) {
        if (indexs == 1) {
            mainfra_vs!!.setIsShow2(false)
            mainfra_vs!!.setListValue(null, 1)
        } else if (indexs == 2) {
            mainfra_vs!!.setIsShow1(false)
            mainfra_vs!!.setListValue(null, 1)
        } else {
            super.onFails(indexs, t, isTrue)
        }

    }

}*/