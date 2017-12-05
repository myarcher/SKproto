package com.mini.skproto.xrecyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import java.util.ArrayList

class XRecyclerView @JvmOverloads constructor(context:Context,funtion:()->Unit) :RecyclerView(context) {
    private var isLoadingData = false
    private var isNoMore = false
    private var mRefreshProgressStyle = ProgressStyle.SysProgress
    private var mLoadingMoreProgressStyle = ProgressStyle.SysProgress
    private val mHeaderViews = ArrayList<View>()
    private var mWrapAdapter: WrapAdapter? = null
    private var mLastY = -1f
    private var mLoadingListener: LoadingListener? = null
    private var mRefreshHeader: ArrowRefreshHeader? = null
    private var pullRefreshEnabled = true
    private var loadingMoreEnabled = true
    private val mPageCount = 0
    //adapter没有数据的时候显示,类似于listView的emptyView
    var emptyView: View? = null
        set(emptyView) {
            field = emptyView
            mDataObserver.onChanged()
        }
    private var mFootView: View? = null
    private val mDataObserver = DataObserver()
    private var appbarState: AppBarStateChangeListener.State = AppBarStateChangeListener.State.EXPANDED

    private val isOnTop: Boolean
        get() = if (mRefreshHeader!!.parent != null) {
            true
        } else {
            false
        }

    /** add by LinGuanHong below  */
    private var scrollDyCounter = 0

    private var scrollAlphaChangeListener: ScrollAlphaChangeListener? = null

    init {
        init()
    }

    private fun init() {
        if (pullRefreshEnabled) {
            mRefreshHeader = ArrowRefreshHeader(context)
            mRefreshHeader!!.setProgressStyle(mRefreshProgressStyle)
        }
        val footView = LoadingMoreFooter(context)
        footView.setProgressStyle(mLoadingMoreProgressStyle)
        mFootView = footView
        mFootView!!.visibility = View.GONE
    }

    fun setFootViewText(loading: String, noMore: String) {
        if (mFootView is LoadingMoreFooter) {
            (mFootView as LoadingMoreFooter).setLoadingHint(loading)
            (mFootView as LoadingMoreFooter).setNoMoreHint(noMore)
        }
    }

    fun addHeaderView(view: View) {
        sHeaderTypes.add(HEADER_INIT_INDEX + mHeaderViews.size)
        mHeaderViews.add(view)
        if (mWrapAdapter != null) {
            mWrapAdapter!!.notifyDataSetChanged()
        }
    }

    //根据header的ViewType判断是哪个header
    private fun getHeaderViewByType(itemType: Int): View? {
        return if (!isHeaderType(itemType)) {
            null
        } else mHeaderViews[itemType - HEADER_INIT_INDEX]
    }

    //判断一个type是否为HeaderType
    private fun isHeaderType(itemViewType: Int): Boolean {
        return mHeaderViews.size > 0 && sHeaderTypes.contains(itemViewType)
    }

    //判断是否是XRecyclerView保留的itemViewType
    private fun isReservedItemViewType(itemViewType: Int): Boolean {
        return if (itemViewType == TYPE_REFRESH_HEADER || itemViewType == TYPE_FOOTER || sHeaderTypes.contains(itemViewType)) {
            true
        } else {
            false
        }
    }

    fun setFootView(view: View) {
        mFootView = view
    }

    fun loadMoreComplete() {
        isLoadingData = false
        if (mFootView is LoadingMoreFooter) {
            (mFootView as LoadingMoreFooter).setState(LoadingMoreFooter.STATE_COMPLETE)
        } else {
            mFootView!!.visibility = View.GONE
        }
    }

    fun setNoMore(noMore: Boolean) {
        isLoadingData = false
        isNoMore = noMore
        if (mFootView is LoadingMoreFooter) {
            (mFootView as LoadingMoreFooter).setState(if (isNoMore) LoadingMoreFooter.STATE_NOMORE else LoadingMoreFooter.STATE_COMPLETE)
        } else {
            mFootView!!.visibility = View.GONE
        }
    }

    fun refresh() {
        if (pullRefreshEnabled && mLoadingListener != null) {
            mRefreshHeader!!.state = BaseRefreshHeader.STATE_REFRESHING
            mLoadingListener!!.onRefresh()
        }
    }

