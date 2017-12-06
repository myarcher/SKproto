package com.mini.skproto.base.layout

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import com.mini.skproto.base.CustomAdapter
import com.mini.skproto.base.SActivity
import com.mini.skproto.statusview.MultipleStatusView
import com.mini.skproto.until.getBitmap
import com.mini.skproto.xrecyclerview.ProgressStyle
import com.mini.skproto.xrecyclerview.XRecyclerView
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent

/**
 *
 *
 * @author
 * @date    2017/10/12
 * @version 1.0
 */
class custome_listview : _LinearLayout, XRecyclerView.LoadingListener {

    public var content_view: LinearLayout? = null
    public var list_recycler: XRecyclerView? = null
    public var empt_views: empt_view? = null
    public var status_view: MultipleStatusView? = null
    public var listener: LoadinfoListener? = null
    public var index: Int = 1
    var adapter: CustomAdapter? = null

    constructor(context: Context, funtion: () -> Unit) : super(context) {
        initview()
    }

    init {
        status_view!!.setOnRetryClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                status_view!!.showLoading()//显示正在加载视图
                if (listener != null) {
                    listener!!.loadinfo(index)
                }
            }
        })
      
    }

    fun initview() {
        lparams(width = matchParent, height = matchParent)
        orientation = VERTICAL
        status_view = MultipleStatusView(context) {
            content_view = linearLayout {
                orientation = VERTICAL
                list_recycler = XRecyclerView(context) {
                    overScrollMode = View.OVER_SCROLL_NEVER
                }
                empt_views = empt_view(context)
            }.lparams(width = matchParent, height = matchParent)
        }.lparams(width = matchParent, height = matchParent).sView(error_view(context), loading_view(context), network_view(context), content_view!!)
    }

    fun initEmptView(text: String, draw: Int) {
        empt_views!!.empt_img!!.setImageDrawable(getBitmap(draw, context))
        empt_views!!.empt_tv!!.setText(text)
    }

    fun initXrView(type: Int, count: Int, isTrue: Boolean) {
        list_recycler!!.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)//设置刷新头部的样式
        list_recycler!!.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader)//设置加载更多加载的样式
        list_recycler!!.setLoadingListener(this)//设置加载刷新的接口
        list_recycler!!.layoutManager = getLayoutManager(type, count)
        //设置列表的空视图
        if (isTrue) {
            list_recycler!!.emptyView = empt_views
        }
        adapter = CustomAdapter(context as SActivity, getItemView()!!)
        list_recycler!!.setAdapter(adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
    }

    //默认是竖直排列，显示一行（和listview的排列）
    fun getLayoutManager(type: Int, count: Int): RecyclerView.LayoutManager {
        var layoutManager: RecyclerView.LayoutManager? = null
        if (type == 1) {
            layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
        } else if (type == 2) {
            layoutManager = GridLayoutManager(context, count)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
        } else if (type == 3) {
            layoutManager = GridLayoutManager(context, count)
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        }
        return layoutManager!!
    }


    fun setIsResh(pull: Boolean, load: Boolean) {
        list_recycler!!.setPullRefreshEnabled(pull)
        list_recycler!!.setLoadingMoreEnabled(load)
    }

    override fun onRefresh() {
        index = 1
        if (listener != null) {
            listener!!.loadinfo(index)
        }
    }

    override fun onLoadMore() {
        index++
        if (listener != null) {
            listener!!.loadinfo(index)
        }
    }

    fun setLoadInfoListener(listener: LoadinfoListener?) {
        this.listener = listener
    }

    /**
     * 停止列表的刷新和加载
     */
    fun stopReLoad() {
        if (list_recycler != null) {
            list_recycler!!.loadMoreComplete()
            list_recycler!!.refreshComplete()
        }
    }

    open  fun setData(list: List<Map<String, Any>>) {
        if (index == 1) {//如果是第一页就移除之前的数据
            adapter!!.clear()
        }
        adapter!!.addList(list)
        adapter!!.notifyDataSetChanged()
        showStatus(1)//显示内容视图
    }

   open fun showStatus(type: Int) {
        if (status_view != null) {
            if (type == 1) {
                status_view!!.showContent()//内容视图
            } else if (type == 2) {
                status_view!!.showError()//错误视图
            } else if (type == 3) {
                status_view!!.showNoNetwork()//内容网络视图
            }
        }
    }
    fun getItemView():View?{
        return null
    }
    
    public fun setListValue(data: List<Map<String,Any>>,status:Int){
        if(data!=null){
            setData(data)
        }
        stopReLoad()
        showStatus(status)
    }
}