    fun reset() {
        setNoMore(false)
        loadMoreComplete()
        refreshComplete()
    }

    fun refreshComplete() {
        mRefreshHeader!!.refreshComplete()
        setNoMore(false)
    }

    fun setRefreshHeader(refreshHeader: ArrowRefreshHeader) {
        mRefreshHeader = refreshHeader
    }

    fun setPullRefreshEnabled(enabled: Boolean) {
        pullRefreshEnabled = enabled
    }

    fun setLoadingMoreEnabled(enabled: Boolean) {
        loadingMoreEnabled = enabled
        if (!enabled) {
            if (mFootView is LoadingMoreFooter) {
                (mFootView as LoadingMoreFooter).setState(LoadingMoreFooter.STATE_COMPLETE)
            }
        }
    }

    fun setRefreshProgressStyle(style: Int) {
        mRefreshProgressStyle = style
        if (mRefreshHeader != null) {
            mRefreshHeader!!.setProgressStyle(style)
        }
    }

    fun setLoadingMoreProgressStyle(style: Int) {
        mLoadingMoreProgressStyle = style
        if (mFootView is LoadingMoreFooter) {
            (mFootView as LoadingMoreFooter).setProgressStyle(style)
        }
    }

    fun setArrowImageView(resId: Int) {
        if (mRefreshHeader != null) {
            mRefreshHeader!!.setArrowImageView(resId)
        }
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<ViewHolder>) {
        mWrapAdapter = WrapAdapter(adapter)
        super.setAdapter(mWrapAdapter)
        adapter.registerAdapterDataObserver(mDataObserver)
        mDataObserver.onChanged()
    }

    //避免用户自己调用getAdapter() 引起的ClassCastException
    override fun getAdapter(): RecyclerView.Adapter<*>? {
        return if (mWrapAdapter != null)
            mWrapAdapter!!.originalAdapter
        else
            null
    }

    override fun setLayoutManager(layout: RecyclerView.LayoutManager) {
        super.setLayoutManager(layout)
        if (mWrapAdapter != null) {
            if (layout is GridLayoutManager) {
                layout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (mWrapAdapter!!.isHeader(position) || mWrapAdapter!!.isFooter(position) || mWrapAdapter!!.isRefreshHeader(position))
                            layout.spanCount
                        else
                            1
                    }
                }

            }
        }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadingListener != null && !isLoadingData && loadingMoreEnabled) {
            val layoutManager = layoutManager
            val lastVisibleItemPosition: Int
            if (layoutManager is GridLayoutManager) {
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            } else if (layoutManager is StaggeredGridLayoutManager) {
                val into = IntArray(layoutManager.spanCount)
                layoutManager.findLastVisibleItemPositions(into)
                lastVisibleItemPosition = findMax(into)
            } else {
                lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }
            Log.i("json", "count1>>" + layoutManager.itemCount + "count2>>" + layoutManager.childCount)
            if (layoutManager.childCount > 0
                    && lastVisibleItemPosition >= layoutManager.itemCount - 1 && layoutManager.itemCount >= layoutManager.childCount && !isNoMore && mRefreshHeader!!.state < BaseRefreshHeader.STATE_REFRESHING) {
                isLoadingData = true
                if (mFootView is LoadingMoreFooter) {
                    (mFootView as LoadingMoreFooter).setState(LoadingMoreFooter.STATE_LOADING)
                } else {
                    mFootView!!.visibility = View.VISIBLE
                }
                mLoadingListener!!.onLoadMore()
            }
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (mLastY == -1f) {
            mLastY = ev.rawY
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> mLastY = ev.rawY
            MotionEvent.ACTION_MOVE -> {
                val deltaY = ev.rawY - mLastY
                mLastY = ev.rawY
                if (isOnTop && pullRefreshEnabled && appbarState == AppBarStateChangeListener.State.EXPANDED) {
                    mRefreshHeader!!.onMove(deltaY / DRAG_RATE)
                    if (mRefreshHeader!!.visibleHeight > 0 && mRefreshHeader!!.state < BaseRefreshHeader.STATE_REFRESHING) {
                        return false
                    }
                }
            }
            else -> {
                mLastY = -1f // reset
                if (isOnTop && pullRefreshEnabled && appbarState == AppBarStateChangeListener.State.EXPANDED) {
                    if (mRefreshHeader!!.releaseAction()) {
                        if (mLoadingListener != null) {
                            mLoadingListener!!.onRefresh()
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

    private inner class DataObserver : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            if (mWrapAdapter != null) {
                mWrapAdapter!!.notifyDataSetChanged()
            }
            if (mWrapAdapter != null && emptyView != null) {
                var emptyCount = 1 + mWrapAdapter!!.headersCount
                if (loadingMoreEnabled) {
                    emptyCount++
                }
                if (mWrapAdapter!!.itemCount == emptyCount) {
                    emptyView!!.visibility = View.VISIBLE
                    this@XRecyclerView.visibility = View.GONE
                } else {

                    emptyView!!.visibility = View.GONE
                    this@XRecyclerView.visibility = View.VISIBLE
                }
            }
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            mWrapAdapter!!.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            mWrapAdapter!!.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            mWrapAdapter!!.notifyItemRangeChanged(positionStart, itemCount, payload)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            mWrapAdapter!!.notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            mWrapAdapter!!.notifyItemMoved(fromPosition, toPosition)
        }
    }

    private inner class WrapAdapter(val originalAdapter: RecyclerView.Adapter<ViewHolder>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val headersCount: Int
            get() = mHeaderViews.size

        fun isHeader(position: Int): Boolean {
            return position >= 1 && position < mHeaderViews.size + 1
        }

        fun isFooter(position: Int): Boolean {
            return if (loadingMoreEnabled) {
                position == itemCount - 1
            } else {
                false
            }
        }

        fun isRefreshHeader(position: Int): Boolean {
            return position == 0
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == TYPE_REFRESH_HEADER) {
                return SimpleViewHolder(mRefreshHeader!!)
            } else if (isHeaderType(viewType)) {
                return SimpleViewHolder(getHeaderViewByType(viewType)!!)
            } else if (viewType == TYPE_FOOTER) {
                return SimpleViewHolder(mFootView!!)
            }
            return originalAdapter!!.onCreateViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (isHeader(position) || isRefreshHeader(position)) {
                return
            }
            val adjPosition = position - (headersCount + 1)
            val adapterCount: Int
            if (originalAdapter != null) {
                adapterCount = originalAdapter.itemCount
                if (adjPosition < adapterCount) {
                    originalAdapter.onBindViewHolder(holder, adjPosition)
                }
            }
        }

        // some times we need to override this
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>?) {
            if (isHeader(position) || isRefreshHeader(position)) {
                return
            }
            val adjPosition = position - (headersCount + 1)
            val adapterCount: Int
            if (originalAdapter != null) {
                adapterCount = originalAdapter.itemCount
                if (adjPosition < adapterCount) {
                    if (payloads!!.isEmpty()) {
                        originalAdapter.onBindViewHolder(holder!!, adjPosition)
                    } else {
                        originalAdapter.onBindViewHolder(holder!!, adjPosition, payloads)
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            return if (loadingMoreEnabled) {
                if (originalAdapter != null) {
                    headersCount + originalAdapter.itemCount + 2
                } else {
                    headersCount + 2
                }
            } else {
                if (originalAdapter != null) {
                    headersCount + originalAdapter.itemCount + 1
                } else {
                    headersCount + 1
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            var position = position
            val adjPosition = position - (headersCount + 1)
            if (isRefreshHeader(position)) {
                return TYPE_REFRESH_HEADER
            }
            if (isHeader(position)) {
                position = position - 1
                return sHeaderTypes[position]
            }
            if (isFooter(position)) {
                return TYPE_FOOTER
            }
            val adapterCount: Int
            if (originalAdapter != null) {
                adapterCount = originalAdapter.itemCount
                if (adjPosition < adapterCount) {
                    val type = originalAdapter.getItemViewType(adjPosition)
                    if (isReservedItemViewType(type)) {
                        throw IllegalStateException("XRecyclerView require itemViewType in adapter should be less than 10000 ")
                    }
                    return type
                }
            }
            return 0
        }

        override fun getItemId(position: Int): Long {
            if (originalAdapter != null && position >= headersCount + 1) {
                val adjPosition = position - (headersCount + 1)
                if (adjPosition < originalAdapter.itemCount) {
                    return originalAdapter.getItemId(adjPosition)
                }
            }
            return -1
        }

        override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
            super.onAttachedToRecyclerView(recyclerView)
            val manager = recyclerView!!.layoutManager
            if (manager is GridLayoutManager) {
                manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (isHeader(position) || isFooter(position) || isRefreshHeader(position))
                            manager.spanCount
                        else
                            1
                    }
                }
            }
            originalAdapter!!.onAttachedToRecyclerView(recyclerView)
        }

        override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
            originalAdapter!!.onDetachedFromRecyclerView(recyclerView)
        }

        override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder?) {
            super.onViewAttachedToWindow(holder)
            val lp = holder!!.itemView.layoutParams
            if (lp != null
                    && lp is StaggeredGridLayoutManager.LayoutParams
                    && (isHeader(holder.layoutPosition) || isRefreshHeader(holder.layoutPosition) || isFooter(holder.layoutPosition))) {
                lp.isFullSpan = true
            }
            originalAdapter!!.onViewAttachedToWindow(holder!!)
        }

        override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder?) {
            originalAdapter!!.onViewDetachedFromWindow(holder)
        }

        override fun onViewRecycled(holder: RecyclerView.ViewHolder?) {
            originalAdapter!!.onViewRecycled(holder)
        }

        override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder?): Boolean {
            return originalAdapter!!.onFailedToRecycleView(holder)
        }

        override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
            originalAdapter!!.unregisterAdapterDataObserver(observer)
        }

        override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
            originalAdapter!!.registerAdapterDataObserver(observer)
        }

        private inner class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }

    fun setLoadingListener(listener: LoadingListener) {
        mLoadingListener = listener
    }

    interface LoadingListener {

        fun onRefresh()

        fun onLoadMore()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //解决和CollapsingToolbarLayout冲突的问题
        var appBarLayout: AppBarLayout? = null
        var p: ViewParent? = parent
        while (p != null) {
            if (p is CoordinatorLayout) {
                break
            }
            p = p.parent
        }
        if (p is CoordinatorLayout) {
            val coordinatorLayout = p as CoordinatorLayout?
            val childCount = coordinatorLayout!!.getChildCount()
            for (i in childCount - 1 downTo 0) {
                val child = coordinatorLayout!!.getChildAt(i)
                if (child is AppBarLayout) {
                    appBarLayout = child as AppBarLayout
                    break
                }
            }
            if (appBarLayout != null) {
                appBarLayout!!.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
                    override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarStateChangeListener.State) {
                        appbarState = state
                    }
                })
            }
        }
    }

    inner class DividerItemDecoration
    /**
     * Sole constructor. Takes in a [Drawable] to be used as the interior
     * divider.
     *
     * @param divider A divider `Drawable` to be drawn on the RecyclerView
     */
    (private val mDivider: Drawable) : RecyclerView.ItemDecoration() {
        private var mOrientation: Int = 0

        /**
         * Draws horizontal or vertical dividers onto the parent RecyclerView.
         *
         * @param canvas The [Canvas] onto which dividers will be drawn
         * @param parent The RecyclerView onto which dividers are being added
         * @param state The current RecyclerView.State of the RecyclerView
         */
        override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
            if (mOrientation == LinearLayoutManager.HORIZONTAL) {
                drawHorizontalDividers(canvas, parent)
            } else if (mOrientation == LinearLayoutManager.VERTICAL) {
                drawVerticalDividers(canvas, parent)
            }
        }

        /**
         * Determines the size and location of offsets between items in the parent
         * RecyclerView.
         *
         * @param outRect The [Rect] of offsets to be added around the child
         * view
         * @param view The child view to be decorated with an offset
         * @param parent The RecyclerView onto which dividers are being added
         * @param state The current RecyclerView.State of the RecyclerView
         */
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
            super.getItemOffsets(outRect, view, parent, state)

            if (parent.getChildAdapterPosition(view) <= mWrapAdapter!!.headersCount + 1) {
                return
            }
            mOrientation = (parent.layoutManager as LinearLayoutManager).orientation
            if (mOrientation == LinearLayoutManager.HORIZONTAL) {
                outRect.left = mDivider.intrinsicWidth
            } else if (mOrientation == LinearLayoutManager.VERTICAL) {
                outRect.top = mDivider.intrinsicHeight
            }
        }

        /**
         * Adds dividers to a RecyclerView with a LinearLayoutManager or its
         * subclass oriented horizontally.
         *
         * @param canvas The [Canvas] onto which horizontal dividers will be
         * drawn
         * @param parent The RecyclerView onto which horizontal dividers are being
         * added
         */
        private fun drawHorizontalDividers(canvas: Canvas, parent: RecyclerView) {
            val parentTop = parent.paddingTop
            val parentBottom = parent.height - parent.paddingBottom

            val childCount = parent.childCount
            for (i in 0 until childCount - 1) {
                val child = parent.getChildAt(i)

                val params = child.layoutParams as RecyclerView.LayoutParams

                val parentLeft = child.right + params.rightMargin
                val parentRight = parentLeft + mDivider.intrinsicWidth

                mDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom)
                mDivider.draw(canvas)
            }
        }

        /**
         * Adds dividers to a RecyclerView with a LinearLayoutManager or its
         * subclass oriented vertically.
         *
         * @param canvas The [Canvas] onto which vertical dividers will be
         * drawn
         * @param parent The RecyclerView onto which vertical dividers are being
         * added
         */
        private fun drawVerticalDividers(canvas: Canvas, parent: RecyclerView) {
            val parentLeft = parent.paddingLeft
            val parentRight = parent.width - parent.paddingRight

            val childCount = parent.childCount
            for (i in 0 until childCount - 1) {
                val child = parent.getChildAt(i)

                val params = child.layoutParams as RecyclerView.LayoutParams

                val parentTop = child.bottom + params.bottomMargin
                val parentBottom = parentTop + mDivider.intrinsicHeight

                mDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom)
                mDivider.draw(canvas)
            }
        }
    }

    override fun scrollToPosition(position: Int) {
        super.scrollToPosition(position)
        /** if we scroll to position 0, the scrollDyCounter should be reset  */
        if (position == 0) {
            scrollDyCounter = 0
        }
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        if (scrollAlphaChangeListener == null) {
            return
        }
        val height = scrollAlphaChangeListener!!.setLimitHeight()
        scrollDyCounter = scrollDyCounter + dy
        if (scrollDyCounter <= 0) {
            scrollAlphaChangeListener!!.onAlphaChange(0)
        } else if (scrollDyCounter <= height && scrollDyCounter > 0) {
            val scale = scrollDyCounter.toFloat() / height
            /** 255/height = x/255  */
            val alpha = 255 * scale
            scrollAlphaChangeListener!!.onAlphaChange(alpha.toInt())
        } else {
            scrollAlphaChangeListener!!.onAlphaChange(255)
        }
    }

    fun setScrollAlphaChangeListener(
            scrollAlphaChangeListener: ScrollAlphaChangeListener
    ) {
        this.scrollAlphaChangeListener = scrollAlphaChangeListener
    }

    interface ScrollAlphaChangeListener {
        fun onAlphaChange(alpha: Int)
        /** you can handle the alpha insert it  */
        fun setLimitHeight(): Int
        /** set a height for the begging of the alpha start to change  */
    }

    companion object {
        private val DRAG_RATE = 3f
        //下面的ItemViewType是保留值(ReservedItemViewType),如果用户的adapter与它们重复将会强制抛出异常。不过为了简化,我们检测到重复时对用户的提示是ItemViewType必须小于10000
        private val TYPE_REFRESH_HEADER = 10000//设置一个很大的数字,尽可能避免和用户的adapter冲突
        private val TYPE_FOOTER = 10001
        private val HEADER_INIT_INDEX = 10002
        private val sHeaderTypes = ArrayList<Int>()//每个header必须有不同的type,不然滚动的时候顺序会变化
    }
}